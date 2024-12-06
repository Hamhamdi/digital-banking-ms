package net.hamdi.virementservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessErrorCodes {
    // Business Errors
    VIREMENT_NOT_FOUND("V404", "Transfer not found"),
    DUPLICATE_VIREMENT("V409", "Duplicate transfer"),
    INVALID_VIREMENT_DATA("V400", "Invalid transfer data"),
    UNAUTHORIZED_OPERATION("V403", "Unauthorized operation"),
    VIREMENT_ALREADY_EXECUTED("V410", "Transfer already executed"),
    VIREMENT_ALREADY_CANCELLED("V411", "Transfer already cancelled"),

    // General Errors
    VALIDATION_ERROR("V400", "Validation failed"),
    INTERNAL_SERVER_ERROR("V500", "Internal server error");

    private final String code;
    private final String message;

}
