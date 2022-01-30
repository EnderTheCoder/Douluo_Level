package douluolevel.douluolevel.command;


import douluolevel.douluolevel.config.ConfigReader;
import douluolevel.douluolevel.core.attibute.Attribute;
import douluolevel.douluolevel.data.QualityData;
import douluolevel.douluolevel.data.UserData;
import douluolevel.douluolevel.database.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static org.bukkit.Bukkit.getLogger;
public class QualityCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
//        getLogger().info(args[0]);
        //这是一条只能在游戏中使用的命令
        if (!(sender instanceof Player)) {
            getLogger().warning(ConfigReader.getMessage(ConfigReader.MessageType.SERVER, "invalid_client_command"));
            return false;
        }

        Player player = (Player) sender;

        //检测参数数量
        if (args.length < 1) return false;



        switch (args[0]) {
            //升级属性
            case "upgrade": {

                if (args.length != 2) return false;

                QualityData quality = ConfigReader.getQuality(args[1]);

                if (quality == null) {
                    player.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.CLIENT, "quality_not_found"));
                    return true;
                }

                UserData user = User.getUser(player.getName());
                assert user != null;

                if (user.getQualityLevel(quality.getName()) >= user.getQualityMaxLevel(quality)) {
                    player.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.CLIENT, "quality_level_max"));
                    return true;
                }

                if (douluolevel.douluolevel.core.Quality.getQualityUpgradeExp(user, quality) > user.getExp_current()) {
                    player.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.CLIENT, "quality_exp_not_enough"));
                    return true;
                }

                douluolevel.douluolevel.core.Quality.upgradeQuality(user, quality);

                player.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.CLIENT, "quality_upgrade_successful"));

                Attribute attribute = new Attribute();
                attribute.applyAttribute(user);
                break;
            }

            //列出可升级的属性
            case "list": {

                if (args.length != 1) return false;

                List<QualityData> qualities = ConfigReader.getQualities();

                UserData user = User.getUser(player.getName());

                assert user != null;
                for (QualityData quality : qualities) {

                    String message = ConfigReader.getMessage(ConfigReader.MessageType.CLIENT, "quality_list");

                    message = message.replace("$quality$", quality.getName());
                    message = message.replace("$quality_name$", quality.getDisplay_name());
                    message = message.replace("$quality_level$", String.valueOf(user.getQualityLevel(quality)));
                    message = message.replace("$quality_max$", String.valueOf(user.getQualityMaxLevel(quality)));

                    player.sendMessage(message);



                }


                break;
            }
            //命令输入错误的参数即提示正确的命令用法
            default: return false;
        }



        return true;
    }
}
