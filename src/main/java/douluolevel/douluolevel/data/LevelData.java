package douluolevel.douluolevel.data;

import org.bukkit.OfflinePlayer;

public class LevelData {
    //等级的名称
    String name;
    //等级上限
    int max;
    //等级经验需求初始值
    int exp_initial;
    //等级经验需求增长值
    int exp_distance;
    //VIP等级上限
    int vip_max;
    //vip需要的权限
    String vip_permission;

    public String getName() {
        return name;
    }

    public String getVip_permission() {
        return vip_permission;
    }

    public int getVip_max() {
        return vip_max;
    }

    public int getMax() {
        return max;
    }

    public int getExp_initial() {
        return exp_initial;
    }

    public int getExp_distance() {
        return exp_distance;
    }

    public LevelData(String name, int max, int exp_initial, int exp_distance, int vip_max, String vip_permission) {
        this.name = name;
        this.max = max;
        this.exp_initial = exp_initial;
        this.exp_distance = exp_distance;
        this.vip_max = vip_max;
        this.vip_permission = vip_permission;
    }


}
