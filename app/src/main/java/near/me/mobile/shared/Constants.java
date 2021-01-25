package near.me.mobile.shared;

public class Constants {

    public static class URL {
        private static String HOST = "http://192.168.0.219:8181/lookup-ws/";
        public static final String GET_LOCATIONS = HOST + "/locations/for/";
        public static final String POST_ADD_LOCATION = HOST + "locations/fast";
        public static final String GET_UPDATES = "http://192.168.0.219:9000/locations";
    }

    public static final String TEST_CLIENT_ID = "1";
}

