package first;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import base.ComeInActivity;
import base.MADConstant;
import liushaobo.mad.R;
import mainpagers.BasePager;
import mainpagers.MainPagerAdapter;
import mainpagers.cpackage.CPager;
import mainpagers.daipaclage.DAIPager;
import mainpagers.dpackage.DAPager;
import mainpagers.mpackage.MPager;
import mainpagers.spackage.SPager;
import utils.FileUtils;
import utils.RecyclerViewCacheUtil;
import utils.logs1.LogcatHelper;
import views.pagers.LazyViewPager;

/**
 * Created by LiuShao on 2016/1/27.
 */
public class FirstInActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {

    @ViewInject(R.id.main_viewpager)
    private MyViewPager main_viewpager;
    @ViewInject(R.id.bottom_layout)
    private RadioGroup bottom_layout;
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private boolean opened = false;

    private List<BasePager> basePagers;
    private Context context;

    public static int madTheme = -1;

    private String[] menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_in_main_layout);
        org.xutils.x.view().inject(this);
        context = this;

        /*日志信息*/
        LogcatHelper.getInstance(this).start();

        //创建资源文件夹
        createFolder();

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        final IProfile profile = new ProfileDrawerItem().withName("Max Muster").withEmail("max.mustermann@gmail.com").withIcon(R.mipmap.ic_launcher).withIdentifier(102);

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.mipmap.header)
                .addProfiles(profile)
                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withRootView(R.id.ff_drawer_layout)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.menu_a).withDescription(R.string.menu_a_describe).withIcon(GoogleMaterial.Icon.gmd_sun).withIdentifier(1).withSelectable(false)


                )// add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        //check if the drawerItem is set.
                        //there are different reasons for the drawerItem to be null
                        //--> click on the header
                        //--> click on the footer
                        //those items don't contain a drawerItem
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                intent = new Intent(FirstInActivity.this, ComeInActivity.class);
                            }
                            if (intent != null) {
                                FirstInActivity.this.startActivity(intent);
                            }
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(false)
                .build();

        RecyclerViewCacheUtil.getInstance().withCacheSize(2).init(result);

        initData();
    }

    /**
     * 创建项目使用的文件夹
     */
    private void createFolder() {
        FileUtils.createFolder(MADConstant.MAD_PATH);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuItem == null) {
            menuItem = getResources().getStringArray(R.array.style_names);
        }

        for (String name : menuItem) {
            MenuItem menuItem = menu.add(name);
            menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            menuItem.setOnMenuItemClickListener(FirstInActivity.this);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private MainPagerAdapter adapter;

    private void initData() {
        basePagers = new ArrayList<>();
        basePagers.add(new CPager(context));
        basePagers.add(new SPager(context));
        basePagers.add(new DAPager(context));
        basePagers.add(new DAIPager(context));
        basePagers.add(new MPager(context));
        adapter = new MainPagerAdapter(basePagers);
        main_viewpager.setAdapter(adapter);

        main_viewpager.setCurrentItem(0, true);
        basePagers.get(0).initData();
        basePagers.get(0).is_load = true;

        main_viewpager.setOnPageChangeListener(new LazyViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onPageSelected(int position) {
                BasePager basePager = basePagers.get(position);
                if (!basePager.is_load) {
                    basePager.is_load = true;
                    basePager.initData();
                }
                setCurrentCheckedButton(position);
            }
        });

        bottom_layout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (result != null && result.isDrawerOpen()) {
                    result.closeDrawer();
                }
                switch (checkedId) {
                    case R.id.bottom_tv1:
                        main_viewpager.setCurrentItem(0, true);
                        break;
                    case R.id.bottom_tv2:
                        main_viewpager.setCurrentItem(1, false);
                        break;
                    case R.id.bottom_tv3:
                        main_viewpager.setCurrentItem(2, false);
                        break;
                    case R.id.bottom_tv4:
                        main_viewpager.setCurrentItem(3, false);
                        break;
                    case R.id.bottom_tv5:
                        main_viewpager.setCurrentItem(4, false);
                        break;
                }
            }
        });
    }

    private void setCurrentCheckedButton(int position) {
        switch (position) {
            case 0:
                bottom_layout.check(R.id.bottom_tv1);
                break;
            case 1:
                bottom_layout.check(R.id.bottom_tv2);
                break;
            case 2:
                bottom_layout.check(R.id.bottom_tv3);
                break;
            case 3:
                bottom_layout.check(R.id.bottom_tv4);
                break;
            case 4:
                bottom_layout.check(R.id.bottom_tv5);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }


    public static final int NONE_THEME = 0;
    public static final int DEFAULT_THEME = 1;
    public static final int DEFAULT_LIGHT_THEME = 2;
    public static final int DEFAULT_LIGH_DARK_THEME = 3;
    public static final int RED_THEME = 4;
    public static final int PINK_THEME = 5;
    public static final int PURPLE_THEME = 6;
    public static final int DEEP_PURPLE_THEME = 7;
    public static final int INDIGO_THEME = 8;
    public static final int BLUE_THEME = 9;
    public static final int LIGHT_BLUE_THEME = 10;
    public static final int CYAN_THEME = 11;
    public static final int TEAL_THEME = 12;
    public static final int GREEN_THEME = 13;
    public static final int LIGHT_THEME = 14;
    public static final int LIME_THEME = 15;
    public static final int YELLOW_THEME = 16;
    public static final int AMBER_THEME = 17;
    public static final int ORANGE_THEME = 18;
    public static final int DEEP_ORANGE_THEME = 19;
    public static final int BROWN_THEME = 20;
    public static final int GREY_THEME = 21;
    public static final int BLUE_GREY_THEME = 22;


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        for (int i = 0; i < menuItem.length; i++) {
            if (item.getTitle().equals(menuItem[i])) {
//                setTheme(i);
//                reload();
                return true;
            }
        }
        return false;
    }

