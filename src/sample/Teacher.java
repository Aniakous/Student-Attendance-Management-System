package sample;

public class Teacher {
    private String TchrCIN;
    private String fullName;
    private String mail;
    private String Phone;


    public Teacher(String TchrCIN, String fullName, String mail, String Phone) {
        this.TchrCIN = TchrCIN;
        this.fullName = fullName;
        this.mail = mail;
        this.Phone = Phone;
    }


    public String getTchrCIN() {
        return TchrCIN;
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



    public void setTchrCIN(String TchrCIN) {
        this.TchrCIN = TchrCIN;
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