package model;

import java.io.Serializable;
import java.util.Date;

public class Submission implements Serializable {
    private static final long serialVersionUID = 1L;

    private String projectId;
    private String submissionLink;
    private String assessmentType;
    private Date submissionDate;
    private String status;

    public Submission(String projectId, String submissionLink, String assessmentType, Date submissionDate) {
        this.projectId = projectId;
        this.submissionLink = submissionLink;
        this.assessmentType = assessmentType;
        this.submissionDate = submissionDate;
        this.status = "Pending"; // Default status
    }

    // Getters and setters
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getSubmissionLink() {
        return submissionLink;
    }

    public void setSubmissionLink(String submissionLink) {
        this.submissionLink = submissionLink;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
