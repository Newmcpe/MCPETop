package ru.newmcpe.mcpetop;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Newmcpe. Contacts:
 * VK: vk.com/newmcpead
 * Skype: chebakov51
 */

public class AddPluginFragment extends Fragment {
    private EditText name;
    private EditText version;
    private EditText shortdesc;
    private EditText longdesc;
    private EditText downloadlink;
    private Button sendrequestaddplugin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.addplugin, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        name = getActivity().findViewById(R.id.namepluginedit);
        version = getActivity().findViewById(R.id.versionpluginedit);
        shortdesc = getActivity().findViewById(R.id.shortdescedit);
        longdesc = getActivity().findViewById(R.id.longdescedit);
        downloadlink = getActivity().findViewById(R.id.downloadlinkedit);
        sendrequestaddplugin = getActivity().findViewById(R.id.sendrequesttoaddplugin);
        sendrequestaddplugin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.getText().equals("") && !version.getText().equals("") && !shortdesc.getText().equals("") && !longdesc.getText().equals("") && !downloadlink.getText().equals("")) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            URL url;
                            try {
                                String nameR = name.getText().toString().replaceAll(" ", "+");
                                String versionR = version.getText().toString().replaceAll(" ", "+");
                                String shortdescR = shortdesc.getText().toString().replaceAll(" ", "+");
                                String longdescR = longdesc.getText().toString().replaceAll(" ", "+");
                                String downloadlinkR = downloadlink.getText().toString().replaceAll(" ", "+");
                                String request = "http://194.67.202.172/scripts/addPlugReq.php?name=" + nameR + "&ver=" + versionR + "&shortdesc=" + shortdescR + "&longdesc=" + longdescR + "&link=" + downloadlinkR;
                                System.out.println(request);
                                url = new URL(request);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setReadTimeout(15000);
                                conn.setConnectTimeout(15000);
                                conn.setRequestMethod("GET");
                                conn.setDoInput(true);
                                int responseCode = conn.getResponseCode();
                                StringBuilder b = new StringBuilder();
                                if (responseCode == HttpsURLConnection.HTTP_OK) {
                                    String line;
                                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                    while ((line = br.readLine()) != null) {
                                        b.append(line);
                                    }
                                }
                                final String res = b.toString();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "sended", Toast.LENGTH_LONG).show();
                                        System.out.println(res);
                                    }
                                });
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    Toast.makeText(getActivity(), "заполните все поля", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
