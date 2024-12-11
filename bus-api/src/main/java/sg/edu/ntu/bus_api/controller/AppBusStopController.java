package sg.edu.ntu.bus_api.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.ntu.bus_api.entity.BusStop;
import sg.edu.ntu.bus_api.service.BusStopApiService;

@RestController
public class AppBusStopController {
  // autowire
  private BusStopApiService busStopApiService;

  // constructor
  public AppBusStopController(BusStopApiService busStopApiService){
    this.busStopApiService = busStopApiService;
  }

  // get the bus stop code
  @GetMapping("/app/stops")
  public ResponseEntity<?> findFirstOne(){
    Map<String, Object> response = new LinkedHashMap<>();
    BusStop busStop = busStopApiService.findFirstOne();
    if(busStop!=null){
      // prepare the response
      response.put("status", "Successful");
      response.put("data", busStop);
      return new ResponseEntity<>(response, HttpStatus.OK);
    }
    // no result is found
    else {
      response.clear();
      response.put("status", "Failed");
      response.put("message", "No data are found.");
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }    
  }


  // get the bus stop code
  @GetMapping("/app/stops/{busStopCode}")
  public ResponseEntity<?> findByBusStopCode(@PathVariable String busStopCode){
    Map<String, Object> response = new LinkedHashMap<>();
    List<BusStop> busStopList = busStopApiService.findByBusStopCode(busStopCode);
    // prepare the response
    response.put("status", "Successful");
    response.put("data", busStopList);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
