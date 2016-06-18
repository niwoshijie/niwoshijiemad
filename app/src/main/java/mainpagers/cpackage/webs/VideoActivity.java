package mainpagers.cpackage.webs;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;

import java.util.List;

public class VideoActivity extends Activity implements SurfaceHolder.Callback {

	private SurfaceView surface;
	private List<String> paths;
	private int index = 0;
	private String configpath;
	private SurfaceHolder surfaceHolder;
	private MediaPlayer player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_video);
//		paths = FileUtil.getVideos();
//		configpath = XMLParse.parse(this, FileUtil.getConfigPath());
//		// web视图
//		WebView webView = (WebView) findViewById(R.id.webView1);
//		webView.setWebChromeClient(new FullscreenableChromeClient(this));
//		webView.getSettings().setJavaScriptEnabled(true);
//		webView.getSettings().setAllowFileAccess(true);
//		webView.getSettings().setPluginState(PluginState.ON);
//		webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
//		webView.loadUrl(configpath);
//		webView.setWebViewClient(new WebViewClient() {
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				view.loadUrl(url);
//				return true;
//			}
//		});
//		// video 试图
//		surface = (SurfaceView) findViewById(R.id.surface);
//		surfaceHolder = surface.getHolder();// SurfaceHolder是SurfaceView的控制接口
//		surfaceHolder.addCallback(this);// 因为这个类实现了SurfaceHolder.Callback接口，所以回调参数直接this
//		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// Surface类型

	}

	public void onColose(View view) {
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (player.isPlaying()) {
			player.stop();
		}
		player.release();
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		play();
	}

	private void play() {
		player = new MediaPlayer();
		player.setAudioStreamType(AudioManager.STREAM_MUSIC);
		player.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				int i = index + 1;
				index = i < paths.size() ? i : 0;
				player.release();
				play();

			}
		});

		try {
			// 设置显示视频显示在SurfaceView上
			player.setDisplay(surfaceHolder);
			player.setDataSource(paths.get(index));
			player.prepare();
			player.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {

	}

	public class FullscreenableChromeClient extends WebChromeClient {

		protected Activity mActivity = null;
		private View mCustomView;
		private CustomViewCallback mCustomViewCallback;
		private int mOriginalOrientation;
		private FrameLayout mContentView;
		private FrameLayout mFullscreenContainer;
		private final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);

		public FullscreenableChromeClient(Activity activity) {
			this.mActivity = activity;
		}

		@Override
		public void onShowCustomView(View view, int requestedOrientation,
				CustomViewCallback callback) {
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				if (mCustomView != null) {
					callback.onCustomViewHidden();
					return;
				}
				mOriginalOrientation = mActivity.getRequestedOrientation();
				FrameLayout decor = (FrameLayout) mActivity.getWindow()
						.getDecorView();
				mFullscreenContainer = new FullscreenHolder(mActivity);
				mFullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
				decor.addView(mFullscreenContainer, COVER_SCREEN_PARAMS);
				mCustomView = view;
				setFullscreen(true);
				mCustomViewCallback = callback;
				mActivity.setRequestedOrientation(requestedOrientation);
			}
			super.onShowCustomView(view, requestedOrientation, callback);
		}

		@Override
		public void onHideCustomView() {
			if (mCustomView == null) {
				return;
			}
			setFullscreen(false);
			FrameLayout decor = (FrameLayout) mActivity.getWindow()
					.getDecorView();
			decor.removeView(mFullscreenContainer);
			mFullscreenContainer = null;
			mCustomView = null;
			mCustomViewCallback.onCustomViewHidden();
			mActivity.setRequestedOrientation(mOriginalOrientation);
		}

		private void setFullscreen(boolean enabled) {
			Window win = mActivity.getWindow();
			WindowManager.LayoutParams winParams = win.getAttributes();
			final int bits = WindowManager.LayoutParams.FLAG_FULLSCREEN;
			if (enabled) {
				winParams.flags |= bits;
			} else {
				winParams.flags &= ~bits;
				if (mCustomView != null) {
					mCustomView
							.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
				} else {
					mContentView
							.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
				}
			}

			win.setAttributes(winParams);
		}

		private class FullscreenHolder extends FrameLayout {
			public FullscreenHolder(Context ctx) {
				super(ctx);
				setBackgroundColor(ctx.getResources().getColor(
						android.R.color.black));
			}

			@Override
			public boolean onTouchEvent(MotionEvent evt) {
				return true;
			}
		}
	}

}
