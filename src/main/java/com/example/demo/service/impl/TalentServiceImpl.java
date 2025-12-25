package com.example.demo.service.impl;

import cn.hutool.crypto.digest.BCrypt; // ⭐ 引入加密工具
import com.example.demo.entity.Talent;
import com.example.demo.entity.User;
import com.example.demo.mapper.TalentMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.TalentService;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TalentServiceImpl implements TalentService {

    @Autowired
    private TalentMapper talentMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Talent> findAll() {
        return talentMapper.findAll();
    }

    /**
     * 新增人才并自动创建关联账号
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addTalent(Talent talent) {
        // 1. 准备账号信息
        User user = new User();

        // 生成基础拼音账号
        String baseUsername = toPinyin(talent.getName());

        // 防重名逻辑
        String finalUsername = baseUsername;
        int count = 1;
        // ⭐ 修复点：这里原来是 findByUsername，一定要改成 selectByUsername
        while (userMapper.selectByUsername(finalUsername) != null) {
            finalUsername = baseUsername + count;
            count++;
        }

        user.setUsername(finalUsername);

        // ⭐ 升级点：创建账号时也进行加密，保持统一
        String encodedPwd = BCrypt.hashpw("123456");
        user.setPassword(encodedPwd);

        user.setName(talent.getName());
        user.setRole(talent.getRole());
        user.setAvatar("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");

        // 2. 插入账号
        userMapper.insert(user);
        Long newUserId = user.getId();

        if (newUserId == null) {
            throw new RuntimeException("账号创建失败：无法获取生成的 User ID");
        }

        // 3. 插入人才档案
        if (talent.getCsScore() == null) talent.setCsScore(0);
        if (talent.getMedScore() == null) talent.setMedScore(0);

        talent.setUserId(newUserId);

        talentMapper.add(talent);

        System.out.println("✅ 创建成功：账号=" + finalUsername + ", 档案ID=" + talent.getId());
    }

    @Override
    public void deleteTalent(Long id) {
        talentMapper.deleteById(id);
    }

    @Override
    public Talent getTalentByUserId(Long userId) {
        return talentMapper.selectByUserId(userId);
    }

    /**
     * 汉字转拼音工具
     */
    private String toPinyin(String chinese) {
        if (chinese == null || chinese.trim().isEmpty()) return "user";

        StringBuilder pinyinStr = new StringBuilder();
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        for (char c : newChar) {
            if (c > 128) {
                try {
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
                    if (strs != null && strs.length > 0) {
                        pinyinStr.append(strs[0]);
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinStr.append(c);
            }
        }
        return pinyinStr.toString().replaceAll("\\s+", "");
    }
}