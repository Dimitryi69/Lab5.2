package com.example.l52;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.l52.Recipie.Recipie;

public class Info extends AppCompatActivity {
    TextView Name;
    TextView Category;
    TextView ing;
    TextView rec;
    TextView time;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Name = findViewById(R.id.Name);
        Category = findViewById(R.id.Category);
        ing = findViewById(R.id.Ing);
        rec = findViewById(R.id.Recipie);
        time = findViewById(R.id.Time);
        img = findViewById(R.id.image);
        Bundle arguments = getIntent().getExtras();
        Intent i = getIntent();
        Recipie myParcelableObject = (Recipie)arguments.get("Selected");
        Name.setText("Name: "+myParcelableObject.Name);
        Category.setText("Category: "+myParcelableObject.Category);
        ing.setText("Ingridients: "+myParcelableObject.Ingridients);
        rec.setText("Recipie: "+myParcelableObject.Recip);
        time.setText("Time: "+myParcelableObject.Time);
        byte[] image = Base64.decode(myParcelableObject.ByteImage, Base64.DEFAULT);
        Bitmap bmp = null;
        if(image!=null && image.length>0)
        {
            bmp = BitmapFactory.decodeByteArray(image,0,image.length);
        }
        img.setImageBitmap(bmp);
    }
}