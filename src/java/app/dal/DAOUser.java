package app.dal;

import java.util.ArrayList;
import java.util.List;
import app.entity.User;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOUser extends __local__DBContext {

    public List<User> extractResults(ResultSet rs) throws SQLException {
        List<User> result = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setUserId(rs.getInt("UserId"));
            user.setEmail(rs.getString("Email"));
            user.setPassword(rs.getString("Password"));
            user.setFullName(rs.getString("FullName"));
            user.setGender(rs.getString("Gender"));
            user.setMobile(rs.getString("Mobile"));
            user.setRole(rs.getString("Role"));
            user.setIsActive(rs.getBoolean("IsActive"));
            result.add(user);
        }
        return result;
    }

    public User getByEmail(String email) throws SQLException {
        String sql = "select * from [User] u\n"
                + "inner join [Gender] g on u.[GenderId] = g.[GenderId]\n"
                + "inner join [Role] r on u.[RoleId] = r.[RoleId]\n"
                + "where [Email] = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, email);
        List<User> result = extractResults(stmt.executeQuery());
        return result.isEmpty() ? null : result.get(0);
    }

    public User getById(int id) throws SQLException {
        String sql = "select * from [User] u\n"
                + "inner join [Gender] g on u.[GenderId] = g.[GenderId]\n"
                + "inner join [Role] r on u.[RoleId] = r.[RoleId]\n"
                + "where [UserId] = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        List<User> result = extractResults(stmt.executeQuery());
        return result.isEmpty() ? null : result.get(0);
    }

    public void updatePasswordById(int id, String password) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("update [User] set [Password] = ? where [UserId] = ?");
        stmt.setString(1, password);
        stmt.setInt(2, id);
        stmt.executeUpdate();
    }

    public void updatePassByUser(String user, String pass) {
        String sql = "UPDATE User\n"
                + "   SET Password = ?\n"
                + " WHERE Email = ?";
        PreparedStatement pre;
        try {
            pre = connection.prepareStatement(sql);
            pre.setString(1, pass);
            pre.setString(2, user);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //=============================
    
    private Vector<User> getFull(String sql) {
        Vector<User> Out = new Vector<User>();
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                Out.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getBoolean(8)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return Out;
    }

    public Vector<User> getAll() {
        return getFull("select * from User");
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM User where UserId = '" + id + "';";
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            if (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getBoolean(8));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM User where Email = ?";
        try {
            PreparedStatement preStat = connection.prepareStatement(sql);
            preStat.setString(1, email);
            ResultSet rs = preStat.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getBoolean(8));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public byte[] getProfileImage(int id) {
        String sql = "SELECT * FROM ProfilePicture where UserId = '" + id + "';";
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            if (rs.next()) {
                return rs.getBytes(2);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int insertProfileImage(int id, byte[] image) {
        String sql = "INSERT INTO ProfilePicture (UserId, Image)\n"
                + "     VALUES (?,?)";
        try {
            PreparedStatement preStat = connection.prepareStatement(sql);
            preStat.setInt(1, id);
            preStat.setBytes(2, image);
            return preStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateProfileImage(int id, byte[] image) {
        if (getProfileImage(id) == null) {
            return insertProfileImage(id, image);
        } else {
            String sql = "UPDATE ProfilePicture\n"
                    + " SET image = ?"
                    + " WHERE id = ?;";
            try {
                PreparedStatement preStat = connection.prepareStatement(sql);
                preStat.setInt(2, id);
                preStat.setBytes(1, image);
                return preStat.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    public int updateUserProfile(int id, String fullName, int genderId, String mobile) {
        String sql = "UPDATE User\n"
                + "   SET FullName = ?\n"
                + "      ,GenderId = ?\n"
                + "      ,Mobile = ?\n"
                + " WHERE UserId = ?";
        try {
            PreparedStatement preStat = connection.prepareStatement(sql);
            preStat.setInt(4, id);
            preStat.setString(1, fullName);
            preStat.setInt(2, genderId);
            preStat.setString(3, mobile);
            return preStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
