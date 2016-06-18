package views.skinupdate;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import base.APP;

public class SkinConfig {
	private static SkinConfig mInstance;
	private Context mContext;

	private SkinConfig(Context mContext) {
		super();
		this.mContext = mContext;
	}

	public static SkinConfig getInstance() {
		if (mInstance == null) {
			mInstance = new SkinConfig(APP.mAppApplication);
		}
		return mInstance;
	}

	public void setSkinResourcePath(String skinPath) {
		SharedPreferences sp = mContext.getSharedPreferences("skin_sharePref", mContext.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("skinPath", skinPath);
		editor.commit();
	}

	public String getSkinResourcePath() {
		SharedPreferences sp = mContext.getSharedPreferences("skin_sharePref", mContext.MODE_PRIVATE);
		return sp.getString("skinPath", "");
	}

	public void setSkinShowTime(long startTime, long endTime) {
		SharedPreferences sp = mContext.getSharedPreferences("skin_sharePref", mContext.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putLong("skinShowStartTime", startTime);
		editor.putLong("skinShowEndTime", endTime);
		editor.commit();
	}

	public boolean isShowSkin() {
		SharedPreferences sp = mContext.getSharedPreferences("skin_sharePref", mContext.MODE_PRIVATE);
		long start = sp.getLong("skinShowStartTime", 0);
		long endTime = sp.getLong("skinShowEndTime", 0);
		long timeMillis = System.currentTimeMillis();
		if (start <= timeMillis && endTime > timeMillis) {
			return true;
		} else {
			return false;
		}
	}

}
