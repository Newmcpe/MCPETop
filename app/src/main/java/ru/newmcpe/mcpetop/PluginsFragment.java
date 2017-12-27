package ru.newmcpe.mcpetop;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Newmcpe on 27.12.2017.
 */

public class PluginsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       /* TextView pluginslist = container.findViewById(R.id.plguinslist);
        pluginslist.setText("Ф");*/
        return inflater.inflate(R.layout.plugins,container,false);
    }
}
