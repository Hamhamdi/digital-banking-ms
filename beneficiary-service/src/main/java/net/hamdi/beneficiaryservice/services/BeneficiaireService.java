package net.hamdi.beneficiaryservice.services;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.hamdi.beneficiaryservice.dto.BeneficiaireDTO;
import net.hamdi.beneficiaryservice.entities.Beneficiaire;
import net.hamdi.beneficiaryservice.exceptionhandler.BeneficiaireNotFoundException;
import net.hamdi.beneficiaryservice.exceptionhandler.DuplicateBeneficiaryException;
import net.hamdi.beneficiaryservice.mapper.BeneficiaireMapper;
import net.hamdi.beneficiaryservice.repositories.BeneficiaireRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Data
@Service
public class BeneficiaireService {

    private final BeneficiaireRepository beneficiaireRepository;
    private final BeneficiaireMapper beneficiaireMapper;
    private final Validator validator;

    // Create a new beneficiaire
    @Transactional
    public BeneficiaireDTO createBeneficiary(BeneficiaireDTO beneficiaryDTO) {

        // Check if RIB already exists
        if (beneficiaireRepository.existsByRib(beneficiaryDTO.rib())) {
            throw new DuplicateBeneficiaryException("Beneficiary with this RIB already exist !");
        }

        // Convert DTO to entity
        Beneficiaire beneficiary = beneficiaireMapper.toEntity(beneficiaryDTO);
        Beneficiaire savedBeneficiary = beneficiaireRepository.save(beneficiary);
        System.out.println(beneficiary);

        // Convert entity back to DTO
        return beneficiaireMapper.toDTO(savedBeneficiary);
    }

    // Update an existing beneficiaire
    @Transactional
    public BeneficiaireDTO updateBeneficiary(Long id, @Valid BeneficiaireDTO beneficiaryDTO) {
        Beneficiaire existingBeneficiary = beneficiaireRepository.findById(id)
                .orElseThrow(() -> new BeneficiaireNotFoundException("Beneficiary not found with id: " + id));

        // Update fields
        existingBeneficiary.setFirstName(beneficiaryDTO.firstName());
        existingBeneficiary.setLastName(beneficiaryDTO.lastName());
        existingBeneficiary.setType(beneficiaryDTO.type());

        Beneficiaire updatedBeneficiary = beneficiaireRepository.save(existingBeneficiary);

        // Convert entity back to DTO
        return beneficiaireMapper.toDTO(updatedBeneficiary);
    }

    // Find beneficiaire by ID
    @Transactional(readOnly = true)
    public BeneficiaireDTO getBeneficiaryById(Long id) {
        Beneficiaire beneficiary = beneficiaireRepository.findById(id)
                .orElseThrow(() -> new BeneficiaireNotFoundException("Beneficiary not found with id: " + id));
        return beneficiaireMapper.toDTO(beneficiary);
    }

    // Find beneficiaire by RIB
    @Transactional(readOnly = true)
    public BeneficiaireDTO getBeneficiaryByRib(String rib) {
        Beneficiaire beneficiary = beneficiaireRepository.findByRib(rib)
                .orElseThrow(() -> new BeneficiaireNotFoundException("Beneficiary not found with RIB: " + rib));
        return beneficiaireMapper.toDTO(beneficiary);
    }

    // List all beneficiaries
    @Transactional(readOnly = true)
    public List<BeneficiaireDTO> getAllBeneficiaries() {
        List<Beneficiaire> beneficiaries = beneficiaireRepository.findAll();
        return beneficiaries.stream()
                .map(beneficiaireMapper::toDTO)
                .collect(Collectors.toList());
    }


    // Delete a beneficiaire
    @Transactional
    public void deleteBeneficiary(Long id) {
        if (!beneficiaireRepository.existsById(id)) {
            throw new BeneficiaireNotFoundException("Beneficiary not found with id: " + id);
        }
        beneficiaireRepository.deleteById(id);
    }

}
