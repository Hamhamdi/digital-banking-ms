package net.hamdi.virementservice.repositories;

import jakarta.validation.constraints.NotNull;
import net.hamdi.virementservice.entities.Virement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VirementRepository extends JpaRepository<Virement, Long> {
    List<Virement> findByBeneficiaireId(@NotNull(message = "Beneficiary ID is required") Long beneficiaireId);
}
