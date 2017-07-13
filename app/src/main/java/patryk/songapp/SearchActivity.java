package patryk.songapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
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
    //ListAdapter mAdapter;
    SimpleAdapter mAdapter;
    ArrayList<HashMap<String, String>> songList;
    ImageButton mButton;
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

        new GetSongs().execute();

        mEditText = (EditText) findViewById(R.id.searchText);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    songList.addAll(getResults(charSequence));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    ArrayList<HashMap<String, String>> getResults(CharSequence s) throws JSONException {

        songList.clear();
        for (int i = 0; i < songs.length(); i++) {
            JSONObject sth = songs.getJSONObject(i);
            String name = sth.getString("Song Clean");
            String artist = sth.getString("ARTIST CLEAN");
            String release_year = sth.getString("Release Year");

            HashMap<String, String> songInfo = new HashMap<>();

            if (name.toLowerCase().contains(s) || artist.toLowerCase().contains(s) || release_year.contains(s)) {
                songInfo.put("Song Clean", name);
                songInfo.put("ARTIST CLEAN", artist);
                songInfo.put("Release Year", release_year);
                songList.add(songInfo);
            }
        }

/*        SimpleAdapter adapter = new SimpleAdapter(SearchActivity.this, songList,
                R.layout.details_layout, new String[]{"Song Clean", "ARTIST CLEAN", "Release Year"},
                new int[]{R.id.song_name, R.id.artist, R.id.year});
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/
        mAdapter.notifyDataSetChanged();

        return songList;
    }

    class GetSongs extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(SearchActivity.this, "Json Data is downloading...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

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

            } else {
                Log.e("TAG", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Toast.makeText(SearchActivity.this, "JSON Data has been downloaded", Toast.LENGTH_SHORT).show();
            mListView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

}