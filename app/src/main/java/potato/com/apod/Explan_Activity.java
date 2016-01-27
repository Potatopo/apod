package potato.com.apod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Explan_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explan);
        TextView text = (TextView) findViewById(R.id.textView);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        if(b!=null)
        {
            String j =(String) b.get("explanat");
            text.setText(j);
        }

    }
}
