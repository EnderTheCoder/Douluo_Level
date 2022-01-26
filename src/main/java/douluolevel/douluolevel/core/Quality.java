package douluolevel.douluolevel.core;

import com.alibaba.fastjson.JSONObject;
import douluolevel.douluolevel.config.ConfigReader;
import douluolevel.douluolevel.data.QualityData;
import douluolevel.douluolevel.data.UserData;
import douluolevel.douluolevel.database.User;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SplittableRandom;

public class Quality {
    //put某个quality进入玩家数据
    public static void putQuality(UserData user, QualityData quality, int level) {
        assert user != null;
        if (user.getQualities() == null) {
            JSONObject newUserQualities = new JSONObject();
            newUserQualities.put(quality.getName(), level);
            user.setQualities(newUserQualities);
        } else user.getQualities().put(quality.getName(), level);

        User.updateUser(user);

    }

    //计算升级某个属性需要的经验值
    public static int getQualityUpgradeExp(UserData user, QualityData quality) {
        for (Map.Entry<?, ?> entry : user.getQualities().entrySet()) {
            if (quality.getName().equals(entry.getKey().toString())) {
                return quality.getExp_distance();
            }
        }
        return quality.getExp_initial();

    }


    //升级某个quality
    public static void upgradeQuality(UserData user, QualityData quality) {

        assert user != null;
        JSONObject userQualities = user.getQualities();

        if (userQualities.get(quality.getName()) == null) putQuality(user, quality, 1);
        else putQuality(user, quality, Integer.parseInt(Objects.requireNonNull(user.getQualities().get(quality.getName()).toString())) + 1);

        Level.subtractExpCurrent(user, getQualityUpgradeExp(user, quality));

    }

    //从玩家数据移除quality
    public static void removeQuality(UserData user, String qualityToRemove) {
        assert user != null;
        user.getQualities().remove(qualityToRemove);
        User.updateUser(user);
    }

    //检查玩家的quality是否存在，不存在即移除
    public static void checkAndRemoveQuality(UserData user) {
        assert user != null;
        for (Map.Entry<?, ?> entry : user.getQualities().entrySet()) {
            if (ConfigReader.getQuality(entry.getKey().toString()) == null)
                removeQuality(user, entry.getKey().toString());
        }

    }
}
