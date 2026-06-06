package cn.tealc.resource;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.tealc.resource.model.Resource;
import cn.tealc.resource.model.RootResource;
import cn.tealc.util.Base64FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @program: WutheringWavesToolResources
 * @description: 资源索引生成工具。
 * 将各目录下的资源文件（权重JSON、基础数据JSON、头像PNG）的MD5和路径信息直接汇总到 Root_%s.json 中，
 * 移除中间层（RoleWeight.json / BaseData.json / RoleHeader.json），客户端只需下载一份索引即可获取全部资源信息。
 * <p>
 * Root_%s.json 结构（均在 resources 下）：
 * - resources.roleWeight: 声骸权重文件（assets/data/{locale}/weight/default/）
 * - resources.baseData: 角色/武器基础数据（assets/data/base/）
 * - resources.roleHeader: 头像图片资源（assets/header/）
 * <p>
 * 使用流程：
 * 1. 将 ENABLE_ENCODING 设为 false
 * 2. 运行 main()，检查生成的 data/Root_zh_CN.json 和 data/Root_en.json 内容是否正确
 * 3. 确认无误后，将 ENABLE_ENCODING 设为 true，再次运行以进行 Base64 编码
 * @author: Leck
 * @create: 2024-10-06 15:54
 */
public class RootResourceUtil {
    /** 设为 false 可跳过 Base64 编码，方便先确认生成的 Root JSON 内容 */
    private static final boolean ENABLE_ENCODING = true;

    /** 输出路径模板: data/Root_zh_CN.json */
    private static final String ROOT_JSON_TEMPLATE = "data/Root_%s.json";
    /** 远端目标路径模板: assets/data/{locale}/{filename} */
    private static final String AIM_FILEPATH = "assets/data/%s/%s";
    /** 本地文件路径模板: assets/data/{locale}/{filename} */
    private static final String FILE_FILEPATH = "assets/data/%s/%s";

    /** 各语言下载资源列表JSON存放目录 */
    private static final String LOCAL_DATA_DIR_FILEPATH = "assets/data/%s";
    /** 声骸权重文件存放目录 */
    private static final String WEIGHT_DIR_TEMPLATE = "assets/data/%s/weight/default";
    /** 角色/武器基础数据存放目录 */
    private static final String BASE_DATA_DIR = "assets/data/base";
    /** 头像PNG存放目录 */
    private static final String HEADER_DIR = "assets/header";

    /** 权重文件远端路径模板 */
    private static final String WEIGHT_AIM_TEMPLATE = "assets/data/%s/weight/default/%s";
    /** 基础数据远端路径模板 */
    private static final String BASE_DATA_AIM_TEMPLATE = "assets/data/base/%s";
    /** 头像远端路径模板 */
    private static final String HEADER_AIM_TEMPLATE = "assets/header/%s";

    public static void main(String[] args) throws IOException {
        if (ENABLE_ENCODING) {
            checkAlreadyEncoded();
        }

        init(Locale.SIMPLIFIED_CHINESE);
        init(Locale.ENGLISH);

        if (ENABLE_ENCODING) {
            encodeRootJsonFiles();
        } else {
            System.out.println("编码已禁用（ENABLE_ENCODING = false），请确认 Root JSON 内容后手动开启编码并重新运行");
        }
    }

