package com.example.certi_gen.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.certi_gen.Classes.User;
import com.example.certi_gen.R;
import com.example.certi_gen.Services.DatabaseHelperUser;

public class UserDetail extends AppCompatActivity {

    ImageView imageView;
    Uri imageUri;
    String imageRef;
    int RESULT_LOAD_IMAGE = 1;
    EditText userName;
    EditText userEmail;
    Button button;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            imageRef = imageUri.getPath();
            imageView.setImageURI(imageUri);
        }
    }
    DatabaseHelperUser DB;
    User user ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
       DB = new DatabaseHelperUser(this);
        userName = findViewById(R.id.user_name);
        imageView = findViewById(R.id.user_image);
        userEmail = findViewById(R.id.user_emailid);
        user = new User();
        button = findViewById(R.id.button);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, RESULT_LOAD_IMAGE);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userEmail.getText().length()==0 || userName.getText().length() == 0)
                    return;
                user.setImageRef(imageRef);
                user.setEmailid(userEmail.getText().toString());
                user.setName(userName.getText().toString());
                DB.insertUSer(user);
                Intent intent = new Intent(UserDetail.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}
