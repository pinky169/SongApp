package patryk.songapp;

import android.content.Context;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

public class LocalDataActivity extends AppCompatActivity {

    private JSONArray songs;
    private ListView mListView;
    private SimpleAdapter mAdapter;
    private ArrayList<HashMap<String, String>> songList;
    private SwipeRefreshLayout srl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localdata_layout);

        songList = new ArrayList<>();
        mAdapter = new SimpleAdapter(LocalDataActivity.this, songList,
                R.layout.details_layout, new String[]{"Song Clean", "ARTIST CLEAN", "Release Year"},
                new int[]{R.id.song_name, R.id.artist, R.id.year});

        mListView = (ListView) findViewById(R.id.listView);
        mListView.setEmptyView(findViewById(R.id.empty_list));

        srl = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetSongs().execute();
            }
        });

        new GetSongs().execute();

        EditText mEditText = (EditText) findViewById(R.id.searchText);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    ArrayList<HashMap<String, String>> songList = new ArrayList<>();
                    SimpleAdapter mAdapter = new SimpleAdapter(LocalDataActivity.this, songList,
                            R.layout.details_layout, new String[]{"Song Clean", "ARTIST CLEAN", "Release Year"},
                            new int[]{R.id.song_name, R.id.artist, R.id.year});
                    mListView.setAdapter(mAdapter);
                    songList.addAll(getResults(charSequence));
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * Finds items from JSON file which match given sequence
     *
     * @param s Character Sequence from EditText
     * @return ArrayList with items that match CharSequence
     * @throws JSONException
     */
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

    /**
     * Hide a keyboard if our EditText loses focus
     */
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

    /**
     * Loads JSON data from a local file
     *
     * @return string converted from json data
     * @throws IOException
     */
    private String loadData() throws IOException {
        InputStream is = getResources().openRawResource(R.raw.songlist);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }

        String jsonString = writer.toString();
        return jsonString;
    }

    /**
     * Loads songs data from a local JSON file into ListView
     * name is a song title
     * artist is a song author
     * release_year is a song release year
     */
    private class GetSongs extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(LocalDataActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
            srl.setRefreshing(true);
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            songList.clear();
            try {
                songs = new JSONArray(loadData());

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
                }
            } catch (final JSONException e) {
                Log.e("SongAppError->", "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

            } catch (IOException e) {
                Log.e("SongAppError->", "IOException: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            srl.setRefreshing(false);
            mListView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }
}