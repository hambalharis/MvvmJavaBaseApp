
package com.app.baseapp.baseui;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class GooglePlacesAutocompleteAdapter extends ArrayAdapter<String> implements Filterable {


    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json?";
    private static final String API_KEY = "AIzaSyCcwDkcTvyr2IwRDB_u1EY6xganjTuGmls";
    private ArrayList<String> resultList;

    public GooglePlacesAutocompleteAdapter(Context context,
                                           int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public String getItem(int index) {
        return resultList.get(index);
    }

    @NotNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    resultList = autocomplete(constraint.toString());
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }


    private static ArrayList<String> autocomplete(String input) {
        ArrayList<String> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            String sb = PLACES_API_BASE
                    + TYPE_AUTOCOMPLETE + OUT_JSON + "&input=" + URLEncoder.encode(input, "utf8") +
                    "&key=" + API_KEY;
            URL url = new URL(sb);
            System.out.println("URL: " + url);
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }

            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            //Log.d(Constant.DEBUG_TAG, "Error processing Places API URL", e);
            return null;
        } catch (IOException e) {
            //Log.d(Constant.DEBUG_TAG, "Error connecting to Places API", e);
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                resultList.add(predsJsonArray.getJSONObject(i).getString(
                        "description"));
            }
        } catch (JSONException e) {
            //Log.e(Constant.DEBUG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }
}

