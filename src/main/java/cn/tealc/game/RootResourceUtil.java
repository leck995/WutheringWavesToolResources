package cn.tealc.game;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.tealc.game.model.Resource;
import cn.tealc.game.model.RootResource;
import cn.tealc.util.Base64FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @program: WutheringWavesToolResources
 * @description:
 * @author: Leck
 * @create: 2024-10-06 15:54
 */
public class RootResourceUtil {
    private static final String ROOT_JSON_TEMPLATE = "data/Root_%s.json";
    private static final String AIM_FILEPATH="assets/data/%s/%s";
    private static final String FILE_FILEPATH="assets/data/%s/%s";

    private static final String Local_DATA_DIR_FILEPATH="assets/data/%s"; //各语言下下载资源列表JSON目录
    private static final String BASE_DATA_DIR_FILEPATH="assets/data/default"; //通用下载资源列表JSON目录
    public static void main(String[] args) throws IOException {
        init(Locale.SIMPLIFIED_CHINESE);
        init(Locale.ENGLISH);

        // 所有 Root.json 生成完毕后，统一对每个 JSON 文件进行 Base64 编码
        encodeAllJsonFiles();
    }
    public static void init(Locale locale) throws IOException{
        String version = "1.3.7";
        RootResource rootResource = new RootResource();
        Map<String, Resource> map = new LinkedHashMap<>();
        //先把各语言下需要的JSON进行添加上
        addFromLocalDir(new File(String.format(Local_DATA_DIR_FILEPATH,locale)),map,locale.toString());
        //然后获取通用的
        addFromLocalDir(new File(String.format(BASE_DATA_DIR_FILEPATH)),map,"default");
        rootResource.setVersion(version);
        rootResource.setResources(map);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(String.format(ROOT_JSON_TEMPLATE,locale)),rootResource);
    }

    private static void addFromLocalDir(File dir,Map<String,Resource> map,String locale){
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

    private static void addFromDefaultDir(File dir,Map<String,Resource> map,Locale locale){
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

    private static void encodeAllJsonFiles() throws IOException {
        String[] dirs = {
                String.format(Local_DATA_DIR_FILEPATH, Locale.SIMPLIFIED_CHINESE),
                String.format(Local_DATA_DIR_FILEPATH, Locale.ENGLISH),
                BASE_DATA_DIR_FILEPATH,
                "assets/data/base"
        };
        for (String dir : dirs) {
            encodeJsonsInDir(new File(dir));
        }
        // 同时编码生成的 Root_<locale>.json
        Base64FileUtil.encodeFile(Path.of(String.format(ROOT_JSON_TEMPLATE, Locale.SIMPLIFIED_CHINESE)));
        Base64FileUtil.encodeFile(Path.of(String.format(ROOT_JSON_TEMPLATE, Locale.ENGLISH)));
    }

    private static void encodeJsonsInDir(File dir) {
        File[] jsons = dir.listFiles();
        if (jsons != null) {
            for (File json : jsons) {
                if (json.getName().equals("Root.json")) {
                    continue;
                }
                String suffix = FileUtil.getSuffix(json);
                if ("json".equals(suffix)) {
                    try {
                        Base64FileUtil.encodeFile(json.toPath());
                    } catch (IOException e) {
                        System.err.println("Base64编码失败: " + json.getName());
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}