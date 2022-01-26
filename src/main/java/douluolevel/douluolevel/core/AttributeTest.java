package douluolevel.douluolevel.core;

import org.bukkit.entity.Player;
import org.serverct.ersha.attributeapi.api.ExAttributeAPI;
import org.serverct.ersha.attributeapi.attribute.data.AttributeData;

import java.util.Arrays;
import java.util.List;

public class AttributeTest {

    public void applyAttributes(Player player) {
        List<String> list = Arrays.asList("物理伤害: 100","生命力: 100");
        AttributeData data = new AttributeData(list);

//增加属性
        ExAttributeAPI.getHandleApi().addAttribute(player, this.getClass(), data);


    }

}
