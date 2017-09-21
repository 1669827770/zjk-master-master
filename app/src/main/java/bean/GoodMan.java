package bean;


import util.PinyinUtils;

/**
 * Created by Administrator on 2016/7/25.
 */
public class GoodMan implements Comparable<GoodMan> {
    private String pinyin;
    private String name;

    public GoodMan(String name) {
        this.name = name;
        this.pinyin= PinyinUtils.hanZiToPinyin(name);
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(GoodMan another) {
        return this.pinyin.compareTo(another.pinyin);
    }
}
