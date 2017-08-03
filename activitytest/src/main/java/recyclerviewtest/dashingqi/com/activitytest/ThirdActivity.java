package recyclerviewtest.dashingqi.com.activitytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ThirdActivity++","this id is "+getTaskId());
        setContentView(R.layout.activity_third);
    }
}
