package com.example.l52;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Person;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.l52.CustomAdapter.CustomAdapter;
import com.example.l52.Recipie.Recipie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class MainActivity extends AppCompatActivity
{
    Context context;
    public static ArrayList<Recipie> dataModels;
    public ListView listView;
    public static CustomAdapter adapter;
    private DatabaseReference mData;
    private String RECIPIE_KEY = "RECIPIE";
    private FirebaseAuth mAuth;
    @Override
    public  void onResume()
    {
        super.onResume();
        adapter= new CustomAdapter(dataModels,getApplicationContext());
        listView.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mData = FirebaseDatabase.getInstance().getReference();
        listView=(ListView)findViewById(R.id.RecipieList);
        dataModels= new ArrayList<Recipie>();
        adapter= new CustomAdapter(dataModels,getApplicationContext());
        listView.setAdapter(adapter);
        mData = FirebaseDatabase.getInstance().getReference(RECIPIE_KEY);
        mAuth = FirebaseAuth.getInstance();
        registerForContextMenu(listView);
        context = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                SelectedRecepie = (Recipie)listView.getItemAtPosition(position);
                Intent Infointent = new Intent(context, Info.class);
                Infointent.putExtra("Selected", SelectedRecepie);
                startActivity(Infointent);
            }
        });
        getData();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FirebaseUser cUser = mAuth.getCurrentUser();
        switch (item.getItemId()) {
            case R.id.sear:
                EditText e = findViewById(R.id.SearchName);
                if(!e.getText().toString().isEmpty())
                {
                    ArrayList<Recipie> searchList = new ArrayList<>();
                    for(Recipie object:dataModels)
                    {
                        if(object.Name.contains(e.getText().toString()))
                            searchList.add(object);
                    }
                    adapter= new CustomAdapter(searchList,getApplicationContext());
                    listView.setAdapter(adapter);
                }
                return true;
            case R.id.sort:
                ArrayList<Recipie> g = this.sortByName(dataModels);
                adapter= new CustomAdapter(g,getApplicationContext());
                listView.setAdapter(adapter);
                return true;
            case R.id.addEl:
                Intent intent = new Intent(this, Add.class);
                startActivityForResult(intent, RESULT);
                return true;
            case R.id.show:
                adapter= new CustomAdapter(dataModels,getApplicationContext());
                listView.setAdapter(adapter);
                return true;
            case R.id.LogIn:
                if(cUser!=null)
                {
                    Toast.makeText(this, "User is already Logged in", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent i = new Intent(this, LogInAct.class);
                    startActivity(i);
                }
                return true;
            case R.id.LogOut:
                if(cUser==null)
                {
                    Toast.makeText(this, "User is already Logged out", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mAuth.signOut();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getData()
    {
        ValueEventListener evListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataModels.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    Recipie rec = ds.getValue(Recipie.class);
                    dataModels.add(rec);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mData.addValueEventListener(evListener);
    }



    //////////////////////
    public Recipie SelectedRecepie;
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu, menu);
        ListView lv = (ListView) v;
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
        SelectedRecepie = (Recipie)lv.getItemAtPosition(acmi.position);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.Edit:
                Intent intent = new Intent(this, Edit.class);
                intent.putExtra("Selected", SelectedRecepie);
                startActivity(intent);
                return true;
            case R.id.delete:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                dataModels.remove(SelectedRecepie);
                                adapter= new CustomAdapter(dataModels,getApplicationContext());
                                listView.setAdapter(adapter);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                return true;
            case R.id.info:
                Intent Infointent = new Intent(this, Info.class);
                Infointent.putExtra("Selected", SelectedRecepie);
                startActivity(Infointent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    static final int RESULT =1;
    public void AddObject(View view)
    {
        Intent intent = new Intent(this, Add.class);
        startActivityForResult(intent, RESULT);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK)
//        {
//
//            Recipie object = (Recipie) data.getExtras().get("AddObj");
//            dataModels.add(object);
//            adapter= new CustomAdapter(dataModels,getApplicationContext());
//            listView.setAdapter(adapter);
//        }
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {

            Recipie object = (Recipie) data.getExtras().get("AddObj");
            object.id = mData.getKey();
//            dataModels.add(object);
//            adapter= new CustomAdapter(dataModels,getApplicationContext());
//            listView.setAdapter(adapter);

            object.PushId = mData.push().getKey();
            DatabaseReference myRef = mData.child(RECIPIE_KEY+"/");
            mData.child(object.PushId).setValue(object);
//            mData.push().setValue(object);
            FirebaseUser cUser = mAuth.getCurrentUser();
            if(cUser==null)
            {
                Toast.makeText(this, "You cant add elements as logged out user", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public ArrayList<Recipie> sortByName(ArrayList<Recipie> arr)
    {
        ArrayList<Recipie> arr1 = arr;
        Collections.sort(arr1, new Comparator<Recipie>() {
            @Override
            public int compare(Recipie p0, Recipie p1) {
                return p0.Time.compareTo(p1.Time);
            }
        });
        return arr1;
    }

    public ArrayList<Recipie> Search(String name)
    {
        ArrayList<Recipie> Sorted = new ArrayList<>();
        for (Recipie object : dataModels) {
            if(object.Name.contains(name))
                Sorted.add(object);
        }
        return Sorted;
    }
}