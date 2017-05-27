package cn.springlogic.oss.service.impl;

import cn.springlogic.oss.config.AliYunOssProperties;
import cn.springlogic.oss.service.AliyunOssService;
import cn.springlogic.oss.service.UploadService;
import cn.springlogic.oss.util.FileUtil;
import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by admin on 2017/5/16.
 */
@Service
@Qualifier("AliyunOssService")
public class AliyunOssServiceImpl implements AliyunOssService, UploadService {

    @Autowired
    private AliYunOssProperties aliYunOssProperties;

    private volatile OSSClient ossClient;

    @PostConstruct
    private void init(){
        ossClient = new OSSClient(aliYunOssProperties.getEndpoint(), aliYunOssProperties.getAccessKey(), aliYunOssProperties.getAccessSecret());
    }

    @PreDestroy
    private void destroy(){
        ossClient.shutdown();
    }

    @Override
    public void uploadStream(String keyUrl, InputStream inputStream) {
        ossClient.putObject(aliYunOssProperties.getBucket(), keyUrl, inputStream);
    }

    @Override
    public String saveFile(String userIdentify, MultipartFile multipartFile) throws IOException {

        /** 生成存储路径 **/
        String url = FileUtil.calcUrl(userIdentify, multipartFile);
        this.uploadStream(url, multipartFile.getInputStream());

        return url;
    }
}