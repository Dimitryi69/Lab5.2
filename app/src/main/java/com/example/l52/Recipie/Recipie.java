package com.example.l52.Recipie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.firebase.database.DatabaseReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Recipie implements Serializable {
    
    public String id;
    public String Name;
    public String Category;
    public String Ingridients;
    public String Recip;
    public byte[] Image;
    public String Time;

    public Recipie(String name, String cat, String ing, String rec, byte[] img, String time, String id)
    {
        this.id = id;
        Name=name;
        Category = cat;
        Ingridients = ing;
        Recip = rec;
        Image = img;
        Time = time;
    }
    public Recipie()
    {}

}
