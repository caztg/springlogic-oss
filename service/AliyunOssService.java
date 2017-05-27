package cn.springlogic.oss.service;

import java.io.InputStream;

/**
 * Created by admin on 2017/5/16.
 */

/**
 * 阿里云OSS操作接口
 */
public interface AliyunOssService {

    /**
     * 保存输入流到阿里云OSS
     * @param keyUrl OSS 存储的唯一标识
     * @param inputStream
     */
    void uploadStream(String keyUrl, InputStream inputStream);

}
