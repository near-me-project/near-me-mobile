package near.me.mobile.shared;

public class Constants {

    public static class URL {
        private static String LOOKUP_HOST = "http://192.168.0.219:8181/lookup-ws/";
        private static String CONSUMER_HOST = "http://192.168.0.219:9000/";
        private static String USER_HOST = "http://192.168.0.219:8888/user-ws/";
        public static final String USER_WS_LOGIN = USER_HOST + "user/login";
        public static final String LOOKUP_GET_LOCATIONS = LOOKUP_HOST + "locations/for/";
        public static final String LOOKUP_ADD_LOCATION = LOOKUP_HOST + "locations/fast";
        public static final String LOOKUP_DELETE_LOCATION = LOOKUP_HOST + "locations/";
        public static final String CONSUMER_SEND_UPDATES = CONSUMER_HOST + "locations";
    }

    public static final String TEST_CLIENT_ID = "1";
}

