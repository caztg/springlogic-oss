package cn.springlogic.oss.web;

import cn.springlogic.oss.service.UploadService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/5/16.
 */
@RestController
@RequestMapping("api/mng/upload")
public class MngUploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MngUploadController.class);

    @Autowired
    @Qualifier("AliyunOssService")
    private UploadService uploadService;

    private String userId = "mng";


    /**
     * 上传单个
     * @param file
     * @return
     */
    @RequestMapping(value = "/single", method = RequestMethod.POST)
    ResponseEntity<Map<String,Object>> imgSingle(@RequestParam(value = "data") MultipartFile file){

        Map<String,Object> map=new HashedMap();

        try {
            /** 判空 **/
            Assert.notNull(file, "img file can not be null");

            /** 保存并获得相对存储路径 **/
            String url = uploadService.saveFile(userId, file);

            /** 返回数据 **/
            List<String> urls = Arrays.asList(url);
            map.put("urls",urls);
            map.put("msg","success");
           return ResponseEntity.ok(map);
        }catch (IllegalArgumentException e){
            LOGGER.error("上传图片失败", e);
            map.put("msg",e.getMessage());
        }catch (Exception e){
            LOGGER.error("上传图片失败", e);
            map.put("msg",e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
    }

    /**
     * 上传多个
     * @param files
     * @return
     */
    @RequestMapping(value = "/multi", method = RequestMethod.POST)
    ResponseEntity<Map<String,Object>> imgMulti(@RequestParam(value = "data") MultipartFile[] files){

        Map<String,Object> map=new HashedMap();

        try {
            /** 判空 **/
            Assert.notNull(files, "img file can not be null");
            Assert.isTrue(files.length > 0, "img file can not be null");

            List<String> urls = new ArrayList<>(files.length);
            for (MultipartFile file : files){

                /** 保存并获得相对存储路径 **/
                String url = uploadService.saveFile(userId, file);

                urls.add(url);
            }

            map.put("urls",urls);
            map.put("msg","success");
            return ResponseEntity.ok(map);
        }catch (IllegalArgumentException e){
            LOGGER.error("上传图片失败", e);
            map.put("msg",e.getMessage());
        }catch (Exception e){
            LOGGER.error("上传图片失败", e);
            map.put("msg",e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
    }

}
