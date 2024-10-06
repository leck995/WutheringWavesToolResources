package cn.tealc;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.tealc.model.Resource;
import cn.tealc.model.RootResource;
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
public class RootResouceUtil {
    public static void main(String[] args) throws IOException {
        init();
    }
    public static void init() throws IOException{
        String version = "1.0.2";
        String aimDir="assets/data/%s";
        String fileDir="data/%s";

        RootResource rootResource = new RootResource();
        Map<String, Resource> map = new LinkedHashMap<>();
        File dir =new File("data");
        File[] jsons = dir.listFiles();
        for (File json : jsons) {
            if (json.getName().equals("Root.json")) {
                continue;
            }
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

        rootResource.setVersion(version);
        rootResource.setResources(map);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("data/Root.json"),rootResource);
    }
}