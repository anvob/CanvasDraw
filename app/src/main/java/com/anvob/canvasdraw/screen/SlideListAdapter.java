package com.anvob.canvasdraw.screen;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anvob.canvasdraw.R;
import com.anvob.canvasdraw.common.Slide;

import java.util.List;

/**
 * Created by anvob on 22.02.2017.
 */

public class SlideListAdapter extends RecyclerView.Adapter<SlideListAdapter.ViewHolder> {
    private final List<Slide> mList;
    private ItemListener mListener;
    //private int mSelectedPosition = -1;

    public interface ItemListener {

        void onItemClick(int position, View view);
    }

    public void setListener(ItemListener listener) {
        this.mListener = listener;
    }

    /*public void setSelected(String name){
        for (Slide s: mList) {
            if(s.equals(name)){
                int old = mSelectedPosition;
                mSelectedPosition=mList.indexOf(s);
                notifyDataSetChanged();

            }
        }
    }*/

    public SlideListAdapter(List<Slide> list) {
        mList = list;
    }


    public void addElement(Slide slide) {
        mList.add(mList.size(), slide);
        notifyItemInserted(mList.size());
    }

    public void addElements(List<Slide> list) {
        clear();
        mList.addAll(mList.size(), list);
        notifyDataSetChanged();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new SlideListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SlideListAdapter.ViewHolder holder, int position) {
        Resources res = holder.view.getResources();
        try {
            holder.image.setImageBitmap(mList.get(position).getBitmap());
            holder.text.setText(res.getString(R.string.frames_count) + mList.get(position).getFramesCount()+"");
        }
        catch (Resources.NotFoundException ex){
            Drawable img = ContextCompat.getDrawable(holder.view.getContext(), R.mipmap.ic_launcher);
            holder.image.setImageDrawable(img);
            //TODO Add string values
            holder.text.setText("Sorry, loading failed...");
        }

        holder.view.setOnClickListener(v->{
            notifyItemChanged(position);
            if(mListener!=null){
                mListener.onItemClick(position,holder.view);
                //mSelectedPosition=position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ImageView image;
        public TextView text;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.view.setClickable(true);
            this.image = (ImageView) view.findViewById(R.id.image);
            this.text = (TextView) view.findViewById(R.id.text);
        }
    }
}
