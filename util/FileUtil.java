package cn.springlogic.oss.util;

import cn.springlogic.communicate.util.CommUtil;
import org.springframework.web.multipart.MultipartFile;
import org.joda.time.DateTime;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Created by admin on 2017/5/16.
 */
public class FileUtil {

    /**
     * 从文件名获取文件扩展名：img.png -> .png
     * @param fileName
     * @return
     */
    public static String getFileExtFromFileName(String fileName){
        if(null != fileName && !"".equals(fileName)){
            int dot = fileName.indexOf('.');
            if((dot > -1) && (dot < (fileName.length() - 1))){
                return fileName.substring(dot);
            }
        }
        return fileName;
    }

    /** 图片上传相对路径模板 **/
    private static String UPLOAD_URL_TEMPLATE = "u{USERID}/{yyyyMM}/{fileExt}/";

    /**http://aishangsancan.oss-cn-shenzhen.aliyuncs.com/umng/201705/.png/20170523112526_135253.png
     * 生成文件相对路径
     * @param fileExt 文件扩展名，例如：.png
     * @return 文件相对路径
     */
    public static String calcUrl(String userId, String fileExt){
        String newFileName = CommUtil.genRandomNum(6) + fileExt;
        String date = DateTime.now().toString("yyyyMM");
        String timeStamp = DateTime.now().toString("yyyyMMddHHmmss");
       // String url = UPLOAD_URL_TEMPLATE.replace("{USERID}", userId).replace("{yyyyMM}", date).replace("{fileExt}", fileExt) + timeStamp + "_" + newFileName;
        String url =  timeStamp + "_" + newFileName;
        return url;
    }

    /**
     * 生成文件相对路径
     * @param multipartFile 文件 MultipartFile
     * @return 文件相对路径
     */
    public static String calcUrl(String userId, MultipartFile multipartFile){
        String fileExt = FileUtil.getFileExtFromFileName(multipartFile.getOriginalFilename());
        return calcUrl(userId, fileExt);
    }

    /**
     * 上传文件
     * @param inputStream 文件输入流
     * @param rootPath 文件存储根路径
     * @param url 文件相对路径
     * @return 文件存储相对路径
     * @throws IOException
     */
    public static String upload(InputStream inputStream, String rootPath, String url) throws IOException {
        File targetFile = new File(rootPath, url);
        if(!targetFile.exists()){
            // 先创建父目录
            File p = targetFile.getParentFile();
            if(null != p && !p.exists()){
                p.mkdirs();
            }
            // 然后创建目标文件
            targetFile.createNewFile();
        }
        // 因为上面已经创建了目标文件，所以这里的复制模式是：REPLACE_EXISTING
        Files.copy(inputStream, Paths.get(rootPath, url), StandardCopyOption.REPLACE_EXISTING);

        return url;
    }

    /**
     * 上传文件
     * @param multipartFile 文件 MultipartFile
     * @param rootPath 文件存储根路径
     * @param url 文件相对路径
     * @return 文件存储相对路径
     * @throws IOException
     */
    public static String upload(MultipartFile multipartFile, String rootPath, String url) throws IOException {
        return upload(multipartFile.getInputStream(), rootPath, url);
    }

}

