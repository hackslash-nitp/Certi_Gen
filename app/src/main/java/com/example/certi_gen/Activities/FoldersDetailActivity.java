package com.example.certi_gen.Activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.certi_gen.Services.DatabaseHelperFolder;
import com.example.certi_gen.Adapters.RecyclerAdapterCertificates;
import com.example.certi_gen.Adapters.RecyclerAdapterFolders;
import com.example.certi_gen.Classes.Certificate;
import com.example.certi_gen.R;
import com.tooltip.Tooltip;

import java.util.ArrayList;
import java.util.List;

public class FoldersDetailActivity extends AppCompatActivity {

    List<Certificate> certificates;
    RecyclerView certificatesRecycler;
    com.example.certi_gen.Services.DatabaseHelperFolder databaseHelperFolder;

    ImageView iv;
    static ImageView certificate_image;
    static int b=0;
    public static void setImage(Bitmap bitmap){
        certificate_image.setImageBitmap(bitmap);
        certificate_image.setVisibility(View.VISIBLE);
        b=1;
    }

    @Override
    public void onBackPressed() {
        if(b==1){
            b=0;
            certificate_image.setVisibility(View.GONE);
        }
        else
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_detail);
        certificate_image = findViewById(R.id.image_certificate);
                iv = findViewById(R.id.folder_creation_info);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tooltip.Builder builder = new Tooltip.Builder(view, R.style.Tooltip)
                        .setCancelable(true)
                        .setDismissOnClick(false)
                        .setCornerRadius(20f)
                        .setGravity(Gravity.TOP)
                        .setText("Template path = user/b&B/work/template" + "\n" + "Excel path = user/b&B/work/Excel" + "\n" + "Signature path = user/b&B/work/Signature" + "\n" + "Date of certi = 22/10/2019");
                builder.show();
            }
        });
        String id = getIntent().getStringExtra("FOLDER_ID");
        certificates = new ArrayList<>();
        certificatesRecycler = findViewById(R.id.certificates_recycler);
        LinearLayoutManager linearLayoutManagerThree = new LinearLayoutManager(getApplicationContext());
        certificatesRecycler.setLayoutManager(linearLayoutManagerThree);
        certificatesRecycler.setHasFixedSize(true);
        databaseHelperFolder = new DatabaseHelperFolder(this);
        certificates =  databaseHelperFolder.getAllCertitificate(id);

        if(certificates.size()!=0){
            certificatesRecycler.setAdapter(new RecyclerAdapterCertificates(certificates));
        }

    }

}
