package com.example.certi_gen.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.certi_gen.Classes.User;
import com.example.certi_gen.Services.DatabaseHelperUser;
import com.example.certi_gen.Adapters.RecyclerAdapterFolders;
import com.example.certi_gen.Classes.Certificate;
import com.example.certi_gen.Classes.Folder;
import com.example.certi_gen.R;
import com.example.certi_gen.Services.DatabaseHelperFolder;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.formula.functions.Na;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    ImageView imageView;
    static int alpha;
    static ArrayList<Folder> folders;
    ArrayList<Certificate> certificates;
    FloatingActionButton floatingActionButton;
    private int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmaptemp;
    Bitmap bitmapsign;
    int a;
    HashMap<String,ArrayList<String>> exceldata;
    ArrayList<String> Name;
    ArrayList<String> Email;
    ArrayList<String> Roll;
    ArrayList<String> Mob;
    ArrayList<String> College;
    ArrayList<String> Rank;
    String certiDate;
    RecyclerView foldersRecycler;
    static RecyclerAdapterFolders recyclerAdapterFolders;
    Dialog dialog;
    DatabaseHelperFolder databaseHelperFolder;
    static LinearLayout selected_bottom_linearlayout;
    static ImageView shareImage;
    static ImageView deleteImage;
    String string = "";
    static ArrayList<Uri> uriArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        final DatabaseHelperUser DB = new DatabaseHelperUser(this);
        updateProfile = findViewById(R.id.update_profile_icon);
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2 = new Dialog(MainActivity.this);
                dialog2.setContentView(R.layout.update_user_profile);
                final List<User> users =  DB.getAllUser();
                ImageView profilepic = dialog2.findViewById(R.id.update_user_pic);
                final EditText userName = dialog2.findViewById(R.id.update_user_name);
                final EditText userEmail = dialog2.findViewById(R.id.update_user_email);
                Button doUpdate = dialog2.findViewById(R.id.do_update_button);
                dialog.show();
                profilepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                        gallery.setType("image/*");
                        a=3;
                        startActivityForResult(gallery, RESULT_LOAD_IMAGE);
                    }
                });
                doUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(userEmail.getText().length()==0 || userName.getText().length()==0)
                            return;
                        DB.updateUser(users.get(0).getEmailid(),userName.getText().toString(),userEmail.getText().toString(),imageRef);
                        dialog2.cancel();
                    }
                });
            }
        });

        imageView = findViewById(R.id.image);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        foldersRecycler = findViewById(R.id.foldersRecycler);
        selected_bottom_linearlayout = findViewById(R.id.selected_bottom_linearlayout);
        shareImage = findViewById(R.id.share_image);
        deleteImage = findViewById(R.id.delete_image);
        exceldata = new HashMap<>();
        databaseHelperFolder = new DatabaseHelperFolder(this);
        Name = new ArrayList<>();
        Email = new ArrayList<>();
        Roll = new ArrayList<>();
        Mob = new ArrayList<>();
        College = new ArrayList<>();
        Rank = new ArrayList<>();

        List<User> users =  DB.getAllUser();
        if(users.size()==0){
            Intent intent = new Intent(this,UserDetail.class);
            startActivity(intent);
            Log.i("userdetail","called");
            finish();
        }

