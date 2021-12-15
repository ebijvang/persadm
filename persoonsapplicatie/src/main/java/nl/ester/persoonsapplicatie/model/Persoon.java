package nl.ester.persoonsapplicatie.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@Accessors
public class Persoon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String voornaam;

    private String achternaam;

    private LocalDate geboorteDatum;

    private String geslacht;

    private String aanspreekTitel;
}