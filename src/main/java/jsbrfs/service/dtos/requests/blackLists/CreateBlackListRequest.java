package jsbrfs.service.dtos.requests.blackLists;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateBlackListRequest(Long applicantId,
                                     @NotBlank String reason,
                                     LocalDate date) {
}
