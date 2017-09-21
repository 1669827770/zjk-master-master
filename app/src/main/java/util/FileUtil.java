package util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
	private static final String TAG = "FileUtil";
	private static final File parentPath = Environment.getExternalStorageDirectory();
	private static String storagePath = "";
	private static final String DST_FOLDER_NAME = "obdTest";

	/**初始化保存路径
	 * @return
	 */
	private static String initPath(){
		if(storagePath.equals("")){
			storagePath = parentPath.getAbsolutePath()+"/" + DST_FOLDER_NAME;
			File f = new File(storagePath);
			if(!f.exists()){
				f.mkdir();
			}
		}
		return storagePath;
	}

	/**保存Bitmap到sdcard
	 * @param b
	 * @return 返回拍照图片在内存卡中的地址
	 */
	public static String saveBitmap(Bitmap b){

		String path = initPath();
		long dataTake = System.currentTimeMillis();
		String jpegName = path + "/" + dataTake +"大哥.jpg";
		Log.i(TAG, "saveBitmap:jpegName = " + jpegName);
		try {
			FileOutputStream fout = new FileOutputStream(jpegName);
			BufferedOutputStream bos = new BufferedOutputStream(fout);
			b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
			Log.i(TAG, "saveBitmap成功");
			return jpegName;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.i(TAG, "saveBitmap:失败");
//			e.printStackTrace();
		}
		return jpegName;
	}


	public static String saveCarImg(Bitmap mBitmap)  {
		String filePath = parentPath.getAbsolutePath()+"/" + "obd/car/img";
		String jpegName = filePath + "/car.jpg";
		File myCaptureFile = new File(jpegName);
		try {
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				if (!myCaptureFile.getParentFile().exists()) {
					myCaptureFile.getParentFile().mkdirs();
				}
				BufferedOutputStream bos;
				bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
				mBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
				bos.flush();
				bos.close();
			} else {
				//"保存失败，SD卡无效"
				return null;
			}
			return jpegName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void savePhoto(String fileName, Bitmap bitmap) {
		File myCaptureFile = new File(fileName);
		try {
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				if (!myCaptureFile.getParentFile().exists()) {
					myCaptureFile.getParentFile().mkdirs();
				}
				BufferedOutputStream bos;
				bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
				bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);
				bos.flush();
				bos.close();
			} else {
				//"保存失败，SD卡无效"
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
