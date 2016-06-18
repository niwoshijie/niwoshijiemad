package mainpagers.cpackage.t23DesignMode.prototype;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by LiuShao on 2016/6/12.
 * 
 */
public class WordDocument implements Cloneable {
    private static final String TAG = "WordDocument";

    private String mText;
    private ArrayList<String> mImages = new ArrayList<>();

    public WordDocument() {
        Log.d(TAG, "WordDocument: " + "");
    }

    /*浅拷贝*/
//    @Override
//    protected WordDocument clone() {
//        try {
//            WordDocument doc = (WordDocument) super.clone();
//            doc.mImages = this.mImages;
//            doc.mText = this.mText;
//            return doc;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    /*深拷贝*/
    @Override
    public Object clone() throws CloneNotSupportedException {
        WordDocument wor = null;
        try {
            wor = (WordDocument) super.clone();
            wor.mText = this.mText;
            wor.mImages = (ArrayList<String>) this.mImages.clone();
            return wor;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
       return null;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public ArrayList<String> getmImages() {
        return mImages;
    }

    public void addImage(String mImages) {
        this.mImages.add(mImages);
    }

    public void showDocument() {
        Log.d(TAG, "showDocument: " + mText);
        System.out.println("showDocumentText: " + mText);
        for (String imgName : mImages) {
            Log.d(TAG, "showDocument: " + imgName);
            System.out.println("showDocumentImage: " + imgName);
        }
    }

}
