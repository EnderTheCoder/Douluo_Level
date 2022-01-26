package douluolevel.douluolevel.database;

import douluolevel.douluolevel.config.ConfigReader;
import org.bukkit.ChatColor;

import java.sql.*;

import static org.bukkit.Bukkit.getLogger;
//用于创建数据库
public class SQLite {
    private Connection connection = null;
    private PreparedStatement statement = null;

    private Timestamp startTime;

    public void connect() {
        this.startTime = new Timestamp(System.currentTimeMillis());
            try {
                Class.forName("org.sqlite.JDBC");
                //如果没有数据库文件的话会自动创建
                this.connection = DriverManager.getConnection("jdbc:sqlite:./plugins/DouluoLevel/main.db");
            } catch ( Exception e ) {
                getLogger().severe( e.getClass().getName() + ": " + e.getMessage() );
                return;
            }
            if (ConfigReader.isOnDebug()) getLogger().info(ChatColor.GREEN + "数据库连接成功");
    }

    public Connection getConnection() {
        try {
            if (this.connection == null || !this.connection.isValid(1000)) connect();
        } catch (SQLException e) {
            getLogger().severe("获取数据库连接时发生错误");

            e.printStackTrace();
        }
        return connection;
    }

    public void prepare(String sql) {
        try {
            statement = getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            getLogger().severe("预处理SQL语句时发生错误");
            e.printStackTrace();
        }
    }

    public void bindString(int number, String value) {
        try {
            statement.setString(number, value);
        } catch (SQLException e) {
            getLogger().severe("绑定参数时时发生错误");
            e.printStackTrace();
        }
    }

    public void bindInt(int number, int value) {
        try {
            statement.setInt(number, value);
        } catch (SQLException e) {
            getLogger().severe("绑定参数时时发生错误");
            e.printStackTrace();
        }

    }

    public void bindDouble(int number, double value) {
        try {
            statement.setDouble(number, value);
        } catch (SQLException e) {
            getLogger().severe("绑定参数时时发生错误");
            e.printStackTrace();
        }
    }

    public void execute() {
        try {
            statement.execute();
        } catch (SQLException e) {
            getLogger().severe("执行SQL语句时发生错误");
            e.printStackTrace();
        }
    }

    public ResultSet result() {
        try {
            return statement.getResultSet();
        } catch (SQLException e) {
            getLogger().severe("获取数据库查询结果时发生错误");
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        //计算数据库查询所用时间并输出调试信息
        Timestamp endTime = new Timestamp(System.currentTimeMillis());

        if (ConfigReader.isOnDebug()) getLogger().info(String.format("数据库连接关闭，本次查询共用时%s毫秒", endTime.getTime() - startTime.getTime()));

        try {
            this.connection.close();
        } catch (SQLException e) {
            getLogger().severe("关闭数据库连接时发生错误");
            e.printStackTrace();
        }
    }

}

