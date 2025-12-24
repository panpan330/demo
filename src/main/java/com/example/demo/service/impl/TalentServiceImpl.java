package com.example.demo.service.impl;

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
    @Transactional(rollbackFor = Exception.class) // 建议加上 rollbackFor，遇到任何异常都回滚
    public void addTalent(Talent talent) {
        // ---------------------------------------------------------
        // 1. 准备账号信息 (User)
        // ---------------------------------------------------------
        User user = new User();

        // 生成基础拼音账号 (如: 张三 -> zhangsan)
        String baseUsername = toPinyin(talent.getName());

        // 防重名逻辑：如果 zhangsan 已存在，则尝试 zhangsan1, zhangsan2...
        String finalUsername = baseUsername;
        int count = 1;
        while (userMapper.findByUsername(finalUsername) != null) {
            finalUsername = baseUsername + count;
            count++;
        }

        user.setUsername(finalUsername);
        user.setPassword("123456"); // 默认密码
        user.setName(talent.getName());
        user.setRole(talent.getRole());
        user.setAvatar("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"); // 默认头像

        // ---------------------------------------------------------
        // 2. 插入账号并获取 ID
        // ---------------------------------------------------------
        userMapper.insert(user);
        Long newUserId = user.getId();

        // 双重检查：确保 ID 真的生成了
        if (newUserId == null) {
            throw new RuntimeException("账号创建失败：无法获取生成的 User ID");
        }

        // ---------------------------------------------------------
        // 3. 插入人才档案 (Talent)
        // ---------------------------------------------------------
        if (talent.getCsScore() == null) talent.setCsScore(0);
        if (talent.getMedScore() == null) talent.setMedScore(0);

        // ⭐ 关键：绑定账号 ID
        talent.setUserId(newUserId);

        talentMapper.add(talent);

        // 简单日志，确认创建成功
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
     * 工具方法：汉字转拼音 (小写、无声调)
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