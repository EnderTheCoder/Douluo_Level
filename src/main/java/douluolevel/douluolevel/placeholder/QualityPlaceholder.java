package douluolevel.douluolevel.placeholder;

import douluolevel.douluolevel.config.ConfigReader;
import douluolevel.douluolevel.data.QualityData;
import douluolevel.douluolevel.data.UserData;
import douluolevel.douluolevel.database.User;
import me.clip.placeholderapi.PlaceholderHook;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Map;

public class QualityPlaceholder extends PlaceholderHook {

//    public QualityPlaceholder(Plugin plugin, String identifier) {
//        super(plugin, identifier);
//    }



    @Override
    public String onPlaceholderRequest(Player player, String string) {

        if (string.contains("quality_data_")) {

            List<QualityData> qualities = ConfigReader.getQualities();

            for (QualityData quality : qualities) {
                if (string.equals("quality_data_exp_initial_" + quality.getName())) return String.valueOf(quality.getExp_initial());
                if (string.equals("quality_data_exp_distance_" + quality.getName())) return String.valueOf(quality.getExp_distance());
                if (string.equals("quality_data_max" + quality.getName())) return String.valueOf(quality.getMax());
                if (string.equals("quality_data_vip_max" + quality.getName())) return String.valueOf(quality.getVip_max());
                if (string.equals("quality_data_vip_permission" + quality.getName())) return String.valueOf(quality.getVip_permission());
                if (string.equals("quality_data_display_name" + quality.getName())) return String.valueOf(quality.getDisplay_name());
                for (int i = 0; i < quality.getAttributes().size(); i++) {
                    if (string.equals("quality_data_attribute_" + i + "_" + quality.getName())) return quality.getAttributes().get(i);
                }
                for (int i = 0; i < quality.getAttributes_distance().size(); i++) {
                    if (string.equals("quality_data_attribute_distance_" + i + "_" + quality.getName())) return String.valueOf(quality.getAttributes_distance().get(i));

                }
            }
        }

        return null;
    }
}
