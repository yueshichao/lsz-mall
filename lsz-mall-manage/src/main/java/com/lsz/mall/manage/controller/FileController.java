package com.lsz.mall.manage.controller;

import com.alibaba.fastjson.JSON;
import com.lsz.mall.base.entity.ResponseMessage;
import com.lsz.mall.base.entity.UploadDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Api(description = "文件管理")
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${pic.filePath}")
    private String filePath;

    @Value("${pic.url}")
    private String picUrl;

    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public ResponseMessage<UploadDto> upload(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseMessage.error("文件为空！");
        }

        String fileName = file.getOriginalFilename();
        log.info("上传文件名：{}", fileName);
        long ttl = System.currentTimeMillis();
        String newFileName = ttl + "_" + fileName;
        File dest = new File(filePath + newFileName);
        try {
            file.transferTo(dest);
            log.info("上传成功");
            UploadDto uploadDto = new UploadDto(picUrl + newFileName, newFileName);
            log.info("updaload = {}", JSON.toJSONString(uploadDto));
            return ResponseMessage.ok(uploadDto);
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
        return ResponseMessage.error("上传失败！");
    }

    @GetMapping(value = "/image/{fileName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] image(@PathVariable String fileName) throws Exception {
        String fullFileName = filePath + fileName;
        log.debug("fullFileName = {}", fullFileName);
        File file = new File(fullFileName);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }

}
