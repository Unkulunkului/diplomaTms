package by.tms.diploma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


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
    @Column(length = 1500)
    private String description;
    private String name;
    private double pricePerDay;
    private String images;
    private String country;
    private boolean withChildren;
}
