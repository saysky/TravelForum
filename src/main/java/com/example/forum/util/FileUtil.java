package com.example.forum.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrBuilder;
import com.example.forum.exception.MyBusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author saysky
 * @date 2021/3/20
 */
public class FileUtil {

    /**
     * 上传文件返回URL
     *
     * @param file
     * @return
     */
    public static Map<String, String> upload(MultipartFile file) {
        Map<String, String> map = new HashMap<>();
        try {
            //用户目录
            final StrBuilder uploadPath = new StrBuilder(System.getProperties().getProperty("user.home"));
            uploadPath.append("/sens/upload/" + DateUtil.thisYear()).append("/").append(DateUtil.thisMonth() + 1).append("/");
            final File mediaPath = new File(uploadPath.toString());
            if (!mediaPath.exists()) {
                if (!mediaPath.mkdirs()) {
                    throw new MyBusinessException("上传失败，请重试");
                }
            }

            //不带后缀
            String nameWithOutSuffix = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf('.')).replaceAll(" ", "_").replaceAll(",", "");

            //文件后缀
            final String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);

            //带后缀
            String fileName = nameWithOutSuffix + "." + fileSuffix;

            //判断文件名是否已存在
            File descFile = new File(mediaPath.getAbsoluteFile(), fileName);
            int i = 1;
            while (descFile.exists()) {
                nameWithOutSuffix = nameWithOutSuffix + "(" + i + ")";
                descFile = new File(mediaPath.getAbsoluteFile(), nameWithOutSuffix + "." + fileSuffix);
                i++;
            }


            file.transferTo(descFile);

            //文件原路径
            final StrBuilder fullPath = new StrBuilder(mediaPath.getAbsolutePath());
            fullPath.append("/");
            fullPath.append(nameWithOutSuffix + "." + fileSuffix);

            //映射路径
            final StrBuilder url = new StrBuilder("/upload/");
            url.append(DateUtil.thisYear());
            url.append("/");
            url.append(DateUtil.thisMonth() + 1);
            url.append("/");
            url.append(nameWithOutSuffix + "." + fileSuffix);

            map.put("size", String.valueOf(descFile.length()));
            map.put("url", url.toString());
            String path = uploadPath.append(nameWithOutSuffix).append(".").append(fileSuffix).toString();
            map.put("path", path);
            map.put("suffix", fileSuffix);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
