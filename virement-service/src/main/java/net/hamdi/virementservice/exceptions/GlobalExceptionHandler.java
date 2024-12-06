package net.hamdi.virementservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            VirementNotFoundException.class,
            InvalidVirementDataException.class,
            InvalidVirementOperationException.class
    })
    public ResponseEntity<ExceptionResponse> handleBusinessExceptions(
            RuntimeException ex,
            WebRequest request
    ) {
        BusinessErrorCodes errorCode = determineErrorCode(ex);

        ExceptionResponse response = ExceptionResponse.builder()
                .errorCode(errorCode.getCode())
                .message(ex.getMessage())
                .path(request.getDescription(false))
                .build();

        log.error("Business Exception: {}", ex.getMessage(), ex);

        return new ResponseEntity<>(response, getHttpStatus(errorCode));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        // Key: extract the field name where validation failed
                        FieldError::getField,
                        // Value: extract error message with null safety // Return default message if exists, otherwise provide a generic error message
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "Unknown validation error",
                        // Conflict resolution: if multiple errors exist for same field, keep first error
                        (firstError, secondError) -> firstError
                ));

        ExceptionResponse response = ExceptionResponse.builder()
                .errorCode(BusinessErrorCodes.VALIDATION_ERROR.getCode())
                .message("Validation failed")
                .validationErrors(errors)
                .build();

        log.error("Validation Exception: {}", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleUnexpectedException(
            Exception ex,
            WebRequest request
    ) {
        ExceptionResponse response = ExceptionResponse.builder()
                .errorCode(BusinessErrorCodes.INTERNAL_SERVER_ERROR.getCode())
                .message("An unexpected error occurred")
                .path(request.getDescription(false))
                .build();

        log.error("Unexpected Exception: {}", ex.getMessage(), ex);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private BusinessErrorCodes determineErrorCode(RuntimeException ex) {
        if (ex instanceof VirementNotFoundException) {
            return BusinessErrorCodes.VIREMENT_NOT_FOUND;
        } else if (ex instanceof InvalidVirementDataException) {
            return BusinessErrorCodes.INVALID_VIREMENT_DATA;
        } else if (ex instanceof InvalidVirementOperationException) {
            return BusinessErrorCodes.UNAUTHORIZED_OPERATION;
        }
        return BusinessErrorCodes.INTERNAL_SERVER_ERROR;
    }

    private HttpStatus getHttpStatus(BusinessErrorCodes errorCode) {
        return switch (errorCode) {
            case VIREMENT_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case DUPLICATE_VIREMENT -> HttpStatus.CONFLICT;
            case INVALID_VIREMENT_DATA, VALIDATION_ERROR -> HttpStatus.BAD_REQUEST;
            case UNAUTHORIZED_OPERATION -> HttpStatus.FORBIDDEN;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
