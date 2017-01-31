package lucassales.com.getninjastest;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.widget.ImageView;

import lucassales.com.getninjastest.network.ServiceGenerator;
import lucassales.com.getninjastest.ui.DetailsActivity;
import lucassales.com.getninjastest.ui.MainActivity;

/**
 * Created by lucas on 30/01/17.
 */

public class Utility {
    private static String getPathFromLink(String link) {
        return link.replaceAll(ServiceGenerator.API_BASE_URL, "");
    }

    public static Intent createIntentForLink(Context context, String link) {
        String path = getPathFromLink(link);
        Intent intent;
        if (path.equals("offers")) {
          intent = new Intent(context, MainActivity.class);
          intent.putExtra(MainActivity.IS_LEAD, false);
        } else if (path.equals("leads")) {
            intent = new Intent(context, MainActivity.class);
            intent.putExtra(MainActivity.IS_LEAD, true);
        } else if (path.startsWith("lead-")) {
            intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(DetailsActivity.PATH, path);
            intent.putExtra(DetailsActivity.IS_LEAD, true);
        } else if (path.startsWith("offer-")) {
            intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(DetailsActivity.PATH, path);
            intent.putExtra(DetailsActivity.IS_LEAD, false);
        } else {
            intent = new Intent();
        }

        return intent;
    }

    @BindingAdapter({"bind:imageViewColor"})
    public static void setImageViewColor(ImageView imageView, @ColorInt int color) {
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            imageView.setImageDrawable(drawable);
        }
    }
}
