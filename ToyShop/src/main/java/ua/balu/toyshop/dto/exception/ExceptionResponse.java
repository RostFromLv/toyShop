package ua.balu.toyshop.dto.exception;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Data
public class ExceptionResponse {
    private String message;
    private int status;
}
