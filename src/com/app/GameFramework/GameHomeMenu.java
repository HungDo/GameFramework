package com.app.GameFramework;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: hung
 * Date: 8/8/13
 * Time: 11:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameHomeMenu extends Activity {

    public ImageView homescreenImg;
    public Button start, highscore, quit;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        GameImageManager.initiate(this);
        // these need to be reset when extended
        homescreenImg = (ImageView) findViewById(R.id.tmp_homescreen_img);
        start = (Button) findViewById(R.id.tmp_start_btn);
        highscore = (Button) findViewById(R.id.tmp_highscore_btn);
        quit = (Button) findViewById(R.id.tmp_quit_btn);

        this.setContentView(R.layout.template);
    }


    public void onPause() {
        super.onPause();
    }

    public void onStop() {
        super.onStop();
    }

    public void onResume() {
        super.onResume();
    }
}
