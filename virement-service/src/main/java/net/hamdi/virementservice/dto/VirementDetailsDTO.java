package net.hamdi.virementservice.dto;

import java.time.LocalDateTime;

// The objective of this DTO is to combine Transfer and Beneficiary details
public record VirementDetailsDTO(
        Long virementId,
        String ribSource,
        String ribDestinataire,
        Double montant,
        String description,
        LocalDateTime dateVirement,
        String type,
        Long beneficiaireId,
        String beneficiaireNom,
        String beneficiairePrenom,
        String beneficiaireRib,
        String beneficiaireType
) {

}
