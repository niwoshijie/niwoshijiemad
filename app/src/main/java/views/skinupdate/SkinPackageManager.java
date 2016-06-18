package views.skinupdate;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Map;

import base.APP;
import utils.MyCallBack;
import utils.NetUtils;
import utils.Utils;

/**
 * 皮肤包管理器
 * 
 * @author devilwwj
 * 
 */
public class SkinPackageManager {
	private static SkinPackageManager mInstance;
	public static final String DEX_SKIN_PATH = APP.mAppApplication.getExternalCacheDir().getParent() + "/wywy_skin/";
	private static String dex_skin_name = "wywy_skin_res_";

	public static String getDex_skin_name(String versionCode) {
		return dex_skin_name + versionCode + ".apk";
	}

	private Context mContext;

	/**
	 * 当前资源包名
	 */
	public String mPackageName;

	/**
	 * 皮肤资源
	 */
	public Resources mResources;

	public Resources getmResources() {
		return mResources;
	}

	private SkinPackageManager(Context mContext) {
		super();
		this.mContext = mContext;
	}

	/**
	 * 获取单例
	 * @return
	 */
	public static SkinPackageManager getInstance() {
		if (mInstance == null) {
			mInstance = new SkinPackageManager(APP.mAppApplication);
		}
		return mInstance;
	}

	/**
	 * 从assets中复制apk到sd中
	 * 
	 * @param context
	 * @param path
	 * @return
	 */
	public boolean copyApkFromAssets(Context context, String srcPath, String path) {
		boolean copyIsFinish = false;

		try {
			InputStream is = new FileInputStream(new File(srcPath));
			File file = new File(path);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			byte[] temp = new byte[1024];
			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i); // 写入到文件
			}

			fos.close();
			is.close();
			copyIsFinish = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return copyIsFinish;

	}

	private static final String TAG = "SkinPackageManager";
	
	/**
	 * 异步加载皮肤资源
	 * @param dexPath_name 需要加载的皮肤资源
	 * @param callback 回调接口
	 */
	public void loadSkinAsync(String dexPath_name, final loadSkinCallBack callback) {
		if (!SkinConfig.getInstance().isShowSkin()) {
			Log.i(TAG,"已超过显示时间，不加载皮肤");
			return;
		}
		new AsyncTask<String, Void, Resources>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				if (callback != null) {
					callback.startloadSkin();
				}
			}

			@Override
			protected Resources doInBackground(String... params) {
				try {
					if (params.length == 1) {

						String dexPath_tmp = params[0];
						// 得到包管理器
						PackageManager mpm = mContext.getPackageManager();
						// 得到包信息
						PackageInfo mInfo = mpm.getPackageArchiveInfo(dexPath_tmp, PackageManager.GET_ACTIVITIES);
						mPackageName = mInfo.packageName;

						AssetManager assetManager = AssetManager.class.newInstance();
						Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
						addAssetPath.invoke(assetManager, dexPath_tmp);

						Resources superRes = mContext.getResources();
						Resources skinResource = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
						SkinConfig.getInstance().setSkinResourcePath(dexPath_tmp); // 保存资源路径

						return skinResource;
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return null;
			}

			@Override
			protected void onPostExecute(Resources result) {
				super.onPostExecute(result);
				mResources = result;
				if (mResources != null) {
					Intent intent = new Intent(SUCCESS_LOADER_SKIN_ACTION);
					APP.mAppApplication.sendBroadcast(intent);
				}
				if (callback != null) {
					if (mResources != null) {
						callback.loadSkinSuccess();
					} else {
						callback.loadSkinFail();
					}
				}
			}

		}.execute(dexPath_name);
	}

	public static interface loadSkinCallBack {
		public void startloadSkin();
		public void loadSkinSuccess();
		public void loadSkinFail();
	}

	public static final String SUCCESS_LOADER_SKIN_ACTION = "success_loader_skin_action";

	public static void downLoaderSkinRes(String url, final String versionCode, Map maps,
			final DownLoaderCallBack downLoaderCallBack) {

		File file = new File(DEX_SKIN_PATH, getDex_skin_name(versionCode));
		if (file.exists() && file.isFile()) {
			SkinPackageManager.getInstance().loadSkinAsync(DEX_SKIN_PATH + getDex_skin_name(versionCode), null);
			Log.i(TAG,"资源文件已存在，不下载");
			return;
		} else {
			File directory = new File(DEX_SKIN_PATH);
			if (directory.exists() && directory.isDirectory()) {
				File[] listFiles = directory.listFiles();
				if (listFiles.length >= 5) {
					File tempFile = listFiles[0];
					for (int i = 0; i < listFiles.length - 1; i++) {
						Log.i(TAG,tempFile.getName() + tempFile.lastModified() + "==" + listFiles[i].getName()
								+ listFiles[i].lastModified());
						if (tempFile.lastModified() >= listFiles[i].lastModified()) {
							tempFile = listFiles[i];
						}
					}
					if (tempFile != null) {
						String absolutePath = tempFile.getAbsolutePath();
						if (tempFile.delete()) {
							Log.i(TAG,"文件数量大于5，删除文件成功--->" + absolutePath);
						} else {
							Log.i(TAG,"文件数量大于5，删除文件失败--->" + absolutePath);
						}
					}
				}
			}
		}
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			if(Utils.hasNetwork(APP.mAppApplication)){
				NetUtils.Post(url,maps,new MyCallBack<String>(){
					@Override
					public void onSuccess(String result) {
						if (downLoaderCallBack != null) {
							downLoaderCallBack.callBack();
						}
						SkinPackageManager.getInstance().loadSkinAsync(DEX_SKIN_PATH + getDex_skin_name(versionCode), null);
						Intent intent = new Intent(SUCCESS_LOADER_SKIN_ACTION);
						APP.mAppApplication.sendBroadcast(intent);
						Log.i(TAG,"下载皮肤资源包成功");
					}
				});
			} else {
				Log.i(TAG,"网络未连接，皮肤下载失败");
			}
		} else {
			Log.i(TAG,"外置储存无效");
		}
	}

	public interface DownLoaderCallBack {
		void callBack();
	}

}
