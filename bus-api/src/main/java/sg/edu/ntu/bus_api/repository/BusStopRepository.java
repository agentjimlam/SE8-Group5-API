package sg.edu.ntu.bus_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.ntu.bus_api.entity.BusStop;
import java.util.List;


@Repository
public interface BusStopRepository extends JpaRepository<BusStop, Long>{
  List<BusStop> findByBusStopCodeIgnoreCase(String busStopCode);

  boolean existsByBusStopCode(String busStopCode);

  BusStop findByBusStopCode(String busStopCode);
} 