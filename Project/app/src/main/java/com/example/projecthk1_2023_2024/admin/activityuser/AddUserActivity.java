package com.example.projecthk1_2023_2024.Admin.activityuser;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projecthk1_2023_2024.Admin.ScanAddProduct;
import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.Util.ListUser;
import com.example.projecthk1_2023_2024.model.User;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddUserActivity extends AppCompatActivity {
    Uri uri;
    EditText name, bir, sex, email, phone, poscode, address ;
    Button btn;
    ImageView back,avt;
    FloatingActionButton btnImg;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReferenceUser = db.collection("User");
    ListUser listUser = ListUser.getInstance();
//    private CollectionReference collectionReference

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user_layout);
        name = findViewById(R.id.namea);
        bir = findViewById(R.id.bira);
        sex = findViewById(R.id.sexa);
        email = findViewById(R.id.emaila);
        phone = findViewById(R.id.phonea);
        poscode = findViewById(R.id.postcodea);
        address = findViewById(R.id.addressa);
        avt = findViewById(R.id.avt);
        btnImg = findViewById(R.id.btnImg);

        back = findViewById(R.id.back_homeA);
        btn = findViewById(R.id.btnSave);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(AddUserActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUser = email.getText().toString().trim();
                String password = "123456";
                Boolean enable = true;
                String nameUser = name.getText().toString().trim();
                String birUser = bir.getText().toString().trim();
                String sexUser = sex.getText().toString().trim();
                String phoneUser = phone.getText().toString().trim();
                String postCodeUser = poscode.getText().toString().trim();
                String addressUser = address.getText().toString().trim();

                CreateUserEmailAccount(addressUser, emailUser,password, nameUser, birUser,sexUser,phoneUser,postCodeUser);

                Toast.makeText(AddUserActivity.this, "Thêm NV thành công có password mặc định là 123456!", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void CreateUserEmailAccount(String address, String emailUser, String password, String nameUser, String birUser, String sexUser, String phoneUser, String posCodeUser) {
        if(!TextUtils.isEmpty(emailUser)&& !TextUtils.isEmpty(nameUser) && !TextUtils.isEmpty(birUser)
                && !TextUtils.isEmpty(sexUser) && !TextUtils.isEmpty(phoneUser)){
            firebaseAuth.createUserWithEmailAndPassword(emailUser,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            currentUser = firebaseAuth.getCurrentUser();
                            String currentUserId = currentUser.getUid();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            try{
                                if (uri!=null){
                                    Log.d("Test", "Uri ko null" + uri);


                                    String imageName = "user/" + nameUser + ".jpg";
                                    StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(imageName);

                                    UploadTask uploadTask = storageRef.putFile(uri);
                                    uploadTask.addOnSuccessListener(taskSnapshot -> {
                                        // Nếu tải lên ảnh thành công, lấy đường link của ảnh
                                        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                            String imageUrl = uri.toString();

                                            Date date = null;
                                            try {
                                                date = dateFormat.parse(birUser);
                                            } catch (ParseException e) {
                                                throw new RuntimeException(e);
                                            }
                                            Timestamp timestamp = new Timestamp(date);
                                            Map<String, Object> userData = new HashMap<>();
                                            userData.put("UserName", nameUser);
                                            userData.put("Address", address);
                                            userData.put("Sex", sexUser);
                                            userData.put("Phone", phoneUser);
                                            userData.put("Postcode", posCodeUser);
                                            userData.put("Email", emailUser);
                                            userData.put("LoginID", currentUserId);
                                            userData.put("Enable", true);
                                            userData.put("Role", "Kho");
                                            userData.put("Image", imageUrl);
                                            userData.put("Start_Date",Timestamp.now());
                                            userData.put("Birthday", timestamp);
//                                            ListUser listUser = ListUser.getInstance();
                                            // Cập nhật đường link vào đối tượng Product

                                            // Lưu đối tượng Product vào Firestore
                                            db.collection("User").document(String.valueOf(listUser.setIdDocument()))
                                                    .set(userData)
                                                    .addOnSuccessListener(aVoid -> {
                                                        Log.d(TAG, "Success");
                                                        startActivity(new Intent(AddUserActivity.this, UserAdminActivity.class));
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Log.d(TAG, "Failure: " + e.getMessage());
                                                    });
                                        });
                                    }).addOnFailureListener(e -> {
                                        Log.d(TAG, "Image upload failed: " + e.getMessage());
                                    });
//                                    ///////


                                }else {
                                    Log.d("Test", "Uri null");
                                    Date date = dateFormat.parse(birUser);
                                    Timestamp timestamp = new Timestamp(date);
//                                  User user = new User(address,timestamp,emailUser,true,"",currentUserId,nameUser,phoneUser,posCodeUser,"Kho",sexUser,Timestamp.now());
                                    Map<String, Object> userData = new HashMap<>();
                                    userData.put("UserName", nameUser);
                                    userData.put("Address", address);
                                    userData.put("Sex", sexUser);
                                    userData.put("Phone", phoneUser);
                                    userData.put("Postcode", posCodeUser);
                                    userData.put("Email", emailUser);
                                    userData.put("LoginID", currentUserId);
                                    userData.put("Enable", true);
                                    userData.put("Role", "Kho");
                                    userData.put("Image", "");
                                    userData.put("Start_Date",Timestamp.now());
                                    userData.put("Birthday", timestamp);
//                                    ListUser listUser = ListUser.getInstance();
//                                    String idDocument = String.valueOf(listUser.getListUser().size()+1);
                                    collectionReferenceUser.document(String.valueOf(listUser.setIdDocument()))
                                            .set(userData)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d(TAG,"Success");
                                                    startActivity(new Intent(AddUserActivity.this, UserAdminActivity.class));
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG,"Failure");
                                                }
                                            });
                                }
                            }catch (ParseException e){
                                e.printStackTrace();
                            }
                        }
                    });
        }else{
            Toast.makeText(this, "Fill full Field", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uri = data.getData();
        avt.setImageURI(uri);
    }
}
