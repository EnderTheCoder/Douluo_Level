package douluolevel.douluolevel.database;

import org.bukkit.ChatColor;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getViewDistance;

//用于创建数据库
public class SQLite {
    public void connect() {
            Connection connection = null;
            try {
                Class.forName("org.sqlite.JDBC");
                //如果没有数据库文件的话会自动创建
                connection = DriverManager.getConnection("jdbc:sqlite:./plugins/DouluoLevel/main.db");
            } catch ( Exception e ) {
                getLogger().severe( e.getClass().getName() + ": " + e.getMessage() );
                return;
            }
            getLogger().info(ChatColor.GREEN + "数据库连接成功");
    }
}
