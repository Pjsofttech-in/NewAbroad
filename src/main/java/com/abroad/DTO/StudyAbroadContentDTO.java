package com.abroad.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudyAbroadContentDTO {

    private Long id;

    private String title;

    private String content;

    private Integer displayOrder;
}
