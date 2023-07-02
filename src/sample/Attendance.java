package sample;


public class Attendance {

    private int attId;
    private int stdID;
    private String TchrCIN;
    private int modId;
    private String stts;
    private String Date;



    public Attendance(int attId, int stdID, String TchrCIN, int modId, String stts, String Date) {
        this.attId = attId;
        this.stdID = stdID;
        this.TchrCIN = TchrCIN;
        this.modId = modId;
        this.stts = stts;
        this.Date = Date;
    }




    public int getAttId() {
        return attId;
    }

    public int getStdID() {
        return stdID;
    }

    public String getTchrCIN() {
        return TchrCIN;
    }

    public int getModId() {
        return modId;
    }

    public String getStts() {
        return stts;
    }

    public String getDate() { return Date;  }




    public void setAttId(int attId) {
        this.attId = attId;
    }

    public void setStdID(int stdID) {
        this.stdID = stdID;
    }

    public void setTchrCIN(String TchrCIN) {
        this.TchrCIN = TchrCIN;
    }

    public void setModId(int modId) {
        this.modId = modId;
    }

    public void setStts(String stts) {
        this.stts = stts;
    }

    public void setDate(String date) { this.Date = Date;}




}