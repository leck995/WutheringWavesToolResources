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
 * @description:
 * @author: Leck
 * @create: 2024-10-06 15:54
 */
public class RoleWeightUtil {
    public static void main(String[] args) throws IOException {
        init();
    }
    public static void init() throws IOException{
        String aimDir="assets/data/weight/default/%s";
        String fileDir="assets/data/weight/default/%s";

        Map<String, Resource> map = new LinkedHashMap<>();
        File dir =new File("assets/data/weight/default");
        File[] jsons = dir.listFiles();
        for (File json : jsons) {
            String suffix = FileUtil.getSuffix(json);
            if (suffix.equals("json")){
                String md5 = DigestUtil.md5Hex(json);
                String name = FileUtil.mainName(json);
                String filename = URLUtil.encode(json.getName());
                String filePath = String.format(fileDir, filename);
                String aimPath = String.format(aimDir, json.getName());
                map.put(name,new Resource(json.getName(),filePath,aimPath,md5));
            }else {
                System.err.println(json.getName()+"非JSON文件");
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("data/RoleWeight.json"),map);
    }
}