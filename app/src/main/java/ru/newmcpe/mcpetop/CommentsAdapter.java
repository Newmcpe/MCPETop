package ru.newmcpe.mcpetop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
    private static List<Comment> list;

    CommentsAdapter(List<Comment> list) {
        CommentsAdapter.list = list;

        setHasStableIds(true);
    }

    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.commentitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentsAdapter.ViewHolder holder, int position) {
        Log.d("Pos", String.valueOf(position));
        Comment plugin = list.get(position);
        holder.nick.setText(plugin.getName());
        holder.commenttext.setText(plugin.getComment());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nick;
        private TextView commenttext;

        ViewHolder(View itemView) {
            super(itemView);
            nick = itemView.findViewById(R.id.nameauthorcomment);
            commenttext = itemView.findViewById(R.id.textcomment);
        }

    }
}