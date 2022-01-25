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
    public static void putQuality(String username, QualityData quality, int level) {
        UserData user = User.getUser(username);

        assert user != null;
        if (user.getQualities() == null) {
            JSONObject newUserQualities = new JSONObject();
            newUserQualities.put(quality.getName(), level);
            user.setQualities(newUserQualities);
        } else user.getQualities().put(quality.getName(), level);

        User.updateUser(user);

    }

    //升级某个quality
    public static void upgradeQuality(String username, QualityData quality) {

        UserData user = User.getUser(username);
        assert user != null;
        JSONObject userQualities = user.getQualities();

        if (userQualities.get(quality.getName()) == null) putQuality(username, quality, 1);
        else putQuality(username, quality, Integer.parseInt(Objects.requireNonNull(user.getQualities().get(quality.getName()).toString()) + 1));
    }

    //从玩家数据移除quality
    public static void removeQuality(String username, String qualityToRemove) {
        UserData user = User.getUser(username);
        assert user != null;
        user.getQualities().remove(qualityToRemove);
        User.updateUser(user);
    }

    //检查玩家的quality是否存在，不存在即移除
    public static void checkAndRemoveQuality(String username) {
        UserData user = User.getUser(username);
        List<QualityData> qualities = ConfigReader.getQualities();
        assert user != null;
        for (Map.Entry<?, ?> entry : user.getQualities().entrySet()) {
            if (ConfigReader.getQuality(entry.getKey().toString()) == null)
                removeQuality(username, entry.getKey().toString());
        }

    }
}
