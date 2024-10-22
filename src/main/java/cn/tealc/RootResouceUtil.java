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
import java.util.Locale;
import java.util.Map;

/**
 * @program: WutheringWavesToolResources
 * @description:
 * @author: Leck
 * @create: 2024-10-06 15:54
 */
public class RootResouceUtil {
    private static final String ROOT_JSON_TEMPLATE = "data/Root_%s.json";
    private static final String AIM_FILEPATH="assets/data/%s/%s";
    private static final String FILE_FILEPATH="assets/data/%s/%s";

    private static final String Local_DATA_DIR_FILEPATH="assets/data/%s"; //各语言下下载资源列表JSON目录
    private static final String BASE_DATA_DIR_FILEPATH="assets/data/default"; //通用下载资源列表JSON目录
    public static void main(String[] args) throws IOException {
        init(Locale.SIMPLIFIED_CHINESE);
        init(Locale.ENGLISH);
    }
    public static void init(Locale locale) throws IOException{
        String version = "1.0.3";
        RootResource rootResource = new RootResource();
        Map<String, Resource> map = new LinkedHashMap<>();
        //先把各语言下需要的JSON进行添加上
        addFromLocalDir(new File(String.format(Local_DATA_DIR_FILEPATH,locale)),map,locale);
        //然后获取通用的
        addFromLocalDir(new File(String.format(BASE_DATA_DIR_FILEPATH)),map,locale);
        rootResource.setVersion(version);
        rootResource.setResources(map);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(String.format(ROOT_JSON_TEMPLATE,locale)),rootResource);
    }

    private static void addFromLocalDir(File dir,Map<String,Resource> map,Locale locale){
        System.out.println(dir.getAbsolutePath());
        File[] jsons = dir.listFiles();
        if (jsons != null) {
            for (File json : jsons) {
                if (json.getName().equals("Root.json")) {
                    continue;
                }
                String suffix = FileUtil.getSuffix(json);
                if (suffix != null) {
                    if (suffix.equals("json")){
                        String md5 = DigestUtil.md5Hex(json);
                        String name = FileUtil.mainName(json);

                        String filename = URLUtil.encode(json.getName());
                        String filePath = String.format(FILE_FILEPATH, locale, filename);
                        String aimPath = String.format(AIM_FILEPATH, locale, json.getName());
                        map.put(name,new Resource(json.getName(),filePath,aimPath,md5));
                    }else {
                        System.err.println(json.getName()+"非JSON文件");
                    }
                }

            }
        }

    }
}