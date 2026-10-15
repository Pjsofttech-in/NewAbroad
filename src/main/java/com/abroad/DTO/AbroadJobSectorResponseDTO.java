
package com.abroad.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AbroadJobSectorResponseDTO {

    private Long id;
    private String name;
    private Long educationId;
    private String educationName;
    private Boolean active;
}