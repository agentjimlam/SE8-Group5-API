package sg.edu.ntu.bus_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.ntu.bus_api.entity.BusRoute;

@Repository
public interface BusRouteRepository extends JpaRepository<BusRoute, Long>{
  // find by service no and direction
  List<BusRoute> findByServiceNoAndDirectionIgnoreCase(
    String serviceNo, String direction
  );
} 