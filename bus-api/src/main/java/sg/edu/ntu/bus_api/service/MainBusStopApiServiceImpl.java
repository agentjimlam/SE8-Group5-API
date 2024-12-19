package sg.edu.ntu.bus_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.ntu.bus_api.entity.BusStop;
import sg.edu.ntu.bus_api.exception.BusStopDuplicateException;
import sg.edu.ntu.bus_api.exception.BusStopNotFoundException;
import sg.edu.ntu.bus_api.repository.BusStopRepository;

@Service
public class MainBusStopApiServiceImpl implements MainBusStopApiService {

    private BusStopRepository busStopRepository;

    // Constructor injection
    public MainBusStopApiServiceImpl(BusStopRepository busStopRepository) {
        this.busStopRepository = busStopRepository;
    }

    public BusStop createBusStop(BusStop busStop) {
        
        BusStop existingBusStop = busStopRepository.findByBusStopCode(busStop.getBusStopCode());
        // Check if bus stop code already exists
        if (busStopRepository.existsByBusStopCode(busStop.getBusStopCode())) {
            // throw new IllegalArgumentException("Bus stop code already exists: " + busStop.getBusStopCode());
            throw new BusStopDuplicateException(existingBusStop.getBusStopId(), existingBusStop.getBusStopCode());
        }
        BusStop newBusStop = busStopRepository.save(busStop);
        return newBusStop;
    }

    public BusStop getBusStop(Long id) {
        return busStopRepository.findById(id).orElseThrow(() -> new BusStopNotFoundException(id));
    }

    public List<BusStop> getAllBusStops() {
        List<BusStop> allBusStops = busStopRepository.findAll();
        return allBusStops;
    }

    public BusStop updateBusStop(Long id, BusStop busStop) {
        // Retrieve the bus stop from database, throw exception if not found
        BusStop busStopToUpdate = busStopRepository.findById(id).orElseThrow(() -> new BusStopNotFoundException(id));

        // Update the bus stop object that was retrieved
        busStopToUpdate.setBusStopCode(busStop.getBusStopCode());
        busStopToUpdate.setRoadName(busStop.getRoadName());
        busStopToUpdate.setDescription(busStop.getDescription());
        busStopToUpdate.setLatitude(busStop.getLatitude());
        busStopToUpdate.setLongitude(busStop.getLongitude());

        // Save updated bus stop back to database
        return busStopRepository.save(busStopToUpdate);
    }

    public void deleteBusStop(Long id) {
        busStopRepository.deleteById(id);
    }
}
