package bean;

import android.graphics.drawable.Drawable;

public class VirusInfo {
	// 包名
	public String packageName;
	// 图标
	public Drawable icon;
	// 名称
	public String name;

	public boolean isVirus;

	public VirusInfo(String packageName, Drawable icon, String name,
                     boolean isVirus) {
		super();
		this.packageName = packageName;
		this.icon = icon;
		this.name = name;
		this.isVirus = isVirus;
	}

	public VirusInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

}
