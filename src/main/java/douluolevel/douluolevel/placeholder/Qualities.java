package douluolevel.douluolevel.placeholder;

import me.clip.placeholderapi.PlaceholderHook;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Qualities extends EZPlaceholderHook {

    public Qualities(Plugin plugin, String identifier) {
        super(plugin, identifier);
    }

    public void PAPIHooker(Plugin plugin){
    }

    @Override
    public String onPlaceholderRequest(Player player, String string) {

        if (string.contains("quality_")) {

        }

        return null;
    }
}
