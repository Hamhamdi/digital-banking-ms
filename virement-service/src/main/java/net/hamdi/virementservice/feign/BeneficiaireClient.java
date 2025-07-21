package net.hamdi.virementservice.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import net.hamdi.virementservice.dto.BeneficiaireDTO;
import net.hamdi.virementservice.exceptions.InvalidVirementDataException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "beneficiary-service", url = "http://localhost:8081")
public interface BeneficiaireClient {

    // Get Beneficiaire By Identifier
    @GetMapping("/api/beneficiaires/{id}")
    @CircuitBreaker(name = "beneficiaryServiceCB",fallbackMethod = "getDefaultBeneficiary")
    BeneficiaireDTO getBeneficiaryById(@PathVariable("id") Long id);

    default BeneficiaireDTO getDefaultBeneficiary(Long id, Exception exception){
        throw new InvalidVirementDataException("Le service bénéficiaire est indisponible.");
    }
}
