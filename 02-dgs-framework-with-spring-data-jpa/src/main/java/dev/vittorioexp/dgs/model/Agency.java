package dev.vittorioexp.dgs.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agency")
public class Agency {
    @Id
    @SequenceGenerator(
            name = "agency_sequence",
            sequenceName = "agency_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "agency_sequence"
    )
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String taxCode;

    @OneToMany(
            mappedBy = "agency",
            cascade = CascadeType.ALL
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Agent> agents;

    @OneToMany(
            mappedBy = "agency",
            cascade = CascadeType.ALL
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Property> properties;

}
