package com.zoubin.ocr.controller;

import com.zoubin.ocr.service.BaiduOcrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
@Api("OCR")
@RestController
@RequestMapping("/api/ocr")
public class OcrController {
    @Resource
    private BaiduOcrService baiduOcrService;
    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public ResponseEntity<Void> handleFileUpload(@RequestParam("files")
                                                     @ApiParam(value = "文件")MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        for (MultipartFile file : files) {
            baiduOcrService.sample(file);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
