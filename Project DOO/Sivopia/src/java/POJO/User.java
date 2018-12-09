package POJO;

import DAO.UserDAO;

public class User {

    private int IdUser;
    private String UserName;
    private String Password;
    private boolean AlreadyVote;
    private UserDAO uD;
    public User() {
        this.IdUser = -1;
        this.UserName = "";
        this.Password = "";
        this.AlreadyVote = false;
    }

    public User(int idUser, String userName, String password, boolean alreadyVote) {
        this.IdUser = idUser;
        this.UserName = userName;
        this.Password = password;
        this.AlreadyVote = alreadyVote;
    }

    public int getIdUser() {
        return IdUser;
    }

    public void setIdUser(int IdUser) {
        this.IdUser = IdUser;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public boolean isAlreadyVote() {
        return AlreadyVote;
    }

    public void setAlreadyVote(boolean AlreadyVote) {
        this.AlreadyVote = AlreadyVote;
    }

}
