package net.hamdi.beneficiaryservice.mapper;

import net.hamdi.beneficiaryservice.dto.BeneficiaireDTO;
import net.hamdi.beneficiaryservice.entities.Beneficiaire;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BeneficiaireMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "rib", target = "rib")
    @Mapping(source = "type", target = "type")
    Beneficiaire toEntity(BeneficiaireDTO dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "rib", target = "rib")
    @Mapping(source = "type", target = "type")
    BeneficiaireDTO toDTO(Beneficiaire entity);
}
