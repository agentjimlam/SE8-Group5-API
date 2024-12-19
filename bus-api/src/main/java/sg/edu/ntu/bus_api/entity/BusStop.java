package sg.edu.ntu.bus_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Must start field names with small letters

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="bus_stop")
public class BusStop {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="BusStopId")
  private Long busStopId;

  @Column(name="BusStopCode")
  @Pattern(regexp = "\\d{5,7}", message = "Bus stop code must be numeric and between 5 and 7 digits long")
  private String busStopCode;

  @Column(name="RoadName")
  @NotBlank(message = "Road name is mandatory")
  private String roadName;

  @Column(name="Description")
  @NotBlank(message = "Description name of bus stop is mandatory")
  private String description;

  @Column(name="Latitude")
  @DecimalMin(value = "1.2366", message = "Latitude must be greater than or equal to 1.2366 to still be in Singapore")
  @DecimalMax(value = "1.4719", message = "Latitude must be less than or equal to 1.4719 to still be in Singapore")
  private Double latitude;

  @Column(name="Longitude")
  @DecimalMin(value = "103.6058", message = "Longitude must be greater than or equal to 103.6058 to still be in Singapore")
  @DecimalMax(value = "104.0473", message = "Longitude must be less than or equal to 104.0473 to still be in Singapore")
  private Double longitude;
}
