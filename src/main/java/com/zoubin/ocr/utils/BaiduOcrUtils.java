package com.zoubin.ocr.utils;

import com.baidu.aip.ocr.AipOcr;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;

public class BaiduOcrUtils {
    //设置APPID/AK/SK
    public static final String APP_ID = "46400901";
    public static final String API_KEY = "3aERo1etOvx1dTWi2arUuKET";
    public static final String SECRET_KEY = "46mak4w7xt9aQ1ymT9xOfv28ztyvKa9O";

    public static String sample(File file) {

        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");

        options = new HashMap<>();

        JSONObject res = new JSONObject();
        StringBuilder stringBuilder = new StringBuilder();

        try {
            InputStream inputStream = Files.newInputStream(file.toPath());
            byte[] fileData = FileCopyUtils.copyToByteArray(inputStream);
            res = client.webImage(fileData, options);
            JSONArray wordsResult = res.getJSONArray("words_result");
            Boolean isStart = false;
            for (Object o : wordsResult.toList()) {
                HashMap<String, String> words = (HashMap<String, String>) o;
                String str = words.get("words");
//                if (str.contains("class")) {
//                    isStart = true;
//                }
//                if (!isStart) {
//                    continue;
//                }
                if (StringUtils.isNumeric(str)) {
                    continue;
                }

                if (str.contains("WANGHUYUE698")) {
                    continue;
                }

                if (str.contains("698-")) {
                    continue;
                }

                if (str.contains("EX-")) {
                    continue;
                }
                if (str.contains("-20")) {
                    continue;
                }
                stringBuilder.append(str).append("\n");
            }
            wordsResult.forEach(o -> {

            });
            res.getInt("words_result_num");
        } catch (IOException e) {

        }
        return stringBuilder.toString();

    }
}
