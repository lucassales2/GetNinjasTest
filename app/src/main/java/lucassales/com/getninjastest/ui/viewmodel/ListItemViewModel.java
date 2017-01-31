package lucassales.com.getninjastest.ui.viewmodel;

import android.databinding.BindingAdapter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import lucassales.com.getninjastest.R;

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

    @BindingAdapter({"bind:state"})
    public static void setStatus(ImageView imageView, State state) {
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            switch (state) {
                case Read:
                    drawable.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.grey), PorterDuff.Mode.SRC_ATOP);
                    break;
                case Unread:
                    drawable.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.blue), PorterDuff.Mode.SRC_ATOP);
                    break;
                case Accepted:
                    drawable.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.green), PorterDuff.Mode.SRC_ATOP);
                    break;
            }
            imageView.setImageDrawable(drawable);
        }
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
