package douluolevel.douluolevel.command;

import douluolevel.douluolevel.core.AttributeTest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor {
    @Override

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        AttributeTest attributeTest = new AttributeTest();
        attributeTest.applyAttributes(player);

        return true;
    }
}