    /** 为指定语言生成资源索引 Root.json，四大分类均放在 resources 下 */
    public static void init(Locale locale) throws IOException {
        String version = "1.4.0";
        RootResource rootResource = new RootResource();

        Map<String, Object> resources = new LinkedHashMap<>();

        // 各语言下的直接JSON资源
        addFromLocalDir(new File(String.format(LOCAL_DATA_DIR_FILEPATH, locale)), resources, locale.toString());

        // 声骸权重文件 → resources.roleWeight
        File weightDir = new File(String.format(WEIGHT_DIR_TEMPLATE, locale));
        if (weightDir.exists() && weightDir.isDirectory()) {
            List<Resource> roleWeight = new ArrayList<>();
            addWeightFiles(weightDir, roleWeight, locale.toString());
            resources.put("roleWeight", roleWeight);
        }

        // 基础数据文件 → resources.baseData
        File baseDir = new File(BASE_DATA_DIR);
        if (baseDir.exists() && baseDir.isDirectory()) {
            List<Resource> baseData = new ArrayList<>();
            addBaseDataFiles(baseDir, baseData);
            resources.put("baseData", baseData);
        }

        // 头像资源 → resources.roleHeader
        File headerDir = new File(HEADER_DIR);
        if (headerDir.exists() && headerDir.isDirectory()) {
            List<Resource> roleHeader = new ArrayList<>();
            addHeaderFiles(headerDir, roleHeader);
            resources.put("roleHeader", roleHeader);
        }

        rootResource.setResources(resources);
        rootResource.setVersion(version);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(String.format(ROOT_JSON_TEMPLATE, locale)), rootResource);
    }

    /** 扫描语言目录下的直接 JSON 资源，跳过目录和中间层文件（RoleWeight.json），直接放入 resources */
    private static void addFromLocalDir(File dir, Map<String, Object> map, String locale) {
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        System.out.println(dir.getAbsolutePath());
        File[] jsons = dir.listFiles();
        if (jsons != null) {
            for (File json : jsons) {
                if (json.isDirectory()) {
                    continue;
                }
                if (json.getName().equals("Root.json") || json.getName().equals("RoleWeight.json")) {
                    continue;
                }
                String suffix = FileUtil.getSuffix(json);
                if (suffix != null) {
                    if (suffix.equals("json")) {
                        String md5 = DigestUtil.md5Hex(json);
                        String name = FileUtil.mainName(json);
                        String filename = URLUtil.encode(json.getName());
                        String filePath = String.format(FILE_FILEPATH, locale, filename);
                        String aimPath = String.format(AIM_FILEPATH, locale, json.getName());
                        map.put(name, new Resource(json.getName(), filePath, aimPath, md5));
                    } else {
                        System.err.println(json.getName() + "非JSON文件");
                    }
                }
            }
        }
    }

    /** 扫描声骸权重目录（assets/data/{locale}/weight/default/）下的所有 JSON 文件 */
    private static void addWeightFiles(File dir, List<Resource> list, String locale) {
        System.out.println(dir.getAbsolutePath());
        File[] jsons = dir.listFiles();
        if (jsons != null) {
            for (File json : jsons) {
                if (json.isDirectory()) {
                    continue;
                }
                String suffix = FileUtil.getSuffix(json);
                if (suffix != null && suffix.equals("json")) {
                    String md5 = DigestUtil.md5Hex(json);
                    String filename = URLUtil.encode(json.getName());
                    String filePath = String.format(WEIGHT_AIM_TEMPLATE, locale, filename);
                    String aimPath = String.format(WEIGHT_AIM_TEMPLATE, locale, json.getName());
                    list.add(new Resource(json.getName(), filePath, aimPath, md5));
                }
            }
        }
    }

    /** 扫描基础数据目录（assets/data/base/）下的所有 JSON 文件（角色/武器） */
    private static void addBaseDataFiles(File dir, List<Resource> list) {
        System.out.println(dir.getAbsolutePath());
        File[] jsons = dir.listFiles();
        if (jsons != null) {
            for (File json : jsons) {
                if (json.isDirectory()) {
                    continue;
                }
                String suffix = FileUtil.getSuffix(json);
                if (suffix != null && suffix.equals("json")) {
                    String md5 = DigestUtil.md5Hex(json);
                    String filename = URLUtil.encode(json.getName());
                    String filePath = String.format(BASE_DATA_AIM_TEMPLATE, filename);
                    String aimPath = String.format(BASE_DATA_AIM_TEMPLATE, json.getName());
                    list.add(new Resource(json.getName(), filePath, aimPath, md5));
                }
            }
        }
    }

    /** 扫描头像目录（assets/header/）下的所有 PNG 文件 */
    private static void addHeaderFiles(File dir, List<Resource> list) {
        System.out.println(dir.getAbsolutePath());
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    continue;
                }
                String suffix = FileUtil.getSuffix(file);
                if (suffix != null && suffix.equals("png")) {
                    String md5 = DigestUtil.md5Hex(file);
                    String filename = URLUtil.encode(file.getName());
                    String filePath = String.format(HEADER_AIM_TEMPLATE, filename);
                    String aimPath = String.format(HEADER_AIM_TEMPLATE, file.getName());
                    list.add(new Resource(file.getName(), filePath, aimPath, md5));
                }
            }
        }
    }

    /** 对源目录下的所有 JSON 进行 Base64 编码（含权重子目录），在生成 Root JSON 前调用 */
    private static void encodeSourceJsonFiles() {
        String[] dirs = {
                String.format(LOCAL_DATA_DIR_FILEPATH, Locale.SIMPLIFIED_CHINESE),
                String.format(LOCAL_DATA_DIR_FILEPATH, Locale.ENGLISH),
                BASE_DATA_DIR,
        };
        for (String dir : dirs) {
            File d = new File(dir);
            if (d.exists()) {
                encodeJsonsInDir(d);
            }
        }
    }

    /** 扫描 assets/data/ 下所有 JSON，若任一已编码则终止执行 */
    private static void checkAlreadyEncoded() {
        File dataDir = new File("assets/data");
        if (!dataDir.exists()) {
            return;
        }
        checkAlreadyEncodedRecursive(dataDir);
    }

    private static void checkAlreadyEncodedRecursive(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    checkAlreadyEncodedRecursive(file);
                } else if (file.getName().endsWith(".json")) {
                    try {
                        if (Base64FileUtil.isEncoded(file.toPath())) {
                            System.err.println("assets/data 下存在已编码的 JSON 文件，重新创建未编码文件后: " + file.getPath());
                            System.exit(1);
                        }
                    } catch (IOException e) {
                        System.err.println("读取文件失败: " + file.getPath());
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /** 对 assets/data/ 和 data/ 下所有 JSON 进行 Base64 编码 */
    private static void encodeRootJsonFiles() {
        File assetsDataDir = new File("assets/data");
        if (assetsDataDir.exists()) {
            encodeJsonsInDir(assetsDataDir);
        }
        File dataDir = new File("data");
        if (dataDir.exists()) {
            encodeJsonsInDir(dataDir);
        }
    }

    private static void encodeJsonsInDir(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    encodeJsonsInDir(file);
                } else {
                    if (file.getName().equals("Root.json")) {
                        continue;
                    }
                    String suffix = FileUtil.getSuffix(file);
                    if ("json".equals(suffix)) {
                        try {
                            if (Base64FileUtil.isEncoded(file.toPath())) {
                                System.out.println("跳过已编码: " + file.getPath());
                                continue;
                            }
                            Base64FileUtil.encodeFile(file.toPath());
                        } catch (IOException e) {
                            System.err.println("Base64编码失败: " + file.getName());
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
