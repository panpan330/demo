package com.example.demo.service.impl;

import cn.hutool.crypto.digest.BCrypt;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Transactional(rollbackFor = Exception.class)
    public void addTalent(Talent talent) {
        // ⭐ 1. 计算坐标
        injectCoordinate(talent);

        // 2. 创建账号逻辑 (保持不变)
        User user = new User();
        String baseUsername = toPinyin(talent.getName());
        String finalUsername = baseUsername;
        int count = 1;
        while (userMapper.selectByUsername(finalUsername) != null) {
            finalUsername = baseUsername + count;
            count++;
        }
        user.setUsername(finalUsername);
        user.setPassword(BCrypt.hashpw("123456"));
        user.setName(talent.getName());
        user.setRole(talent.getRole());
        user.setAvatar("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");
        userMapper.insert(user);

        talent.setUserId(user.getId());

        // ⭐ 3. 保存人才 (这里会把坐标写入数据库)
        talentMapper.add(talent);

        System.out.println("✅ 新增成功：坐标 [" + talent.getLng() + ", " + talent.getLat() + "]");
    }

    @Override
    public void updateTalent(Talent talent) {
        // ⭐ 更新时也要重新计算
        injectCoordinate(talent);
        talentMapper.update(talent);
        System.out.println("✅ 更新成功：坐标 [" + talent.getLng() + ", " + talent.getLat() + "]");
    }

    @Override
    public void deleteTalent(Long id) {
        talentMapper.deleteById(id);
    }

    @Override
    public Talent getTalentByUserId(Long userId) {
        return talentMapper.selectByUserId(userId);
    }

    // ⭐⭐ 核心坐标计算方法 (带日志) ⭐⭐
    private void injectCoordinate(Talent talent) {
        String addr = talent.getAddress();
        System.out.println("🔍 正在为地址 [" + addr + "] 计算坐标...");

        if (addr == null || addr.trim().isEmpty()) {
            System.out.println("❌ 地址为空，跳过计算");
            return;
        }

        Map<String, double[]> cityMap = new HashMap<>();
        cityMap.put("北京", new double[]{116.407526, 39.90403});
        cityMap.put("上海", new double[]{121.473701, 31.230416});
        cityMap.put("广州", new double[]{113.264434, 23.129162});
        cityMap.put("深圳", new double[]{114.057868, 22.543099});
        cityMap.put("杭州", new double[]{120.15507, 30.274084});
        cityMap.put("成都", new double[]{104.066541, 30.572269});
        cityMap.put("武汉", new double[]{114.305393, 30.593099});
        cityMap.put("西安", new double[]{108.93977, 34.341574});
        cityMap.put("南京", new double[]{118.796877, 32.060255});
        cityMap.put("重庆", new double[]{106.551556, 29.563009});
        // 你可以按需加更多城市...

        boolean found = false;
        for (String city : cityMap.keySet()) {
            if (addr.contains(city)) {
                double[] coord = cityMap.get(city);
                // 加点随机偏移，防止重叠
                double randomLat = (Math.random() - 0.5) * 0.05;
                double randomLng = (Math.random() - 0.5) * 0.05;

                talent.setLng(coord[0] + randomLng);
                talent.setLat(coord[1] + randomLat);
                found = true;
                System.out.println("🎯 命中城市 [" + city + "] -> 坐标生成完毕");
                break;
            }
        }

        if (!found) {
            System.out.println("⚠️ 未匹配到城市，坐标将为空 (请在地址中包含 '北京/上海' 等城市名)");
        }
    }

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
                    if (strs != null && strs.length > 0) pinyinStr.append(strs[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) { e.printStackTrace(); }
            } else { pinyinStr.append(c); }
        }
        return pinyinStr.toString().replaceAll("\\s+", "");
    }
}