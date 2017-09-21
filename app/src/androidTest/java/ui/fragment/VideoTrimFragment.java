package ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import videodemo.R;
import view.VideoTrimmerView;

/**
 * Created by admin on 2017-07-18.
 */

public class VideoTrimFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videotrim, container, false);
        VideoTrimmerView vtv_videotrimmerview = view.findViewById(R.id.vtv_videotrimmerview);

        /**
         * 接受在VideoTrimActivity点击传过来的视频路径
         */
        Bundle bundle1 = getArguments();
        String videopath = bundle1.getString("VideoPath");

        /**
         * 将传过来的视频路径传递给VideoTrimmerView
         */
        vtv_videotrimmerview.setVideoPath(videopath);

        return view;
    }
}
