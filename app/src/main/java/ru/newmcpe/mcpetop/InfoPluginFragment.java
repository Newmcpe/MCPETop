package ru.newmcpe.mcpetop;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Newmcpe on 28.12.2017.
 */

public class InfoPluginFragment extends Fragment {
    static Activity context;
    private TextView descinabout;
    private TextView verinabout;
    private TextView nameinabout;
    private String namePlugin;
    private String downloadLink;
    private TextView downloadscount;
    private ArrayList<Comment> list;
    private CommentsAdapter adapter;

    private static void downloadUsingStream(final String urlStr, final String file) throws IOException {
        Toast.makeText(context, "Скачиваю...", Toast.LENGTH_LONG).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url;
                    url = new URL(urlStr);
                    BufferedInputStream bis = new BufferedInputStream(url.openStream());
                    FileOutputStream fis = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int count = 0;
                    while ((count = bis.read(buffer, 0, 1024)) != -1) {
                        fis.write(buffer, 0, count);
                    }
                    fis.close();
                    bis.close();
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Скачивание закончено! Файл находится в " + file, Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.infoplugin, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        context = getActivity();
        namePlugin = getArguments().getString("namePlugin");
        descinabout = getActivity().findViewById(R.id.bigdescinabout);
        downloadscount = getActivity().findViewById(R.id.downloadcounter);
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(namePlugin);
        list = new ArrayList<Comment>();
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.commetsrecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new CommentsAdapter(initComments());
        recyclerView.setAdapter(adapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;
                try {
                    url = new URL("http://194.67.202.172/scripts/getInfoAboutPlugin.php?name=" + namePlugin);
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
                                JSONObject info = new JSONObject(b.toString());
                                descinabout.setText(info.getString("descrip"));
                                actionBar.setSubtitle(info.getString("ver"));
                                downloadLink = info.getString("linktodownload");
                                downloadscount.setText(info.getString("downloads"));
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
        getActivity().findViewById(R.id.downloadbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Permissions.check(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE, null,
                        new PermissionHandler() {
                            @Override
                            public void onGranted() {
                                //do your task.
                                String pathtoDownload = Environment.getExternalStorageDirectory().getPath() + "/MCPETop/Plugins/";
                                System.out.println(pathtoDownload);
                                File folder = new File(pathtoDownload);
                                folder.mkdirs();
                                String nametodownload = downloadLink.split("/")[5];
                                System.out.println(nametodownload);
                                try {
                                    downloadUsingStream(downloadLink, pathtoDownload + nametodownload);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        URL url;
                                        try {
                                            url = new URL("http://194.67.202.172/scripts/addOneXromosoma.php?name=" + namePlugin);
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
                                            System.out.println(b.toString());

                                        } catch (Throwable e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            }

                            @Override
                            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                                Toast.makeText(getActivity(), "вы должны разрешить приложению доступ к файлам,чтобы скачать плагины!", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public boolean onBlocked(Context context, ArrayList<String> blockedList) {
                                Toast.makeText(getActivity(), "вы должны разрешить приложению доступ к файлам,чтобы скачать мод!", Toast.LENGTH_LONG).show();
                                return super.onBlocked(context, blockedList);
                            }
                        });
            }
        });
        getActivity().findViewById(R.id.addcommentbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText nick = getActivity().findViewById(R.id.nicknameedittext);
                final EditText comment = getActivity().findViewById(R.id.commentedittext);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        URL url;
                        try {
                            url = new URL("http://194.67.202.172/scripts/addOnePidor.php?pluginname=" + namePlugin + "&nick=" + nick.getText() + "&comment=" + comment.getText());
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
                                    list.add(new Comment(nick.getText().toString(),comment.getText().toString()));
                                    adapter.notifyDataSetChanged();
                                }
                            });
                            System.out.println(b.toString());

                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private List<Comment> initComments() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;
                try {
                    url = new URL("http://194.67.202.172/scripts/getCommentsForPlugin.php?name=" + namePlugin);
                    HttpURLConnection   conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    int responseCode = HttpsURLConnection.HTTP_OK;
                    final StringBuilder b = new StringBuilder();
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        b.append(line);
                    }
                    Log.d("GETTINGRESPONSE", b.toString());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONArray jsonObject = new JSONArray(b.toString());
                                for (int i = 0; i < jsonObject.length(); i++) {
                                    JSONObject arrPlug = jsonObject.getJSONObject(i);
                                    list.add(new Comment(arrPlug.getString("nick"), arrPlug.getString("comment")));
                                    Log.d("LogAdding", arrPlug.getString("nick") + " : " + arrPlug.getString("comment"));
                                    adapter.notifyDataSetChanged();
                                }

                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    Log.d("GETTINGRESPONSE", b.toString());

                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return list;
    }
}
