package net.hamdi.beneficiaryservice.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.hamdi.beneficiaryservice.dto.BeneficiaireDTO;
import net.hamdi.beneficiaryservice.services.BeneficiaireService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swagger.BaseController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/beneficiaires")
@Tag(name = BaseController.BENEFICIARY_TAG, description = BaseController.BENEFICIARY_DESCRIPTION)
public class BeneficiaireController {

    private final BeneficiaireService beneficiaryService;


    // Create a new Beneficiaire
    @PostMapping
    @Operation(summary = "Create a new beneficiary", description = "Adds a new beneficiary to the system")
    public ResponseEntity<BeneficiaireDTO> createBeneficiary(@Valid @RequestBody BeneficiaireDTO beneficiaryDTO) {
        BeneficiaireDTO createdBeneficiary = beneficiaryService.createBeneficiary(beneficiaryDTO);
        return new ResponseEntity<>(createdBeneficiary, HttpStatus.CREATED);
    }

    // Get Beneficiaire by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get beneficiary by ID", description = "Retrieves beneficiary details using their unique identifier")
    public ResponseEntity<BeneficiaireDTO> getBeneficiaryById(@PathVariable("id") Long id) {
        BeneficiaireDTO beneficiaryDTO = beneficiaryService.getBeneficiaryById(id);
        return ResponseEntity.ok(beneficiaryDTO);
    }

    // Get Beneficiaire by RIB
    @GetMapping("/rib/{rib}")
    @Operation(summary = "Get beneficiary by RIB", description = "Retrieves a beneficiary details using their RIB (Relevé d'Identité Bancaire)")
    public ResponseEntity<BeneficiaireDTO> getBeneficiaryByRib(@PathVariable String rib) {
        BeneficiaireDTO beneficiaryDTO = beneficiaryService.getBeneficiaryByRib(rib);
        return ResponseEntity.ok(beneficiaryDTO);
    }

    // List all Beneficiaires
    @GetMapping
    @Operation(summary = "List all beneficiaries", description = "Retrieves a list of all beneficiaries in the system")
    public ResponseEntity<List<BeneficiaireDTO>> getAllBeneficiaries() {
        List<BeneficiaireDTO> beneficiaries = beneficiaryService.getAllBeneficiaries();
        return ResponseEntity.ok(beneficiaries);
    }

    // Update a Beneficiaire
    @PutMapping("/{id}")
    @Operation(summary = "Update a beneficiary", description = "Updates the details of an existing beneficiary")
    public ResponseEntity<BeneficiaireDTO> updateBeneficiary(
            @PathVariable Long id,
            @Valid @RequestBody BeneficiaireDTO beneficiaryDTO
    ) {
        BeneficiaireDTO updatedBeneficiary = beneficiaryService.updateBeneficiary(id, beneficiaryDTO);
        return ResponseEntity.ok(updatedBeneficiary);
    }

    // Delete a Beneficiaire
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a beneficiary", description = "Removes a beneficiary from the system")
    public ResponseEntity<Void> deleteBeneficiary(@PathVariable Long id) {
        beneficiaryService.deleteBeneficiary(id);
        return ResponseEntity.noContent().build();
    }

}
