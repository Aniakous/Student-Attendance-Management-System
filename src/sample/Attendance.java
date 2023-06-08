package sample;

import java.util.Date;

public class Attendance {

    private int attId;
    private int stdID;
    private int tchrId;
    private String sem;
    private int modId;
    private String stts;
    private String date;


    public Attendance(int attId, int stdID, int tchrId, String sem, int modId, String stts, String date) {
        this.attId = attId;
        this.stdID = stdID;
        this.tchrId = tchrId;
        this.sem = sem;
        this.modId = modId;
        this.stts = stts;
        this.date = date;
    }


    public int getAttId() {
        return attId;
    }

    public int getStdID() {
        return stdID;
    }

    public int getTchrId() {
        return tchrId;
    }

    public String getSem() {
        return sem;
    }

    public int getModId() {
        return modId;
    }

    public String getStts() {
        return stts;
    }

    public String getDate() {
        return date;
    }


    public void setAttId(int attId) {
        this.attId = attId;
    }

    public void setStdID(int stdID) {
        this.stdID = stdID;
    }

    public void setTchrId(int tchrId) {
        this.tchrId = tchrId;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public void setModId(int modId) {
        this.modId = modId;
    }

    public void setStts(String stts) {
        this.stts = stts;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
