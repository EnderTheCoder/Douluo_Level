package douluolevel.douluolevel.core.skill;

import com.sucy.skill.SkillAPI;
import douluolevel.douluolevel.config.ConfigReader;
import douluolevel.douluolevel.data.QualityData;
import douluolevel.douluolevel.data.UserData;

import java.util.List;
import java.util.Map;

import static org.bukkit.Bukkit.getLogger;

public class Skill {

    public static void applySkill(UserData user) {
        List<QualityData> qualities = ConfigReader.getQualities();


        if (user.getQualities().size() > 0)
            for (QualityData quality : qualities) {

                for (Map.Entry<?, ?> entry : user.getQualities().entrySet()) {
                    if (entry.getKey().equals(quality.getName())) {

                        if (ConfigReader.isOnDebug()) {
                            getLogger().info(String.valueOf(quality.getSkill_ap().size()));
                        }
                        for (int i = 0; i < quality.getSkill_ap().size(); i++) {




                            int deserved_ap = quality.getSkill_ap_distance().get(i) * Integer.parseInt(entry.getValue().toString());
                            int now_ap = user.getSkillAPIPlayerData().getAttribute(quality.getSkill_ap().get(i));


                            if (ConfigReader.isOnDebug()) {
                                getLogger().info(String.valueOf(deserved_ap));
                                getLogger().info(String.valueOf(now_ap));
                            }

                            user.getSkillAPIPlayerData().giveAttribute(quality.getSkill_ap().get(i), deserved_ap - now_ap);
                        }
                    }
//                    for (int i = 0; i < quality.getSkill_skill().size(); i++) {
//                        //计算应得的等级
//                        int deserved_skill_level = quality.getSkill_skill_distance().get(i) * Integer.parseInt(entry.getValue().toString());
//                        //如果这个技能不存在就加给他
//                        if (!user.getSkillAPIPlayerData().hasSkill(quality.getSkill_skill().get(i)) || user.getSkillAPIPlayerData().getSkill(quality.getSkill_skill().get(i)).getLevel() == 0) {
//                            com.sucy.skill.api.skills.Skill skill = SkillAPI.getSkill(quality.getSkill_skill().get(i));
//                            if (ConfigReader.isOnDebug()) {
////                                user.getSkillAPIPlayerData().giveSkill(skill);
//
////                                getLogger().info(skill.getKey());
////                                getLogger().info(skill.getMessage());
////                                getLogger().info(String.valueOf(deserved_skill_level));
////                                getLogger().info(String.valueOf(user.getSkillAPIPlayerData().hasSkill("昊天飞锤")));
//
////                                user.getSkillAPIPlayerData().giveSkill(skill);
////                                getLogger().info(String.valueOf(user.getSkillAPIPlayerData().getSkill(quality.getSkill_skill().get(i)).getLevel()));
////                                user.getSkillAPIPlayerData().getSkill(skill.getName()).getLevel();
//                                user.getSkillAPIPlayerData().giveSkill(skill);
////                                user.getSkillAPIPlayerData().upgradeSkill(skill);
//
//                            }
////                            user.getSkillAPIPlayerData().giveSkill(skill);
//                        }
//
//
//                        //升级，直至应得的等级。
//                        while (user.getSkillAPIPlayerData().getSkillLevel(quality.getSkill_skill().get(i)) < deserved_skill_level) {
//                            if (!user.getSkillAPIPlayerData().upgradeSkill(SkillAPI.getSkill(quality.getSkill_skill().get(i)))) break;
//                        }
//
//
//                    }
                }

            }


    }

}
