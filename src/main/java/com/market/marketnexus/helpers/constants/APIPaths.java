package com.market.marketnexus.helpers.constants;

public class APIPaths {

   public static final String INDEX = "/";

   public static final String MARKETPLACE = "marketplace";

   public static final String SALES = APIPaths.MARKETPLACE + "/sales";

   public static final String CART = APIPaths.MARKETPLACE + "/cart";

   public static final String ORDER = APIPaths.MARKETPLACE + "/order";

   public static final String ACCOUNT = APIPaths.MARKETPLACE + "/account";
   public static final String API = "api";
   public static final String STATS = APIPaths.ACCOUNT + APIPaths.INDEX + APIPaths.API + "/stats";
   public static final String NATIONS = APIPaths.MARKETPLACE + APIPaths.INDEX + APIPaths.API + "/nations";
}
