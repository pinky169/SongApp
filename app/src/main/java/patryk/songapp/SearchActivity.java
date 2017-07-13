package patryk.songapp;

import android.content.Context;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {

    final String url = "https://bitbucket.org/tooploox/android-recruitment-intern/raw/3cad5128a0f89b9db5ea90af67c02fed196cbeac/songs-list/songs-list.json";
    JSONArray songs;
    ListView mListView;
    SimpleAdapter mAdapter;
    ArrayList<HashMap<String, String>> songList;
    EditText mEditText;
    String jsonStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        songList = new ArrayList<>();

        mAdapter = new SimpleAdapter(SearchActivity.this, songList,
                R.layout.details_layout, new String[]{"Song Clean", "ARTIST CLEAN", "Release Year"},
                new int[]{R.id.song_name, R.id.artist, R.id.year});

        mListView = (ListView) findViewById(R.id.listView);

        mListView.setEmptyView(findViewById(R.id.empty_list));

        new GetSongs().execute();

        mEditText = (EditText) findViewById(R.id.searchText);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (isNetworkAvailable(getApplicationContext())) {
                        ArrayList<HashMap<String, String>> songList = new ArrayList<>();
                        SimpleAdapter mAdapter = new SimpleAdapter(SearchActivity.this, songList,
                                R.layout.details_layout, new String[]{"Song Clean", "ARTIST CLEAN", "Release Year"},
                                new int[]{R.id.song_name, R.id.artist, R.id.year});
                        mListView.setAdapter(mAdapter);
                        songList.addAll(getResults(charSequence));
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public boolean isNetworkAvailable(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public ArrayList<HashMap<String, String>> getResults(CharSequence s) throws JSONException {

        songList.clear();
        for (int i = 0; i < songs.length(); i++) {
            JSONObject sth = songs.getJSONObject(i);
            String name = sth.getString("Song Clean");
            String artist = sth.getString("ARTIST CLEAN");
            String release_year = sth.getString("Release Year");

            HashMap<String, String> info = new HashMap<>();

            if (name.toLowerCase().contains(s.toString().toLowerCase()) || artist.toLowerCase().contains(s.toString().toLowerCase()) || release_year.contains(s)) {
                info.put("Song Clean", name);
                info.put("ARTIST CLEAN", artist);
                info.put("Release Year", release_year);
                songList.add(info);
            }
        }
        mAdapter.notifyDataSetChanged();

        return songList;
    }

    //Hide a keyboard if our editText loses focus
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

    private class GetSongs extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(SearchActivity.this, "Json Data is downloading...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            if (isNetworkAvailable(getApplicationContext())) {

                HttpHandler sh = new HttpHandler();
                // Making a request to url and getting response
                jsonStr = sh.makeServiceCall(url);

                Log.e("TAG", "Response from url: " + jsonStr);
                if (jsonStr != null) {
                    try {

                        songs = new JSONArray(jsonStr);

                        // looping through All songs
                        for (int i = 0; i < songs.length(); i++) {
                            JSONObject s = songs.getJSONObject(i);
                            String name = s.getString("Song Clean");
                            String artist = s.getString("ARTIST CLEAN");
                            String release_year = s.getString("Release Year");

                            // temp hash map for single song
                            HashMap<String, String> songInfo = new HashMap<>();

                            // adding each child node to HashMap key => value
                            songInfo.put("Song Clean", name);
                            songInfo.put("ARTIST CLEAN", artist);
                            songInfo.put("Release Year", release_year);

                            // adding songs to song list
                            songList.add(songInfo);
                            //songListBackup.add(songInfo);

                        }
                    } catch (final JSONException e) {
                        Log.e("TAG", "Json parsing error: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                }
            } else {
                Log.e("TAG", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check your internet connection.",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mListView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

}