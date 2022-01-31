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
    List<String> skill_ap;
    List<Integer> skill_ap_distance;
    List<String> skill_skill;
    List<Integer> skill_skill_distance;

    public List<Integer> getSkill_ap_distance() {
        return skill_ap_distance;
    }

    public List<Integer> getSkill_skill_distance() {
        return skill_skill_distance;
    }

    public List<String> getSkill_ap() {
        return skill_ap;
    }

    public List<String> getSkill_skill() {
        return skill_skill;
    }

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




    public QualityData(
            String name,
            String display_name,
            int exp_initial,
            int exp_distance,
            int max,
            int vip_max,
            String vip_permission,
            List<String> attributes,
            List<Integer> attributes_distance,
            List<String> skill_ap,
            List<Integer> skill_ap_distance,
            List<String> skill_skill,
            List<Integer> skill_skill_distance
    ) {
        this.name = name;
        this.attributes = attributes;
        this.attributes_distance = attributes_distance;
        this.display_name = display_name;
        this.exp_initial = exp_initial;
        this.vip_permission = vip_permission;
        this.max = max;
        this.exp_distance = exp_distance;
        this.vip_max = vip_max;
        this.skill_ap = skill_ap;
        this.skill_ap_distance = skill_ap_distance;
        this.skill_skill = skill_skill;
        this.skill_skill_distance = skill_skill_distance;
    }
}
