package ru.newmcpe.mcpetop;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
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

public class PluginsFragment extends Fragment {
    TextView listplugins;
    private PluginAdapter adapter;

    @Override
    public void onStart() {
        super.onStart();

      /*  new Thread(new Runnable() {

            @Override
            public void run() {
                URL url;
                try {

                    url = new URL("http://192.168.1.2/parser.php");
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
//                            parsingresult.setText("эщкере");
                        }
                    });
                    System.out.println(b.toString());

                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new PluginAdapter(initPlugins());
        recyclerView.setAdapter(adapter);

    }

    private List<Plugin> initPlugins() {
        final List<Plugin> list = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;
                try {

                    url = new URL("http://192.168.1.2/parser.php");
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
                                Log.d("Couting", String.valueOf(jsonObject.length()));
                                for(int i = 0;i < jsonObject.length(); i++){
                                    JSONArray arrPlug = jsonObject.getJSONArray(i);
                                    list.add(new Plugin(arrPlug.getString(0),arrPlug.getString(1)));
                                    Log.d("LogAdding",arrPlug.getString(0) + " : " + arrPlug.getString(1));
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
        return inflater.inflate(R.layout.plugins, container, false);
    }
}
