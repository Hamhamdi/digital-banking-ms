package net.hamdi.beneficiaryservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.hamdi.beneficiaryservice.entities.Beneficiaire;
import net.hamdi.beneficiaryservice.services.BeneficiaireService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/beneficiaires")
public class BeneficiaireController {

    private final BeneficiaireService beneficiaryService;

    // Create a new Beneficiaire
    @PostMapping
    public ResponseEntity<Beneficiaire> createBeneficiary(@Valid @RequestBody Beneficiaire beneficiary) {
        Beneficiaire createdBeneficiary = beneficiaryService.createBeneficiary(beneficiary);
        return new ResponseEntity<>(createdBeneficiary, HttpStatus.CREATED);
    }

    // Get Beneficiaire by ID
    @GetMapping("/{id}")
    public ResponseEntity<Beneficiaire> getBeneficiaryById(@PathVariable Long id) {
        Beneficiaire beneficiary = beneficiaryService.getBeneficiaryById(id);
        return ResponseEntity.ok(beneficiary);
    }

    // Get Beneficiaire by RIB
    @GetMapping("/rib/{rib}")
    public ResponseEntity<Beneficiaire> getBeneficiaryByRib(@PathVariable String rib) {
        Beneficiaire beneficiary = beneficiaryService.getBeneficiaryByRib(rib);
        return ResponseEntity.ok(beneficiary);
    }

    // List all Beneficiaires
    @GetMapping
    public ResponseEntity<List<Beneficiaire>> getAllBeneficiaries() {
        List<Beneficiaire> beneficiaries = beneficiaryService.getAllBeneficiaries();
        return ResponseEntity.ok(beneficiaries);
    }

    // Update a Beneficiaire
    @PutMapping("/{id}")
    public ResponseEntity<Beneficiaire> updateBeneficiary(
            @PathVariable Long id,
            @Valid @RequestBody Beneficiaire beneficiaryDetails
    ) {
        Beneficiaire updatedBeneficiary = beneficiaryService.updateBeneficiary(id, beneficiaryDetails);
        return ResponseEntity.ok(updatedBeneficiary);
    }

    // Delete a Beneficiaire
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeneficiary(@PathVariable Long id) {
        beneficiaryService.deleteBeneficiary(id);
        return ResponseEntity.noContent().build();
    }

}
