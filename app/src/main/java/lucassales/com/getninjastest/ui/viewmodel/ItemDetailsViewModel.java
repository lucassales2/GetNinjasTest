package lucassales.com.getninjastest.ui.viewmodel;

import java.util.List;

import lucassales.com.getninjastest.network.dto.InfoDto;

/**
 * Created by lucas on 30/01/17.
 */

public class ItemDetailsViewModel {

    private final double latitude;
    private final double longitude;
    private String title;
    private String user;
    private String address;
    private long distance;
    private List<InfoDto> infos;
    private boolean isLead;
    private String phone;
    private String email;
    private String linkAccept;
    private String linkReject;

    public ItemDetailsViewModel(String address, long distance, String email, List<InfoDto> infos, boolean isLead, String phone, String title, String user, double latitude, double longitude, String linkAccept, String linkReject) {
        this(address, distance, email, infos, isLead, phone, title, user, latitude, longitude);
        this.linkAccept = linkAccept;
        this.linkReject = linkReject;
    }
    public ItemDetailsViewModel(String address, long distance, String email, List<InfoDto> infos, boolean isLead, String phone, String title, String user, double latitude, double longitude) {
        this.address = address;
        this.distance = distance;
        this.email = email;
        this.infos = infos;
        this.isLead = isLead;
        this.phone = phone;
        this.title = title;
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getLinkAccept() {
        return linkAccept;
    }

    public String getLinkReject() {
        return linkReject;
    }

    public String getUser() {
        return user;
    }

    public String getAddress() {
        return address;
    }

    public String getDistance() {
        if (distance / 1000 >= 1) {
            return String.format("a %d Km de você", (int) distance / 1000);
        } else {
            return String.format("a %d metros de você", distance);
        }

    }

    public String getEmail() {
        return email;
    }

    public List<InfoDto> getInfos() {
        return infos;
    }

    public boolean isLead() {
        return isLead;
    }

    public String getPhone() {
        return phone;
    }

    public String getTitle() {
        return title;
    }

}
