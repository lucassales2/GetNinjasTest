package lucassales.com.getninjastest.ui.viewmodel;

import java.util.List;

import lucassales.com.getninjastest.network.dto.InfoDto;

/**
 * Created by lucas on 30/01/17.
 */

public class ItemDetailsViewModel {

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

    public ItemDetailsViewModel(String address, long distance, String email, List<InfoDto> infos, boolean isLead, String phone, String title, String user, String linkAccept, String linkReject) {
        this(address, distance, email, infos, isLead, phone, title, user);
        this.linkAccept = linkAccept;
        this.linkReject = linkReject;
    }

    public ItemDetailsViewModel(String address, long distance, String email, List<InfoDto> infos, boolean isLead, String phone, String title, String user) {
        this.address = address;
        this.distance = distance;
        this.email = email;
        this.infos = infos;
        this.isLead = isLead;
        this.phone = phone;
        this.title = title;
        this.user = user;
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

    public long getDistance() {
        return distance;
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
