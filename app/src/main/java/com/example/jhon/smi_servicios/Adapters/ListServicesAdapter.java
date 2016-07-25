package com.example.jhon.smi_servicios.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.jhon.smi_servicios.Models.Services;
import com.example.jhon.smi_servicios.R;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;


/**
 * Created by jhon on 12/05/16.
 */
public class ListServicesAdapter extends BaseAdapter{
    List<Services> data;
    Context context;

    public ListServicesAdapter(List<Services> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView != null){
            v = convertView;
        }
        else{
            v = View.inflate(context,R.layout.template_list_detail_services,null);
        }

        ImageView img = (ImageView) v.findViewById(R.id.template_list_img);

        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(300)
                .oval(false)
                .build();
        Picasso.with(context).load(data.get(position).getImgurl()).transform(transformation).into(img);
        TextView txt = (TextView) v.findViewById(R.id.template_list_name);
        txt.setText(data.get(position).getName());

        return v;
    }
}
