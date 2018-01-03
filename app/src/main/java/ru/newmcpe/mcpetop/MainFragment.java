package ru.newmcpe.mcpetop;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Newmcpe. Contacts:
 * VK: vk.com/newmcpead
 * Skype: chebakov51
 */

public class MainFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Button pluginsButton = getActivity().findViewById(R.id.pluginsbutton);
        pluginsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.changeFragment(new PluginsFragment());
            }
        });
        Button modsButton = getActivity().findViewById(R.id.modsbutton);
        modsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.changeFragment(new ModsFragment());
            }
        });
        Button texturePacksButton = getActivity().findViewById(R.id.texturepacksbutton);
        texturePacksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // MainActivity.changeFragment(new PluginsFragment());
                Toast.makeText(getActivity(), "Функция временно не работает. Ждите обновлений", Toast.LENGTH_SHORT).show();
            }
        });
        Button mapsButton = getActivity().findViewById(R.id.mapsbutton);
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  MainActivity.changeFragment(new PluginsFragment());
                Toast.makeText(getActivity(), "Функция временно не работает. Ждите обновлений", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
