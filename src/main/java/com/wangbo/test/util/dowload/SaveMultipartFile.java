package com.wangbo.test.util.dowload;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

public class SaveMultipartFile {

    public String saveFacePictrue(String picBaseStr) {
        if (picBaseStr.startsWith("data:image")) {
            // 去除开头data:image/jpg;base64,声明数据协议及类型名称及编码形式
            picBaseStr = picBaseStr.substring(picBaseStr.indexOf(',') + 1);
        }
        byte[] bytes = Base64.decodeBase64(picBaseStr);
        // 新的图片文件名 = 获取时间戳+"."图片扩展名
        String newFileName = String.valueOf(System.nanoTime());
        int hashcode = Math.abs(newFileName.hashCode());
        String residentPictureUrl = "upload/private/";
        String imgDir = residentPictureUrl + "redress/idno/" + hashcode % 256 + "/";
        File imgFile = new File(imgDir);
        if (!imgFile.exists()) {// 如果不存在此目录就新建目录
            imgFile.mkdirs();
        }
        String imgPath = imgDir + newFileName;
        try(InputStream sbs = new ByteArrayInputStream(bytes)) {
            Thumbnails.of(sbs).scale(1f).outputQuality(1.0).outputFormat("jpg").toFile(imgPath);
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        return imgPath;
    }
    
    public void saveFile(MultipartFile[] attachs, Integer baseId) {
        for (MultipartFile file : attachs) {
            if (!file.isEmpty()) {
                try {
                    String fileName = file.getOriginalFilename();
                    // 系统存储文件路径
                    String fileUrl = "upload/private/";
                    String fileDir = fileUrl + "redress/attach/" + baseId + "/";
                    File newFile = new File(fileDir);
                    if (!newFile.exists()) {
                        newFile.mkdirs();
                    }
                    String filePath = fileDir + fileName;
                    FileOutputStream out = new FileOutputStream(filePath);
                    InputStream in = file.getInputStream();
                    byte buffer[] = new byte[1024];
                    int len = 0;
                    while ((len = in.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }
                    in.close();
                    out.close();
//                    RedressAttachment attachmentInfo = new RedressAttachment();
//                    attachmentInfo.setRectificatoryId(baseId);
//                    attachmentInfo.setAccessoryName(fileName);
//                    attachmentInfo.setAccessoryAddress(filePath);
//                    attachments.add(attachmentInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
