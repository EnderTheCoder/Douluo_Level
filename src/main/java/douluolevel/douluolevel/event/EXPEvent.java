package douluolevel.douluolevel.event;

import com.sucy.skill.api.event.PlayerExperienceGainEvent;
import douluolevel.douluolevel.core.Level;
import douluolevel.douluolevel.data.UserData;
import douluolevel.douluolevel.database.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class EXPEvent implements Listener {

    @EventHandler
    public void expUP(PlayerExperienceGainEvent event) {

        UserData user = User.getUser(event.getPlayerData().getPlayer().getName());

        int exp = (int) event.getExp();

        assert user != null;
        int newLevel = Level.getPlayerNewLevel(user, exp);
        Level.addExpRecord(user, exp);
        Level.addExpCurrent(user, exp);
        if (newLevel > user.getLevel()) {
            Level.setLevel(user, newLevel);
        }
    }
}
