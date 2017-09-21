package bean;

/**
 * Created by dzl on 2016/8/16.
 */
public class Lyric implements Comparable<Lyric> {
    /** 歌词的开始显示位置 */
    public int startShowPosition;
    /** 歌词文本 */
    public String text;

    @Override
    public String toString() {
        return "Lyric{" +
                "startShowPosition=" + startShowPosition +
                ", text='" + text + '\'' +
                '}';
    }

    public Lyric(String text, int startShowPosition) {
        this.text = text;
        this.startShowPosition = startShowPosition;
    }

    /** 这个方法用于比较，能比较就能排序 */
    @Override
    public int compareTo(Lyric another) {
        // 根据播放位置进行比较
        Integer integer = Integer.valueOf(startShowPosition);
        return integer.compareTo(another.startShowPosition);
    }
}
