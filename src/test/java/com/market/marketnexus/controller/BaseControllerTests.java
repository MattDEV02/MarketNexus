package com.market.marketnexus.controller;

import com.market.marketnexus.helpers.constants.GlobalValues;
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

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class BaseControllerTests {
   @Autowired
   private MockMvc mockMvc;

   @Test
   public void testShowHomePage() throws Exception {
      this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
              .andExpect(MockMvcResultMatchers.status().isOk())
              .andExpect(content().encoding(GlobalValues.CHARSET))
              .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
              .andExpect(content().string(containsString("Home")))
              .andExpect(MockMvcResultMatchers.view().name("index.html"));
   }

   @Test
   public void testShowFAQs() throws Exception {
      this.mockMvc.perform(MockMvcRequestBuilders.get("/FAQs"))
              .andExpect(MockMvcResultMatchers.status().isOk())
              .andExpect(content().encoding(GlobalValues.CHARSET))
              .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
              .andExpect(content().string(containsString("FAQs")))
              .andExpect(MockMvcResultMatchers.view().name("FAQs.html"));
   }
}
