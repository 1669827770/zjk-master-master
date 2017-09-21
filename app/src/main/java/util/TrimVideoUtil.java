package util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

import ui.activity.video.SingleCallback;
import ui.activity.video.VideoInfo;


public class TrimVideoUtil {

    private static final String TAG = TrimVideoUtil.class.getSimpleName();
    public static int VIDEO_MAX_DURATION = 20;// 20秒  这个20秒会转换成一个屏幕的宽度
    public static final int MIN_TIME_FRAME = 5;   //选择的视频是否小于5秒

    /**
     * UnitConverter.dpToPxx(20),这个20左右连个手柄距离两边屏幕的距离和，也就是各自是10
     * thumb_Width，表示每一秒应该展示的宽度（一秒取一个图片，一个图片对应的宽度）
     */
    private static final float thumb_Width = (DeviceUtil.getDeviceWidth() - UnitConverter.dpToPxx(20)) / VIDEO_MAX_DURATION;
    private static final int thumb_Height = UnitConverter.dpToPx(60);
    private static long one_frame_time = 1000000;//隔多长时间取一帧     一秒取一帧

//    public void setMaxDuration(int i){
//        this.VIDEO_MAX_DURATION=i;
//    }

//    public static void startTrim(File src, String dst, long startMs, long endMs, OnTrimVideoListener callback) throws IOException {
//        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
//        final String fileName = "trimmedVideo_" + timeStamp + ".mp4";
//        final String filePath = dst + fileName;
//
//        File file = new File(filePath);
//        file.getParentFile().mkdirs();
//        genVideoUsingMp4Parser(src, file, startMs, endMs, callback);
//    }

//    private static void genVideoUsingMp4Parser(File src, File dst, long startMs, long endMs, OnTrimVideoListener callback) throws IOException {
//
//        // NOTE: Switched to using FileDataSourceViaHeapImpl since it does not use memory mapping (VM).
//        // Otherwise we get OOM with large movie files.
//        Movie movie = MovieCreator.build(new FileDataSourceViaHeapImpl(src.getAbsolutePath()));
//
//        List<Track> tracks = movie.getTracks();
//        movie.setTracks(new LinkedList<Track>());
//        // remove all tracks we will create new tracks from the old
//
//        double startTime1 = startMs / 1000;
//        double endTime1 = endMs / 1000;
//
//        boolean timeCorrected = false;
//
//        // Here we try to find a track that has sync samples. Since we can only start decoding
//        // at such a sample we SHOULD make sure that the start of the new fragment is exactly
//        // such a frame
////        for (Track track : tracks) {
////            if (track.getSyncSamples() != null && track.getSyncSamples().length > 0) {
////                if (timeCorrected) {
////                    // This exception here could be a false positive in case we have multiple tracks
////                    // with sync samples at exactly the same positions. E.g. a single movie containing
////                    // multiple qualities of the same video (Microsoft Smooth Streaming file)
////
////                    throw new RuntimeException("The startTime has already been corrected by another track with SyncSample. Not Supported.");
////                }
////                startTime1 = correctTimeToSyncSample(track, startTime1, false);
////                endTime1 = correctTimeToSyncSample(track, endTime1, true);
////                timeCorrected = true;
////            }
////        }
//
//        if(startTime1 == 0)
//            startTime1 = startMs/1000;
//
//        if(endTime1 == 0)
//            endTime1 = endMs/1000;
//
//        for (Track track : tracks) {
//            long currentSample = 0;
//            double currentTime = 0;
//            double lastTime = -1;
//            long startSample1 = -1;
//            long endSample1 = -1;
//
//            for (int i = 0; i < track.getSampleDurations().length; i++) {
//                long delta = track.getSampleDurations()[i];
//
//
//                if (currentTime > lastTime && currentTime <= startTime1) {
//                    // current sample is still before the new starttime
//                    startSample1 = currentSample;
//                }
//                if (currentTime > lastTime && currentTime <= endTime1) {
//                    // current sample is after the new start time and still before the new endtime
//                    endSample1 = currentSample;
//                }
//                lastTime = currentTime;
//                currentTime += (double) delta / (double) track.getTrackMetaData().getTimescale();
//                currentSample++;
//            }
////            movie.addTrack(new AppendTrack(new CroppedTrack(track, startSample1, endSample1)));
//            movie.addTrack(new CroppedTrack(track, startSample1, endSample1));
//        }
//
//        dst.getParentFile().mkdirs();
//
//        if (!dst.exists()) {
//            dst.createNewFile();
//        }
//
//        Container out = new DefaultMp4Builder().build(movie);
//
//        FileOutputStream fos = new FileOutputStream(dst);
//        FileChannel fc = fos.getChannel();
//        out.writeContainer(fc);
//
//        fc.close();
//        fos.close();
//        callback.onFinishTrim(Uri.parse(dst.toString()));
//    }

//    private static double correctTimeToSyncSample(Track track, double cutHere, boolean next) {
//        double[] timeOfSyncSamples = new double[track.getSyncSamples().length];
//        long currentSample = 0;
//        double currentTime = 0;
//        for (int i = 0; i < track.getSampleDurations().length; i++) {
//            long delta = track.getSampleDurations()[i];
//
//            if (Arrays.binarySearch(track.getSyncSamples(), currentSample + 1) >= 0) {
//                // samples always start with 1 but we start with zero therefore +1
//                timeOfSyncSamples[Arrays.binarySearch(track.getSyncSamples(), currentSample + 1)] = currentTime;
//            }
//            currentTime += (double) delta / (double) track.getTrackMetaData().getTimescale();
//            currentSample++;
//
//        }
//        double previous = 0;
//        for (double timeOfSyncSample : timeOfSyncSamples) {
//            if (timeOfSyncSample > cutHere) {
//                if (next) {
//                    return timeOfSyncSample;
//                } else {
//                    return previous;
//                }
//            }
//            previous = timeOfSyncSample;
//        }
//        return timeOfSyncSamples[timeOfSyncSamples.length - 1];
//    }

