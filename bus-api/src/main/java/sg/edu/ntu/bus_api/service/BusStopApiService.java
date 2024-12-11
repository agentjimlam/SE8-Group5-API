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

import sg.edu.ntu.bus_api.entity.BusStop;
import sg.edu.ntu.bus_api.repository.BusStopRepository;

@Service
public class BusStopApiService {
  // get the data BUS STOPS
  // https://datamall2.mytransport.sg/ltaodataservice/BusStops
  final String BUS_STOPS_URL = "https://datamall2.mytransport.sg/ltaodataservice/BusStops";
  // Logger
  private final static Logger logger = LoggerFactory.getLogger(BusStopApiService.class);
  // autowire the bean repository
  private BusStopRepository busStopRepo;

  // constructor
  public BusStopApiService(BusStopRepository busStopRepo) {
    this.busStopRepo = busStopRepo;
  }

  // public main to run the service
  public void main() {
    // get bus stops
    getBusStops();
  }

    // find by bus service no
  public List<BusStop> findByBusStopCode(String busStopCode){
    List<BusStop> results = busStopRepo.findByBusStopCodeIgnoreCase(busStopCode);
    return results;
  }


  // this will actually return one sample. It will not
  // return the who list because it is too large.
  // It is called findAll() just to show the find
  public BusStop findFirstOne(){
    // find the first id
    Optional<BusStop> optionalBusStop = busStopRepo.findById(1L);
    // if the result is present
    if(optionalBusStop.isPresent()){
      BusStop foundBusStop = optionalBusStop.get();
      return foundBusStop;
    }
    // if no result
    else {
      return null;
    }
  }


  // counter to keep track of the skip
  // if array in the value is less than 500,
  // then stop the skip.
  private Integer arrayLength;

  // web client
  public HttpStatusCode getBusStops() {
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
          .uri(BUS_STOPS_URL + skip)
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
      parseIntoBusStops(jsonResult);
      // increment the skip
      skipNo = skipNo + 500;
      // set to the next skip
      skip = skipName + skipNo.toString();
      // for the very last skip, the array length will be
      // less than 500. Then, the loop will break.
    } while (arrayLength == 500);
    // return the Http status code
    // I don't want to handle anything complicated.
    // Just return the status code returned by Datamall.
    return statusCode;
  }

  // parse the json into BusStops object
  // store in database
  public void parseIntoBusStops(String jsonResult) {
    // Object mapper
    ObjectMapper objectMapper = new ObjectMapper();
    // the root node
    // Access the "value" array
    try {
      JsonNode rootNode = objectMapper.readTree(jsonResult);
      JsonNode busStopsArray = rootNode.get("value");
      // reinitialize the array length
      arrayLength = busStopsArray.size(); // keep track of the count
      // loop through each bus stop object in the array
      for (JsonNode node : busStopsArray) {
        BusStop busStop = new BusStop();
        busStop.setBusStopCode(node.get("BusStopCode").asText());
        busStop.setRoadName(node.get("RoadName").asText());
        busStop.setDescription(node.get("Description").asText());
        busStop.setLatitude(node.get("Latitude").asDouble());
        busStop.setLongitude(node.get("Longitude").asDouble());
        // save to bus stop repo
        busStopRepo.save(busStop);
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
