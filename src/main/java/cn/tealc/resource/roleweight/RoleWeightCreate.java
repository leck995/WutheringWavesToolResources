package cn.tealc.resource.roleweight;

import cn.tealc.resource.model.weight.PhantomWeight;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class RoleWeightCreate {

    private static final String[] SUB_PROP_KEYS = {
        "暴击伤害", "暴击", "攻击", "攻击百分比", "生命", "生命百分比",
        "防御", "防御百分比", "共鸣效率", "普攻伤害加成", "重击伤害加成",
        "共鸣技能伤害加成", "共鸣解放伤害加成"
    };

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        generateAll();
    }



    private static void write(String name, int roleId, int... weights) throws IOException {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < SUB_PROP_KEYS.length; i++) {
            map.put(SUB_PROP_KEYS[i], weights[i]);
        }
        PhantomWeight pw = new PhantomWeight();
        pw.setRoleName(name);
        pw.setSubPropWeights(map);
        File file = new File("assets/data/zh_CN/weight/default/" + roleId + ".json");
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(file, pw);
    }

    public static void generateAll() throws IOException {
        椿();
        守岸人();
        长离();
        相里要();
        折枝();
        今汐();
        吟霖();
        忌炎();
        鉴心();
        卡卡罗();
        安可();
        维里奈();
        凌阳();
        漂泊者湮灭();
        漂泊者衍射();
        漂泊者气动();
        秧秧();
        白芷();
        炽霞();
        散华();
        秋水();
        丹瑾();
        莫特斐();
        渊武();
        桃祈();
        釉瑚();
        珂莱塔();
        灯灯();
        洛可可();
        菲比();
        布兰特();
        坎特蕾拉();
        赞妮();
        夏空();
        卡提希娅();
        露帕();
        弗洛洛();
        奥古斯塔();
        尤诺();
        嘉贝莉娜();
        仇远();
        千咲();
        卜灵();
        琳奈();
        莫宁();
        爱弥斯();
        陆赫斯();
        西格莉卡();
        绯雪();
        达妮娅();
    }

    public static void 椿() throws IOException {
        write("椿", 1603, 3, 3, 2, 2, 0, 0, 0, 0, 1, 2, 0, 1, 1);
    }

    public static void 守岸人() throws IOException {
        write("守岸人", 1505, 2, 0, 0, 0, 2, 3, 0, 0, 3, 0, 0, 1, 2);
    }

    public static void 长离() throws IOException {
        write("长离", 1205, 3, 3, 1, 2, 0, 0, 0, 0, 2, 0, 0, 2, 1);
    }

    public static void 相里要() throws IOException {
        write("相里要", 1305, 3, 3, 1, 1, 0, 0, 0, 0, 2, 0, 0, 0, 2);
    }

    public static void 折枝() throws IOException {
        write("折枝", 1105, 3, 3, 1, 1, 0, 0, 0, 0, 2, 2, 0, 0, 0);
    }

    public static void 今汐() throws IOException {
        write("今汐", 1304, 3, 3, 1, 1, 0, 0, 0, 0, 1, 0, 0, 2, 2);
    }

    public static void 吟霖() throws IOException {
        write("吟霖", 1302, 3, 3, 1, 1, 0, 0, 0, 0, 1, 0, 0, 2, 2);
    }

    public static void 忌炎() throws IOException {
        write("忌炎", 1404, 3, 3, 2, 2, 0, 0, 0, 0, 1, 0, 1, 1, 0);
    }

    public static void 鉴心() throws IOException {
        write("鉴心", 1405, 3, 3, 1, 2, 0, 0, 0, 0, 2, 0, 1, 0, 1);
    }

    public static void 卡卡罗() throws IOException {
        write("卡卡罗", 1301, 3, 3, 1, 2, 0, 0, 0, 0, 1, 0, 0, 1, 1);
    }

    public static void 安可() throws IOException {
        write("安可", 1203, 3, 3, 1, 2, 0, 0, 0, 0, 2, 1, 0, 1, 1);
    }

    public static void 维里奈() throws IOException {
        write("维里奈", 1503, 1, 1, 2, 3, 0, 1, 0, 0, 3, 0, 0, 0, 0);
    }

    public static void 凌阳() throws IOException {
        write("凌阳", 1104, 3, 3, 1, 2, 0, 0, 0, 0, 1, 2, 0, 1, 1);
    }

    public static void 漂泊者湮灭() throws IOException {
        write("漂泊者·湮灭", 1605, 3, 3, 1, 2, 0, 0, 0, 0, 1, 1, 1, 0, 1);
    }

    public static void 漂泊者衍射() throws IOException {
        write("漂泊者·衍射", 1502, 3, 3, 1, 2, 0, 0, 0, 0, 1, 0, 0, 1, 2);
    }

    public static void 漂泊者气动() throws IOException {
        write("漂泊者·气动", 1406, 3, 3, 1, 2, 0, 0, 0, 0, 1, 0, 0, 2, 0);
    }

    public static void 秧秧() throws IOException {
        write("秧秧", 1402, 3, 3, 1, 2, 0, 0, 0, 0, 2, 0, 0, 0, 2);
    }

    public static void 白芷() throws IOException {
        write("白芷", 1103, 1, 1, 0, 1, 2, 3, 0, 0, 3, 0, 0, 0, 1);
    }

    public static void 炽霞() throws IOException {
        write("炽霞", 1202, 3, 3, 1, 1, 0, 0, 0, 0, 1, 0, 0, 2, 2);
    }

    public static void 散华() throws IOException {
        write("散华", 1102, 3, 3, 1, 2, 0, 0, 0, 0, 2, 0, 2, 1, 1);
    }

    public static void 秋水() throws IOException {
        write("秋水", 1403, 2, 3, 1, 2, 0, 0, 0, 0, 1, 0, 0, 0, 0);
    }

    public static void 丹瑾() throws IOException {
        write("丹瑾", 1602, 3, 3, 1, 2, 0, 0, 0, 0, 1, 0, 0, 2, 2);
    }

    public static void 莫特斐() throws IOException {
        write("莫特斐", 1204, 3, 3, 1, 2, 0, 0, 0, 0, 2, 0, 0, 1, 1);
    }

    public static void 渊武() throws IOException {
        write("渊武", 1303, 1, 1, 0, 0, 0, 0, 2, 3, 3, 0, 0, 1, 1);
    }

    public static void 桃祈() throws IOException {
        write("桃祈", 1601, 1, 1, 0, 0, 0, 0, 2, 3, 3, 0, 0, 1, 2);
    }

    public static void 釉瑚() throws IOException {
        write("釉瑚", 1106, 1, 1, 2, 3, 0, 0, 0, 0, 3, 0, 0, 2, 1);
    }

    public static void 珂莱塔() throws IOException {
        write("珂莱塔", 1107, 3, 3, 1, 2, 0, 0, 0, 0, 1, 0, 0, 2, 0);
    }

    public static void 灯灯() throws IOException {
        write("灯灯", 1504, 3, 3, 1, 2, 0, 0, 0, 0, 1, 2, 0, 1, 1);
    }

    public static void 洛可可() throws IOException {
        write("洛可可", 1606, 3, 3, 1, 2, 0, 0, 0, 0, 1, 0, 2, 1, 0);
    }

    public static void 菲比() throws IOException {
        write("菲比", 1506, 3, 3, 1, 2, 0, 0, 0, 0, 1, 1, 2, 0, 1);
    }

    public static void 布兰特() throws IOException {
        write("布兰特", 1206, 3, 3, 1, 2, 0, 0, 0, 0, 2, 1, 0, 0, 1);
    }

    public static void 坎特蕾拉() throws IOException {
        write("坎特蕾拉", 1607, 3, 3, 1, 2, 0, 0, 0, 0, 2, 1, 0, 0, 0);
    }

    public static void 赞妮() throws IOException {
        write("赞妮", 1507, 3, 3, 1, 2, 0, 0, 0, 0, 1, 0, 2, 0, 0);
    }

    public static void 夏空() throws IOException {
        write("夏空", 1407, 3, 3, 1, 2, 0, 0, 0, 0, 1, 1, 1, 0, 2);
    }

    public static void 卡提希娅() throws IOException {
        write("卡提希娅", 1409, 3, 3, 0, 0, 2, 2, 0, 0, 1, 1, 0, 0, 1);
    }

    public static void 露帕() throws IOException {
        write("露帕", 1207, 3, 3, 1, 2, 0, 0, 0, 0, 1, 0, 0, 0, 2);
    }

    public static void 弗洛洛() throws IOException {
        write("弗洛洛", 1608, 3, 3, 1, 2, 0, 0, 0, 0, 0, 2, 0, 1, 0);
    }

    public static void 奥古斯塔() throws IOException {
        write("奥古斯塔", 1306, 3, 3, 1, 2, 0, 0, 0, 0, 1, 0, 2, 0, 0);
    }

    public static void 尤诺() throws IOException {
        write("尤诺", 1410, 3, 3, 1, 2, 0, 0, 0, 0, 1, 0, 0, 0, 2);
    }

    public static void 嘉贝莉娜() throws IOException {
        write("嘉贝莉娜", 1208, 3, 3, 1, 2, 0, 0, 0, 0, 1, 0, 2, 0, 0);
    }

    public static void 仇远() throws IOException {
        write("仇远", 1411, 3, 3, 1, 2, 0, 0, 0, 0, 1, 0, 2, 0, 0);
    }

    public static void 千咲() throws IOException {
        write("千咲", 1508, 3, 3, 1, 2, 0, 0, 0, 0, 1, 0, 0, 0, 1);
    }

    public static void 卜灵() throws IOException {
        write("卜灵", 1307, 0, 0, 2, 3, 1, 1, 1, 1, 3, 0, 0, 0, 0);
    }

    public static void 琳奈() throws IOException {
        write("琳奈", 1509, 3, 3, 1, 2, 0, 0, 0, 0, 1, 2, 0, 0, 0);
    }

    public static void 莫宁() throws IOException {
        write("莫宁", 1209, 1, 0, 0, 0, 0, 0, 2, 3, 3, 0, 1, 0, 1);
    }

    public static void 爱弥斯() throws IOException {
        write("爱弥斯", 1210, 3, 3, 1, 2, 0, 0, 0, 0, 1, 0, 0, 0, 2);
    }

    public static void 陆赫斯() throws IOException {
        write("陆·赫斯", 1510, 3, 3, 1, 2, 0, 0, 0, 0, 1, 2, 0, 0, 0);
    }

    public static void 西格莉卡() throws IOException {
        write("西格莉卡", 1412, 3, 3, 1, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0);
    }

    public static void 绯雪() throws IOException {
        write("绯雪", 1108, 3, 3, 1, 2, 0, 0, 0, 0, 1, 0, 0, 0, 2);
    }

    public static void 达妮娅() throws IOException {
        write("达妮娅", 1211, 3, 3, 1, 2, 0, 0, 0, 0, 1, 0, 0, 0, 2);
    }


}
