package lucassales.com.getninjastest.ui.presenter;

import java.lang.ref.WeakReference;

/**
 * Created by lucas on 31/01/17.
 */

public abstract class Presenter<Listener> {

    private boolean mHasInitialized;
    private WeakReference<Listener> mListener;
    private boolean isRunning;

    protected Presenter(Listener listener) {
        mListener = new WeakReference<>(listener);
    }

    protected Listener getListener() {
        return mListener.get();
    }

    protected boolean isRunning() {
        return isRunning;
    }

    public void onResume() {
        if (!mHasInitialized) {
            mHasInitialized = true;
            init();
        }
        isRunning = true;
    }

    public void onPause() {
        isRunning = false;
    }

    protected abstract void init();


}
