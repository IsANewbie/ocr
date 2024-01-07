package com.zoubin.ocr.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class MigrationUtils {

    public static void main(String[] args) {
        String sourceDirPath = "/Users/zoubin/APC/infra";
        String targetDirPath = "/Users/zoubin/GitHub/migration/Plms-base/infra";
        doCopy(sourceDirPath, targetDirPath);
    }

    public static void doCopy(String sourceDirPath, String targetDirPath) {
        // 源文件夹路径
        Path sourceDir = Paths.get(sourceDirPath);
        // 目标文件夹路径
        Path targetDir = Paths.get(targetDirPath);
        try {
            // 使用Files.walk来遍历源文件夹中的所有文件和文件夹
            Files.walk(sourceDir)
                    // 使用forEach操作来复制每个java文件
                    .filter(filePath -> Files.isDirectory(filePath) || filePath.toString().endsWith(".java"))
                    .filter(filePath -> !filePath.toString().contains("_"))
                    .forEach(filePath -> {
                        try {
                            // 获取相对路径
                            Path relativePath = sourceDir.relativize(filePath);
                            // 构建目标路径
                            Path targetPath = targetDir.resolve(relativePath);
                            // 确保目标路径的父文件夹存在
                            Files.createDirectories(targetPath.getParent());
                            // 复制文件
                            Files.copy(filePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            System.err.printf("无法复制文件： %s%n", filePath);
                        }
                    });
        } catch (IOException e) {
            System.err.println("无法访问源文件夹或目标文件夹");
        }
    }
}
