package net.hamdi.beneficiaryservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import net.hamdi.beneficiaryservice.enums.TypeBeneficiaire;

public record BeneficiaireDTO(
        Long id,

        @NotBlank(message = "First name is required")
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        String lastName,

        @NotBlank(message = "RIB is required")
        @Pattern(
                regexp = "^\\d{5}\\d{5}\\d{11}\\d{2}$",
                message = "Invalid RIB format. Must be 23 digits: BBBBBAAAAA-XXXXXXXXXXX-CC"
        )
        String rib,

        @NotNull(message = "Beneficiary type is required")
        TypeBeneficiaire type
) {
}
