package com.xcqwan.sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xcqwan.mentionview.Mention;

/**
 * Created by Thanks on 16/10/12.
 */

public class MentionAdapter extends LDBaseAdapter<Mention> {
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_group, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        Mention mention = getItem(position);
        holder.tvName.setText(mention.relName);
        holder.tvDescript.setText(mention.relId);
        return view;
    }

    static class ViewHolder {
        TextView tvName;
        TextView tvDescript;

        ViewHolder(View view) {
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvDescript = (TextView) view.findViewById(R.id.tv_descript);
        }
    }
}