Log.i("userdetail","not called");

        LinearLayoutManager linearLayoutManagerThree = new LinearLayoutManager(getApplicationContext());
        foldersRecycler.setLayoutManager(linearLayoutManagerThree);
        foldersRecycler.setHasFixedSize(true);


        shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Certificate>certificates = folders.get(alpha).getCertificateList();
                uriArrayList = new ArrayList<>();
                for (int i=0;i<certificates.size();i++){
                    String ref = certificates.get(i).getImageRef();
                    String name = ref.substring(ref.length()-17,ref.length()-1);
                    String folder = ref.substring(0,ref.length()-18);
                    try{
                        File f = new File(folder,name);
                        //Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                        Uri uri = Uri.fromFile(f);
                        uriArrayList.add(uri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND_MULTIPLE);

                intent.setType("image/*");
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,uriArrayList);
                startActivity(intent.createChooser(intent,"share via."));
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // custom dialog
                dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.add_material);
                dialog.setTitle("Certi_Gen...");

                final ImageView template = dialog.findViewById(R.id.template_image);
                final ImageView signImage = dialog.findViewById(R.id.sign_image);
                Button addsign = dialog.findViewById(R.id.add_signature);
                Button addsheet = dialog.findViewById(R.id.add_sheet);
                Button cancel = dialog.findViewById(R.id.cancel);
                Button confirm = dialog.findViewById(R.id.confirm);
                final EditText date = dialog.findViewById(R.id.date_text);

                template.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("image/*");
                        a=1;
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE_REQUEST);
                        if(bitmaptemp!=null) {
                            template.setImageDrawable(new BitmapDrawable(getResources(), bitmaptemp));
                        }

                    }
                });

                addsign.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("image/jpeg");
                        a=2;
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE_REQUEST);
                        if(bitmapsign!=null) {
                            signImage.setImageDrawable(new BitmapDrawable(getResources(), bitmapsign));
                        }

                    }
                });

                addsheet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            InputStream myInput;
                            // initialize asset manager
                            AssetManager assetManager = getAssets();
                            //  open excel sheet
                            myInput = assetManager.open("myexcelsheet.xls");
                            // Create a POI File System object
                            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
                            // Create a workbook using the File System
                            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
                            // Get the first sheet from workbook
                            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
                            // We now need something to iterate through the cells.
                            Iterator<Row> rowIter = mySheet.rowIterator();
                            int rowno =0;
                            while (rowIter.hasNext()) {
                                HSSFRow myRow = (HSSFRow) rowIter.next();
                                if(rowno !=0) {
                                    Iterator<Cell> cellIter = myRow.cellIterator();
                                    int colno =0;
                                    String name="", email="", roll="", mob="", college="", rank="";
                                    while (cellIter.hasNext()) {
                                        HSSFCell myCell = (HSSFCell) cellIter.next();
                                        if (colno==0){
                                            name = myCell.toString();
                                            Name.add(name);
                                        }else if (colno==1){
                                            email = myCell.toString();
                                            Email.add(email);
                                        }else if (colno==2){
                                            roll = myCell.toString();
                                            Roll.add(roll);
                                        }else if (colno==3){
                                            mob = myCell.toString();
                                            Mob.add(mob);
                                        }else if (colno==4){
                                            college = myCell.toString();
                                            College.add(college);
                                        }else if (colno==5){
                                            rank = myCell.toString();
                                            Rank.add(rank);
                                        }
                                        colno++;
                                    }
                                }
                                rowno++;
                            }
                            exceldata.put("NAME", Name);
                            exceldata.put("EMAIL", Email);
                            exceldata.put("ROLL", Roll);
                            exceldata.put("MOB", Mob);
                            exceldata.put("COLLEGE", College);
                            exceldata.put("RANK", Rank);
                        } catch (Exception e) {
                            System.out.println(e);
                        }

                    }
                });

                date.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length()==2){
                            date.setText("/"+s);
                        }
                        else if(s.length()==5){
                            date.setText("/"+s);
                        }
                        else if (s.length()==10){
                            certiDate = s.toString();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        certificates = new ArrayList<>();
                        for (int i=0;i<exceldata.get("NAME").toArray().length;i++){
                            Certificate certificate = new Certificate();
                            certificate.setEmailId(exceldata.get("EMAIL").toArray()[i].toString());
                            certificate.setName(exceldata.get("NAME").toArray()[i].toString());
                            certificate.setRollNo(exceldata.get("ROLL").toArray()[i].toString());
                            certificate.setPhoneNumber(exceldata.get("MOB").toArray()[i].toString());
                            certificate.setPosition(exceldata.get("RANK").toArray()[i].toString());
                            certificate.setFolderRelation("");
                            certificate.setSize("10");
                            databaseHelperFolder.insertCerti(certificate);
                            Bitmap myLogo = bitmaptemp;
                            Bitmap jungle = bitmaptemp.copy(Bitmap.Config.ARGB_8888,true);
                            Bitmap signature = bitmapsign;
                            int width = signature.getWidth();
                            int height = signature.getHeight();

                            float bitmapRatio = (float)width / (float) height;
                            if (bitmapRatio > 1) {
                                width = 300;
                                height = (int) (width / bitmapRatio);
                            } else {
                                height = 300;
                                width = (int) (height * bitmapRatio);
                            }
                            signature = Bitmap.createScaledBitmap(signature, width, height, true);
                            Canvas canvas = new Canvas(jungle);
                            final Bitmap tempBitmap = Bitmap.createBitmap(myLogo.getWidth(), myLogo.getHeight(), Bitmap.Config.RGB_565);
                            Canvas canvas1 = new Canvas(tempBitmap);
                            canvas.drawBitmap(myLogo, 0, 0, null);
                            TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
                            Paint rectPaint = new Paint();
                            rectPaint.setColor(getResources().getColor(android.R.color.transparent));
                            //rectPaint.setStyle(Paint.Style.STROKE);
                            //rectPaint.setStrokeWidth(4.0f);

                            final Frame imageFrame = new Frame.Builder()

                                    .setBitmap(myLogo)                 // your image bitmap
                                    .build();

                            String imageText = "";

                            SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);

                            for (int j = 0; j < textBlocks.size(); j++) {
                                TextBlock textBlock = textBlocks.get(textBlocks.keyAt(j));
                                Paint paint = new Paint();
                                paint.setStyle(Paint.Style.FILL);
                                paint.setColor(getResources().getColor(android.R.color.black)); // Text Color
                                paint.setTextSize(130); //Text Size
                                paint.setTypeface(Typeface.create("Arial", Typeface.BOLD));
                                Paint paint1 = new Paint();
                                paint1.setStyle(Paint.Style.FILL);
                                paint1.setColor(getResources().getColor(android.R.color.black)); // Text Color
                                paint1.setTextSize(60); //Text Size
                                paint1.setTypeface(Typeface.create("Arial", Typeface.BOLD));
                                RectF rect = new RectF(textBlock.getBoundingBox());
                                canvas1.drawRect(rect, rectPaint);
                                if(j==1){
                                    canvas.drawText(certificate.getName(), rect.left+10, rect.top+270, paint);
                                }
                                else if(j==2){
                                    canvas.drawText(certiDate, rect.left-630, rect.top+500, paint1);
                                }
                                else if(j==3){
                                    canvas.drawBitmap(signature, rect.left+610, rect.top-170, new Paint());
                                }
                                //String s = String.valueOf(rect.top)+String.valueOf(rect.bottom)+String.valueOf(rect.left)+String.valueOf(rect.right);
                                //imageText = imageText+s+"\n";
                                template.setImageDrawable(new BitmapDrawable(getResources(), jungle));
                                BitmapDrawable draw = (BitmapDrawable) template.getDrawable();
                                Bitmap bitmap = draw.getBitmap();

                                FileOutputStream outStream = null;
                                File sdCard = Environment.getExternalStorageDirectory();
                                File dir = new File(sdCard.getAbsolutePath() + "/Camera");
                                dir.mkdirs();
                                String fileName = String.format("%d.jpg", System.currentTimeMillis());
                                string = sdCard.getAbsolutePath()+"/Camera"+fileName;
                                certificate.setImageRef(string);
                                certificates.add(certificate);
                                File outFile = new File(dir, fileName);
                                try {
                                    outStream = new FileOutputStream(outFile);
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                                    outStream.flush();
                                    outStream.close();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }


                        }
                        if(folders==null){
                            folders = new ArrayList<>();
                            Folder folder = new Folder();
                            String s = Calendar.getInstance().getTime().toString();
                            String y = "";
                            for(int i=0;i<19;i++){
                                y = y + s.charAt(i);
                            }
                            folder.setName(y);
                            Date date = Calendar.getInstance().getTime();
                            folder.setDateCreated(date.toString());
                            folder.setNoOfItem(Integer.toString(certificates.size()));
                            folder.setDescription("Created on " + Calendar.getInstance().getTime().toString() + " with " + Integer.toString(certificates.size()) + " items");
                            folder.setSize(Integer.toString(certificates.size() * 12)+" Bytes");
                            folder.setCertificateList(certificates);
                            folder.setCertiDate(certiDate);
                            folder.setExcelPath("");
                            folder.setSignPath("");
                            folder.setTempPath("");
                            folders.add(folder);
                            databaseHelperFolder.insertFolder(folder);
                            foldersRecycler.setAdapter(new RecyclerAdapterFolders(folders,MainActivity.this));
                        }
                        else {
                            Folder folder = new Folder();
                            String s = Calendar.getInstance().getTime().toString();
                            String y = "";
                            for(int i=0;i<19;i++){
                                y = y + s.charAt(i);
                            }
                            folder.setName(y);
                            Date date = Calendar.getInstance().getTime();
                            folder.setDateCreated(date.toString());
                            folder.setNoOfItem(Integer.toString(certificates.size()));
                            folder.setDescription("Created on " + Calendar.getInstance().getTime().toString() + " with " + Integer.toString(certificates.size()) + " items");
                            folder.setSize(Integer.toString(certificates.size() * 12)+" Bytes");
                            folder.setCertificateList(certificates);
                            folder.setCertiDate(certiDate);
                            folder.setExcelPath("");
                            folder.setSignPath("");
                            folder.setTempPath("");
                            folders.add(folder);
                            databaseHelperFolder.insertFolder(folder);
                            foldersRecycler.setAdapter(new RecyclerAdapterFolders(folders,MainActivity.this));
                        }
                    }
                });


                dialog.show();
            }
        });

