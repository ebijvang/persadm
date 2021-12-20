package nl.ester.persoonsapplicatie.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Accessors(chain = true)
@Table(name="adressen")
public class Adres {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String straat;
    private Integer huisnummer;
    private String huisnummerToevoeging;
    private String postcode;
    private String woonplaats;
    private String land;
    @OneToMany(mappedBy = "persoon", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Persoon> personen;
}
