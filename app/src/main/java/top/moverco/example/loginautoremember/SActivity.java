package top.moverco.example.loginautoremember;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

/**
 * Created by UPC on 2016/8/23.
 */
public class SActivity extends Activity {

    private Button quit;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quit = (Button)findViewById(R.id.quit);

    }
}