    public static void backgroundShootVideoThumb(final int j , final Context context, final Uri videoUri, final SingleCallback<ArrayList<Bitmap>, Integer> callback) {

        final ArrayList<Bitmap> thumbnailList = new ArrayList<>();

        BackgroundExecutor.execute(new BackgroundExecutor.Task("", 0L, "") {
                   @Override
                   public void execute() {
                       try {
                           MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                           mediaMetadataRetriever.setDataSource(context, videoUri);

                           // Retrieve media data use microsecond检索媒体数据使用微秒    这个时间是取帧的的总时长
                           long videoLengthInMs = Long.parseLong(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) * 1000;
                           Log.d("00000000000000000000001", "[" + videoLengthInMs + "]");

                           //如果视频时长小于一帧就是1一帧，大于的话就取numThumbs帧
                           long  numThumbs = videoLengthInMs < one_frame_time? 1 : (videoLengthInMs / one_frame_time);
                           //interval间隔的意思
//                           final long interval = videoLengthInMs / numThumbs;

                           //每次截取到1帧之后上报（每次1帧绘制一次）（原来是三帧）
                           for (long i = 0; i < numThumbs; ++i) {
                               Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(i * one_frame_time, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);

//                               try {
//                                   //每一帧宽度thumb_Width是屏幕的宽度除以总时长
//                                   if(j==1){    //解决视频封面不清晰问题
                                       bitmap = Bitmap.createScaledBitmap(bitmap,(int) thumb_Width, thumb_Height, true);
//                                   }
////
//                               } catch (Exception e) {
//                                   e.printStackTrace();
//                               }
                               //将截屏保存在list集合中
                               thumbnailList.add(bitmap);
                                if(thumbnailList.size() == 1) {
                                    //第二个参数one_frame_time是随便写的，没什么用，因为复制过来的工具类就有这个参数，所以就加上了
                                    callback.onSingleCallback((ArrayList<Bitmap>)thumbnailList.clone(), (int) one_frame_time);
                                    thumbnailList.clear();
                                }
                           }
                           if(thumbnailList.size() > 0) {
                               callback.onSingleCallback((ArrayList<Bitmap>) thumbnailList.clone(), (int) one_frame_time);
                               thumbnailList.clear();
                           }
                           mediaMetadataRetriever.release();

                       } catch (final Throwable e) {
                           Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                       }
                   }
               }
        );

    }

    /**
     * 需要设计成异步的
     *
     * @param mContext
     * @return
     */
    public static ArrayList<VideoInfo> getAllVideoFiles(Context mContext) {
        VideoInfo video;
        ArrayList<VideoInfo> videos = new ArrayList<>();
        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            Cursor cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null,
                    null, null, MediaStore.Video.Media.DATE_MODIFIED + " desc");
            while (cursor.moveToNext()) {
                video = new VideoInfo();

                if (cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION)) != 0) {
                    video.setDuration(cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION)));
                    video.setVideoPath(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)));
                    video.setCreateTime(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED)));
                    video.setVideoName(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME)));
                    videos.add(video);
                }
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videos;
    }

    public static String getVideoFilePath(String url) {

        if (TextUtils.isEmpty(url) || url.length() < 5) {
            return "";
        }

        if (url.substring(0, 4).equalsIgnoreCase("http")) {

        } else {
            url = "file://" + url;
        }
        return url;
    }
}
