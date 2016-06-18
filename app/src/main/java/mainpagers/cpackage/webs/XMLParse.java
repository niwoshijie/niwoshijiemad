package mainpagers.cpackage.webs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;

public class XMLParse {

	public static String parse(Context context, String path) {
		String config = null;
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(path);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			XmlPullParserFactory pullFactory;
			pullFactory = XmlPullParserFactory.newInstance();
			XmlPullParser xmlPullParser = pullFactory.newPullParser();
			xmlPullParser.setInput(inputStream, "UTF-8");
			int eventType = xmlPullParser.getEventType();
			boolean isDone = false;
			while ((eventType != XmlPullParser.END_DOCUMENT)
					&& (isDone != true)) {
				String localName = null;
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					localName = xmlPullParser.getName();
					if (localName.equalsIgnoreCase("http")) {
						config = xmlPullParser.getAttributeValue(null, "uri");
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				}
				eventType = xmlPullParser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return config;

	}
}
