package sample;


public class Student {
    private Integer studentId;
    private String fullName;
    private Integer fieldId;
    private String mail;
    private String phone;


    public Student(Integer studentId, String fullName, Integer fieldId, String mail, String phone) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.fieldId = fieldId;
        this.mail = mail;
        this.phone = phone;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public String getMail() {
        return mail;
    }

    public String getPhone() {
        return phone;
    }



    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}