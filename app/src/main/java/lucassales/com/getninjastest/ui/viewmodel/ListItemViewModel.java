package lucassales.com.getninjastest.ui.viewmodel;

/**
 * Created by lucas on 30/01/17.
 */

public class ListItemViewModel {
    private String title;
    private String userName;
    private String adress;
    private String stringDate;
    private State state;
    private String link;

    public ListItemViewModel(String adress, State state, String stringDate, String title, String userName, String link) {
        this.adress = adress;
        this.state = state;
        this.stringDate = stringDate;
        this.title = title;
        this.userName = userName;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public String getStringDate() {
        return stringDate;
    }

    public String getAdress() {
        return adress;
    }

    public State getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }

    public String getUserName() {
        return userName;
    }

    public enum State {
        Read,
        Unread,
        Accepted
    }


}
