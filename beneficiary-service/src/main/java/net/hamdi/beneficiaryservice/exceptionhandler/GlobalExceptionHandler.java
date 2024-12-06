package net.hamdi.beneficiaryservice.exceptionhandler;

import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler{

    // Handler for custom Business Exceptions
    @ExceptionHandler({
            BeneficiaireNotFoundException.class,
            DuplicateBeneficiaryException.class,
            InvalidBeneficiaryDataException.class
    })
    public ResponseEntity<ExceptionResponse> handleBusinessExceptions(
            RuntimeException ex,
            WebRequest request
    ) {
        // Determine ErrorCode From BusinessErrorCodes for the specific exception
        BusinessErrorCodes errorCode = determineErrorCode(ex);

        ExceptionResponse response = ExceptionResponse.builder()
                .errorCode(errorCode.getCode())
                .message(ex.getMessage())
                .path(request.getDescription(false))
                .build();
        // Save log for Traceability
        log.error("Business Exception: {}", ex.getMessage(), ex);

        return new ResponseEntity<>(response, getHttpStatus(errorCode));
    }

    // Handler for Validation Exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }
        );

        ExceptionResponse response = ExceptionResponse.builder()
                .errorCode(BusinessErrorCodes.VALIDATION_ERROR.getCode())
                .message("Validation failed")
                .path(request.getDescription(false))
                .validationErrors(errors)
                .build();

        log.error("Validation Exception: {}", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handler for Constraint Violation Exceptions
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            WebRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );

        ExceptionResponse response = ExceptionResponse.builder()
                .errorCode(BusinessErrorCodes.VALIDATION_ERROR.getCode())
                .message("Constraint validation failed")
                .path(request.getDescription(false))
                .validationErrors(errors)
                .build();

        log.error("Constraint Violation Exception: {}", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handler for unexpected exceptions
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

    // Helper method to determine error code
    private BusinessErrorCodes determineErrorCode(RuntimeException ex) {
        if (ex instanceof BeneficiaireNotFoundException) {
            return BusinessErrorCodes.BENEFICIARY_NOT_FOUND;
        } else if (ex instanceof DuplicateBeneficiaryException) {
            return BusinessErrorCodes.DUPLICATE_BENEFICIARY;
        } else if (ex instanceof InvalidBeneficiaryDataException) {
            return BusinessErrorCodes.INVALID_BENEFICIARY_DATA;
        }
        return BusinessErrorCodes.INTERNAL_SERVER_ERROR;
    }

    // Helper method to determine HTTP status
    private HttpStatus getHttpStatus(BusinessErrorCodes errorCode) {
        return switch (errorCode) {
            case BENEFICIARY_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case DUPLICATE_BENEFICIARY -> HttpStatus.CONFLICT;
            case INVALID_BENEFICIARY_DATA, VALIDATION_ERROR -> HttpStatus.BAD_REQUEST;
            case UNAUTHORIZED_OPERATION -> HttpStatus.FORBIDDEN;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

}
