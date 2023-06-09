package sample;

public class Field {

    private Integer FieldID;
    private String Name;

    public Field(Integer FieldID, String Name) {
        this.FieldID = FieldID;
        this.Name = Name;
    }

    public int getFieldID() {
        return FieldID;
    }

    public String getName() {
        return Name;
    }

    public void setFieldID(Integer FieldID) {
        this.FieldID = FieldID;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}
