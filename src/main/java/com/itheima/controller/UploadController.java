package com.itheima.controller;

import com.itheima.entity.AliOSSProperties;
import com.itheima.entity.Result;
import com.itheima.utils.AliyunOSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class UploadController {

    /**
     * 文件上传---本地存储
     * @param username
     * @param age
     * @param file
     * @return
     */
    // @PostMapping("/upload")
    // public Result upload(String username, Integer age, MultipartFile file) throws Exception {
    //     log.info("参数：{}，{}，{}", username, age, file);
    //
    //     //1.获取原始文件名
    //     String originalFilename = file.getOriginalFilename();   //eg: 001.002.003.jpg
    //
    //     //2.通过UUID生成随机字符串
    //     String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
    //
    //     //2.将前端上传的文件存到本地
    //     file.transferTo(new File("D:/"+ newFileName));
    //
    //     return Result.success(newFileName);
    // }


    // private final String bucketName = "java147-tlias";  //oss上的桶空间名
    // private final String endpoint = "https://oss-cn-shenzhen.aliyuncs.com";

    // @Value("${aliyun.oss.endpoint}")
    // private String endpoint;  //oss上桶对应的域名
    // @Value("${aliyun.oss.bucket}")
    // private String bucketName; //oss上的桶空间名

    @Autowired
    private AliOSSProperties aliOSSProperties;


    /**
     * 文件上传---阿里云OSS存储
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        //1.获取原始文件名, 截取后缀
        String originalFilename = file.getOriginalFilename();   //eg: 1.2.3.jpg
        log.info("原始文件名：{}", originalFilename);
        String extName = originalFilename.substring(originalFilename.lastIndexOf("."));     //获取后缀 .jpg

        //2.调用阿里云OSS工具类，将文件上传到oss
        String url = AliyunOSSUtils.upload(aliOSSProperties.getEndpoint(), aliOSSProperties.getBucket(), file.getBytes(), extName);

        //3.返回图片路径
        return Result.success(url);
    }

}
