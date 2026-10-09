package com.abroad.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AbroadJobRoleResponseDTO {

    private Long id;
    private String name;
    private SectorResponseDTO sector;
    private Boolean active;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SectorResponseDTO {
        private Long id;
        private String name;
    }
}
