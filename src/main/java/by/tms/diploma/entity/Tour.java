package by.tms.diploma.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@ToString(exclude = "hotel")
@EqualsAndHashCode(exclude = "hotel")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Tours")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToOne(cascade=CascadeType.ALL)
    private Hotel hotel;
    private TypeOfRest typeOfRest;
    @Column(length = 3000)
    private String description;
    private double price;
    @OneToOne(cascade = CascadeType.ALL)
    private Image images;
    @ElementCollection
    private List<String> visitedCountries;
    private int tourDuration;
    private int dayAtSea;
}
