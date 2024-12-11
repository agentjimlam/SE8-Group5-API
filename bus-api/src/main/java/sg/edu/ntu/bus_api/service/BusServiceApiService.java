package sg.edu.ntu.bus_api.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import sg.edu.ntu.bus_api.entity.BusService;
import sg.edu.ntu.bus_api.entity.BusStop;
import sg.edu.ntu.bus_api.repository.BusServiceRepository;

@Service
public class BusServiceApiService {
  // get the data BUS STOPS
  // https://datamall2.mytransport.sg/ltaodataservice/BusServices
  final String BUS_SERVICES_URL = "https://datamall2.mytransport.sg/ltaodataservice/BusServices";
  // Logger
  private final static Logger logger = LoggerFactory.getLogger(BusServiceApiService.class);
  // autowire the bean repository
  private BusServiceRepository busServiceRepo;

  // constructor
  public BusServiceApiService(BusServiceRepository busServiceRepo) {
    this.busServiceRepo = busServiceRepo;
  }

  // public main to run the service
  public void main() {
    // get bus stops
    getBusServices();
  }

  // counter to keep track of the skip
  // if array in the value is less than 500,
  // then stop the skip.
  private Integer arrayLength;

  // find by bus service no
  public List<BusService> findByBusServiceNo(String serviceNo){
    List<BusService> results = busServiceRepo.findByServiceNoIgnoreCase(serviceNo);
    return results;
  }

  // find only the first id, used by the app endpoint
  // the whole data is too larget to return at once.
    public BusService findFirstOne(){
    // find the first id
    Optional<BusService> optionalBusService = busServiceRepo.findById(1L);
    // if the result is present
    if(optionalBusService.isPresent()){
      BusService foundBusService = optionalBusService.get();
      return foundBusService;
    }
    // if no result
    else {
      return null;
    }
  }


  // web client
  public HttpStatusCode getBusServices() {
    // REST client
    // get only 500 request at a time
    String skipName = "?$skip=";
    Integer skipNo = 0;
    String skip = skipName + skipNo.toString();
    // create the rest client
    RestClient restClient = RestClient.create();
    // result status code.
    HttpStatusCode statusCode;
    do {
    // the order of rest client get matters.
    // must be .get, .uri., .header.
    ResponseEntity<String> result = restClient.get()
        .uri(BUS_SERVICES_URL + skip)
        .header("accountKey", ApiKey.myApiKey)
        .retrieve()
        .toEntity(String.class);
    // print the result for debugging
    //System.out.println("Response status: " + result.getStatusCode());
    statusCode = result.getStatusCode();
    // System.out.println("Response headers: " + result.getHeaders());
    // System.out.println("Contents: " + result.getBody());
    String jsonResult = result.getBody();
    // ObjectMapper JSON to object
    parseIntoBusService(jsonResult);
    // increment the skip
    skipNo = skipNo + 500;
    // set to the next skip
    skip = skipName + skipNo.toString();
    // for the very last skip, the array length will be
    // less than 500. Then, the loop will break.    
    } while(arrayLength == 500);
    // return the Http status code
    return statusCode;
  }

  // parse the json into BusStops object
  // store in database
  public void parseIntoBusService(String jsonResult) {
    // Object mapper
    ObjectMapper objectMapper = new ObjectMapper();
    // the root node
    // Access the "value" array
    try {
      JsonNode rootNode = objectMapper.readTree(jsonResult);
      JsonNode busServicesArray = rootNode.get("value");
      // reinitialize the array length
      arrayLength = busServicesArray.size(); // keep track of the count
      // loop through each bus stop object in the array
      for (JsonNode node : busServicesArray) {
        BusService busService = new BusService();
        busService.setServiceNo(node.get("ServiceNo").asText());
        busService.setOperator(node.get("Operator").asText());
        busService.setDirection(node.get("Direction").asInt());
        busService.setCategory(node.get("Category").asText());
        busService.setOriginCode(node.get("OriginCode").asText());
        busService.setDestinationCode(node.get("DestinationCode").asText());
        busService.setAM_Peak_Freq(node.get("AM_Peak_Freq").asText());
        busService.setAM_Offpeak_Freq(node.get("AM_Offpeak_Freq").asText());
        busService.setPM_Peak_Freq(node.get("PM_Peak_Freq").asText());
        busService.setAM_Offpeak_Freq(node.get("PM_Offpeak_Freq").asText());
        busService.setLoopDesc(node.get("LoopDesc").asText());
        // save to bus stop repo
        busServiceRepo.save(busService);
      }
    } catch (JsonMappingException exp) {
      String message = "GET Bus Services: Json mapping exception.";
      System.out.println(message);
      logger.error(message, exp);
    } catch (JsonProcessingException exp) {
      String message = "GET Bus Services: Json processing exception.";
      System.out.println(message);
      logger.error(message, exp);
    }
  }

}
