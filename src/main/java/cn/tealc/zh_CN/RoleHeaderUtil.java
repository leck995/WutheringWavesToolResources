package cn.tealc.zh_CN;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.tealc.model.Resource;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @program: WutheringWavesToolResources
 * @description: 计算头像的md5等信息，将所需信息输出为json，以供下载
 * @author: Leck
 * @create: 2024-10-06 15:54
 */
@Deprecated
public class RoleHeaderUtil {
    private static final String JSON_PATH_TEMPLATE="assets/data/%s/RoleHeader.json";
    private static final String AIM_DIR_TEMPLATE="assets/header/%s"; //json中的保存目标路径
    private static final String FILE_DIR_TEMPLATE="assets/header/%s";//当前文件路径
    private static final String FILE_DIR="assets/header";//当前文件路径
    public static void main(String[] args) throws IOException {
        init();
    }

    public static void init() throws IOException {
        save(Locale.SIMPLIFIED_CHINESE);
        save(Locale.ENGLISH);
    }

    private static void save(Locale locale) throws IOException{
        Map<String, Resource> map = new LinkedHashMap<>();
        File dir =new File(FILE_DIR);
        File[] imgs = dir.listFiles();
        for (File img : imgs) {
            String suffix = FileUtil.getSuffix(img);
            if (suffix.equals("png")){
                String md5 = DigestUtil.md5Hex(img);
                String name = FileUtil.mainName(img);
                String filename = URLUtil.encode(img.getName());
                String filePath = String.format(FILE_DIR_TEMPLATE, filename);
                String aimPath = String.format(AIM_DIR_TEMPLATE, img.getName());
                map.put(name,new Resource(img.getName(),filePath,aimPath,md5));
            }else {
                System.err.println(img.getName()+"非PNG文件");
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(String.format(JSON_PATH_TEMPLATE,locale.getLanguage())),map);
    }
}