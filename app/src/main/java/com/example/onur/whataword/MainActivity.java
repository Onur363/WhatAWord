package com.example.onur.whataword;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    Toolbar toolbar;
    RecyclerView recyclerView;
    RecycelerAdapter recycelerAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Words> kelimeler;
    ArrayList<HashMap<String, String>> arrayList;
    String EN[], TR[], CEN[], CTR[],Alfabe[];
    int ID[];
    LinearLayout relativeLayout;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = (LinearLayout) findViewById(R.id.main_content);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listeleme();


        final Intent[] i = new Intent[2];

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottommenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_ekle:
                        i[0] =new Intent(MainActivity.this,KelimeEkle.class);
                        startActivity(i[0]);
                        break;
                    case R.id.menu_ayarlar:
                        i[1]=new Intent(MainActivity.this,Ayarlar.class);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem menuItem=menu.findItem(R.id.ara);
        SearchView searchView= (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    /******* Veri tabanından almış olduğumuz bilgileri recycler view adapter kullanarak recycler view içini set ettilk***********/

    public void listeleme() {
        final DataBase db = new DataBase(getApplicationContext());
        arrayList = db.kelimler();
        kelimeler=new ArrayList<Words>();

        if (arrayList.size() == 0) {
            Snackbar snackbar = Snackbar.make(relativeLayout, "Henüz kayıt eklenmemiş", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {

            EN = new String[arrayList.size()];
            TR = new String[arrayList.size()];
            ID = new int[arrayList.size()];
            CTR = new String[arrayList.size()];
            CEN = new String[arrayList.size()];

            for (int i = 0; i < arrayList.size(); i++) {
                ID[i] = Integer.parseInt(arrayList.get(i).get(WordContent.WordEntity.ID));
                EN[i] = arrayList.get(i).get(WordContent.WordEntity.EN);
                TR[i] = arrayList.get(i).get(WordContent.WordEntity.TR);
                CEN[i] = arrayList.get(i).get(WordContent.WordEntity.CEN);
                CTR[i] = arrayList.get(i).get(WordContent.WordEntity.CTR);

                Words words = new Words(ID[i], EN[i], TR[i],CEN[i],CTR[i]);
                kelimeler.add(words);
            }
            recycelerAdapter=new RecycelerAdapter(kelimeler, new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent i=new Intent(MainActivity.this,KelimeGuncel.class);
                    i.putExtra("ID",Integer.valueOf(v.getTag().toString()));
                    startActivity(i);
                }
            }, new CustomItemLongClickListenr() {

                @Override
                public void onItemLongClick(final View v, int position, long id) {


                    AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this,R.style.Theme_AppCompat_Light_Dialog_Alert);
                    dialog.setIcon(R.drawable.uyari2);
                    dialog.setMessage("Kelimeyi silmek istiyor musunuz?");
                    dialog.setTitle("UYARI!");
                    dialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.delete(Integer.valueOf(String.valueOf(v.getTag())));
                            Intent intent=new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);

                        }
                    });
                    dialog.show();

                }
            });
            recyclerView.setAdapter(recycelerAdapter);

        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText=newText.toLowerCase();
        ArrayList<Words> newList=new ArrayList<Words>();
        for(Words wory:kelimeler)
        {
            String name=wory.getEn().toLowerCase();
            if(name.startsWith(newText)) //newText iin içine girilen karakter ile başlyıp sıralamaya yarıyor
                newList.add(wory);
        }
        recycelerAdapter.setFilter(newList);
        return true;
    }

}

