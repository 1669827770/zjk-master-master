package ui.activity;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import bean.PieEntity;
import view.Pie;

import static base.MyApplication.mContext;

/**
 * Created by admin on 2017-07-10.
 */

public class PieChartDemoActivity extends Activity{
    private int[] colors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Pie pie = new Pie(mContext);
        setContentView(pie);

        List<PieEntity> entities = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            entities.add(new PieEntity(i + 1, colors[i]));
        }
        pie.setDatas(entities);

    }
}
