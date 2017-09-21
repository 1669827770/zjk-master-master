package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import bean.Lyric;
import util.LyricLoader;
import videodemo.R;


/**
 * 歌词View
 * Created by dzl on 2016/8/16.
 */
public class LyricView extends View {

    /** 歌词数据  */
    private ArrayList<Lyric> lyrics;
    /** 高亮行歌词的索引 */
    private int highlightIndex;
    /** 画笔，指定画出来的内容的颜色、大小等等的属性 */
    private final Paint paint;
    /** 默认歌词的颜色 */
    private static final int DEFAULT_COLOR = Color.WHITE;
    /** 高亮行歌词的颜色 */
    private static final int HIGHTLIGHT_COLOR = Color.GREEN;
    /** 默认歌词的文字大小 */
    private float highlightSize;
    /** 高亮行歌词的文字大小 */
    private float defaultSize;
    /** 高亮行的y坐标 */
    private float highlightY;
    /** 行高 */
    private final float rowHeight;
    private int currentPosition;

    public LyricView(Context context, AttributeSet attrs) {
        super(context, attrs);

        highlightSize = getResources().getDimension(R.dimen.hightlight_size);
        defaultSize = getResources().getDimension(R.dimen.default_size);

        paint = new Paint();
        paint.setColor(DEFAULT_COLOR);
        paint.setTextSize(defaultSize);
        paint.setAntiAlias(true);   // 设置搞锯齿（让文字边缘比较平滑）

        rowHeight = getTextHeight("哈哈") + 10;

        // 模拟歌词数据
//        lyrics = new ArrayList<>();
//        highlightIndex = 0;
//        for (int i = 0; i < 10; i++) {
//            Lyric lyric = new Lyric("我又捡到钱了^_^ " + i, i * 2000);
//            lyrics.add(lyric);
//        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (lyrics == null || lyrics.isEmpty()) {
            // 如果没有歌词文件
            return;
        }

        Lyric hightlightLyric = lyrics.get(highlightIndex);   // 取出高亮行歌词

        if (highlightIndex != lyrics.size() - 1) {
            // 如果不是最后一行，则让歌词平滑的往上滑动

            // 高亮已经显示的时间 = 当前播放时间 - 高亮行的开始显示时间
            float showedTime = currentPosition - hightlightLyric.startShowPosition;

            // 高亮总的显示时间 = 高亮行下一行开始显示时间 - 高亮行的开始显示时间
            float totalShowTime = lyrics.get(highlightIndex + 1).startShowPosition - hightlightLyric.startShowPosition;

            // 比例 = 高亮已经显示的时间 / 高亮总的显示时间
            float ratio = showedTime / totalShowTime;

            // translationY = 行高 * 比例;
            float translationY = rowHeight * ratio;
            canvas.translate(0, -translationY); // 因为要往上滑，所以y是负数
        }


        String text = hightlightLyric.text;

        // 画高亮歌词（水平垂直都居中的歌词）
        drawCenterText(canvas, text);

        // 画高亮行上面的歌词：
        for(int i = 0; i < highlightIndex; i++) {
            Lyric lyric = lyrics.get(i);
            // y = 高亮行的y - 行差距(高亮行索引 - i) * 行高
            float y = highlightY - (highlightIndex - i) * rowHeight;
            drawHorizontalCenterText(canvas, lyric.text, y, false);
        }

        // 画高亮行下面的歌词
        for(int i = highlightIndex + 1; i < lyrics.size(); i++) {
            Lyric lyric = lyrics.get(i);
            // y = 高亮行的y + 行差距(i - 高亮行索引) * 行高
            float y = highlightY + (i - highlightIndex) * rowHeight;
            drawHorizontalCenterText(canvas, lyric.text, y, false);
        }
    }

    /** 画水平和垂直都居中的文本 */
    private void drawCenterText(Canvas canvas, String text) {
        int textHeight = getTextHeight(text);
        // y = LyricView的高 / 2 + 歌词文本高 / 2
        highlightY = getHeight() / 2 + textHeight / 2;
        drawHorizontalCenterText(canvas, text, highlightY, true);
    }

    /** 画水平居中的文本 */
    private void drawHorizontalCenterText(Canvas canvas, String text, float y, boolean isDrawHightlight) {
        paint.setColor(isDrawHightlight ? HIGHTLIGHT_COLOR : DEFAULT_COLOR);
        paint.setTextSize(isDrawHightlight ? highlightSize : defaultSize);
        int textWidth = getTextWidth(text);
        // x = LyricView的宽 / 2 - 歌词文本宽 / 2
        float x = getWidth() / 2 - textWidth / 2;
        canvas.drawText(text, x, y, paint);
    }

    /** 获取文本的高 */
    private int getTextHeight(String text) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();
    }

    /** 获取指定文本的宽 */
    private int getTextWidth(String text) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width();
    }

    /** 加载音乐路径下对应的歌词文件 */
    public void setMusicPath(String musicPath) {
        highlightIndex = 0; // 当加载一个歌词的时候，说明这个音乐刚刚开始播放
        lyrics = LyricLoader.loadLyric(musicPath);
    }

    /** 根据当前播放位置，找出高亮行 */
    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;

        if (lyrics == null || lyrics.isEmpty()) {
            //  如果没有歌词
            return;
        }

        // 查找高亮行
        for (int i = 0; i < lyrics.size(); i++) {
            Lyric lyric = lyrics.get(i);
            if (currentPosition >= lyric.startShowPosition) {
                if (i == lyrics.size() - 1) {
                    highlightIndex = i;
                    break;
                } else if (currentPosition < lyrics.get(i + 1).startShowPosition) {
                    highlightIndex = i;
                    break;
                }
            }
        }

        // 重新调用onDraw
        invalidate();   // 这个方法内部会调用ondraw
    }

}
