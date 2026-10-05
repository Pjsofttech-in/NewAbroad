package com.abroad.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudyAbroadRequestDTO {

    private Long countryId;

    private String title;

    private String subTitle;

    private String description;

    private String image;

    private String imageAltText;

    private String slug;

    private Boolean active;

    private List<StudyAbroadContentDTO> contents;
}
