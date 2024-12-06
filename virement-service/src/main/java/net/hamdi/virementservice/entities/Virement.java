package net.hamdi.virementservice.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.hamdi.virementservice.enums.TypeVirement;
import net.hamdi.virementservice.enums.VirementStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "virements")
@Entity
public class Virement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Beneficiary ID is required")
    @Column(name = "beneficiaire_id")
    private Long beneficiaireId;

    @NotNull(message = "Source RIB is required")
    @Pattern(
            regexp = "^\\d{5}\\d{5}\\d{11}\\d{2}$",
            message = "Invalid RIB format. Must be 23 digits: BBBBBAAAAA-XXXXXXXXXXX-CC"
    )
    @Column(name = "rib_source")
    private String ribSource;

    @NotNull(message = "Destination RIB is required")
    @Pattern(
            regexp = "^\\d{5}\\d{5}\\d{11}\\d{2}$",
            message = "Invalid RIB format. Must be 23 digits: BBBBBAAAAA-XXXXXXXXXXX-CC"
    )
    @Column(name = "rib_destinataire")
    private String ribDestinataire;

    @Positive(message = "Transfer amount must be positive")
    @Column(name = "montant")
    private Double montant;

    @Column(name = "description")
    private String description;

    @NotNull(message = "Transfer date is required")
    @Column(name = "date_virement")
    private LocalDateTime dateVirement;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Transfer type is required")
    @Column(name = "type")
    private TypeVirement type;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    @Column(name = "status")
    private VirementStatus status;
}
