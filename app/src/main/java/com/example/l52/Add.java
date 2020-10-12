package com.example.l52;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.l52.CustomAdapter.CustomAdapter;
import com.example.l52.Recipie.Recipie;

import java.io.ByteArrayOutputStream;

import static com.example.l52.MainActivity.adapter;
import static com.example.l52.MainActivity.dataModels;

public class Add extends AppCompatActivity {

    EditText Name;
    EditText Category;
    EditText ing;
    EditText rec;
    EditText time;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Name = findViewById(R.id.Name);
        Name = findViewById(R.id.Name);
        Category = findViewById(R.id.Cat);
        ing = findViewById(R.id.Ing);
        rec = findViewById(R.id.Recipie);
        time = findViewById(R.id.Time);
        img = findViewById(R.id.img);
    }

    BitmapDrawable bd;
    Bitmap bit;

    public void OnAddClick(View view) {
        bd = (BitmapDrawable)img.getDrawable();
        bit = bd.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] b;
        b = stream.toByteArray();
        Recipie object = new Recipie(Name.getText().toString(), Category.getText().toString(), ing.getText().toString(),
                rec.getText().toString(), b, time.getText().toString(), (dataModels.size()+1)+"");
        Intent intent = new Intent();
        intent.putExtra("AddObj", object);
        setResult(RESULT_OK, intent);
        finish();
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public void ImageCapture(View view)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(photo);
        }
    }
}