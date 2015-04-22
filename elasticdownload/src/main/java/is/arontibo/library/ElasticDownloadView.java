package is.arontibo.library;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * Created by thibaultguegan on 15/03/15.
 */
public class ElasticDownloadView extends FrameLayout implements IntroView.EnterAnimationListener{

    private static final String LOG_TAG = ElasticDownloadView.class.getSimpleName();

    private IntroView mIntroView;
    private ProgressDownloadView mProgressDownloadView;

    /**
     * MARK: Constructor
     */

    public ElasticDownloadView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_elasticdownload, this, true);
    }

    /**
     * MARK: Overrides
     */

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mIntroView = (IntroView) findViewById(R.id.intro_view);
        mIntroView.setListener(this);
        mProgressDownloadView = (ProgressDownloadView) findViewById(R.id.progress_download_view);

        ViewTreeObserver vto = mProgressDownloadView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mProgressDownloadView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mIntroView.getLayoutParams().width = mProgressDownloadView.getWidth();
                mIntroView.getLayoutParams().height = mProgressDownloadView.getHeight();
            }
        });
    }

    /**
     * MARK: Public methods
     */

    public void startIntro() {
        mIntroView.startAnimation();
    }

    public void setProgress(float progress) {
        mProgressDownloadView.setPercentage(progress);
    }

    public void success() {
        mProgressDownloadView.drawSuccess();
    }

    public void fail() {
        mProgressDownloadView.drawFail();
    }


    /**
     * MARK: Enter animation overrides
     */

    @Override
    public void onEnterAnimationFinished() {
        mIntroView.setVisibility(INVISIBLE);
        mProgressDownloadView.setVisibility(VISIBLE);
        mProgressDownloadView.setProgress(mProgressDownloadView.getProgress());

        //do further actions if necessary
    }

}