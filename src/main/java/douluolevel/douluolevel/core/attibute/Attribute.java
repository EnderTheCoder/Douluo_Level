package douluolevel.douluolevel.core.attibute;

import douluolevel.douluolevel.data.QualityData;
import douluolevel.douluolevel.data.UserData;
import org.bukkit.entity.Player;
import org.serverct.ersha.attributeapi.api.ExAttributeAPI;
import org.serverct.ersha.attributeapi.attribute.data.AttributeData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static douluolevel.douluolevel.config.ConfigReader.getQualities;
import static org.bukkit.Bukkit.getPlayer;

public class Attribute {

    public void applyAttribute(UserData userData) {
        List<String> list = new ArrayList<>();
        List<QualityData> qualities= getQualities();

        for (Map.Entry<?, ?> entry: userData.getQualities().entrySet()) {
            for (QualityData quality : qualities) {
                if (quality.getName().equals(entry.getKey())) {
                    for (int i = 0; i < quality.getAttributes().size(); i++) {
                        //计算用户的Attribute属性，并加入数据列表中
                        list.add(quality.getAttributes().get(i) + ": " + (quality.getAttributes_distance().get(i) * Integer.parseInt(entry.getValue().toString())));
                    }
                }
            }
        }
        //应用Attribute
        AttributeData data = new AttributeData(list);
        ExAttributeAPI.getHandleApi().addAttribute(getPlayer(userData.getUuid()), this.getClass(), data);
    }






}
