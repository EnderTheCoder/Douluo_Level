package douluolevel.douluolevel.data;

import com.alibaba.fastjson.JSONObject;
import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.player.PlayerData;
import douluolevel.douluolevel.config.ConfigReader;
import douluolevel.douluolevel.core.Level;
import douluolevel.douluolevel.core.Quality;
import douluolevel.douluolevel.core.attibute.Attribute;
import douluolevel.douluolevel.core.skill.Skill;
import douluolevel.douluolevel.database.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Objects;
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

    public void delete() {
        User.deleteUser(this);
    }

    public int getMaxLevel() {
        return getPlayer().hasPermission(ConfigReader.getLevel().getVip_permission()) ? ConfigReader.getLevel().getVip_max() : ConfigReader.getLevel().getMax();
    }

    public int getQualityMaxLevel(QualityData quality) {
        return getPlayer().hasPermission(quality.vip_permission) ? quality.getVip_max() : quality.getMax();
    }

    public int getQualityMaxLevel(String requiredQualityName) {
        return getQualityMaxLevel(Objects.requireNonNull(ConfigReader.getQuality(requiredQualityName)));
    }


    public int getQualityLevel(QualityData quality) {
        for (Map.Entry<?, ?> entry : getQualities().entrySet()) {
            if (entry.getKey().toString().equals(quality.getName())) return Integer.parseInt(entry.getValue().toString());
        }
        return 0;
    }

    public int getQualityLevel(String requiredQualityName) {
        for (Map.Entry<?, ?> entry : getQualities().entrySet()) {
            if (entry.getKey().toString().equals(requiredQualityName)) return Integer.parseInt(entry.getValue().toString());
        }
        return 0;
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

    public void setLevelAndExp (int level) {
        Level.setLevel(this, level);
    }

    public void purge() {
        User.deleteUser(this);
    }

    public void remake() {
        purge();
        User.userInit(this.getOfflinePlayer());
    }

    public void setExpAndLevel(int exp) {
        setLevel(Level.calcLevel(this, exp));
        setExp_record(exp);
        update();
    }

    public void applyAttribute() {
        Attribute attribute = new Attribute();
        attribute.applyAttribute(this);
    }

    public void putQuality(QualityData quality, int level) {
        Quality.putQuality(this, quality, level);
    }

    public void checkAndRemoveQuality() {
        Quality.checkAndRemoveQuality(this);
    }

    public PlayerData getSkillAPIPlayerData() {
        return SkillAPI.getPlayerData(getOfflinePlayer());
    }

    public void applySKills() {
        Skill.applySkill(this);
    }
}
