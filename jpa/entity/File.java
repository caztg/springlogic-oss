package cn.springlogic.oss.jpa.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by admin on 2017/4/20.
 */
@Data
@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /*
    1图片
    2音频
    3视频
    4文件
     */
    private int type;
    /**
     * 比如:
     用户头像avatar
     文章媒体article_media
     */
    @Column(name = "entity_type")
    private String entityType;

    private String title;

    private String uri;
    @Column(name = "create_time")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

}
