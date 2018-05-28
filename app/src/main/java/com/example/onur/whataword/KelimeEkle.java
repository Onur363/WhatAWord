package com.example.onur.whataword;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class KelimeEkle extends AppCompatActivity {

    EditText edtEn,edtTr,edtCen,edtCtr;
    Button btnEkle;
    ImageButton imgTrans;
    LinearLayout linearLayout;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelime_ekle);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        edtEn= (EditText) findViewById(R.id.txtEn); edtTr= (EditText) findViewById(R.id.txtTr);
        edtCen= (EditText) findViewById(R.id.txtCen); edtCtr= (EditText)findViewById(R.id.txtCtr);
        btnEkle= (Button) findViewById(R.id.btnEkle); imgTrans= (ImageButton) findViewById(R.id.btnTranslate);

        btnEkle.setElevation(10);

        btnEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtEn.getText().toString().trim().equals(""))
                    edtEn.setError("Bu alan boş bırakılamaz");
                else if(edtTr.getText().toString().trim().equals("")) {
                    edtTr.setError("Bu alan boş bırakılamaz\n" +
                            "Not:Yandaki butona basarak kelimenin türkçe karşılığına bak");
                }
                else
                {
                    BackGroundTask backGroundTask=new BackGroundTask(KelimeEkle.this);
                    backGroundTask.execute("Add",edtEn.getText().toString(),edtTr.getText().toString(),edtCen.getText().toString(),edtCtr.getText().toString());

                }
            }
        });

        imgTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("http:\\translate.google.com"));
                startActivity(i);
            }
        });


        bottomNavigationView= (BottomNavigationView) findViewById(R.id.bottommenu);
        bottomNavigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.gri)));
        bottomNavigationView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.gri)));
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_liste:
                        Intent i=new Intent(KelimeEkle.this,MainActivity.class);
                        startActivity(i);
                        break;
                    case R.id.menu_ayarlar:
                        Intent i2=new Intent(KelimeEkle.this,Ayarlar.class);
                        startActivity(i2);
                        break;
                }
                return true;
            }
        });

    }


    class BackGroundTask extends AsyncTask<String,Integer,String> {

        ProgressDialog progressDialog;
        KelimeEkle ek;
        public BackGroundTask(KelimeEkle ek){

           this.ek=ek;
        }

        @Override
        protected void onPreExecute() {

            progressDialog=new ProgressDialog(KelimeEkle.this);
            progressDialog.setProgressStyle(R.style.Widget_AppCompat_ProgressBar);
            progressDialog.setMessage("Kayıt işlemi yapılıyor");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setProgress(0);
            progressDialog.setMax(100);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            DataBase db=new DataBase(getApplicationContext());

            String method=params[0];

            if(method.equals("Add"))
            {
                for(int i=1;i<101;i+=100)
                {
                    try{

                        String en,tr,cen,ctr;
                        en= params[1];tr= params[2]; cen= params[3]; ctr= params[4];
                        db.KelimeEkle(en,tr,cen,ctr);
                        publishProgress(i);
                        Thread.sleep(1000);

                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
                return "Add";
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Integer progress=values[0];
            progressDialog.setProgress(progress);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Intent i=new Intent(KelimeEkle.this,MainActivity.class);
            startActivity(i);
        }
    }
}
