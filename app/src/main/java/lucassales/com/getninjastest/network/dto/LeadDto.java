package lucassales.com.getninjastest.network.dto;

import java.util.Date;
import java.util.Map;

/**
 * Created by lucas on 30/01/17.
 */

public class LeadDto {

    private Date created_at;
    private Embedded _embedded;
    private Map<String, HttpReferenceDto> _links;


    public Embedded get_embedded() {
        return _embedded;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Map<String, HttpReferenceDto> get_links() {
        return _links;
    }

    public class Embedded {
        private AddressDto address;
        private UserDto user;
        private Request request;

        public AddressDto getAddress() {
            return address;
        }

        public String getTitleRequest() {
            return request.title;
        }

        public UserDto getUser() {
            return user;
        }

        private class Request {
            private String title;
        }
    }

}
