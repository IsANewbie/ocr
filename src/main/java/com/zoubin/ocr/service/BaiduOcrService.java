package com.zoubin.ocr.service;

import com.baidu.aip.ocr.AipOcr;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@Service
public class BaiduOcrService {
    //设置APPID/AK/SK
    public static final String APP_ID = "46400901";
    public static final String API_KEY = "3aERo1etOvx1dTWi2arUuKET";
    public static final String SECRET_KEY = "46mak4w7xt9aQ1ymT9xOfv28ztyvKa9O";

    public void sample(MultipartFile file) {

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
        try {
            String originalFilename = file.getOriginalFilename();
            byte[] fileData = FileCopyUtils.copyToByteArray(file.getInputStream());
            res = client.webImage(fileData, options);
            JSONArray wordsResult = res.getJSONArray("words_result");
            StringBuilder stringBuilder = new StringBuilder();
            Boolean isStart = false;
            for (Object o : wordsResult.toList()) {
                HashMap<String, String> words = (HashMap<String, String>) o;
                String str = words.get("words");
                if (str.contains("class")) {
                    isStart = true;
                }
                if (!isStart) {
                    continue;
                }
                if (StringUtils.isNumeric(str)) {
                    continue;
                }

                if (str.contains("WANGHUYUE698")) {
                    continue;
                }
                stringBuilder.append(str).append("\n");
            }
            wordsResult.forEach(o -> {

            });
            res.getInt("words_result_num");
            System.out.println(originalFilename);
            System.out.println(stringBuilder.toString());
        } catch (IOException e) {

        }


    }

}
