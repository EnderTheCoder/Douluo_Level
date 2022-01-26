package douluolevel.douluolevel.core;

import douluolevel.douluolevel.config.ConfigReader;
import douluolevel.douluolevel.data.LevelData;
import douluolevel.douluolevel.data.UserData;

import static douluolevel.douluolevel.config.ConfigReader.getLevel;

public class Level {
    //计算用户现在的等级
    public static int getPlayerNewLevel(UserData user, int expNew) {
        LevelData level = getLevel();

        int max = user.getPlayer().hasPermission(level.getVip_permission()) ? level.getVip_max() : level.getMax() ;

        int exp_now = user.getExp_record() + expNew;
        //防止等级超过最大值
        return Math.min(max, (exp_now - level.getExp_initial()) / level.getExp_distance() + 1);

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
        int expRequired = ConfigReader.getLevel().getExp_initial();
        expRequired += ConfigReader.getLevel().getExp_distance() * (newLevel - 1);
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


}
