package douluolevel.douluolevel.data;

import com.alibaba.fastjson.JSONObject;
import douluolevel.douluolevel.database.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class UserData {
    String username;
    UUID uuid;
    int level;
    int exp_record;
    int exp_current;
    JSONObject qualities;

    public int getExp_current() {
        return exp_current;
    }

    public int getExp_record() {
        return exp_record;
    }

    public int getLevel() {
        return level;
    }

    public String getUsername() {
        return username;
    }

    public UUID getUuid() {
        return uuid;
    }

    public JSONObject getQualities() {
        return qualities;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(this.uuid);
    }

    public void setExp_current(int exp_current) {
        this.exp_current = exp_current;
    }

    public void setExp_record(int exp_record) {
        this.exp_record = exp_record;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setQualities(JSONObject qualities) {
        this.qualities = qualities;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void update() {
        User.updateUser(this);
    }

    public UserData(OfflinePlayer player, int level, int exp_record, int exp_current, JSONObject qualities) {
        this.username = player.getName();
        this.uuid = player.getUniqueId();
        this.level = level;
        this.exp_current = exp_current;
        this.exp_record = exp_record;
        this.qualities = qualities;

    }

    public UserData(String username, String uuid, int level, int exp_record, int exp_current, String qualities) {
        this.username = username;
        this.uuid = UUID.fromString(uuid);
        this.level = level;
        this.exp_current = exp_current;
        this.exp_record = exp_record;
        this.qualities = (JSONObject) JSONObject.parse(qualities);

    }

}
