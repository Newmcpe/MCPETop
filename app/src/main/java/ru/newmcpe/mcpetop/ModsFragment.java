package ru.newmcpe.mcpetop;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Newmcpe on 27.12.2017.
 */

public class ModsFragment extends Fragment {
    TextView listplugins;
    private ModsAdapter adapter;
    private ArrayList<Mod> list;

    @Override
    public void onStart() {
        super.onStart();
        list = new ArrayList<Mod>();
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.modsrecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ModsAdapter(initMods());
        recyclerView.setAdapter(adapter);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Моды");
    }

    private List<Mod> initMods() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;
                try {
                    url = new URL("http://194.67.202.172/scripts/ModsList.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    int responseCode = conn.getResponseCode();
                    final StringBuilder b = new StringBuilder();
                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        String line;
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        while ((line = br.readLine()) != null) {
                            b.append(line);
                        }
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONArray jsonObject = new JSONArray(b.toString());
                                for (int i = 0; i < jsonObject.length(); i++) {
                                    JSONObject arrPlug = jsonObject.getJSONObject(i);
                                    list.add(new Mod(arrPlug.getString("name"), arrPlug.getString("ver"), arrPlug.getString("shortdesc")));
                                    Log.d("LogAdding", arrPlug.getString("name") + " : " + arrPlug.getString("ver") + " : " + arrPlug.getString("shortdesc"));
                                }
                                adapter.notifyDataSetChanged();
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    System.out.println(b.toString());

                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return list;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.modslist, container, false);
    }
}