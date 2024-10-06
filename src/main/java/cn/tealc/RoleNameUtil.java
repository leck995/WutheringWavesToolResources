package cn.tealc;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: WutheringWavesToolResources
 * @description: 生成角色名称JSON
 * @author: Leck
 * @create: 2024-10-06 16:05
 */
public class RoleNameUtil {
    private static final Map<String, List<String>> nameMap = new HashMap<String, List<String>>();
    public static void main(String[] args) throws IOException {
        put("守岸人");







        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValues(new File("data/RoleName.json"));
    }


    private static void put(String... alias) {
        nameMap.put(alias[0],List.of(alias));
    }
}