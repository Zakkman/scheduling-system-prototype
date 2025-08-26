package application.models.users;

import application.enums.Track;
import application.enums.Strand;
import application.enums.Specialization;
import application.enums.Section;
import application.models.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class Student extends AbstractEntity {

    @OneToOne
    @NotNull
    @MapsId
    private User user;

    @Pattern(regexp = "\\d{12}", message = "LRN must be a 12-digit number")
    @Column(unique = true)
    @NotBlank(message = "LRN is required")
    private String lrn;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Track track;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Strand strand;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Specialization specialization;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Section section;

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

    public String getLrn() {
        return lrn;
    }

    public void setLrn(String lrn) {
        this.lrn = lrn;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public Strand getStrand() {
        return strand;
    }

    public void setStrand(Strand strand) {
        this.strand = strand;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public byte[] getVerificationPhoto() {
        return verificationPhoto;
    }

    public void setVerificationPhoto(byte[] verificationPhoto) {
        this.verificationPhoto = verificationPhoto;
    }
}
