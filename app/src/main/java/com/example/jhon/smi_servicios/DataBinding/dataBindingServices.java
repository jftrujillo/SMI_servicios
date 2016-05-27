package com.example.jhon.smi_servicios.DataBinding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * Created by jhon on 12/05/16.
 */
public class dataBindingServices {

    @BindingAdapter("app:imgUrl")
    public static void loadImg(ImageView img,String url){
        Picasso.with(img.getContext()).load(url).into(img);
    }


}
