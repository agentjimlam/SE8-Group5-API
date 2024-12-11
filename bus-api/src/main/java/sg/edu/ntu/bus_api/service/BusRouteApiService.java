package sg.edu.ntu.bus_api.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import sg.edu.ntu.bus_api.entity.BusRoute;
import sg.edu.ntu.bus_api.entity.BusStop;
import sg.edu.ntu.bus_api.repository.BusRouteRepository;

@Service
public class BusRouteApiService {
  // get the data BUS STOPS
  // https://datamall2.mytransport.sg/ltaodataservice/BusStops
  final String BUS_ROUTES_URL = 
    "https://datamall2.mytransport.sg/ltaodataservice/BusRoutes";
  // Logger
  private final static Logger logger = LoggerFactory.getLogger(BusRouteApiService.class);
  // autowire the bean repository
  private BusRouteRepository busRouteRepo;

  // constructor
  public BusRouteApiService(BusRouteRepository busRouteRepo) {
    this.busRouteRepo = busRouteRepo;
  }

  // public main to run the service
  public void main() {
    // get bus stops
    getBusRoutes();
  }

  // find only the first id.
  // the actual data is too larget.
  public BusRoute findFirstOne(){
    // find the first id
    Optional<BusRoute> optionalBusRoute = busRouteRepo.findById(1L);
    // if the result is present
    if(optionalBusRoute.isPresent()){
      BusRoute foundBusRoute = optionalBusRoute.get();
      return foundBusRoute;
    }
    // if no result
    else {
      return null;
    }
  }

  // find by service no and direction
  public List<BusRoute> findByServiceDirection(String serviceNo, String direction) {
    return 
      busRouteRepo.findByServiceNoAndDirectionIgnoreCase
        (serviceNo, direction);
  }

  // counter to keep track of the skip
  // if array in the value is less than 500,
  // then stop the skip.
  private Integer arrayLength;

  // web client
  public HttpStatusCode getBusRoutes() {
    // REST client
    // get only 500 request at a time
    final String skipName = "?$skip=";
    Integer skipNo = 0;
    String skip = skipName + skipNo.toString();
    // create the rest client
    RestClient restClient = RestClient.create();
    // result status code.
    HttpStatusCode statusCode;
    // Loop all the skips, increment by 500 each time
    do {
      // the order of rest client get matters.
      // must be .get, .uri., .header.
      ResponseEntity<String> result = restClient.get()
          .uri(BUS_ROUTES_URL + skip)
          .header("accountKey", ApiKey.myApiKey)
          .retrieve()
          .toEntity(String.class);
      // print the result for debugging
      // System.out.println("Response status: " + result.getStatusCode());
      statusCode = result.getStatusCode();
      // System.out.println("Response headers: " + result.getHeaders());
      // System.out.println("Contents: " + result.getBody());
      String jsonResult = result.getBody();
      // ObjectMapper JSON to object
      // parse will keep track of the data array length.
      parseIntoBusRoutes(jsonResult);
      // increment the skip
      skipNo = skipNo + 500;
      // set to the next skip
      skip = skipName + skipNo.toString();
      // for the very last skip, the array length will be
      // less than 500. Then, the loop will break.
    } while (arrayLength == 500);
    // TODO: assemble bus routes

    // return the Http status code
    // I don't want to handle anything complicated.
    // Just return the status code returned by Datamall.
    return statusCode;
  }

  // parse the json into BusStops object
  // store in database
  public void parseIntoBusRoutes(String jsonResult) {
    // Object mapper
    ObjectMapper objectMapper = new ObjectMapper();
    // the root node
    // Access the "value" array
    try {
      JsonNode rootNode = objectMapper.readTree(jsonResult);
      JsonNode busRoutesArray = rootNode.get("value");
      // reinitialize the array length
      arrayLength = busRoutesArray.size(); // keep track of the count
      // loop through each bus stop object in the array
      for (JsonNode node : busRoutesArray) {
        BusRoute busRoute = new BusRoute();
        busRoute.setServiceNo(node.get("ServiceNo").asText());
        busRoute.setOperator(node.get("Operator").asText());
        busRoute.setDirection(node.get("Direction").asText());
        busRoute.setStopSequence(node.get("StopSequence").asText());
        busRoute.setBusStopCode(node.get("BusStopCode").asText());
        busRoute.setDistance(node.get("Distance").asText());
        busRoute.setWD_FirstBus(node.get("WD_FirstBus").asText());
        busRoute.setWD_LastBus(node.get("WD_LastBus").asText());
        busRoute.setSAT_FirstBus(node.get("SAT_FirstBus").asText());
        busRoute.setSAT_LastBus(node.get("SAT_LastBus").asText());
        busRoute.setSUN_FirstBus(node.get("SUN_FirstBus").asText());
        busRoute.setSUN_LastBus(node.get("SUN_LastBus").asText());
        // save to bus stop repo
        busRouteRepo.save(busRoute);
      }
    } catch (JsonMappingException exp) {
      String message = "GET Bus Stops: Json mapping exception.";
      System.out.println(message);
      logger.error(message, exp);
    } catch (JsonProcessingException exp) {
      String message = "GET Bus Stops: Json processing exception.";
      System.out.println(message);
      logger.error(message, exp);
    }
  }

}
