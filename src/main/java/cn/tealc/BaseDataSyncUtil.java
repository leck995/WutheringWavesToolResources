package cn.tealc;

import cn.tealc.model.RoleBaseData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luciad.imageio.webp.WebPReadParam;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @program: WutheringWavesToolResources
 * @description: 用于角色id与各语言名字获取，下载图像。同步hakush
 * @author: Leck
 * @create: 2024-10-22 16:41
 */
public class BaseDataSyncUtil {
    //private static final String filepath="assets/data/RoleBaseData.json";

    private static final String hakush="https://api.hakush.in/ww";
    private static final String iconDir="assets/header/";
    public static void main(String[] args) throws IOException {
        getBaseData();
    }


    private static void getBaseData() throws IOException {
        sync("https://api.hakush.in/ww/data/character.json","assets/data/base/RoleBaseData.json");
        sync("https://api.hakush.in/ww/data/weapon.json","assets/data/base/WeaponBaseData.json");
    }

    private static void sync(String url,String filepath) throws IOException {
        Map<Integer, RoleBaseData> roleBaseDataMap = new HashMap<Integer,RoleBaseData>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode tree = mapper.readTree(URI.create(url).toURL());
        tree.fieldNames().forEachRemaining(field -> {
            JsonNode roleNode = tree.get(field);
            RoleBaseData roleBaseData = create(field, roleNode);
            roleBaseDataMap.put(roleBaseData.getRoleId(), roleBaseData);
        });
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filepath), roleBaseDataMap);
    }

    public static RoleBaseData create(String id,JsonNode roleNode) {
        RoleBaseData roleBaseData = new RoleBaseData(Integer.parseInt(id));
        Map<Locale, String> names = roleBaseData.getNames();
        names.put(Locale.ENGLISH, roleNode.get("en").asText());
        names.put(Locale.SIMPLIFIED_CHINESE, roleNode.get("zh-Hans").asText());
        names.put(Locale.JAPANESE, roleNode.get("ja").asText());
        names.put(Locale.KOREA, roleNode.get("ko").asText());

        String icon = roleNode.get("icon").asText();
        String temp = icon.substring(0, icon.lastIndexOf("."));
        String s = temp.replace("/Game/Aki","");
        String iconurl = hakush+s + ".webp";

        File file = new File(iconDir+id+"png");


        webpToPng(iconurl,file);
        return roleBaseData;
    }


    public static void webpToPng(String webpUrl, File png){
        if (!png.getParentFile().exists()) {
            png.getParentFile().mkdirs();
        }
        if (png.exists()) {
            System.out.println("图像存在，跳过");
            return;
        }
        try {
            ImageReader reader = ImageIO.getImageReadersByMIMEType("image/webp").next();
            InputStream inputStream = new URL(webpUrl).openStream();
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);

            WebPReadParam readParam = new WebPReadParam();
            readParam.setBypassFiltering(true);
            reader.setInput(imageInputStream);
            BufferedImage bufferedImage = reader.read(0,readParam);
            // 将图像写入 PNG 文件
            Path outputPath = Paths.get(png.getAbsolutePath());
            ImageIO.write(bufferedImage, "png", outputPath.toFile());
            System.out.println("保存成功："+webpUrl);
        } catch (IOException e) {
            System.out.println(webpUrl+"保存失败");
            System.out.println(e.getMessage());
        }
    }








  }