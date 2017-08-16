package com.anvob.canvasdraw.screen;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.anvob.canvasdraw.R;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        initToolbar();
        TextView textView = (TextView) findViewById(R.id.about);
        TextView versionView = (TextView) findViewById(R.id.version);
        //String versionName = String.format("Version name: %s", BuildConfig.VERSION_NAME);
        versionView.setText(String.format("Version: %s", com.anvob.canvasdraw.BuildConfig.VERSION_NAME));
        textView.setText(Html.fromHtml(getString(R.string.about_content)));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
