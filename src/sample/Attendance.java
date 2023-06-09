package sample;


public class Attendance {
    private int attId;
    private int stdID;
    private String TchrCIN;
    private int modId;
    private String stts;


    // Constructor
    public Attendance(int attId, int stdID, String TchrCIN, int modId, String stts) {
        this.attId = attId;
        this.stdID = stdID;
        this.TchrCIN = TchrCIN;
        this.modId = modId;
        this.stts = stts;
    }

    // Getters
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



    // Setters
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


}
