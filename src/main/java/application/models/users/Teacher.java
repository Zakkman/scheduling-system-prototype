package application.models.users;

import application.models.AbstractEntity;
import application.models.school.Department;
import application.models.school.Subject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class Teacher extends AbstractEntity {

    @OneToOne
    @NotNull
    @MapsId
    private User user;

    @Column(unique = true)
    @NotBlank(message = "Teacher ID is required")
    private String teacherId;

    @ManyToOne
    @NotNull
    private Department department;

    @ManyToMany
    @JoinTable(
        name = "teacher_subjects",
        joinColumns = @JoinColumn(name = "teacher_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjects;

    @Lob
    @NotNull(message = "Verification photo is required")
    private byte[] verificationPhoto;

    // Getters and Setters

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public byte[] getVerificationPhoto() {
        return verificationPhoto;
    }

    public void setVerificationPhoto(byte[] verificationPhoto) {
        this.verificationPhoto = verificationPhoto;
    }
}
