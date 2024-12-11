package sg.edu.ntu.bus_api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class AppBusArrivalControllerTest {
  @Autowired
  private MockMvc mockie;

  // GET one cart - OK
  @DisplayName("Get one bus arrival.")
  @Test
  public void testGetOneArrival() throws Exception {
    // 1. get the request uri
    RequestBuilder request = MockMvcRequestBuilders.get(
      "/app/arrival?BusStopCode=83139");
    // 2. perform the request and get response
    mockie.perform(request)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("text/plain;charset=UTF-8"));
  }

} // END OF CLASS