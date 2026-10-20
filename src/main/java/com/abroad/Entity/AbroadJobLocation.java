package com.abroad.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "abroad_job_location")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbroadJobLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    @JsonIgnore
    private AbroadJobCountry country;

    @Column(nullable = false)
    @JsonProperty("location")
    private String name;

    @Column(nullable = false)
    private Boolean active = true;

    @JsonProperty("countryId")
    public Long getCountryId() {
        return country != null ? country.getId() : null;
    }

    @JsonProperty("countryName")
    public String getCountryName() {
        return country != null ? country.getName() : null;
    }

    @JsonProperty("countryId")
    public void setCountryId(Long countryId) {
        if (countryId != null) {
            AbroadJobCountry newCountry = new AbroadJobCountry();
            newCountry.setId(countryId);
            this.country = newCountry;
        }
    }
}