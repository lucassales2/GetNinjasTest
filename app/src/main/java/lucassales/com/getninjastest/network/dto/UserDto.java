package lucassales.com.getninjastest.network.dto;

import java.util.List;

/**
 * Created by lucas on 30/01/17.
 */

public class UserDto {

    private String name;
    private String email;
    private Embedded _embedded;

    public Embedded get_embedded() {
        return _embedded;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public class Embedded {
        private List<Phone> phones;

        public List<Phone> getPhones() {
            return phones;
        }
    }

    public class Phone {
        private String number;

        public String getNumber() {
            return number;
        }
    }

}
