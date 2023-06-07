package sample;

public class Field {

    private Integer FieldID;
    private String Name;

    public Field(Integer FieldID, String Name) {
        FieldID = FieldID;
        Name = Name;
    }

    public Integer getFieldID() {
        return FieldID;
    }

    public String getName() {
        return Name;
    }

    public void setFieldID(Integer FieldID) {
        FieldID = FieldID;
    }

    public void setName(String Name) {
        Name = Name;
    }
}
