package patryk.songapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class Details extends AppCompatActivity {

    private ArrayList<HashMap<String, String>> list = new ArrayList<>();
    private HashMap<String, String> result = null;
    private ListView mListView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itunes_info_layout);

        mListView = (ListView) findViewById(R.id.detailsList);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            result = (HashMap<String, String>) getIntent().getSerializableExtra("results");
            list.add(result);
        }

        SimpleAdapter mAdapter = new SimpleAdapter(Details.this, list,
                R.layout.itunes_info_details_layout,
                new String[]{"trackName", "artistName", "releaseDate", "collectionName", "country", "primaryGenreName", "trackPrice"},
                new int[]{R.id.titleDetailed, R.id.artistDetailed, R.id.date, R.id.collectionName, R.id.country, R.id.genre, R.id.price});
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}