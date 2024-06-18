package com.market.marketnexus.controller;

import com.market.marketnexus.helpers.constants.APIPrefixes;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class StatsControllerTests {
   @Autowired
   private MockMvc mockMvc;

   @Test
   public void testTableData() throws Exception {
      this.mockMvc.perform(MockMvcRequestBuilders
                      .get("/" + APIPrefixes.STATS + "/tableData")
                      .accept(MediaType.APPLICATION_JSON))
              .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
              .andExpect(MockMvcResultMatchers.content().string(""))
              .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/login/**"));
   }

   @Test
   public void testChartData() throws Exception {
      this.mockMvc.perform(MockMvcRequestBuilders
                      .get("/" + APIPrefixes.STATS + "/chartData")
                      .accept(MediaType.APPLICATION_JSON))
              .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
              .andExpect(MockMvcResultMatchers.content().string(""))
              .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/login/**"));
   }

   @Test
   public void testMapData() throws Exception {
      this.mockMvc.perform(MockMvcRequestBuilders
                      .get("/" + APIPrefixes.STATS + "/mapData")
                      .accept(MediaType.APPLICATION_JSON))
              .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
              .andExpect(MockMvcResultMatchers.content().string(""))
              .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/login/**"));
   }

   @Test
   public void testCalendarDataSalesData() throws Exception {
      this.mockMvc.perform(MockMvcRequestBuilders
                      .get("/" + APIPrefixes.STATS + "/calendarData/salesData")
                      .accept(MediaType.APPLICATION_JSON))
              .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
              .andExpect(MockMvcResultMatchers.content().string(""))
              .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/login/**"));
   }

   @Test
   public void testCalendarDataOrdersData() throws Exception {
      this.mockMvc.perform(MockMvcRequestBuilders
                      .get("/" + APIPrefixes.STATS + "/calendarData/ordersData")
                      .accept(MediaType.APPLICATION_JSON))
              .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
              .andExpect(MockMvcResultMatchers.content().string(""))
              .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/login/**"));
   }
}
