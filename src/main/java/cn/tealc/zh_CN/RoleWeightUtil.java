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
 * @description: 把对应语言的声骸权重文件信息保存至json，注声骸权重文件需要存在。暂时只支持简体中文
 * @author: Leck
 * @create: 2024-10-06 15:54
 */
public class RoleWeightUtil {
    private static final String JSON_PATH_TEMPLATE = "assets/data/%s/RoleWeight.json";
    private static final String AIM_DIR_TEMPLATE = "assets/data/%s/weight/default/%s"; //json中的保存目标路径
    private static final String FILE_DIR_TEMPLATE = "assets/data/%s/weight/default/%s";//当前文件路径
    private static final String FILE_DIR = "assets/data/%s/weight/default";//当前文件路径

    public static void main(String[] args) throws IOException {
        init();
    }

    private static void init() throws IOException {
        save(Locale.SIMPLIFIED_CHINESE);
    }

    private static void save(Locale locale) throws IOException {
        Map<String, Resource> map = new LinkedHashMap<>();
        File dir = new File(String.format(FILE_DIR, locale));
        File[] jsons = dir.listFiles();
        for (File json : jsons) {
            String suffix = FileUtil.getSuffix(json);
            if (suffix.equals("json")) {
                String md5 = DigestUtil.md5Hex(json);
                String name = FileUtil.mainName(json);
                String filename = URLUtil.encode(json.getName());
                String filePath = String.format(FILE_DIR_TEMPLATE, locale, filename);
                String aimPath = String.format(AIM_DIR_TEMPLATE, locale, json.getName());
                map.put(name, new Resource(json.getName(), filePath, aimPath, md5));
            } else {
                System.err.println(json.getName() + "非JSON文件");
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(String.format(JSON_PATH_TEMPLATE,locale)), map);
    }
}