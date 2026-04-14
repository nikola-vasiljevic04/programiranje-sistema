package com.ecotravel.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TimeSlotRequestDTO {

    @NotNull(message = "ID avanture je obavezan")
    private Long adventureId;

    @NotNull(message = "Vreme početka je obavezno")
    @Future(message = "Vreme početka mora biti u budućnosti")

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @NotNull(message = "Vreme kraja je obavezno")
    @Future(message = "Vreme kraja mora biti u budućnosti")

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    @NotNull(message = "Maksimalan broj učesnika je obavezan")
    @Positive(message = "Broj učesnika mora biti veći od nule")
    private Integer maxParticipants;
}