package douluolevel.douluolevel.placeholder;

import douluolevel.douluolevel.data.UserData;
import douluolevel.douluolevel.database.User;
import me.clip.placeholderapi.PlaceholderHook;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class UserPlaceholder extends PlaceholderHook {

//    public UserPlaceholder(Plugin plugin, String identifier) {
//        super(plugin, identifier);
//    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {

        if (params.contains("user_data_")) {

            if (player == null) return null;

            UserData user = User.getUser(player.getName());
            assert user != null;
            if (params.equals("user_data_username")) return user.getUsername();
            if (params.equals("user_data_uuid")) return String.valueOf(user.getUuid());
            if (params.equals("user_data_level")) return String.valueOf(user.getLevel());
            if (params.equals("user_data_exp_record")) return String.valueOf(user.getExp_record());
            if (params.equals("user_data_exp_current")) return String.valueOf(user.getExp_current());
            if (params.contains("user_data_quality_")) {
                for (Map.Entry<?, ?> entry : user.getQualities().entrySet()) {
                    if (params.equals("user_data_quality_level_"+ entry.getKey().toString())) return entry.getValue().toString();
                }
            }
        }
        return null;
    }
}
