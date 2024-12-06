package net.hamdi.virementservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import net.hamdi.virementservice.enums.TypeVirement;

public record VirementDTO(
        Long id,

        @NotNull(message = "Beneficiary ID is required")
        Long beneficiaireId,

        @NotNull(message = "Source RIB is required")
        @Pattern(
                regexp = "^\\d{5}\\d{5}\\d{11}\\d{2}$",
                message = "Invalid RIB format. Must be 23 digits: BBBBBAAAAA-XXXXXXXXXXX-CC"
        )
        String ribSource,

        @NotNull(message = "Destination RIB is required")
        @Pattern(
                regexp = "^\\d{5}\\d{5}\\d{11}\\d{2}$",
                message = "Invalid RIB format. Must be 23 digits: BBBBBAAAAA-XXXXXXXXXXX-CC"
        )
        String ribDestinataire,

        @Positive(message = "Transfer amount must be positive")
        Double montant,

        String description,

        @NotNull(message = "Transfer type is required")
        TypeVirement type
) {
}
