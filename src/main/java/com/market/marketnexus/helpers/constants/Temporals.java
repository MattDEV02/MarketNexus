package com.market.marketnexus.helpers.constants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Temporals {
   public static final String DATE_FORMAT = "yyyy-MM-dd";
   public static final String DATE_TIME_FORMAT = "dd/MM/YYYY HH:mm";
   public static final String BIRTHDATE_MIN_VALUE = "1900-01-01";
   public static final String BIRTHDATE_MAX_VALUE = LocalDate.now().format(DateTimeFormatter.ofPattern(Temporals.DATE_FORMAT));

}
