package com.example.rafa.pspdescargaimagenes;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class Imagen extends AppCompatActivity{

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagen);
        iv = (ImageView) findViewById(R.id.imagen);
        Bitmap foto = getIntent().getParcelableExtra("foto");
        iv.setImageBitmap(foto);
    }
}
