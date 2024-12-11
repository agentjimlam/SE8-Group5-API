package sg.edu.ntu.bus_api.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.ntu.bus_api.entity.BusService;
import sg.edu.ntu.bus_api.entity.BusStop;
import sg.edu.ntu.bus_api.service.BusServiceApiService;

@RestController
public class AppBusServiceController {
  // autowire
  private BusServiceApiService busServiceApiService;
  // constructor
  public AppBusServiceController(BusServiceApiService busServiceApiService){
    this.busServiceApiService = busServiceApiService;
  }

  @GetMapping("/app/services")
  // get the bus stop code
  public ResponseEntity<?> findFirstOne(){
    Map<String, Object> response = new LinkedHashMap<>();
    BusService busService = busServiceApiService.findFirstOne();
    if(busService!=null){
      // prepare the response
      response.put("status", "Successful");
      response.put("data", busService);
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

  // find by bus service no
  @GetMapping("/app/services/{serviceNo}")
  public ResponseEntity<?> findByServiceNo(@PathVariable String serviceNo){
    Map<String, Object> response = new LinkedHashMap<>();
    List<BusService> busServiceList = busServiceApiService.findByBusServiceNo(serviceNo);
    response.put("status", "Successful");
    response.put("data", busServiceList);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
