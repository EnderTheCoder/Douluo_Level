package douluolevel.douluolevel.database;

import org.omg.CORBA.PUBLIC_MEMBER;

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
                ");\n" +
                "\n" +
                "create unique index user_username_uindex\n" +
                "    on user (username);\n" +
                "\n" +
                "create unique index user_uuid_uindex\n" +
                "    on user (uuid);\n");
        s.execute();
        s.close();
    }


//    public static boolean


}
