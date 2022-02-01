package douluolevel.douluolevel.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import douluolevel.douluolevel.DouluoLevel;
import douluolevel.douluolevel.data.LevelData;
import douluolevel.douluolevel.data.QualityData;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;
import java.util.*;

import static org.bukkit.Bukkit.getLogger;

public class ConfigReader {
    static FileConfiguration config = DouluoLevel.instance.getConfig();

    public static boolean isOnDebug() {
        return config.getBoolean("debug");
    }

    //消息类型
    public enum MessageType {
        //客户端
        CLIENT,
        //服务端
        SERVER
    }

    //获取等级
    public static LevelData getLevel() {
        return new LevelData(
                config.getString("level.name"),
                config.getInt("level.max"),
                config.getInt("level.exp_initial"),
                config.getInt("level.exp_distance"),
                config.getInt("level.vip_max"),
                config.getString("level.vip_permission")
        );
    }
    //获取所有quality
    public static List<QualityData> getQualities() {
        List<QualityData> qualities= new ArrayList<>();
        List<?> qualitiesRaw = config.getList("qualities");
        for (Object quality : qualitiesRaw) {
            JSONObject qualityJson = (JSONObject) JSONObject.toJSON(quality);
            for (Map.Entry<?,?> entry1: qualityJson.entrySet()) {
                for (Map.Entry<?,?> entry2: ((JSONObject) JSONObject.toJSON(entry1)).entrySet()) {

                    List<String> attributes = new ArrayList<>();
                    JSONArray attributesArray = JSON.parseArray(getDesc(entry2.getValue().toString(), "attributes"));
                    if (attributesArray != null) attributes = attributesArray.toJavaList(String.class);

                    List<Integer> attributes_distance = new ArrayList<>();
                    JSONArray attributesDistanceArray = JSON.parseArray(getDesc(entry2.getValue().toString(), "attributes_distance"));
                    if (attributesDistanceArray != null) attributes_distance = attributesDistanceArray.toJavaList(Integer.class);

                    List<String> skill_ap = new ArrayList<>();
                    JSONArray skillAPArray = JSON.parseArray(getDesc(entry2.getValue().toString(), "skill_ap"));
                    if (skillAPArray != null) skill_ap = skillAPArray.toJavaList(String.class);

                    List<Integer> skill_ap_distance = new ArrayList<>();
                    JSONArray skillAPDistanceArray = JSON.parseArray(getDesc(entry2.getValue().toString(), "skill_ap_distance"));
                    if (skillAPDistanceArray != null) skill_ap_distance = skillAPDistanceArray.toJavaList(Integer.class);

                    //废弃占位代码
                    List<String> skill_skill = new ArrayList<>();
                    List<Integer> skill_skill_distance = new ArrayList<>();

                    qualities.add(new QualityData(
                            entry1.getKey().toString(),
                            getDesc(entry2.getValue().toString(), "display_name"),
                            Integer.parseInt(Objects.requireNonNull(getDesc(entry2.getValue().toString(), "exp_initial"))),
                            Integer.parseInt(Objects.requireNonNull(getDesc(entry2.getValue().toString(), "exp_distance"))),
                            Integer.parseInt(Objects.requireNonNull(getDesc(entry2.getValue().toString(), "max"))),
                            Integer.parseInt(Objects.requireNonNull(getDesc(entry2.getValue().toString(), "vip_max"))),
                            getDesc(entry2.getValue().toString(), "vip_permission"),
                            attributes,
                            attributes_distance,
                            skill_ap,
                            skill_ap_distance,
                            skill_skill,
                            skill_skill_distance
                    ));
                }
            }
        }
        return qualities;
    }
    //获取单一quality
    public static QualityData getQuality(String requiredQualityName) {
        List<QualityData> qualities = getQualities();
        for (QualityData quality : qualities) {
            if (quality.getName().equals(requiredQualityName)) return quality;
        }
        return null;
    }
    //反射
    private static String getDesc(String jsonStr, String key) {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        for (Map.Entry<?,?> entry : jsonObject.entrySet()) {
            if (key.equals(entry.getKey())) {
                return entry.getValue().toString();
            }
        }
        return null;
    }

    //用于获取提示信息
    public static String getMessage(MessageType type, String messageName) {

        return config.getString("message." + (type == MessageType.CLIENT ? "client" : "server") + "." + messageName);

    }
    //检测是否存在消息
    public static boolean isSpecialLevelMessageExists(int level) {
        return config.getString("message.client.special." + level) != null;
    }
    //检测是否存在登记
    public static boolean isLevelCommandExists(int level) {
        return config.getList("level_command." + level) != null;
    }

    public static List<String> getLevelCommands(int level) {
        return config.getStringList("level_command." + level);
    }

}
