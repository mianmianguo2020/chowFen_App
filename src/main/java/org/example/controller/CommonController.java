package org.example.controller;

/**
 * @author Christy Guo
 * @create 2023-03-28 8:49 PM
 */

import lombok.extern.slf4j.Slf4j;
import org.example.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/common")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;
    /**
     * @param file 上传的文件
     * @Description: 文件上传
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) { //和前端的name保持一致 file
        //file是一个临时文件,需要转存到指定位置，否则本次请求完成后临时文件会被删除
        log.info("已经接收到文件，文件名为：" + file.toString());


        //原始文件名
        String originalFilename = file.getOriginalFilename();  //abc.jpg
        //截取原始文件名的后缀（使用UUID+原始文件名的后缀以防上传文件名重复）
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));  // suffix = .jpg  截取是带点的后缀

        // 使用UUID重新生成文件名，防止文件名重复，造成后面上传的文件覆盖前面上传的文件
        String fileName = UUID.randomUUID().toString() + suffix; //随机生成的30多位+后缀

        String fileNameWithoutSuffix = fileName.substring(0, fileName.lastIndexOf("."));
        log.info("fileName：" + fileNameWithoutSuffix);

        //创建一个目录对象
        File dir = new File(basePath);
        //判断当前目录是否存在
        if (!dir.exists()) {
            //如果目录不存在则创建
            dir.mkdirs();
        }

        try {
            //将临时文件转存到指定位置
            file.transferTo(new File(basePath + fileName));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(fileName);
    }

    /**
     * @param name 文件名称
     * @Description: 文件下载回显给页面
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {

        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath+name));

            //输出流,通过输出流将文件写回浏览器，在浏览器展示图片
            ServletOutputStream outputStream = response.getOutputStream();


            System.out.println(response);
            //设置一下为图片文件
            response.setContentType("image/jpeg");

            // 输入流读取到 内容放到 bytes数组中
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){   //当为-1的时候输入流读取完成
                outputStream.write(bytes,0,len);          //写输入流到浏览器
                outputStream.flush();
            }

            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
