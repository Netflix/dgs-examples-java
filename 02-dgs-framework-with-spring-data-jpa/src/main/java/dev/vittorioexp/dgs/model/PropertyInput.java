package dev.vittorioexp.dgs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyInput {
    private Integer id;
    private String name;
    private PropertyType type;
    private String longitude;
    private String latitude;
    private String purchaseDate;
    private Integer agencyId;
}
