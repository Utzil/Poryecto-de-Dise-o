package DAO;

import POJO.User;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDAO {

    private User user = null;

    private String[] querys = {
        "INSERT INTO tblUser (Username, Password, Alreadyvote) VALUES (?, ?, ?)",//0
        "SELECT * FROM tblUser",//1
        "SELECT * FROM tblUser WHERE IdUser = ",
        "SELECT * FROM tblUser WHERE UserName = ",
        "UPDATE tblUser SET ALREADYVOTE = ? WHERE IdUser = ?"
    };

    public UserDAO() {
        this.user = new User();
    }

    public boolean create()
            throws Exception {
        boolean created = false;
        try {
            HashMap hmap = new HashMap();
            hmap.put(1, getUser().getUserName());
            hmap.put(2, getUser().getPassword());
            hmap.put(3, getUser().isAlreadyVote());
            created = jdbc.getInstance().createOrUpdate(querys[0], hmap);
        } catch (Exception ex) {
            throw ex;
        }
        return created;
    }

    public List<User> readAll()
            throws Exception {
        List<User> userList = new ArrayList<User>();
        try {
            ResultSet rs = jdbc.getInstance().read(querys[1]);
            while (rs.next()) {
                User u = new User();
                u.setIdUser(rs.getInt("IdUser"));
                u.setUserName(rs.getString("UserName"));
                u.setPassword(rs.getString("Password"));
                u.setAlreadyVote(rs.getBoolean("AlreadyVote"));
                userList.add(u);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return userList;
    }

    public User read(int idUser)
            throws Exception {
        User u = null;
        try {
            String queryWithParams = querys[2] + idUser;
            ResultSet rs = jdbc.getInstance().read(queryWithParams);
            if (rs.next()) {
                u = new User();
                u.setIdUser(rs.getInt("IdUser"));
                u.setUserName(rs.getString("UserName"));
                u.setPassword(rs.getString("Password"));
                u.setAlreadyVote(rs.getBoolean("AlreadyVote"));
            }
        } catch (Exception ex) {
            throw ex;
        }
        return u;
    }

    public User read(String userName)
            throws Exception {
        User u = null;
        try {
            String queryWithParams = querys[3] + "'" + userName + "'";
            ResultSet rs = jdbc.getInstance().read(queryWithParams);
            if (rs.next()) {
                u = new User();
                u.setIdUser(rs.getInt("IdUser"));
                u.setUserName(rs.getString("UserName"));
                u.setPassword(rs.getString("Password"));
                u.setAlreadyVote(rs.getBoolean("AlreadyVote"));
            }
        } catch (Exception ex) {
            throw ex;
        }
        return u;
    }

    public boolean update()
            throws Exception {
        boolean updated = false;
        try {
            HashMap hmap = new HashMap();
            hmap.put(1, getUser().isAlreadyVote());
            hmap.put(2, getUser().getIdUser());
            updated = jdbc.getInstance().createOrUpdate(querys[4], hmap);
        } catch (Exception ex) {
            throw ex;
        }
        return updated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
