package by.tms.diploma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToOne
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
