package com.pinyougou.shop.controller;

import com.pinyougou.commom.util.FastDFSClient;
import com.pinyougou.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/upload")
@RestController
public class UploadController {

    @PostMapping
    public Result upload(MultipartFile file) throws Exception {
        Result result = Result.fail("文件上传失败");
        try {
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:fastdfs/tracker.conf");
            //获取文件后缀名字
            String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            //获取http链接地址
            String url = fastDFSClient.uploadFile(file.getBytes(), extName);
            result = Result.ok(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
