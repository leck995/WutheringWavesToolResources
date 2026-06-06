package cn.tealc.game.model;

import java.util.Map;

/**
 * @program: WutheringWavesToolResources
 * @description:
 * @author: Leck
 * @create: 2024-10-06 16:46
 */
public class RootResource {
    private String version;
    /** 混合 Map，value 为 Resource 或 Map&lt;String, Resource&gt; */
    private Map<String, Object> resources;


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, Object> getResources() {
        return resources;
    }

    public void setResources(Map<String, Object> resources) {
        this.resources = resources;
    }
}