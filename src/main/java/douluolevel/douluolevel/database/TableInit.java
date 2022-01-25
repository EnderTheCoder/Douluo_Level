package douluolevel.douluolevel.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TableInit {

    public static boolean checkTable(String tableName) {
        SQLite s = new SQLite();
        s.prepare("SELECT * FROM sqlite_master WHERE type='table' AND name = ?");
        s.bindString(1, tableName);
        s.execute();
        ResultSet resultSet = s.result();
        try {
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void table_user() {
        SQLite s = new SQLite();
        s.prepare("create table user\n" +
                "(\n" +
                "    username    varchar(64),\n" +
                "    uuid        varchar(32),\n" +
                "    level       integer,\n" +
                "    exp_record  integer,\n" +
                "    exp_current integer,\n" +
                "    qualities   text\n" +
                ");");
        s.execute();
    }


//    public static boolean


}
