package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ui.activity.video.DateUtil;
import ui.activity.video.SingleCallback;
import ui.activity.video.VideoInfo;
import util.ScreenUtils;
import videodemo.R;


/**
 * Author：J.Chou
 * Date：  2016.08.01 3:45 PM
 * Email： who_know_me@163.com
 * Describe:
 */
public class VideoGridViewAdapter extends RecyclerView.Adapter<VideoGridViewAdapter.VideoViewHolder> {

    private ArrayList<VideoInfo> videoListData;
    private Context context;
    private SingleCallback<Boolean, VideoInfo> mSingleCallback;
    private ArrayList<VideoInfo> videoSelect = new ArrayList<>();
    private ArrayList<ImageView> selectIconList = new ArrayList<>();

    VideoGridViewAdapter(Context context, ArrayList<VideoInfo> dataList) {
        if(dataList.size()==0){
            Toast.makeText(context,"手机内暂无视频可供选择", Toast.LENGTH_SHORT).show();
        }

        this.context = context;
        this.videoListData=dataList;
//        ondataNumber.refrsh(videoListData);

    }


//    public static OnVideoNumber ondataNumber;
//
//    //2.创建接受接口对象的方法
//    public static setOnPathListener(OnVideoNumber onDataNumber) {
//        this.ondataNumber = onDataNumber;
//    }
//
//    //1.定义回调接口
//    public interface OnVideoNumber {
//        void refrsh(ArrayList<VideoInfo> videoInfos);
//
//    }


    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = null;
        view = inflater.inflate(R.layout.video_item, null);

        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        VideoInfo video = videoListData.get(position);
        holder.durationTv.setText(DateUtil.convertSecondsToTime(video.getDuration() / 1000));
        //如果用ImageLoader还有有重影问题，所以该用glide
//        ImageLoader.getInstance().displayImage(TrimVideoUtil.getVideoFilePath(video.getVideoPath()),holder.videoCover);
        String videoPath = video.getVideoPath();
        holder.videoCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(videoPath).thumbnail(0.1F).skipMemoryCache(true).
        into(holder.videoCover);
    }

    @Override
    public int getItemCount() {
        return videoListData.size();
    }

    void setItemClickCallback(final SingleCallback singleCallback) {
        this.mSingleCallback = singleCallback;
    }

    private boolean isSelected = false;

    class VideoViewHolder extends RecyclerView.ViewHolder {

        ImageView videoCover, selectIcon;
        View videoItemView, videoSelectPanel;
        TextView durationTv;

        VideoViewHolder(final View itemView) {
            super(itemView);
            videoItemView = itemView.findViewById(R.id.video_view);
            videoCover = (ImageView) itemView.findViewById(R.id.cover_image);
            durationTv = (TextView) itemView.findViewById(R.id.video_duration);
            videoSelectPanel = itemView.findViewById(R.id.video_select_panel);
            selectIcon = (ImageView) itemView.findViewById(R.id.select);

            int size = ScreenUtils.getScreenWidth() / 4;
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) videoCover.getLayoutParams();
            params.width = size;
            params.height = size;
            videoCover.setLayoutParams(params);
            videoCover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VideoInfo videoInfo = videoListData.get(getAdapterPosition());
                    if(videoSelect.size() > 0 ){
                        if(videoInfo.equals(videoSelect.get(0))){
                            selectIcon.setImageResource(R.drawable.icon_video_unselected);
                            clearAll();
                            isSelected = false;
                        }else{
                            selectIconList.get(0).setImageResource(R.drawable.icon_video_unselected);
                            clearAll();
                            addData(videoInfo);
                            selectIcon.setImageResource(R.drawable.icon_video_selected);
                            isSelected = true;
                        }

                    }else{
                        clearAll();
                        addData(videoInfo);
                        selectIcon.setImageResource(R.drawable.icon_video_selected);
                        isSelected = true;
                    }
                    mSingleCallback.onSingleCallback(isSelected, videoListData.get(getAdapterPosition()));
                }
            });
        }

        private void addData(VideoInfo videoInfo) {
            videoSelect.add(videoInfo);
            selectIconList.add(selectIcon);
        }
    }

    private void clearAll() {
        videoSelect.clear();
        selectIconList.clear();
    }
}
