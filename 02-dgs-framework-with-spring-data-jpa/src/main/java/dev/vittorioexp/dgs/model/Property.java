package dev.vittorioexp.dgs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "property")
public class Property {
    @Id
    @SequenceGenerator(
            name = "property_sequence",
            sequenceName = "property_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "property_sequence"
    )
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private PropertyType type;

    private String longitude;

    private String latitude;

    private LocalDate purchaseDate;

    @ManyToOne
    @JoinColumn(name = "agency_id")
    private Agency agency;

}
