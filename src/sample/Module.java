package sample;


import java.util.ArrayList;
import java.util.List;


public class Module {
    private Integer ModID;
    private String Name;
    private Integer FieldID;
    private String Desc;
    private List<Student> students;

    public Module(Integer ModID, String Name, Integer FieldID, String Desc) {
        this.ModID = ModID;
        this.Name = Name;
        this.FieldID = FieldID;
        this.Desc = Desc;
        this.students = new ArrayList<>();

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

    public String getDesc() { return Desc; }


    public List<Student> getStudents() {
        return students;
    }


    public void addStudent(Student student) {
        students.add(student);
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

    public void setDesc(String Desc) {

        Desc = Desc;
    }

}