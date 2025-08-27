package cn.tealc.release;

import cn.hutool.crypto.digest.DigestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: WutheringWavesToolResources
 * @description: 用于生成发布新版本的json
 * @author: Leck
 * @create: 2024-12-22 15:20
 */
public class ReleaseCreateUtil {
    public static void main(String[] args) throws IOException {
        Release latestRelease = latestRelease();
        ReleaseList releaseList = new ReleaseList(null, latestRelease);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("release.json"),releaseList);

    }

    private static Release latestRelease(){
        String latestVersion = "1.3.1";
        String latestName = "正式版1.3.1";
        String latestDescription = """
                若无法自动更新，检查是否通过“WutheringWavesTool.exe”启动助手，仍无法解决请前往发布页或者助手群手动下载更新。
                
                V1.3.1更新
                1. 修复库街区登录状态异常的问题
                2. 其他修复与优化

                """;
        boolean latestForce = false; //是否是强制更新
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String dateTime = format.format(date);

        File latestFile = new File("E:\\新建文件夹\\鸣潮发布\\WutheringWavesTool-windows-x64-1.3.1.zip");
        String latestMd5 = DigestUtil.md5Hex(latestFile);


        //https://api.github.com/repos/leck995/WutheringWavesTool/releases/latest
        //如果有github,则第一个放github下载链接
        String[] latestUrls = {"https://wwt.tealc.fun/WutheringWavesTool-windows-x64-1.3.1.zip"};
        String warning = "更新警告";//强制更新警告

        boolean isPre = false;

        Release latestRelease = new Release(latestName, latestVersion, latestDescription, dateTime,latestForce, latestUrls, latestMd5,warning,isPre);
        return latestRelease;
    }

    /**
     * 预览版本
     * @return {@link Release }
     */
    private static Release preRelease(){
        String preVersion = "1.2.1";
        String preName = "预览版1.2.1";
        String preDescription = "预览版说明";
        boolean preForce = false;
        String[] preUrls = {"",""};
        String preMd5 = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String dateTime = format.format(date);
        Release preRelease = new Release(preName, preVersion, preDescription,dateTime, preForce, preUrls,preMd5,null,true);

        return null;
    }
}