package net.hamdi.beneficiaryservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.hamdi.beneficiaryservice.enums.TypeBeneficiaire;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "beneficiaries")
@Entity
public class Beneficiaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "RIB is required")
    @Pattern(
            regexp = "^\\d{5}\\d{5}\\d{11}\\d{2}$",
            message = "Invalid RIB format. Must be 23 digits: BBBBBAAAAA-XXXXXXXXXXX-CC"
    )
    @Column(name = "bank_account_number", nullable = false, unique = true)
    private String rib;

    @NotNull(message = "Beneficiary type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "beneficiary_type", nullable = false)
    private TypeBeneficiaire type;

}
