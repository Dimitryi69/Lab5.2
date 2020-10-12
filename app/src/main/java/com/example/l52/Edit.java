package com.example.l52;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.l52.Recipie.Recipie;

import java.io.ByteArrayOutputStream;

import static com.example.l52.MainActivity.dataModels;

public class Edit extends AppCompatActivity {

    EditText Name;
    EditText Category;
    EditText ing;
    EditText rec;
    EditText time;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Name = findViewById(R.id.Name);
        Category = findViewById(R.id.Cat);
        ing = findViewById(R.id.Ing);
        rec = findViewById(R.id.Recipie);
        time = findViewById(R.id.Time);
        img = findViewById(R.id.img);
        Bundle arguments = getIntent().getExtras();
        Intent i = getIntent();
        Recipie myParcelableObject = (Recipie)arguments.get("Selected");
        Name.setText(myParcelableObject.Name);
        Category.setText(myParcelableObject.Category);
        ing.setText(myParcelableObject.Ingridients);
        rec.setText(myParcelableObject.Recip);
        time.setText(myParcelableObject.Time);
        byte[] image = myParcelableObject.Image;
        Bitmap bmp = null;
        if(image!=null && image.length>0)
        {
            bmp = BitmapFactory.decodeByteArray(image,0,image.length);
        }
        img.setImageBitmap(bmp);
    }

    public void OnSaveClick(View view)
    {
        Bundle arguments = getIntent().getExtras();
        Intent i = getIntent();
        Recipie myParcelableObject = (Recipie)arguments.get("Selected");
        for (Recipie object : dataModels) {
            if(object.Name.equals(myParcelableObject.Name)&&object.Category.equals(myParcelableObject.Category)
                    &&object.Time.equals(myParcelableObject.Time)&&object.Ingridients.equals(myParcelableObject.Ingridients)&&
                    object.Recip.equals(myParcelableObject.Recip))
            {
                object.Name = Name.getText().toString();
                object.Category = Category.getText().toString();
                object.Ingridients = ing.getText().toString();
                object.Recip = rec.getText().toString();
                object.Time = time.getText().toString();
                BitmapDrawable bd;
                Bitmap bit;
                bd = (BitmapDrawable)img.getDrawable();
                bit = bd.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bit.compress(Bitmap.CompressFormat.PNG,100,stream);
                byte[] b;
                b = stream.toByteArray();
                object.Image = b;
                this.finish();
            }
        }
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