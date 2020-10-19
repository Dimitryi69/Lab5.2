package com.example.l52.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.l52.Add;
import com.example.l52.R;
import com.example.l52.Recipie.Recipie;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Recipie> implements View.OnClickListener{

    private ArrayList<Recipie> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView info;
    }

    public CustomAdapter(ArrayList<Recipie> data, Context context) {
        super(context, R.layout.item_template, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Recipie dataModel=(Recipie)object;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Recipie dataModel = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_template, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.Name);
        viewHolder.txtType.setText(dataModel.Category);
        viewHolder.txtVersion.setText(dataModel.Time);
        viewHolder.info.setImageBitmap((BitmapFactory.decodeByteArray(Base64.decode(dataModel.ByteImage, Base64.DEFAULT), 0, Base64.decode(dataModel.ByteImage, Base64.DEFAULT).length)));
        return convertView;
    }
}
