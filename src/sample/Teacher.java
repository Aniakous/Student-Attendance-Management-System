package sample;

public class Teacher {
    private Integer TchrID;
    private String fullName;
    private String mail;
    private String Phone;


    public Teacher(Integer TchrID, String fullName, String mail, String Phone) {
        this.TchrID = TchrID;
        this.fullName = fullName;
        this.mail = mail;
        this.Phone = Phone;
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

    public String getPhone() {
        return Phone;
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

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }
}