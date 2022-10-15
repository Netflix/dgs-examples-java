package dev.vittorioexp.dgs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agent")
public class Agent {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", unique = true)
    private String id;

    @Column(nullable = false)
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "agency_id")
    private Agency agency;

}
