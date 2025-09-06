package application.backend.appointment.models;

import application.backend.common.AbstractEntity;
import application.backend.users.models.SpecificUser;
import application.backend.users.models.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
public class Appointment extends AbstractEntity {

    @ManyToOne
    @NotNull
    private User appointee;

    @ManyToOne
    @NotNull
    private User appointer;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime startTime;

    private LocalTime endTime;

    @NotBlank
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AppointmentStatus status;

}
