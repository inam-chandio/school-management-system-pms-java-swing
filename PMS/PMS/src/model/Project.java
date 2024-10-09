package model;

import java.io.Serializable;

public class Project implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String studentId;
    private String assessmentType;
    private String supervisorId;
    private String secondMarkerId;

    public Project(String id, String name, String studentId, String assessmentType, String supervisorId, String secondMarkerId) {
        this.id = id;
        this.name = name;
        this.studentId = studentId;
        this.assessmentType = assessmentType;
        this.supervisorId = supervisorId;
        this.secondMarkerId = secondMarkerId;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId;
    }

    public String getSecondMarkerId() {
        return secondMarkerId;
    }

    public void setSecondMarkerId(String secondMarkerId) {
        this.secondMarkerId = secondMarkerId;
    }
}
