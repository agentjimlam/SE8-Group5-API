package sg.edu.ntu.bus_api.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.ntu.bus_api.entity.BusRoute;
import sg.edu.ntu.bus_api.entity.BusStop;
import sg.edu.ntu.bus_api.service.BusRouteApiService;

@RestController
public class AppBusRouteController {
  // autowire
  private BusRouteApiService busRouteApiService;
  // constructor
  public AppBusRouteController(BusRouteApiService busServiceApiService){
    this.busRouteApiService = busServiceApiService;
  }

  // get the bus route by service no and direction
  @GetMapping("/app/routes/service/{serviceNo}/direction/{direction}")
  public ResponseEntity<?> findByServiceDirection(
    @PathVariable(required=true) String serviceNo,
    @PathVariable(required=true) String direction
  ){
      Map<String, Object> response = new LinkedHashMap<>();
      List<BusRoute> busRouteList = busRouteApiService.findByServiceDirection(serviceNo, direction);
      if(!busRouteList.isEmpty()){
        // prepare the response
        response.put("status", "Successful");
        response.put("data", busRouteList);
        return new ResponseEntity<>(response, HttpStatus.OK);
      }
      // if no result
      else {
        response.clear();
        response.put("status", "Failed");
        response.put("message", "No data are found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
      }
    }
  


  // get the bus stop code
  @GetMapping("/app/routes")  
  public ResponseEntity<?> findFirstOne(){
    Map<String, Object> response = new LinkedHashMap<>();
    BusRoute busRoute = busRouteApiService.findFirstOne();
    if(busRoute!=null){
      // prepare the response
      response.put("status", "Successful");
      response.put("data", busRoute);
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

}
