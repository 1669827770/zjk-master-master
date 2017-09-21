package util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import bean.Lyric;

public class LyricLoader {

	public static void main(String[] args) {
		String musicPath = "G:\\classes\\term88\\手机影音\\day04\\资料\\audio\\TongHua.mp3";
		ArrayList<Lyric> lyrics = loadLyric(musicPath);
		for (Lyric lyric : lyrics) {
			System.out.println(lyric);
		}
	}
	
	/** 加载指定音乐路径下面的歌词 */
	public static ArrayList<Lyric> loadLyric(String musicPath) {
		ArrayList<Lyric> lyrics = null;
		// 1、	根据获取歌词文件（把音乐的扩展名换成歌词扩展名） , 取出："G:\\classes\\term88\\手机影音\\day04\\资料\\audio\\TongHua"
		String prefix = musicPath.substring(0, musicPath.lastIndexOf("."));
		File lrcFile = new File(prefix + ".lrc");
		File txtFile = new File(prefix + ".txt");
		
		// 2、	读取歌词文件（BufferReader）
		if (lrcFile.exists()) {
			lyrics = readLyricFile(lrcFile);
		} else if (txtFile.exists()) {
			lyrics = readLyricFile(txtFile);
		}
		
		// 4、	对歌词集合进行排序
		if (lyrics != null && !lyrics.isEmpty()) {
			Collections.sort(lyrics);
		}
		
		return lyrics;
	}

	/**
	 * 把歌词文件读取并解析成JavaBean的集合
	 * @param lrcFile
	 * @return
	 */
	private static ArrayList<Lyric> readLyricFile(File lrcFile) {
		ArrayList<Lyric> lyrics = new ArrayList<>();
		InputStream in = null;
		BufferedReader reader = null;
		try {
			in = new FileInputStream(lrcFile);
			reader = new BufferedReader(new InputStreamReader(in, "gbk"));
			String line;
			while((line = reader.readLine()) != null) {
				// 3、 解析一行一行的歌词（解析一个一个的歌词JavaBean）： [01:48.07][00:41.00]你哭着对我说 童话里都是骗人的 
				String[] strings = line.split("]");
				String lyricText = strings[strings.length - 1];	// 分割之后的最后一个元素就是歌词文本
				
				// 遍历所有的歌词时间
				for (int i = 0; i < strings.length - 1; i++) {
					String time = strings[i];	// 取出这样的时间:[01:48.07
					int startShowPostion = parseTime(time);
					Lyric lyric = new Lyric(lyricText, startShowPostion);
					lyrics.add(lyric);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(reader);
		}
		return lyrics;
	}

	/** 反这样的时间（[01:48.07）解析成一个毫秒值的时间 */
	private static int parseTime(String time) {
		String minute = time.substring(1, 3);		// 取出[01:48.07的01
		String sencond = time.substring(4, 6);		// 取出[01:48.07的48
		String millisecond = time.substring(7, 9);	// 取出[01:48.07的07
		int minuteMillis = Integer.parseInt(minute) * 60 * 1000;
		int sencondMillis = Integer.parseInt(sencond) * 1000;
		int millis = Integer.parseInt(millisecond) * 10;
		return minuteMillis + sencondMillis + millis;
	}

	private static void close(BufferedReader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
