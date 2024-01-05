package com.zoubin.ocr.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
    public static void main(String[] args) {
        // 指定要遍历的文件夹路径
        String folderPath = "/Users/zoubin/code_image";

        // 创建一个 File 对象，表示要遍历的文件夹
        File folder = new File(folderPath);

        // 调用递归方法来列出文件
        listFiles(folder);
    }

    private static void listFiles(File folder)  {
        // 获取文件夹下的所有文件和子文件夹
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getAbsolutePath();
                    if (!fileName.contains(".jpg")) {
                        continue;
                    }
                    // 如果是文件，则打印文件名
                    System.out.println("文件: " + file.getAbsolutePath());
                    String newJavaFileName = fileName.replace(".jpg", ".java");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    String content = BaiduOcrUtils.sample(file);
                    writeStringToFile(content, newJavaFileName);
                } else if (file.isDirectory()) {
                    // 如果是文件夹，则递归调用 listFiles 方法
                    System.out.println("文件夹: " + file.getAbsolutePath());
                    listFiles(file);
                }
            }
        }
    }

    private static void writeStringToFile(String content, String filePath) {
        try {
            // 将字符串转换为字节数组
            byte[] contentBytes = content.getBytes();

            // 获取文件路径的 Path 对象
            Path path = Paths.get(filePath);
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                file.delete();
            }
            // 使用 Files.write 方法写入文件
            Files.write(path, contentBytes);

            System.out.println("字符串已成功写入文件: " + filePath);
        } catch (IOException e) {
            System.err.println("写入文件时出现异常: " + e.getMessage());
        }
    }
}
