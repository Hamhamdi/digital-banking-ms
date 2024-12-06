package net.hamdi.virementservice.dto;

public record BeneficiaireDTO (
        Long id,
        String firstName,
        String lastName,
        String rib,
        String type
){
}
