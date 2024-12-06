package net.hamdi.virementservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.hamdi.virementservice.dto.VirementDTO;
import net.hamdi.virementservice.dto.VirementDetailsDTO;
import net.hamdi.virementservice.services.VirementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swagger.BaseController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/virement")
@Tag(name = BaseController.VIREMENT_TAG, description = BaseController.VIREMENT_DESCRIPTION)
public class VirementController {

    private final VirementService virementService;

    // Create a new Virement
    @PostMapping
    @Operation(summary = "Create a new transfer", description = "Initiates a new financial transfer between accounts")
    public ResponseEntity<VirementDTO> creerVirement(@Valid @RequestBody VirementDTO virementDTO) {

        VirementDTO createdTransfer = virementService.creerVirement(virementDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransfer);
    }

    // Get all Virements
    @GetMapping
    @Operation(summary = "List all transfers", description = "Retrieves a list of all financial transfers")
    public ResponseEntity<List<VirementDTO>> getAllVirements() {
        List<VirementDTO> allVirements = virementService.getAllVirements();
        return ResponseEntity.ok(allVirements);
    }

    // Get Virement by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get transfer by ID", description = "Retrieves details of a specific transfer")
    public ResponseEntity<VirementDTO> getVirementById(@PathVariable("id") Long id) {
        VirementDTO virement = virementService.getVirementById(id);
        return ResponseEntity.ok(virement);
    }

    // Get Virements by Beneficiary ID
    @GetMapping("/beneficiary/{beneficiaryId}")
    @Operation(summary = "Get transfers by beneficiary", description = "Retrieves all transfers associated with a specific beneficiary")
    public ResponseEntity<List<VirementDTO>> getVirementsByBeneficiaire(@PathVariable("beneficiaryId") Long beneficiaryId) {
        List<VirementDTO> virements = virementService.getVirementsByBeneficiaire(beneficiaryId);
        return ResponseEntity.ok(virements);
    }

    // Get Virement details (including Beneficiary details)
    @GetMapping("/{id}/details")
    @Operation(summary = "Get transfer details", description = "Retrieves comprehensive details of a transfer, including beneficiary information")
    public ResponseEntity<VirementDetailsDTO> getVirementDetails(@PathVariable("id") Long id) {
        VirementDetailsDTO virementDetails = virementService.getVirementDetails(id);
        return ResponseEntity.ok(virementDetails);
    }

    // Execute a Virement
    @PutMapping("/{id}/execute")
    @Operation(summary = "Execute transfer", description = "Marks a transfer as executed")
    public ResponseEntity<VirementDTO> executeVirement(@PathVariable("id") Long id) {
        VirementDTO executedVirement = virementService.markAsExecuted(id);
        return ResponseEntity.ok(executedVirement);
    }

    // Update a Virement
    @PutMapping("/{id}")
    @Operation(summary = "Update transfer", description = "Updates the details of an existing transfer")
    public ResponseEntity<VirementDTO> updateVirement(
            @PathVariable("id") Long id,
            @Valid @RequestBody VirementDTO virementDTO) {
        VirementDTO updatedVirement = virementService.updateVirement(id, virementDTO);
        return ResponseEntity.ok(updatedVirement);
    }

    // Cancel a Virement
    @PutMapping("/{id}/cancel")
    @Operation(summary = "Cancel transfer", description = "Cancels a pending transfer")
    public ResponseEntity<VirementDTO> cancelVirement(@PathVariable("id") Long id) {
        VirementDTO cancelledVirement = virementService.cancelVirement(id);
        return ResponseEntity.ok(cancelledVirement);
    }



}
