package douluolevel.douluolevel.listener;

import douluolevel.douluolevel.core.Quality;
import douluolevel.douluolevel.core.attibute.Attribute;
import douluolevel.douluolevel.data.UserData;
import douluolevel.douluolevel.database.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginEvent implements Listener {
    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //若用户第一次登录初始化数据库
        if (!User.isUserExists(player.getName())) {
            User.userInit(player);
        }

        UserData user = User.getUser(player.getName());

        assert user != null;
        //在登录时给予玩家attribute加成
        user.applyAttribute();
        //在登录时给予玩家skillAPI加成
        user.applySKills();

        //登录时检测有没有已经被移除的quality，若有则删除
        Quality.checkAndRemoveQuality(user);
    }
}
