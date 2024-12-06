package net.hamdi.virementservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    private String errorCode;
    private String message;
    private String path;

    private Map<String, String> validationErrors;
}
