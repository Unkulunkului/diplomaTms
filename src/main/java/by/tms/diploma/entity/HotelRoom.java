package by.tms.diploma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HotelRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ElementCollection
    private List<HotelRoomTypeEnum> roomType;
    private boolean isHasSafe;
    private boolean isHasMinibar;
    private boolean isHasHairdryer;
    private boolean isHasTV;
    private boolean isHasAirConditioning;
}