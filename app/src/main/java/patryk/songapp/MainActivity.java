package patryk.songapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton ibLeft, ibRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);

        ibLeft = (ImageButton) findViewById(R.id.nav_left);
        ibRight = (ImageButton) findViewById(R.id.nav_right);

        ibRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LocalDataActivity.class);
                startActivity(i);
                //finish();
            }
        });

        ibLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RemoteDataActivity.class);
                startActivity(i);
            }
        });
    }
}
