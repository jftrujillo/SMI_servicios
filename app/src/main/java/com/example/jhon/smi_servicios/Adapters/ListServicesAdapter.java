package com.example.jhon.smi_servicios.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
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
    int type;
    /*
    * 0 es home 1 es road
    * */

    public ListServicesAdapter(List<Services> data, Context context, int type) {
        this.data = data;
        this.context = context;
        this.type=type;
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

        AppCompatImageView img = (AppCompatImageView) v.findViewById(R.id.template_list_img);
        if (type == 0){
            if (data.get(position).getName().equals("Asistencia Medica")){
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_smi_icons_asistencia_medica));
                Log.i("Amarillo","Amarillo");
            }
            if (data.get(position).getName().equals("Asistencia Electrica")){
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_smi_icons_paso_de_corriente));

            }

            if (data.get(position).getName().equals("Cerrajeria")){
                //Picasso.with(context).load(R.drawable.ic_smi_icons_apertura_de_vehiculos).into(img);
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_smi_icons_apertura_de_vehiculos));
            }

            if (data.get(position).getName().equals("Vidrios")){
                //Picasso.with(context).load(R.drawable.ic_smi_icons_apertura_de_vehiculos).into(img);//falta
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_ventanas));
            }

            if (data.get(position).getName().equals("Vigilancia Privada")){
               // Picasso.with(context).load(R.drawable.ic_smi_icons_alquiler_de_vehiculos).into(img);//
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_smi_icons_alquiler_de_vehiculos));
            }

            if (data.get(position).getName().equals("Asistencia Jurídica")){
                //Picasso.with(context).load(R.drawable.ic_smi_icons_asistencia_juridica).into(img);//
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_smi_icons_asistencia_juridica));
            }

            if (data.get(position).getName().contains("fachadas")){
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_paint));
            }
            TextView txt = (TextView) v.findViewById(R.id.template_list_name);
            txt.setText(data.get(position).getName());
        }

        else if (type == 1){
            if (data.get(position).getName().equals("Grua")){
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_smi_icons_servicio_de_grua));
            }

            if (data.get(position).getName().equals("Mecanico")){
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_build_black_24dp));
            }

            if (data.get(position).getName().contains("Vehiculos")){
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_smi_icons_apertura_de_vehiculos));
            }

            if (data.get(position).getName().equals("Cambio de llantas")){
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_smi_icons_cambio_de_llanta));
            }

            if (data.get(position).getName().equals("Conductor Profesional")){
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_smi_icons_conductor_profesional));
            }

            if (data.get(position).getName().equals("Dezplazamientos viales")){
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_smi_icons_desplazamientos_viales));
            }

            if (data.get(position).getName().equals("Paso de corriente")){
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_smi_icons_paso_de_corriente));
            }
            TextView txt = (TextView) v.findViewById(R.id.template_list_name);
            txt.setText(data.get(position).getName());
        }
        return v;
    }
}
