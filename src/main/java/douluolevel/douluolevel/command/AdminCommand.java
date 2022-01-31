package douluolevel.douluolevel.command;

import com.sucy.skill.api.enums.ExpSource;
import douluolevel.douluolevel.DouluoLevel;
import douluolevel.douluolevel.config.ConfigReader;
import douluolevel.douluolevel.core.Level;
import douluolevel.douluolevel.data.QualityData;
import douluolevel.douluolevel.data.UserData;
import douluolevel.douluolevel.database.User;
import douluolevel.douluolevel.tools.Tools;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Map;


public class AdminCommand implements CommandExecutor {
    @Override

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (args.length < 1) return false;

        switch (args[0]) {
            case "set": {

                UserData user = User.getUser(args[2]);

                if (user == null) {
                    sender.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.SERVER, "player_not_found"));
                    return true;
                }

                if (args.length < 4) return false;

                switch (args[1]) {
                    //0:操作
                    //1:操作对象
                    //2:玩家
                    //3:数值
                    case "level": {
                        if (!Tools.isNumberLegal(args[3])) {
                            sender.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.SERVER, "number_illegal"));
                            return false;
                        }
                        user.setLevelAndExp(Integer.parseInt(args[3]));

                        sender.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.SERVER, "admin_successful_set_level"));


                        for (int i = 1; i <= Integer.parseInt(args[3]); i++)
                            Level.runLevelCommands(user, i);
                        break;
                    }
                    //0:操作
                    //1:操作对象
                    //2:玩家
                    //3:属性名称
                    //4:属性等级
                    case "quality": {

                        if (args.length < 5) return false;

                        if (!Tools.isNumberLegal(args[4])) {
                            sender.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.SERVER, "number_illegal"));
                            return false;
                        }
                        QualityData quality = ConfigReader.getQuality(args[3]);
                        if (quality == null) {
                            sender.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.SERVER, "quality_not_found"));
                            return false;
                        }

                        user.putQuality(quality, Integer.parseInt(args[4]));
                        sender.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.SERVER, "admin_successful_set_quality"));
                        break;
                    }
                    //0:操作
                    //1:操作对象
                    //2:玩家
                    //3:数值
                    case "exp_current": {
                        if (!Tools.isNumberLegal(args[3])) {
                            sender.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.SERVER, "number_illegal"));
                            return false;
                        }
                        user.setExp_current(Integer.parseInt(args[3]));
                        user.update();
                        sender.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.SERVER, "admin_successful_set_exp_current"));
                        break;
                    }
                    //0:操作
                    //1:操作对象
                    //2:玩家
                    //3:数值
                    case "exp_record": {
                        if (!Tools.isNumberLegal(args[3])) {
                            sender.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.SERVER, "number_illegal"));
                            return false;
                        }
                        user.setExpAndLevel(Integer.parseInt(args[3]));
                        for (int i = 1; i <= user.getLevel(); i++)
                            Level.runLevelCommands(user, i);
                        sender.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.SERVER, "admin_successful_set_exp_record"));
                        break;
                    }
                    default: return false;
                }

                break;
            }
            //清空某个玩家的数据
            case "purge": {
                UserData user = User.getUser(args[1]);

                if (user == null) {
                    sender.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.SERVER, "player_not_found"));
                    return true;
                }

                if (user.getOfflinePlayer().isOnline()) {
                    sender.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.SERVER, "player_is_online"));
                    return true;
                }

                user.purge();
                sender.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.SERVER, "admin_successful_purge"));
                break;
            }
            //初始化玩家数据
            case "remake": {
                UserData user = User.getUser(args[1]);

                if (user == null) {
                    sender.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.SERVER, "player_not_found"));
                    return true;
                }

                user.remake();

                sender.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.SERVER, "admin_successful_remake"));
                break;
            }
            //重载配置文件
            case "reload": {
                DouluoLevel.instance.saveDefaultConfig();
                DouluoLevel.instance.reloadConfig();

                for (Player player : Bukkit.getOnlinePlayers()) {
                    UserData user = User.getUser(player.getName());
                    assert user != null;
                    user.checkAndRemoveQuality();
                    user.applyAttribute();
                    user.applySKills();
                }
                sender.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.SERVER, "admin_successful_reload"));
                break;
            }
            //0:操作
            //1:用户名
            case "show": {

                UserData user = User.getUser(args[1]);
                if (user == null) {
                    sender.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.SERVER, "player_not_found"));
                    return true;
                }
                if (!user.getOfflinePlayer().isOnline()) {
                    sender.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.SERVER, "player_is_offline"));
                    return true;
                }
                sender.sendMessage(
                        ConfigReader.getMessage(ConfigReader.MessageType.SERVER, "player_level")
                                .replace("$player$", user.getUsername())
                                .replace("$player_level$", String.valueOf(user.getLevel()))
                );
                for (Map.Entry<?, ?> entry: user.getQualities().entrySet()) {

                    QualityData quality = ConfigReader.getQuality(entry.getKey().toString());

                    assert quality != null;
                    sender.sendMessage(
                            ConfigReader.getMessage(ConfigReader.MessageType.SERVER, "player_quality_list")
                                    .replace("$quality$", quality.getName())
                                    .replace("$quality_name$", quality.getDisplay_name())
                                    .replace("$quality_max$", String.valueOf(user.getQualityMaxLevel(quality)))
                                    .replace("$quality_level$", entry.getValue().toString())
                    );
                }
                break;
            }

            case "test": {
                sender.getServer();
//                Bukkit.dispatchCommand(new CommandSender(), "tell EnderTheCoder test");
                break;
            }

            default: return false;

        }


        return true;
    }
}
