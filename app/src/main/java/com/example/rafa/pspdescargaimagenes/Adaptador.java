package com.example.rafa.pspdescargaimagenes;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class Adaptador extends BaseAdapter {

    private ArrayList<String> objeto;
    private Activity activity;

    public Adaptador(Activity activity, ArrayList<String> objeto){
        super();
        this.activity = activity;
        this.objeto = objeto;
    }

    @Override
    public int getCount() {
        return objeto.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item, null, true);
        TextView tv = (TextView)view.findViewById(R.id.nombre);

        tv.setText(objeto.get(position));

        return view;
    }
}

