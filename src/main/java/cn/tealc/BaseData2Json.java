package cn.tealc;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.tealc.model.Resource;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: WutheringWavesToolResources
 * @description: 在default目录下生成头像图片的资源列表JSon
 * @author: Leck
 * @create: 2024-10-22 20:22
 */
public class BaseData2Json {
    private static final String JSON_PATH_TEMPLATE="assets/data/default/BaseData.json";
    private static final String AIM_DIR_TEMPLATE="assets/data/base/%s"; //json中的保存目标路径
    private static final String FILE_DIR_TEMPLATE="assets/data/base/%s";//当前文件路径
    private static final String FILE_DIR="assets/data/base";//当前文件路径
    public static void main(String[] args) throws IOException {
        init();
    }

    public static void init() throws IOException {
        save();
    }

    private static void save() throws IOException{
        Map<String, Resource> map = new LinkedHashMap<>();
        File dir = new File(FILE_DIR);
        File[] jsons = dir.listFiles();
        for (File json : jsons) {
            String suffix = FileUtil.getSuffix(json);
            if (suffix!= null){
                if (suffix.equals("json")){
                    String md5 = DigestUtil.md5Hex(json);
                    String name = FileUtil.mainName(json);
                    String filename = URLUtil.encode(json.getName());
                    String filePath = String.format(FILE_DIR_TEMPLATE, filename);
                    String aimPath = String.format(AIM_DIR_TEMPLATE, json.getName());
                    map.put(name,new Resource(json.getName(),filePath,aimPath,md5));
                }else {
                    System.err.println(json.getName()+"非JSON文件");
                }
            }

        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(JSON_PATH_TEMPLATE),map);
    }
}