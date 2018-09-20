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

public class ModsAdapter extends RecyclerView.Adapter<ModsAdapter.ViewHolder> {
    private static List<Mod> list;

    ModsAdapter(List<Mod> list) {
        ModsAdapter.list = list;
        setHasStableIds(true);
    }

    @Override
    public ModsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pluginsitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ModsAdapter.ViewHolder holder, int position) {
        Log.d("Pos", String.valueOf(position));
        Mod plugin = list.get(position);
        holder.namemod.setText(plugin.getName());
        holder.versionmod.setText(plugin.getVersion());
        holder.desc.setText(plugin.getDesc());
        holder.versionmod.setTextColor(Color.rgb(145, 145, 145));
        holder.desc.setTextColor(Color.rgb(145, 145, 145));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView namemod;
        private TextView versionmod;
        private TextView desc;
        private CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            namemod = itemView.findViewById(R.id.namepluginedit);
            versionmod = itemView.findViewById(R.id.versionplugin);
            desc = itemView.findViewById(R.id.desc);
            cardView = itemView.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String pluginName = list.get(getPosition()).getName();
                    InfoModFragment infoModFragment = new InfoModFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("nameMod", pluginName);
                    infoModFragment.setArguments(bundle);
                    MainActivity.changeFragment(infoModFragment);
                }
            });
        }


    }
}