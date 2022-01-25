package douluolevel.douluolevel.database;

import douluolevel.douluolevel.data.UserData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class User {

    public static void updateUser(UserData user) {
        SQLite sqLite = new SQLite();
        sqLite.prepare("update user set username = ?, uuid = ?, level = ?, exp_record = ?, exp_current = ?, qualities = ? WHERE username = ?");
        sqLite.bindString(1, user.getUsername());
        sqLite.bindString(2, user.getUuid().toString());
        sqLite.bindInt(3, user.getLevel());
        sqLite.bindInt(4, user.getExp_record());
        sqLite.bindInt(5, user.getExp_current());
        sqLite.bindString(6, user.getQualities().toJSONString());
        sqLite.bindString(7, user.getUsername());
        sqLite.execute();
        sqLite.close();
    }

    public static UserData getUser(String username) {
        SQLite sqLite= new SQLite();
        sqLite.prepare("SELECT * FROM user WHERE username = ?");
        sqLite.bindString(1, username);
        sqLite.execute();
        ResultSet resultSet = sqLite.result();
        try {
            if (!resultSet.next()) return null;
            else {
                UserData user = new UserData(
                        resultSet.getString("username"),
                        resultSet.getString("uuid"),
                        resultSet.getInt("level"),
                        resultSet.getInt("exp_record"),
                        resultSet.getInt("exp_current"),
                        resultSet.getString("qualities")
                );
                sqLite.close();
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteUser(UserData user) {
        SQLite sqLite = new SQLite();
        sqLite.prepare("DELETE FORM user WHERE username = ?");
        sqLite.bindString(1, user.getUsername());
        sqLite.execute();
        sqLite.close();
    }

    public static void insertUser(UserData user) {
        SQLite sqLite = new SQLite();
        sqLite.prepare("INSERT INTO user (username, uuid, level, exp_record, exp_current, qualities) VALUES (?, ?, ?, ?, ?, ?)");
        sqLite.bindString(1, user.getUsername());
        sqLite.bindString(2, user.getUuid().toString());
        sqLite.bindInt(3, user.getLevel());
        sqLite.bindInt(4, user.getExp_record());
        sqLite.bindInt(5, user.getExp_current());
        sqLite.bindString(6, user.getQualities().toJSONString());
        sqLite.execute();
        sqLite.close();
    }

    public static boolean isUserExists(String username) {
        return getUser(username) != null;
    }

}
