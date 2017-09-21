package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import videodemo.R;
import view.ruler.RuleView;

/**
 * Created by admin on 2017-05-03.
 */

public class RulerActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity__ruler);

        RuleView rule_view = (RuleView) findViewById(R.id.rule_view);

    }
}
