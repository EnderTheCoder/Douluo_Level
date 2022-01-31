package douluolevel.douluolevel.placeholder;

import douluolevel.douluolevel.DouluoLevel;
import douluolevel.douluolevel.config.ConfigReader;
import douluolevel.douluolevel.data.QualityData;
import douluolevel.douluolevel.data.UserData;
import douluolevel.douluolevel.database.User;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Map;


public class Placeholder extends PlaceholderExpansion {

    private final DouluoLevel plugin;

    public Placeholder(DouluoLevel plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "Ender";
    }

    @Override
    public String getIdentifier() {
        return "douluolevel";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {


        List<QualityData> qualities = ConfigReader.getQualities();

        if (params.contains("user_")) {

            if (player == null) return null;

            UserData user = User.getUser(player.getName());
            assert user != null;
            if (params.equals("user_username")) return user.getUsername();
            if (params.equals("user_uuid")) return String.valueOf(user.getUuid());
            if (params.equals("user_level")) return String.valueOf(user.getLevel());
            if (params.equals("user_exp_record")) return String.valueOf(user.getExp_record());
            if (params.equals("user_exp_current")) return String.valueOf(user.getExp_current());
            if (params.contains("user_quality_")) {
                for (Map.Entry<?, ?> entry : user.getQualities().entrySet()) {
                    if (params.equals("user_quality_level_"+ entry.getKey().toString())) return entry.getValue().toString();
                }
            }
        }
        if (params.contains("quality_")) {

            for (QualityData quality : qualities) {
                if (params.equals("quality_exp_initial_" + quality.getName()))
                    return String.valueOf(quality.getExp_initial());
                if (params.equals("quality_xp_distance_" + quality.getName()))
                    return String.valueOf(quality.getExp_distance());
                if (params.equals("quality_max" + quality.getName())) return String.valueOf(quality.getMax());
                if (params.equals("quality_vip_max" + quality.getName())) return String.valueOf(quality.getVip_max());
                if (params.equals("quality_vip_permission" + quality.getName()))
                    return String.valueOf(quality.getVip_permission());
                if (params.equals("quality_display_name" + quality.getName()))
                    return String.valueOf(quality.getDisplay_name());
                for (int i = 0; i < quality.getAttributes().size(); i++) {
                    if (params.equals("quality_attribute_" + i + "_" + quality.getName()))
                        return quality.getAttributes().get(i);
                }
                for (int i = 0; i < quality.getAttributes_distance().size(); i++) {
                    if (params.equals("quality_attribute_distance_" + i + "_" + quality.getName()))
                        return String.valueOf(quality.getAttributes_distance().get(i));

                }
            }
        }


        return null;
    }
}
