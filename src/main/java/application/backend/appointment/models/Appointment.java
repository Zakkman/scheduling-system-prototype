package application.backend.appointment.models;

import application.backend.common.AbstractEntity;
import application.backend.users.models.User;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointer_id")
    @NotNull
    private User appointer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointee_id")
    @NotNull
    private User appointee;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime startTime;

    private LocalTime endTime;

    @NotBlank
    private String place;

    @NotBlank
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AppointmentStatus status;

}