//        if(folders==null&&certificates!=null){
//            folders = new ArrayList<>();
//            Folder folder = new Folder();
//            folder.setName(Calendar.getInstance().getTime().toString());
//            Date date = Calendar.getInstance().getTime();
//            folder.setDateCreated(date.toString());
//            folder.setNoOfItem(Integer.toString(certificates.size()));
//            folder.setDescription("Created on " + Calendar.getInstance().getTime().toString() + " with " + Integer.toString(certificates.size()) + " items");
//            folder.setSize(Integer.toString(certificates.size() * 12)+" Bytes");
//            folder.setCertificateList(certificates);
//            folder.setCertiDate(certiDate);
//            folder.setExcelPath("");
//            folder.setSignPath("");
//            folder.setTempPath("");
//            folders.add(folder);
//            foldersRecycler.setAdapter(new RecyclerAdapterFolders(folders,this));
//        }
//        else if(certificates!=null) {
//            Folder folder = new Folder();
//            folder.setName(Calendar.getInstance().getTime().toString());
//            Date date = Calendar.getInstance().getTime();
//            folder.setDateCreated(date.toString());
//            folder.setNoOfItem(Integer.toString(certificates.size()));
//            folder.setDescription("Created on " + Calendar.getInstance().getTime().toString() + " with " + Integer.toString(certificates.size()) + " items");
//            folder.setSize(Integer.toString(certificates.size() * 12)+" Bytes");
//            folder.setCertificateList(certificates);
//            folder.setCertiDate(certiDate);
//            folder.setExcelPath("");
//            folder.setSignPath("");
//            folder.setTempPath("");
//            folders.add(folder);
//            recyclerAdapterFolders.setFolderList(folders);
//            recyclerAdapterFolders.notifyItemInserted(folders.size());
//        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(a!=3) {
            try {
                if (resultCode == RESULT_OK) {
                    if (requestCode == PICK_IMAGE_REQUEST) {
                        Uri selectedImageUri = data.getData();
                        // Get the path from the Uri
                        final String path = getPathFromURI(selectedImageUri);
                        if (path != null) {
                            File f = new File(path);
                            selectedImageUri = Uri.fromFile(f);
                        }
                        // Set the image in ImageView
                        if (a == 1) {
                            bitmaptemp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                            ImageView imageView = dialog.findViewById(R.id.template_image);
                            imageView.setImageDrawable(new BitmapDrawable(getResources(), bitmaptemp));
                        }
                        if (a == 2) {
                            bitmapsign = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                            ImageView imageView = dialog.findViewById(R.id.sign_image);
                            imageView.setImageDrawable(new BitmapDrawable(getResources(), bitmapsign));
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("FileSelectorActivity", "File select error", e);
            }
        }else{
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
                imageUri = data.getData();
                imageRef = imageUri.getPath();
                ImageView iv = dialog.findViewById(R.id.update_user_pic);
                iv.setImageURI(imageUri);
            }
        }
    }
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    static public void showOptions(final int position, final Context context){
        if(selected_bottom_linearlayout.getVisibility()==View.GONE){
            selected_bottom_linearlayout.setVisibility(View.VISIBLE);
          alpha = position;
            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    folders.remove(position);
                    recyclerAdapterFolders.notifyItemRemoved(position);
                    recyclerAdapterFolders.notifyItemRangeChanged(position,folders.size());
                }
            });
        }
        else {
            selected_bottom_linearlayout.setVisibility(View.GONE);
        }
    }
    int RESULT_LOAD_IMAGE=1;
    Uri imageUri;
    String imageRef;
    Dialog dialog2;
    ImageView updateProfile;



}
