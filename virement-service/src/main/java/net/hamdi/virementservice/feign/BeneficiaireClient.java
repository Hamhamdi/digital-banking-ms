package net.hamdi.virementservice.feign;

import net.hamdi.virementservice.dto.BeneficiaireDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "beneficiary-service", url = "http://localhost:8080")
public interface BeneficiaireClient {

    // Get Beneficiaire By Identifier
    @GetMapping("/api/beneficiaires/{id}")
    BeneficiaireDTO getBeneficiaryById(@PathVariable("id") Long id);

}
