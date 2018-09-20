package ru.newmcpe.mcpetop;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Newmcpe on 28.12.2017.
 */

public class PluginAdapter extends RecyclerView.Adapter<PluginAdapter.ViewHolder> {
    private static List<Plugin> list;

    PluginAdapter(List<Plugin> list) {
        PluginAdapter.list = list;
        //setHasStableIds(true);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pluginsitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("Pos", String.valueOf(position));
        Plugin plugin = list.get(position);
        Log.d("Pos",plugin.getName() + " : " + plugin.getVersion());
        holder.nameplugin.setText(plugin.getName());
        holder.versionplugin.setText(plugin.getVersion());
        holder.desc.setText(plugin.getDesc());
        holder.versionplugin.setTextColor(Color.rgb(145,145,145));
        holder.desc.setTextColor(Color.rgb(145,145,145));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameplugin;
        private TextView versionplugin;
        private TextView desc;
        private CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            nameplugin = itemView.findViewById(R.id.namepluginedit);
            versionplugin = itemView.findViewById(R.id.versionplugin);
            desc = itemView.findViewById(R.id.desc);
            cardView = itemView.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String pluginName = list.get(getPosition()).getName();
                    InfoPluginFragment infoPluginFragment = new InfoPluginFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("namePlugin",pluginName);
                    infoPluginFragment.setArguments(bundle);
                    MainActivity.changeFragment(infoPluginFragment);
                }
            });
        }


    }
}
