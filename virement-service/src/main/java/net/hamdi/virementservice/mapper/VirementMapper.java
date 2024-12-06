package net.hamdi.virementservice.mapper;

import net.hamdi.virementservice.dto.BeneficiaireDTO;
import net.hamdi.virementservice.dto.VirementDTO;
import net.hamdi.virementservice.dto.VirementDetailsDTO;
import net.hamdi.virementservice.entities.Virement;
import net.hamdi.virementservice.enums.TypeVirement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface VirementMapper {

    // Mapping from Virement to VirementDTO
    @Mapping(target = "id", source = "id")
    @Mapping(target = "beneficiaireId", source = "beneficiaireId")
    @Mapping(target = "ribSource", source = "ribSource")
    @Mapping(target = "ribDestinataire", source = "ribDestinataire")
    @Mapping(target = "montant", source = "montant")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "type", source = "type")
    VirementDTO toDTO(Virement entity);

    // Mapping from VirementDTO to Virement
    @Mapping(target = "id", source = "id")
    @Mapping(target = "beneficiaireId", source = "beneficiaireId")
    @Mapping(target = "ribSource", source = "ribSource")
    @Mapping(target = "ribDestinataire", source = "ribDestinataire")
    @Mapping(target = "montant", source = "montant")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "type", source = "type")
    Virement toEntity(VirementDTO dto);

    @Mapping(target = "virementId", source = "virement.id")
    @Mapping(target = "ribSource", source = "virement.ribSource")
    @Mapping(target = "ribDestinataire", source = "virement.ribDestinataire")
    @Mapping(target = "montant", source = "virement.montant")
    @Mapping(target = "description", source = "virement.description")
    @Mapping(target = "dateVirement", source = "virement.dateVirement")
    @Mapping(target = "type", source = "virement.type", qualifiedByName = "mapVirementType")
    @Mapping(target = "beneficiaireId", source = "beneficiaire.id")
    @Mapping(target = "beneficiaireNom", source = "beneficiaire.firstName")
    @Mapping(target = "beneficiairePrenom", source = "beneficiaire.lastName")
    @Mapping(target = "beneficiaireRib", source = "beneficiaire.rib")
    @Mapping(target = "beneficiaireType", source = "beneficiaire.type")
    VirementDetailsDTO toDetailsDTO(Virement virement, BeneficiaireDTO beneficiaire);


    // Enum mapping methods
    @Named("mapVirementType")
    default String mapVirementType(TypeVirement type) {
        return type != null ? type.name() : null;
    }

    @Named("mapDTOToVirementType")
    default TypeVirement mapDTOToVirementType(String type) {
        return type != null ? TypeVirement.valueOf(type) : null;
    }
}
