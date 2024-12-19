package sg.edu.ntu.bus_api.controller;

import sg.edu.ntu.bus_api.entity.BusStop;
import sg.edu.ntu.bus_api.service.MainBusStopApiService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/app1")
public class MainBusStopController {

    private MainBusStopApiService mainBusStopApiService;

    // Constructor injection
    public MainBusStopController(MainBusStopApiService mainBusStopApiService) {
        this.mainBusStopApiService = mainBusStopApiService;
    }

    // Create a bus stop.
    // CAUTION: this method creates bus stop in the local database and it is persists
    @PostMapping("/bus-stops")
    public ResponseEntity<BusStop> createBusStop(@Valid @RequestBody BusStop busStop) {
        BusStop newBusStop = mainBusStopApiService.createBusStop(busStop);
        return new ResponseEntity<>(newBusStop, HttpStatus.CREATED);
    }

    // Read - get all Bus Stop
    @GetMapping("/bus-stops-all")
    public ResponseEntity<List<BusStop>> getAllBusStops() {
        List<BusStop> allBusStops = mainBusStopApiService.getAllBusStops();
        return new ResponseEntity<>(allBusStops, HttpStatus.OK);
    }

    // Read - get one Bus Stop
    @GetMapping("/bus-stops/{id}")
    public ResponseEntity<BusStop> getBusStop(@PathVariable Long id) {
        return new ResponseEntity<>(mainBusStopApiService.getBusStop(id), HttpStatus.OK);
    }

    // Update
    @PutMapping("/bus-stops/{id}")
    public ResponseEntity<BusStop> updateBusStop(@PathVariable Long id, @RequestBody BusStop busStop) {
        BusStop updatedBusStop = mainBusStopApiService.updateBusStop(id, busStop);
        return new ResponseEntity<>(updatedBusStop, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/bus-stops/{id}")
    public ResponseEntity<HttpStatus> deleteBusStop(@PathVariable Long id) {
        mainBusStopApiService.deleteBusStop(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

// When you annotate a class with @RestController, it becomes the entry point
// for all web requests. This means that any object you return from this class
// will be serialized i.e., converted to JSON and returned to the client.

// @Controller -> Returns views (HTML, Thymeleaf templates)
// @RestController -> Returns data (like JSON) for REST APIs
// @RequestBody converts the received JSON to object during GET requests