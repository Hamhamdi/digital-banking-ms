package net.hamdi.virementservice.feign;

import net.hamdi.virementservice.dto.BeneficiaireDTO;

import java.util.Map;

public class BeneficiaireClientFallback implements BeneficiaireClient {
    @Override
    public BeneficiaireDTO getBeneficiaryById(Long id) {
        throw new RuntimeException("Beneficiary service is unavailable");
    }

}
