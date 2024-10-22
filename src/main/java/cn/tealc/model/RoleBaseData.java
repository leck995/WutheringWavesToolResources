package cn.tealc.model;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @program: WutheringWavesToolResources
 * @description:
 * @author: Leck
 * @create: 2024-10-22 16:42
 */
public class RoleBaseData {
    private int roleId; //官方的角色id
    private Map<Locale,String> names = new HashMap<>(); //不同语言的名字
    private String icon;

    public RoleBaseData(int roleId) {
        this.roleId = roleId;
        this.icon = String.format("%d.png", roleId);
    }

    public RoleBaseData() {
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Map<Locale, String> getNames() {
        return names;
    }

    public void setNames(Map<Locale, String> names) {
        this.names = names;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}