package by.tms.diploma.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    private Country country;
    @Column(length = 1500)
    private String description;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> images;
    private String name;
    private int stars;
    private int lineFromTheSea;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy="hotel")
    private Set<Tour> tours;
    private HotelFoodEnum typeOfFood;
}
