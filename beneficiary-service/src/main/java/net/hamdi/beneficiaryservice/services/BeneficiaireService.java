package net.hamdi.beneficiaryservice.services;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.hamdi.beneficiaryservice.entities.Beneficiaire;
import net.hamdi.beneficiaryservice.exceptionhandler.BeneficiaireNotFoundException;
import net.hamdi.beneficiaryservice.exceptionhandler.DuplicateBeneficiaryException;
import net.hamdi.beneficiaryservice.repositories.BeneficiaireRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Data
@Service
public class BeneficiaireService {

    private final BeneficiaireRepository beneficiaireRepository;

    // Create a new beneficiaire
    @Transactional
    public Beneficiaire createBeneficiary(@Valid Beneficiaire beneficiary) {
        // Check if RIB already exists
        if (beneficiaireRepository.existsByRib(beneficiary.getRib())) {
            throw new DuplicateBeneficiaryException("Beneficiary with this RIB already exists");
        }
        return beneficiaireRepository.save(beneficiary);
    }

    // Update an existing beneficiaire
    @Transactional
    public Beneficiaire updateBeneficiary(Long id, @Valid Beneficiaire beneficiaryDetails) {
        Beneficiaire existingBeneficiary = beneficiaireRepository.findById(id)
                .orElseThrow(() -> new BeneficiaireNotFoundException("Beneficiary not found with id: " + id));

        // Update fields
        existingBeneficiary.setFirstName(beneficiaryDetails.getFirstName());
        existingBeneficiary.setLastName(beneficiaryDetails.getLastName());
        existingBeneficiary.setType(beneficiaryDetails.getType());

        return beneficiaireRepository.save(existingBeneficiary);
    }

    // Find beneficiaire by ID
    @Transactional(readOnly = true)
    public Beneficiaire getBeneficiaryById(Long id) {
        return beneficiaireRepository.findById(id)
                .orElseThrow(() -> new BeneficiaireNotFoundException("Beneficiary not found with id: " + id));
    }

    // Find beneficiaire by RIB
    @Transactional(readOnly = true)
    public Beneficiaire getBeneficiaryByRib(String rib) {
        return beneficiaireRepository.findByRib(rib)
                .orElseThrow(() -> new BeneficiaireNotFoundException("Beneficiary not found with RIB: " + rib));
    }

    // List all beneficiaries
    @Transactional(readOnly = true)
    public List<Beneficiaire> getAllBeneficiaries() {
        return beneficiaireRepository.findAll();
    }

    // Delete a beneficiaire
    @Transactional
    public void deleteBeneficiary(Long id) {
        Beneficiaire beneficiaire = getBeneficiaryById(id);
        beneficiaireRepository.delete(beneficiaire);
    }

}
