package sg.edu.ntu.bus_api;

/*
 *   "odata.metadata": "https://datamall2.mytransport.sg/ltaodataservice/v3/BusArrival",
  "BusStopCode": "83139",
 */

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * One sample:
 * 
 * {
  "status": "Successful",
  "data": [
    {
      "busServiceId": 1,
      "serviceNo": "118",
      "operator": "GAS",
      "direction": 1,
      "category": "TRUNK",
      "originCode": "65009",
      "destinationCode": "97009",
      "loopDesc": "",
      "am_Peak_Freq": "5-08",
      "am_Offpeak_Freq": "09-14",
      "pm_Peak_Freq": "8-10",
      "pm_Offpeak_Freq": null
    },
    ...
}
 */


@SpringBootTest
@AutoConfigureMockMvc
public class AppBusServiceControllerTest {
  @Autowired
  private MockMvc mockie;

  // GET one cart - OK
  @DisplayName("Get all bus services.")
  @Test
  public void testGetAllServices() throws Exception {
    // 1. get the request uri
    RequestBuilder request = MockMvcRequestBuilders.get(
      "/app/services");
    // 2. perform the request and get response
    mockie.perform(request)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value( "Successful"));
        //.andExpect(jsonPath("$.data.[0].serviceNo").hasJsonPath())
        //.andExpect(jsonPath("$.data.[0].serviceNo").value("118"));
  }

} // END OF CLASS