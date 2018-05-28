package com.example.onur.whataword;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.HashMap;

public class KelimeGuncel extends AppCompatActivity {

    private HashMap<String,String> map;
    EditText edtEn,edtTr,edtCen,edtCtr;
    Button btnEkle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelime_guncel);


        final RelativeLayout relativeLayout= (RelativeLayout)findViewById(R.id.kelime);

        Intent ıntent=getIntent();
        final int id=ıntent.getIntExtra("ID",0);


        final DataBase db=new DataBase(getApplicationContext());
        map=db.kelimedetay(id);

        edtEn= (EditText) findViewById(R.id.txtEn); edtTr= (EditText) findViewById(R.id.txtTr);
        edtCen= (EditText) findViewById(R.id.txtCen); edtCtr= (EditText)findViewById(R.id.txtCtr);
        btnEkle= (Button) findViewById(R.id.btnEkle);

        edtEn.setText(map.get(WordContent.WordEntity.EN).toString());edtTr.setText(map.get(WordContent.WordEntity.TR).toString());
        edtCen.setText(map.get(WordContent.WordEntity.CEN).toString());edtCtr.setText(map.get(WordContent.WordEntity.CTR).toString());



        btnEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String en,tr,cen,ctr;
                en=edtEn.getText().toString(); ctr=edtCtr.getText().toString();
                cen=edtCen.getText().toString(); tr=edtTr.getText().toString();

                db.Update(en,tr,cen,ctr,id);

                Snackbar snackbar=Snackbar.make(relativeLayout,"Güncelleme işlemi yapıldı",Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.WHITE);
                snackbar.setAction("GÖSTER", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                    }
                });
                snackbar.show();

            }
        });

        final Intent[] i=new Intent[3];
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottommenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_liste:
                        i[0]=new Intent(KelimeGuncel.this,MainActivity.class);
                        startActivity(i[0]);
                        break;
                    case R.id.menu_ekle:
                        i[1]=new Intent(KelimeGuncel.this,KelimeEkle.class);
                        startActivity(i[1]);
                        break;
                    case R.id.menu_ayarlar:
                        i[2]=new Intent(KelimeGuncel.this,Ayarlar.class);
                        startActivity(i[2]);
                        break;
                }
                return true;
            }
        });

    }
}
