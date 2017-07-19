package patryk.songapp;

import android.content.Context;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import patryk.songapp.enums.Entity;


public class RemoteDataActivity extends AppCompatActivity {

    EditText searchWindow;
    SimpleAdapter mAdapter;
    private SwipeRefreshLayout sR;
    private ListView mListView;
    private ArrayList<HashMap<String, String>> songList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remotedata_layout);

        songList = new ArrayList<>();

        mListView = (ListView) findViewById(R.id.dataList);
        mListView.setEmptyView(findViewById(R.id.empty_list2));

        searchWindow = (EditText) findViewById(R.id.searchWindow);
        searchWindow.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    new GetSongs().execute(searchWindow.getText().toString());
                    return true;
                }
                return false;
            }
        });

        sR = (SwipeRefreshLayout) findViewById(R.id.sR);
        sR.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetSongs().execute("");
                mAdapter.notifyDataSetChanged();
            }
        });

        new GetSongs().execute("");
    }

    public boolean isNetworkAvailable(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    //Hide a keyboard if our EditText loses focus
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    Log.d("focus", "touchevent");
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private class GetSongs extends AsyncTask<String, Void, Void> {

        private String artist;
        private String track;
        private String year;
        private Response response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!isNetworkAvailable(RemoteDataActivity.this)) {
                Toast.makeText(RemoteDataActivity.this, "No internet connection", Toast.LENGTH_LONG).show();
            }
            sR.setRefreshing(true);
        }

        @Override
        protected Void doInBackground(String... arg0) {

            if (isNetworkAvailable(getApplicationContext())) {

                String a = arg0[0];
                songList.clear();

                if (a.equals("")) {
                    response = new Search("Metallica").setEntity(Entity.SONG).setLimit(10).execute();
                } else {
                    response = new Search(a).setEntity(Entity.SONG).setLimit(20).execute();
                }

                if (response != null && response.getResultCount() != 0) {
                    List<Result> results = response.getResults();

                    if (results != null && results.size() > 0) {
                        for (Result result : results) {
                            artist = result.getArtistName();
                            track = result.getTrackName();
                            year = result.getReleaseDate();

                            HashMap<String, String> info = new HashMap<>();

                            info.put("artistName", artist);
                            info.put("trackName", track);
                            info.put("releaseDate", year);
                            songList.add(info);
                        }
                    }
                }
            } else {
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mAdapter = new SimpleAdapter(RemoteDataActivity.this, songList,
                    R.layout.details_layout, new String[]{"trackName", "artistName", "releaseDate"},
                    new int[]{R.id.song_name, R.id.artist, R.id.year});
            mListView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            sR.setRefreshing(false);
        }
    }
}