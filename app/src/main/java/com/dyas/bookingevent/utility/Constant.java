package com.dyas.bookingevent.utility;

public class Constant {
    public static class Code {
      public static String SERVER = "http://192.168.42.206/";

        public static String BASE_URL_WS = "bookingevent-rest/index.php/";
        public static String BASE_URL_WS_food_delivery = "fooddelivery-api/index.php/";


        public static String REGISTER           =  SERVER+BASE_URL_WS + "member/registerMember/format/json";
        public static String LOGIN           =  SERVER+BASE_URL_WS + "member/loginMember/format/json";

        public static String GET_KATEGORI           =  SERVER+BASE_URL_WS + "event/getKategoriEvent/format/json";

    }
}
