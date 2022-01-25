package douluolevel.douluolevel.data;

import java.util.List;

public class QualityData {


    String name;
    String display_name;
    int exp_initial;
    int exp_distance;
    int max;
    int vip_max;
    String vip_permission;
    List<String> attributes;
    List<Integer> attributes_distance;

    public String getName() {
        return name;
    }

    public int getExp_distance() {
        return exp_distance;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public int getExp_initial() {
        return exp_initial;
    }

    public int getMax() {
        return max;
    }

    public int getVip_max() {
        return vip_max;
    }

    public String getVip_permission() {
        return vip_permission;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public List<Integer> getAttributes_distance() {
        return attributes_distance;
    }

    public QualityData(String name, String display_name, int exp_initial, int exp_distance, int max, int vip_max, String vip_permission, List<String> attributes, List<Integer> attributes_distance) {
        this.name = name;
        this.attributes = attributes;
        this.attributes_distance = attributes_distance;
        this.display_name = display_name;
        this.exp_initial = exp_initial;
        this.vip_permission = vip_permission;
        this.max = max;
        this.exp_distance = exp_distance;
        this.vip_max = vip_max;
    }
}
