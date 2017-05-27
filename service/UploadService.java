package cn.springlogic.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by admin on 2017/5/16.
 */
public interface UploadService {

    /**
     * 保存MultipartFile到本地
     */
    String saveFile(String userIdentify, MultipartFile multipartFile) throws IOException;

}
