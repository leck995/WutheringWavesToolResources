package cn.tealc;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.tealc.model.Resource;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: WutheringWavesToolResources
 * @description:
 * @author: Leck
 * @create: 2024-10-06 15:54
 */
public class RoleHeaderUtil {
    public static void main(String[] args) throws IOException {
        init();
    }
    public static void init() throws IOException{
        String aimDir="assets/header/%s";
        String fileDir="assets/header/%s";

        Map<String, Resource> map = new LinkedHashMap<>();
        File dir =new File("assets/header");
        File[] imgs = dir.listFiles();
        for (File img : imgs) {
            String suffix = FileUtil.getSuffix(img);
            if (suffix.equals("png")){
                String md5 = DigestUtil.md5Hex(img);
                String name = FileUtil.mainName(img);
                String filename = URLUtil.encode(img.getName());
                String filePath = String.format(fileDir, filename);
                String aimPath = String.format(aimDir, img.getName());
                map.put(name,new Resource(img.getName(),filePath,aimPath,md5));
            }else {
                System.err.println(img.getName()+"非PNG文件");
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("data/RoleHeader.json"),map);
    }
}