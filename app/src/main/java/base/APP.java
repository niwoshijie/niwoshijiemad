package base;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;

import org.androidannotations.annotations.EApplication;
import org.xutils.x;

import java.util.ArrayList;

import liushaobo.mad.R;
import views.skinupdate.ISkinUpdate;
import views.skinupdate.SkinConfig;
import views.skinupdate.SkinPackageManager;

@EApplication
public class APP extends Application {
    public static APP mAppApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppApplication = this;


        initXutils();
        initImageLoader();
        initSkin();

    }

    public ArrayList<ISkinUpdate> skinUpdates = new ArrayList<ISkinUpdate>();

    private void initSkin() {
        String skinPath = SkinConfig.getInstance().getSkinResourcePath();
        if (!TextUtils.isEmpty(skinPath)) {
            //如果已经换皮肤，那么第二次进来时，需要加载该皮肤
            SkinPackageManager.getInstance().loadSkinAsync(skinPath, null);
        }
    }

    public void changeSkin() {
        for (ISkinUpdate skin : skinUpdates) {
            skin.updateSkin();
        }
    }


    private void initImageLoader() {
        //initialize and create the image loader logic
        //        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
        //            @Override
        //            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
        //                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
        //            }
        //            @Override
        //            public void cancel(ImageView imageView) {
        //                Picasso.with(imageView.getContext()).cancelRequest(imageView);
        //            }
        //        });

        //initialize and create the image loader logic
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Glide.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Glide.clear(imageView);
            }

            @Override
            public Drawable placeholder(Context ctx, String tag) {
                //define different placeholders for different imageView targets
                //default tags are accessible via the DrawerImageLoader.Tags
                //custom ones can be checked via string. see the CustomUrlBasePrimaryDrawerItem LINE 111
                if (DrawerImageLoader.Tags.PROFILE.name().equals(tag)) {
                    return DrawerUIUtils.getPlaceHolder(ctx);
                } else if (DrawerImageLoader.Tags.ACCOUNT_HEADER.name().equals(tag)) {
                    return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(com.mikepenz.materialdrawer.R.color.primary).sizeDp(56);
                } else if ("customUrlItem".equals(tag)) {
                    return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(R.color.md_red_500).sizeDp(56);
                }
                //we use the default one for
                //DrawerImageLoader.Tags.PROFILE_DRAWER_ITEM.name()
                return super.placeholder(ctx, tag);
            }
        });
    }


    private void initXutils() {
        //initialize xutils
        x.Ext.init(this);
        x.Ext.setDebug(true);

    }


}
