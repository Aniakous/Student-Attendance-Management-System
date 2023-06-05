package sample;

import java.util.Date;

public class Student {
    private Integer studentId;
    private String fullName;
    private String  birth;
    private Integer fieldId;
    private String mail;
    private Integer phone;

    public Student(Integer studentId, String fullName, String birth, Integer fieldId, String mail, Integer phone) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.birth = birth;
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

    public String getBirth() {
        return birth;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public String getMail() {
        return mail;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setBirth(String  birth) {
        this.birth = birth;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }
}