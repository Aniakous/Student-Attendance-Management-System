package sample;

public class Teacher {
    private Integer TchrID;
    private String fullName;
    private String mail;


    public Teacher(Integer TchrID, String fullName, String mail) {
        TchrID = TchrID;
        this.fullName = fullName;
        this.mail = mail;
    }


    public Integer getTchrID() {
        return TchrID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMail() {
        return mail;
    }


    public void setTchrID(Integer TchrID) {
        TchrID = TchrID;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}