package com.kido.videopager.utils;

import com.kido.videopager.R;
import com.kido.videopager.VideoData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kido
 */

public class TestData {

    public static final int[] VIDEO_THUMBS = {R.drawable.image_hor_1, R.drawable.image_ver_1, R.drawable.image_hor_2, R.drawable.image_hor_3,
            R.drawable.image_hor_4, R.drawable.image_ver_2, R.drawable.image_ver_3, R.drawable.image_ver_4};

    public static final String[] VIDEO_WIDTH_HEIGHT = {"400x300", "300x400", "400x300", "400x300",
            "400x300", "300x400", "300x400", "300x400",
    };

    public static List<VideoData> loadVideoData() {
        List<VideoData> datas = new ArrayList<>();
        for (int i = 0, z = VIDEO_THUMBS.length; i < z; i++) {
            VideoData data = new VideoData();
            data.videoThumb = VIDEO_THUMBS[i];
            data.title = "This is the video title.This is the video title.This is the video title. " + i;
            String[] widthHeight = VIDEO_WIDTH_HEIGHT[i].split("x");
            data.width = Integer.parseInt(widthHeight[0]);
            data.height = Integer.parseInt(widthHeight[1]);
            datas.add(data);
        }
        return datas;
    }
}
