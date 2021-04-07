package by.tms.diploma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String country;
    @Column(length = 1500)
    private String description;
    private String images;
    private String name;
    private int stars;
    private boolean pets;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return stars == hotel.stars &&
                Objects.equals(country, hotel.country) &&
                Objects.equals(name, hotel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, name, stars);
    }
}
