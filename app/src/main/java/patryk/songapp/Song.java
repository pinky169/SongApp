package patryk.songapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Song {

    public String name;
    public String artist;

    // Constructor to convert JSON object into a Java class instance
    public Song(JSONObject object) {
        try {
            this.name = object.getString("Song Clean");
            this.artist = object.getString("ARTIST CLEAN");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Factory method to convert an array of JSON objects into a list of objects
    // Song.fromJson(jsonArray);
    public static ArrayList<Song> fromJson(JSONArray jsonObjects) {
        ArrayList<Song> songs = new ArrayList<Song>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                songs.add(new Song(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return songs;
    }
}