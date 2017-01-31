package lucassales.com.getninjastest.network.dto;

/**
 * Created by lucas on 30/01/17.
 */

public class AddressDto {

    private String city;
    private String street;
    private String neighborhood;
    private String uf;
    private Geolocation geolocation;

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getUf() {
        return uf;
    }

    public class Geolocation {
        private double latitude;
        private double longitude;

        public double getLongitude() {
            return longitude;
        }

        public double getLatitude() {
            return latitude;
        }
    }


}
