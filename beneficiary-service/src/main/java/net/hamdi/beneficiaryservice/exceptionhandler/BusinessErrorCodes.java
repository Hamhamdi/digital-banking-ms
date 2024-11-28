package net.hamdi.beneficiaryservice.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessErrorCodes {

    // Beneficiary specific error codes
    BENEFICIARY_NOT_FOUND("B404", "Beneficiary not found"),
    DUPLICATE_BENEFICIARY("B409", "Beneficiary already exists"),
    INVALID_BENEFICIARY_DATA("B400", "Invalid beneficiary data"),
    UNAUTHORIZED_OPERATION("B403", "Unauthorized operation"),

    // Generic error codes
    VALIDATION_ERROR("G400", "Validation failed"),
    INTERNAL_SERVER_ERROR("G500", "Internal server error");

    private final String code;
    private final String message;

}
