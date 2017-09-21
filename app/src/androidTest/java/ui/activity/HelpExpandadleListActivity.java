package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ExpandableListView;

import adapter.HelpInfoAdapter;
import videodemo.R;

/**
 * Created by admin on 2017-07-05.
 */

public class HelpExpandadleListActivity extends Activity {

    private ExpandableListView mHelpinfoListone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpexpandadlelist);
        initView();
        initData();
    }

    private void initData() {

        HelpInfoAdapter helpInfoAdapter = new HelpInfoAdapter(getApplicationContext());
        mHelpinfoListone.setAdapter(helpInfoAdapter);

    }

    private void initView() {
        mHelpinfoListone = (ExpandableListView) findViewById(R.id.helpinfo_listone);
    }
}
