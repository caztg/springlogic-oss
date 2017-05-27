package cn.springlogic.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by admin on 2017/5/16.
 */

@Data
@ConfigurationProperties(prefix = "oss.aliyun")
public class AliYunOssProperties {

    private String accessKey;

    private String accessSecret;

    private String endpoint;

    private String bucket;

}
