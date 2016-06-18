package views.skinupdate;

import android.content.res.Resources;

public class SkinGetResouceId {
	private static final String SKIN_PACKAGE_NAME = "com.wywy.skin";

	public static final String SKIN_DRAWABLE = "drawable";
	public static final String SKIN_STRING = "string";
	public static final String SKIN_COLOR = "color";
	public static final String SKIN_DIMEN = "dimen";
	public static final String SKIN_LAYOUT = "layout";
	
	public static int getDrawable(String name, String type, Resources res) {
		return res.getIdentifier(name, type, SKIN_PACKAGE_NAME);
	}
}
