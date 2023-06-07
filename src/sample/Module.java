package sample;

public class Module {
    private Integer ModID;
    private String Name;
    private Integer FieldID;

    public Module(Integer ModID, String Name, Integer FieldID) {
        ModID = ModID;
        Name = Name;
        FieldID = FieldID;
    }

    public Integer getModID() {
        return ModID;
    }

    public String getName() {
        return Name;
    }

    public Integer getFieldID() {
        return FieldID;
    }

    public void setModID(Integer ModID) {
        ModID = ModID;
    }

    public void setName(String Name) {
        Name = Name;
    }

    public void setFieldID(Integer FieldID) {
        FieldID = FieldID;
    }
}