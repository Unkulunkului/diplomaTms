package by.tms.diploma.entity;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = "tours")
@EqualsAndHashCode(exclude = "tours")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String country;
    @Column(length = 1500)
    private String description;
    @OneToOne(cascade = CascadeType.ALL)
    private Image images;
    private String name;
    private int stars;
    private int lineFromTheSea;
    @OneToMany(mappedBy="hotel")
    private Set<Tour> tours;
    private HotelFoodEnum typeOfFood;
}
