package lucassales.com.getninjastest.network.response;

import java.util.List;
import java.util.Map;

import lucassales.com.getninjastest.network.dto.AddressDto;
import lucassales.com.getninjastest.network.dto.HttpReferenceDto;
import lucassales.com.getninjastest.network.dto.InfoDto;
import lucassales.com.getninjastest.network.dto.UserDto;

/**
 * Created by lucas on 30/01/17.
 */

public class DetailsResponse {

    private long distance;
    private int lead_price;
    private String title;
    private Embedded1 _embedded;
    private Map<String, HttpReferenceDto> _links;

    public Embedded1 get_embedded() {
        return _embedded;
    }

    public Map<String, HttpReferenceDto> get_links() {
        return _links;
    }

    public long getDistance() {
        return distance;
    }

    public int getLead_price() {
        return lead_price;
    }

    public String getTitle() {
        return title;
    }

    public Map<String, HttpReferenceDto> getLinks() {
        return _links;
    }

    public class Embedded1 {

        public UserDto getUser() {
            return user;
        }

        public AddressDto getAddress() {
            return address;
        }

        public List<InfoDto> getInfo() {
            return info;
        }

        private UserDto user;
        private List<InfoDto> info;
        private AddressDto address;

    }


}
