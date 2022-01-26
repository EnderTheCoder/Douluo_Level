package douluolevel.douluolevel.command;


import douluolevel.douluolevel.config.ConfigReader;
import douluolevel.douluolevel.core.AttributeTest;
import douluolevel.douluolevel.core.attibute.Attribute;
import douluolevel.douluolevel.data.QualityData;
import douluolevel.douluolevel.data.UserData;
import douluolevel.douluolevel.database.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getPlayer;

public class Quality implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //这是一条只能在游戏中使用的命令
        if (!(sender instanceof Player)) {
            getLogger().warning("命令调用错误，您必须在游戏中使用此命令，在控制台使用此命令是无效的");
            return false;
        }

        Player player = (Player) sender;


        //检测参数数量
        if (args.length < 1) return false;

        switch (args[0]) {
            case "upgrade": {

                if (args.length != 2) return false;

                QualityData quality = ConfigReader.getQuality(args[1]);

                if (quality == null) {
                    player.sendMessage(ConfigReader.getMessage(ConfigReader.MessageType.CLIENT, "quality_not_found"));
                    return true;
                }

                UserData user = User.getUser(player.getName());
                assert user != null;

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

            case "list": {

                if (args.length != 1) return false;

                break;
            }
            //命令输入错误的参数即提示正确的命令用法
            default: return false;
        }



        return true;
    }
}
