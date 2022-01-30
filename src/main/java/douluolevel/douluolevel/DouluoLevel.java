package douluolevel.douluolevel;

import douluolevel.douluolevel.command.AdminCommand;
import douluolevel.douluolevel.command.QualityCommand;
import douluolevel.douluolevel.config.ConfigReader;
import douluolevel.douluolevel.database.SQLite;
import douluolevel.douluolevel.database.TableInit;
import douluolevel.douluolevel.listener.EXPEvent;
import douluolevel.douluolevel.listener.LoginEvent;
import douluolevel.douluolevel.placeholder.QualityPlaceholder;
import douluolevel.douluolevel.placeholder.UserPlaceholder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public final class DouluoLevel extends JavaPlugin {

    public static JavaPlugin instance;

    @Override
    public void onEnable() {
        //暴露出instance便于其他类访问
        instance = this;
        //保存默认的配置文件，如果配置文件没有的话会自动生成一个
        saveDefaultConfig();


        Bukkit.getPluginManager().registerEvents(new EXPEvent(), this);
        Bukkit.getPluginManager().registerEvents(new LoginEvent(), this);


        //数据库初始化
        SQLite sql = new SQLite();
        if (sql.getConnection() != null) {
            if (!TableInit.checkTable("user")) TableInit.table_user();
        } else {
            getLogger().severe("检测到未能创建数据库文件，请检查你的服务器所在目录的文件权限");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        //检测是否安装placeholder
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            //这里注册placeholder
//            new QualityPlaceholder().
//            new UserPlaceholder(this, "douluolevel").hook();
        } else {
            getLogger().severe("检测到你的服务器没有安装PlaceholderAPI，正在停用本插件");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info(ChatColor.AQUA + "你成功加载了本插件-|斗罗等级DouluoLevel|-,本插件由Ender开发，GitHub地址：https://github.com/EnderTheCoder/");

        if (Bukkit.getPluginCommand("leveladmin") != null) {
            Objects.requireNonNull(Bukkit.getPluginCommand("leveladmin")).setExecutor(new AdminCommand());
        }

        if (Bukkit.getPluginCommand("quality") != null) {
            Objects.requireNonNull(Bukkit.getPluginCommand("quality")).setExecutor(new QualityCommand());
        }


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
