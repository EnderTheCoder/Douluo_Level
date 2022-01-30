package douluolevel.douluolevel.core;

import douluolevel.douluolevel.config.ConfigReader;
import douluolevel.douluolevel.data.LevelData;
import douluolevel.douluolevel.data.UserData;
import org.bukkit.Bukkit;

import java.util.List;

import static douluolevel.douluolevel.config.ConfigReader.getLevel;

public class Level {
    //计算用户现在的等级
    public static int getPlayerNewLevel(UserData user, int expNew) {
        return calcLevel(user, user.getExp_record() + expNew);
    }

    public static int calcLevel(UserData user, int exp) {
        LevelData level = getLevel();
        int max = user.getPlayer().hasPermission(level.getVip_permission()) ? level.getVip_max() : level.getMax();
        return Math.max(Math.min(max, (exp - level.getExp_initial()) / level.getExp_distance() + 1), 0);
    }

    //自然升级
    public static void upgrade(UserData user, int expNew) {
        assert user != null;
        user.setLevel(getPlayerNewLevel(user, expNew));
        user.update();
    }

    //管理员设置等级
    public static void setLevel(UserData user, int newLevel) {


        user.setLevel(newLevel);
        int expRequired;
        if (newLevel == 0) expRequired = 0;
        else {
            expRequired = ConfigReader.getLevel().getExp_initial();
            expRequired += ConfigReader.getLevel().getExp_distance() * (newLevel - 1);
        }
        user.setExp_record(expRequired);
        user.update();
    }

    public static void setExpRecord(UserData user, int newExpRecord) {
        user.setExp_record(newExpRecord);

        user.update();
    }

    public static void setExpCurrent(UserData user, int newExpCurrent){
        user.setExp_current(newExpCurrent);
        user.update();
    }

    public static void addExpCurrent(UserData user, int addedExpCurrent) {
        setExpCurrent(user, user.getExp_current() + addedExpCurrent);
    }

    public static void subtractExpCurrent(UserData user, int subtractedExpCurrent) {
        setExpCurrent(user, user.getExp_current() - subtractedExpCurrent);
    }

    public static void addExpRecord(UserData user, int addedExpRecord) {
        setExpRecord(user, user.getExp_record() + addedExpRecord);
    }

//    public static void subtractExpRecord(UserData user, int subtractedExpCurrent) {
//
//    }

    public static void runLevelCommands(UserData user, int newLevel) {
        if (ConfigReader.isSpecialLevelMessageExists(newLevel)) {
            user.getPlayer().sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.CLIENT, "level_upgrade.special." + newLevel));
        }
    }

    public static void runLevelMessages(UserData user, int newLevel) {
        if (ConfigReader.isLevelCommandExists(newLevel)) {

            List<String> commands = ConfigReader.getLevelCommands(newLevel);

            for (String command : commands) {
                Bukkit.dispatchCommand(user.getPlayer(), command.replace("$player$", user.getUsername()));
            }

        }
    }

}
