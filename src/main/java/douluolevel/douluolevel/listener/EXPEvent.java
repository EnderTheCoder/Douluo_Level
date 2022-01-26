package douluolevel.douluolevel.listener;

import com.sucy.skill.api.event.PlayerExperienceGainEvent;
import douluolevel.douluolevel.config.ConfigReader;
import douluolevel.douluolevel.core.Level;
import douluolevel.douluolevel.core.attibute.Attribute;
import douluolevel.douluolevel.data.UserData;
import douluolevel.douluolevel.database.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class EXPEvent implements Listener {

    @EventHandler
    public void expUP(PlayerExpChangeEvent event) {


        UserData user = User.getUser(event.getPlayer().getName());
        //获取经验
        int exp = event.getAmount();

        assert user != null;
        //计算新等级
        int newLevel = Level.getPlayerNewLevel(user, exp);
        //增加经验
        Level.addExpRecord(user, exp);
        Level.addExpCurrent(user, exp);
        //增加等级
        if (newLevel > user.getLevel()) {
            Level.upgrade(user, exp);

            user.getPlayer().sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.CLIENT, "level_upgrade"));
        }
        //重新应用Attribute
//        Attribute attribute = new Attribute();
//        attribute.applyAttribute(user);
    }
}
