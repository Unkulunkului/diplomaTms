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
    @ManyToOne
    private Hotel hotel;
    private String description;
    private String name;
    private double price;
    @ElementCollection
    private List<String> images;
    private String country;
    private TourEnum type;
    private boolean withChildren;
}
