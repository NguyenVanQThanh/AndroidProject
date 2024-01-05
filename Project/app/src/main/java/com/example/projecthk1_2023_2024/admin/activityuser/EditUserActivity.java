package com.example.projecthk1_2023_2024.Admin.activityuser;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.projecthk1_2023_2024.Admin.AdminHomeActivity;
import com.example.projecthk1_2023_2024.Admin.ScanAddProduct;
import com.example.projecthk1_2023_2024.Admin.productactivity.ProductAdminActivity;
import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.Util.ListUser;
import com.example.projecthk1_2023_2024.model.User;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class EditUserActivity extends AppCompatActivity {
    ImageView imgAcc, backDetail;
    EditText edtName, edtSex, edtPhone, edtAddress, edtPost, edtEmail;
    FloatingActionButton addImg;
    Button btnUpdate;
    Uri uri;
    ListUser listUser = ListUser.getInstance();
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_account_layout);
        imgAcc = findViewById(R.id.avt);
        addImg = findViewById(R.id.btnImg);
        edtName = findViewById(R.id.edt_name);
        edtSex = findViewById(R.id.edt_sex);
        edtPhone = findViewById(R.id.edt_phone);
        edtAddress = findViewById(R.id.edt_address);
        edtPost = findViewById(R.id.edt_postcode);
        edtEmail = findViewById(R.id.edt_email);
        backDetail = findViewById(R.id.back_homeA);
        backDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        String idUser = getIntent().getStringExtra("IdUser");
        Pair<String, User> userPair = listUser.find(idUser);
        btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edtName.getText().toString();
                String userSex = edtSex.getText().toString();
                String userPhone = edtPhone.getText().toString();
                String userAdress = edtAddress.getText().toString();
                String userPost = edtPost.getText().toString();
                String userEmail = edtEmail.getText().toString();
                String image = userPair.second.getImage();

                String userID = userPair.second.getLoginID();
                Boolean userEnable = userPair.second.getEnable();
                String userRole = userPair.second.getRole();
                Timestamp userStart = userPair.second.getStart_Date();
                Timestamp userBir = userPair.second.getBirthday();

                FirebaseFirestore db = FirebaseFirestore.getInstance();


                if (uri != null) {
                    String oldImageUrl = image;

                    if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
                        StorageReference oldImageRef = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageUrl);
                        oldImageRef.delete().addOnSuccessListener(aVoid -> {
                            String imageName = "user/" + edtName.getText().toString() + ".jpg";
                            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(imageName);
                            UploadTask uploadTask = storageRef.putFile(uri);
                            uploadTask.addOnSuccessListener(taskSnapshot -> {
                                storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                    Log.d("Linkkkkkk1111",uri.toString());
                                    String imageUrl = uri.toString();
                                    Log.d("Linkkkkkk",imageUrl);
                                    Map<String, Object> userData = new HashMap<>();
                                    userData.put("UserName", userName);
                                    userData.put("Address", userAdress);
                                    userData.put("Sex", userSex);
                                    userData.put("Phone", userPhone);
                                    userData.put("Postcode", userPost);
                                    userData.put("Email", userEmail);
                                    userData.put("LoginID", userID);
                                    userData.put("Enable", userEnable);
                                    userData.put("Role", userRole);
                                    userData.put("Start_Date", userStart);
                                    userData.put("Birthday", userBir);
                                    userData.put("Image", imageUrl);
                                    db.collection("User").document(userPair.first)
                                            .set(userData)
                                            .addOnSuccessListener(a -> {
                                                startActivity(new Intent(EditUserActivity.this, UserAdminActivity.class));
                                            })
                                            .addOnFailureListener(e -> {
                                                Log.d(TAG, "Failure: " + e.getMessage());
                                            });
                                });
                            }).addOnFailureListener(e -> {
                                Log.d(TAG, "Image upload failed: " + e.getMessage());
                            });
                        }).addOnFailureListener(e -> {
                            Log.d(TAG, "Failed to delete old image: " + e.getMessage());
                        });
                    } else {
                        String imageName = "user/" + edtName.getText().toString() + ".jpg";
                        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(imageName);
                        UploadTask uploadTask = storageRef.putFile(uri);
                        uploadTask.addOnSuccessListener(taskSnapshot -> {
                            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                String imageUrl = uri.toString();
                                Map<String, Object> userData = new HashMap<>();
                                userData.put("UserName", userName);
                                userData.put("Address", userAdress);
                                userData.put("Sex", userSex);
                                userData.put("Phone", userPhone);
                                userData.put("Postcode", userPost);
                                userData.put("Email", userEmail);
                                userData.put("LoginID", userID);
                                userData.put("Enable", userEnable);
                                userData.put("Role", userRole);
                                userData.put("Start_Date", userStart);
                                userData.put("Birthday", userBir);
                                userData.put("Image", imageUrl);
                                db.collection("User").document(userPair.first)
                                        .set(userData)
                                        .addOnSuccessListener(aVoid -> {
                                            startActivity(new Intent(EditUserActivity.this, UserAdminActivity.class));
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.d(TAG, "Failure: " + e.getMessage());
                                        });
                            });
                        }).addOnFailureListener(e -> {
                            Log.d(TAG, "Image upload failed: " + e.getMessage());
                        });
                    }
                } else {
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("UserName", userName);
                    userData.put("Address", userAdress);
                    userData.put("Sex", userSex);
                    userData.put("Phone", userPhone);
                    userData.put("Postcode", userPost);
                    userData.put("Email", userEmail);
                    userData.put("LoginID", userID);
                    userData.put("Enable", userEnable);
                    userData.put("Role", userRole);
                    userData.put("Image", image);
                    userData.put("Start_Date", userStart);
                    userData.put("Birthday", userBir);
                    db.collection("User")
                            .document(userPair.first)
                            .set(userData)
                            .addOnSuccessListener(aVoid -> {
                                startActivity(new Intent(EditUserActivity.this, UserAdminActivity.class));
                            })
                            .addOnFailureListener(e -> {});
                }
                }




        });





        edtName.setText(userPair.second.getUserName());
        edtPhone.setText(userPair.second.getPhone());
        edtPost.setText(userPair.second.getPostcode());
        edtSex.setText(userPair.second.getSex());
        edtAddress.setText(userPair.second.getAddress());
        edtEmail.setText(userPair.second.getEmail());
        Glide.with(getApplicationContext())
                .load(userPair.second.getImage())
                //.placeholder()
                .fitCenter()
                .into(imgAcc);

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.with(EditUserActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uri = data.getData();
        imgAcc.setImageURI(uri);
//

    }


}
