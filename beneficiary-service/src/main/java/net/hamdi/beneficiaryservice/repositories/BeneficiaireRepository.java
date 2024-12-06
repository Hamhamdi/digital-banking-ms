package net.hamdi.beneficiaryservice.repositories;

import net.hamdi.beneficiaryservice.entities.Beneficiaire;
import net.hamdi.beneficiaryservice.enums.TypeBeneficiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeneficiaireRepository extends JpaRepository<Beneficiaire,Long > {

    // Get beneficiaries By RIB
    Optional<Beneficiaire> findByRib(String rib );

    // Get beneficiaries By LastName
    List<Beneficiaire> findByLastName(String lastname);

    // Get beneficiaries By Type (Physique/Morale)
    List<Beneficiaire> findByType(TypeBeneficiaire type);

    // Custom query to get beneficiaries By FullName
    @Query("SELECT b FROM Beneficiaire b" +
            " WHERE b.firstName LIKE %:name%" +
            " OR b.lastName LIKE %:name%")
    List<Beneficiaire> findByFullNameContaining(@Param("name") String name);

    // Check if beneficiary exists with a specific RIB
    boolean existsByRib(String rib);

}
