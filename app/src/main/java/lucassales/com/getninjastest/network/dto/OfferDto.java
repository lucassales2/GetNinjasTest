package lucassales.com.getninjastest.network.dto;

import java.util.Date;
import java.util.Map;

/**
 * Created by lucas on 30/01/17.
 */

public class OfferDto {
    private String state;
    private Embedded1 _embedded;
    private Map<String, HttpReferenceDto> _links;

    public String getState() {
        return state;
    }

    public Embedded1 get_embedded() {
        return _embedded;
    }

    public Map<String, HttpReferenceDto> get_links() {
        return _links;
    }

    public class Embedded1 {
        private Request request;

        public Request getRequest() {
            return request;
        }
    }

    public class Request {
        private Date created_at;
        private String title;
        private Embedded2 _embedded;

        public Date getCreated_at() {
            return created_at;
        }

        public Embedded2 get_embedded() {
            return _embedded;
        }

        public String getTitle() {
            return title;
        }

    }

    public class Embedded2 {
        private UserDto user;
        private AddressDto address;

        public AddressDto getAddress() {
            return address;
        }

        public UserDto getUser() {
            return user;
        }
    }
}
