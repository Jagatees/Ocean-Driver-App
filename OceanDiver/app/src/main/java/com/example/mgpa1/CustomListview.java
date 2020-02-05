package com.example.mgpa1;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomListview extends ArrayAdapter<String> {
    private String[] objects;
    private String[] desc;
    private Integer[] imgid;
    private Activity context;
    public CustomListview(Activity context, String[] objects,String[] desc,Integer[] imgid) {
        super(context, R.layout.listview_layout, objects);

        this.context = context;
        this.objects = objects;
        this.desc = desc;
        this.imgid = imgid;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r=convertView;
        ViewHolder viewHolder=null;
        if (r==null)
        {
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.listview_layout,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder) r.getTag();

        }
        viewHolder.ivw.setImageResource(imgid[position]);
        viewHolder.tvw1.setText(objects[position]);
        viewHolder.tvw2.setText(desc[position]);
        return r;


    }
    class ViewHolder
    {
        TextView tvw1;
        TextView tvw2;
        ImageView ivw;
        ViewHolder(View v)
        {
            tvw1 = v.findViewById(R.id.tvobjname);
            tvw2 = v.findViewById(R.id.tvdescription);
            ivw=v.findViewById(R.id.imageView);
        }

    }
}
