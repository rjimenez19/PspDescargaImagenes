package com.example.rafa.pspdescargaimagenes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText ed;
    private ListView lv;
    private Adaptador adap;

    private ArrayList<String> b;
    ArrayList<Bitmap> a;
    private String direccion;

    private ProgressBar pb;
    private Procesos desc;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        activity = this;
        pb = (ProgressBar) findViewById(R.id.progreso);
        b =  new ArrayList<>();
        ed = (EditText) findViewById(R.id.buscar);
        a = new ArrayList<>();
        lv = (ListView) findViewById(R.id.lvLista);
        adap = new Adaptador(this, b);
        lv.setAdapter(adap);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                verFoto(position);
            }
        });
    }

    public void verFoto(int posicion){
        Intent i = new Intent(this,Imagen.class);
        i.putExtra("foto", a.get(posicion));
        startActivity(i);
    }

    public ArrayList<String> descargar(String urlpagina) {
        try {
            URL url = new URL(urlpagina);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String linea;
            ArrayList<String> out = new ArrayList<>();
            direccion = ed.getText().toString();
            while ((linea = in.readLine()) != null) {
                if (linea.contains("<img src=")) {
                    linea=linea.substring(linea.indexOf("<img src=") + 10);
                    linea=linea.substring(0,linea.indexOf('"'));
                    if(linea.indexOf("/")==0) {
                        linea = direccion + linea.substring(1);
                    }
                    out.add(linea.trim());
                }
            }
            in.close();
            return out;
        } catch (IOException ex) {
        }
        return null;
    }

    public boolean guardarImagen(ArrayList<String> urlImagen) {
        try {
            for(int i = 0; i < urlImagen.size(); i++) {
                URL url = new URL(urlImagen.get(i));
                InputStream in = url.openStream();
                OutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + "foto" + i + ".jpg");
                byte[] buffer = new byte[2048];
                int longitud;
                while ((longitud = in.read(buffer)) != -1) {
                    out.write(buffer, 0, longitud);
                }
                in.close();
                out.close();
            }
            return true;
        } catch (MalformedURLException ex) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public void search(View v) {
        direccion = ed.getText().toString();
        desc = new Procesos();
        desc.execute(direccion);
    }

    //*********************************************************************************************/
    public class Procesos extends AsyncTask<String, Integer, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... params) {

            b = descargar(params[0]);

            for(int i=0;i<=1000;i++){
                if(i%5==0){
                    publishProgress(i);
                }
            }

            URL url = null;
            try {
                for(String i : b) {
                    url = new URL(i);
                    a.add(BitmapFactory.decodeStream(url.openConnection().getInputStream()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            guardarImagen(b);
            return b;
        }

        @Override
        protected void onPreExecute() {
            pb.setMax(100);
            pb.setProgress(0);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            pb.setProgress(pb.getProgress()+1);
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            lv = (ListView) findViewById(R.id.lvLista);
            adap = new Adaptador(activity, result);
            lv.setAdapter(adap);
            adap.notifyDataSetChanged();
        }
    }
}