//     public void setTheme(int index) {
//        switch (index) {
//            case DEFAULT_THEME:
//                madTheme = R.style.DefaultTheme;
//                break;
//            case DEFAULT_LIGHT_THEME:
//                madTheme = R.style.DefaultLightTheme;
//                break;
//            case DEFAULT_LIGH_DARK_THEME:
//                madTheme = R.style.DefaultLightDarkTheme;
//                break;
//            case RED_THEME:
//                madTheme = R.style.RedTheme;
//                break;
//            case PINK_THEME:
//                madTheme = R.style.PinkTheme;
//                break;
//            case PURPLE_THEME:
//                madTheme = R.style.PurpleTheme;
//                break;
//            case DEEP_PURPLE_THEME:
//                madTheme = R.style.DeepPurpleTheme;
//                break;
//            case INDIGO_THEME:
//                madTheme = R.style.IndigoTheme;
//                break;
//            case BLUE_THEME:
//                madTheme = R.style.BlueTheme;
//                break;
//            case LIGHT_BLUE_THEME:
//                madTheme = R.style.LightBlueTheme;
//                break;
//            case CYAN_THEME:
//                madTheme = R.style.CyanTheme;
//                break;
//            case TEAL_THEME:
//                madTheme = R.style.TealTheme;
//                break;
//            case GREEN_THEME:
//                madTheme = R.style.GreenTheme;
//                break;
//            case LIGHT_THEME:
//                madTheme = R.style.LightGreenTheme;
//                break;
//            case LIME_THEME:
//                madTheme = R.style.LimeTheme;
//                break;
//            case YELLOW_THEME:
//                madTheme = R.style.YellowTheme;
//                break;
//            case AMBER_THEME:
//                madTheme = R.style.AmberTheme;
//                break;
//            case ORANGE_THEME:
//                madTheme= R.style.OrangeTheme;
//                break;
//            case DEEP_ORANGE_THEME:
//                madTheme = R.style.DeepOrangeTheme;
//                break;
//            case BROWN_THEME:
//                madTheme = R.style.BrownTheme;
//                break;
//            case GREY_THEME:
//                madTheme = R.style.GreyTheme;
//                break;
//            case BLUE_GREY_THEME:
//                madTheme = R.style.BlueGreyTheme;
//                break;
//            default:
//                break;
//        }
//         super.setTheme(madTheme);
//    }




}
