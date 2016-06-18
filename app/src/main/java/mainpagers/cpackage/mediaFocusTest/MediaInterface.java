package mainpagers.cpackage.mediaFocusTest;

/**
 * Created by LiuShao on 2016/3/14.
 */
public class MediaInterface{

    private static MediaInterface mediaInterface;
    public static MediaInterface getMediaFocusInterface(){
        if(mediaInterface==null){
            mediaInterface = new MediaInterface();
        }
        return mediaInterface;
    }

    public MediaFocusInterface mediaFocusInterface;

    public void setMediaFocusInterface(MediaFocusInterface mediaFocusInterface) {
        this.mediaFocusInterface = mediaFocusInterface;
    }

    public static final int MEDIA_MUSIC = 200;
    public static final int MEDIA_VIDEO = 210;
    public static final int MEDIA_TTS = 220;

    public interface MediaFocusInterface{
        void onReceiveAudioStart(int type);
        void onReceiveAudioStop(int type);
    }

}
