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

    @Override
    @Transactional
    public void addTalent(Talent talent) {
        // --- 第一步：准备账号信息 ---
        User user = new User();

        // 1. 转拼音 (例如 "张三" -> "zhangsan")
        String baseUsername = toPinyin(talent.getName());

        // 2. ⭐ 智能防重名逻辑
        // 如果数据库里已经有了 "zhangsan"，就自动改成 "zhangsan1", "zhangsan_2" 等
        String finalUsername = baseUsername;
        int count = 1;

        while (userMapper.findByUsername(finalUsername) != null) {
            finalUsername = baseUsername + count;
            count++;
        }

        // 3. 填入账号信息
        user.setUsername(finalUsername);
        user.setPassword("123456"); // 默认密码
        user.setName(talent.getName());
        user.setRole(talent.getRole());
        user.setAvatar("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");

        // --- 第二步：插入账号表 ---
        userMapper.insert(user);
        Long newUserId = user.getId();

        // 检查 ID 是否获取成功
        if (newUserId == null) {
            throw new RuntimeException("严重错误：账号创建失败，数据库未返回 ID");
        }

        // --- 第三步：关联并插入人才表 ---
        if (talent.getCsScore() == null) talent.setCsScore(0);
        if (talent.getMedScore() == null) talent.setMedScore(0);

        // ⭐ 关键：把 UserID 绑定给人才
        talent.setUserId(newUserId);

        talentMapper.add(talent);

        System.out.println("✅ 账号创建成功: " + finalUsername + " (ID: " + newUserId + ")");
    }

    @Override
    public void deleteTalent(Long id) {
        talentMapper.deleteById(id);
    }

    @Override
    public Talent getTalentByUserId(Long userId) {
        return talentMapper.selectByUserId(userId);
    }

    // 工具方法：汉字转拼音
    private String toPinyin(String chinese) {
        if (chinese == null) return "user";
        StringBuilder pinyinStr = new StringBuilder();
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : newChar) {
            if (c > 128) {
                try {
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
                    if (strs != null && strs.length > 0) pinyinStr.append(strs[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) { e.printStackTrace(); }
            } else {
                pinyinStr.append(c);
            }
        }
        return pinyinStr.toString().replaceAll("\\s+", "");
    }
}