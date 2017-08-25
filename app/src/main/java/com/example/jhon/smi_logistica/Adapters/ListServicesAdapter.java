package com.example.jhon.smi_logistica.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.jhon.smi_logistica.Models.Services;
import com.example.jhon.smi_logistica.R;

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
            if (data.get(position).getId().equals("45B18B75-90DB-4C98-9935-3C73A284B535")){
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_smi_icons_asistencia_medica));
                Log.i("Amarillo","Amarillo");
            }
            if (data.get(position).getId().equals("0E980C91-A9D7-46D0-A835-F2C3B9BACF6E")){
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_asistencia_electrica));

            }

            if (data.get(position).getId().equals("675ABF9F-2003-4228-B447-AFEBC41F01F5")){
                //Picasso.with(context).load(R.drawable.ic_smi_icons_apertura_de_vehiculos).into(img);
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_cerrajeria));
            }

            if (data.get(position).getId().equals("068DA69D-16F4-40E9-801F-3338F8384B85")){
                //Picasso.with(context).load(R.drawable.ic_smi_icons_apertura_de_vehiculos).into(img);//falta
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_ventanas));
            }

            if (data.get(position).getId().contains("BEC3F8C6-3788-4E16-9D3E-1309E8734991")){
               // Picasso.with(context).load(R.drawable.ic_smi_icons_alquiler_de_vehiculos).into(img);//
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_plomeria));
            }

            if (data.get(position).getId().equals("48D00472-1BE9-4E37-B9EB-3F057F3F6D93")){
                //Picasso.with(context).load(R.drawable.ic_smi_icons_asistencia_juridica).into(img);//
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_asistencia_juridica));
            }

            if (data.get(position).getId().contains("7D756D94-9369-4AE2-B0D8-C55F5FBE050A")){
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_paint));
            }

            if (data.get(position).getId().contains("5F283C26-1577-4583-BFAC-D34BA01D3434")) {
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_vigilancia_privada));
            }



            TextView txt = (TextView) v.findViewById(R.id.template_list_name);
            txt.setText(data.get(position).getName());
        }

        else if (type == 1){
            if (data.get(position).getId().equals("A474EA08-BA85-4F12-B305-3F564C0A73C9")){
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_smi_icons_servicio_de_grua));
            }

            if (data.get(position).getId().equals("B4E6D3C3-0824-4422-9ACF-B61C8A8CAE95")){
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_mecanico));
            }

            if (data.get(position).getId().contains("2AF3C208-8EE8-447E-877B-D83CAAB1A3B3")){
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_smi_icons_apertura_de_vehiculos));
            }

            if (data.get(position).getId().equals("19D209CB-2C14-484A-A26D-4D5D47D965F2")){
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_smi_icons_cambio_de_llanta));
            }

            if (data.get(position).getId().equals("F07E8F8B-C2DA-4EF6-B81C-81A75FE7D1E0")){
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_conductor_profesional));
            }

            if (data.get(position).getId().equals("98349252-11DD-4F55-855D-C8388BD29E0F")){
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_smi_icons_desplazamientos_viales));
            }

            if (data.get(position).getId().equals("913E9ABD-952F-41CE-8812-636164FEA602")){
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_smi_icons_paso_de_corriente));
            }
            if (data.get(position).getId().equals("1EF07BD2-E7B5-461C-A514-68EC0F83DB75")) {
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_smi_icons_asistencia_juridica));
            }

            if (data.get(position).getId().equals("5FD30672-B651-4D50-94EB-61AFCF924CE7")) {
                img.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_gasolina));
            }
            TextView txt = (TextView) v.findViewById(R.id.template_list_name);
            txt.setText(data.get(position).getName());
        }
        return v;
    }
}
