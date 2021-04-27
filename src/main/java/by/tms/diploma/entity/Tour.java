package by.tms.diploma.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;


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
    @Column(name = "tour_id")
    private long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    private TypeOfRest typeOfRest;
    @Column(length = 3000)
    private String description;
    private double price;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Image> images;
    private String visitedCountries;
    private int tourDuration;
    private int dayAtSea;
}
