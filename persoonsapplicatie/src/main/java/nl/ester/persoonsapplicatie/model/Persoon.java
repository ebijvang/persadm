package nl.ester.persoonsapplicatie.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "personen")
public class Persoon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String voornaam;

    private String achternaam;

    private LocalDate geboorteDatum;

    private String geslacht;

    private String aanspreekTitel;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "persoon_id", nullable = false)
    private Persoon persoon;
}