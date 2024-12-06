package net.hamdi.virementservice.services;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.hamdi.virementservice.dto.BeneficiaireDTO;
import net.hamdi.virementservice.dto.VirementDTO;
import net.hamdi.virementservice.dto.VirementDetailsDTO;
import net.hamdi.virementservice.entities.Virement;
import net.hamdi.virementservice.enums.VirementStatus;
import net.hamdi.virementservice.exceptions.InvalidVirementDataException;
import net.hamdi.virementservice.exceptions.InvalidVirementOperationException;
import net.hamdi.virementservice.exceptions.VirementNotFoundException;
import net.hamdi.virementservice.feign.BeneficiaireClient;
import net.hamdi.virementservice.mapper.VirementMapper;
import net.hamdi.virementservice.repositories.VirementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class VirementService {

    private final VirementRepository virementRepository;
    private final BeneficiaireClient beneficiaireClient;
    private final VirementMapper virementMapper;

    @Transactional
    public VirementDTO creerVirement(@Valid VirementDTO virementDTO) {

        // Validate beneficiary exists by Feign Client
        BeneficiaireDTO beneficiaire = beneficiaireClient.getBeneficiaryById(virementDTO.beneficiaireId());
        System.out.println(beneficiaire);
        if (beneficiaire == null) {
            throw new InvalidVirementDataException("Beneficiary with ID : " + virementDTO.beneficiaireId() + " , doesn't exist.");
        }

        // Validate RIB
        if (!beneficiaire.rib().equals(virementDTO.ribDestinataire())) {
            throw new InvalidVirementDataException("Invalid RIB.");
        }

        // Map DTO to Entity
        Virement virement = virementMapper.toEntity(virementDTO);
        virement.setDateVirement(LocalDateTime.now());

        // Set virement status to Pending (in this case to make sure the virement will be executed by a payment external API)
        // When the virement executed the status switch to Executed
        virement.setStatus(VirementStatus.PENDING);

        // Save and return as DTO
        Virement savedVirement = virementRepository.save(virement);
        return virementMapper.toDTO(savedVirement);
    }

    @Transactional(readOnly = true)
    public List<VirementDTO> getAllVirements() {
        return virementRepository.findAll().stream()
                .map(virementMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public VirementDTO getVirementById(Long id) {
        Virement virement = virementRepository.findById(id)
                .orElseThrow(() -> new VirementNotFoundException("The transfer with id : " + id + " doesn't exist."));
        return virementMapper.toDTO(virement);
    }

    @Transactional(readOnly = true)
    public List<VirementDTO> getVirementsByBeneficiaire(Long beneficiaireId) {
        return virementRepository.findByBeneficiaireId(beneficiaireId).stream()
                .map(virementMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public VirementDetailsDTO getVirementDetails(Long id) {
        Virement virement = virementRepository.findById(id)
                .orElseThrow(() -> new VirementNotFoundException("The transfer with id : " + id + " doesn't exist."));

        BeneficiaireDTO beneficiaire = Optional.ofNullable(beneficiaireClient.getBeneficiaryById(virement.getBeneficiaireId()))
                .orElseThrow(() -> new RuntimeException("Error while getting beneficiary details."));

        return virementMapper.toDetailsDTO(virement, beneficiaire);
    }
    // A provisional Virement execution endpoint to mark the payment as Executed
    @Transactional
    public VirementDTO markAsExecuted(Long id) {
        Virement virement = virementRepository.findById(id)
                .orElseThrow(() -> new VirementNotFoundException("The transfer with id : " + id + " doesn't exist."));
        if (virement.getStatus() != VirementStatus.PENDING) {
            throw new InvalidVirementOperationException("Transfer execution failed. ! Already Executed / Canceled.");
        }

        virement.setStatus(VirementStatus.EXECUTED);
        Virement updatedVirement = virementRepository.save(virement);
        return virementMapper.toDTO(updatedVirement);
    }

    @Transactional
    public VirementDTO updateVirement(Long id, @Valid VirementDTO virementDTO) {
        Virement existingVirement = virementRepository.findById(id)
                .orElseThrow(() -> new VirementNotFoundException("The transfer with id : " + id + " doesn't exist."));
        // Making sure that only the transfers which are still in Pending Status
        // We can't update a transfer which is already executed or canceled !
        if (existingVirement.getStatus() != VirementStatus.PENDING) {
            throw new InvalidVirementOperationException("Can't update the transfer details ! Already Executed / Canceled.");
        }

        existingVirement.setMontant(virementDTO.montant());
        existingVirement.setDescription(virementDTO.description());

        Virement updatedVirement = virementRepository.save(existingVirement);
        return virementMapper.toDTO(updatedVirement);
    }

    @Transactional
    public VirementDTO cancelVirement(Long id) {
        Virement virement = virementRepository.findById(id)
                .orElseThrow(() -> new VirementNotFoundException("The transfer with id : " + id + " doesn't exist."));
        if (virement.getStatus() != VirementStatus.PENDING) {
            throw new InvalidVirementOperationException("Can't cancel the transfer ! Already Executed / Canceled.");
        }
        // Cancel a transfer
        virement.setStatus(VirementStatus.CANCELLED);
        Virement cancelededVirement = virementRepository.save(virement);
        return virementMapper.toDTO(cancelededVirement);
    }


}
