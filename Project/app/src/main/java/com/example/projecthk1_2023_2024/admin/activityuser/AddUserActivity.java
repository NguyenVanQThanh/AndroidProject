package com.example.projecthk1_2023_2024.Admin.activityuser;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import com.example.projecthk1_2023_2024.R;
import com.example.projecthk1_2023_2024.Util.ListUser;
import com.example.projecthk1_2023_2024.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddUserActivity extends AppCompatActivity {
    EditText name, bir, sex, email, phone, poscode, address ;
    Button btn;
    ImageView back;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReferenceUser = db.collection("User");
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

        back = findViewById(R.id.back);
        btn = findViewById(R.id.btnSave);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUser = email.getText().toString().trim();
                String password = "123456";
                String nameUser = name.getText().toString().trim();
                String birUser = bir.getText().toString().trim();
                String sexUser = sex.getText().toString().trim();
                String phoneUser = phone.getText().toString().trim();
                String postCodeUser = poscode.getText().toString().trim();
                //String address = address.getText().toString();

                CreateUserEmailAccount(emailUser,password,nameUser,birUser,sexUser,phoneUser,postCodeUser);
            }
        });


    }

    private void CreateUserEmailAccount(String emailUser, String password, String nameUser, String birUser, String sexUser, String phoneUser, String posCodeUser) {
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
                                Date date = dateFormat.parse(birUser);
                                Timestamp timestamp = new Timestamp(date);
                                User user = new User("",timestamp,emailUser,true,"",currentUserId,nameUser,phoneUser,posCodeUser,"Kho",sexUser,Timestamp.now());
                                ListUser listUser = ListUser.getInstance();
                                String idDocument = String.valueOf(listUser.getListUser().size()+1);
                                collectionReferenceUser.document(idDocument)
                                        .set(user)
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
                            }catch (ParseException e){
                                e.printStackTrace();
                            }
                        }
                    });
        }else{
            Toast.makeText(this, "Fill full Field", Toast.LENGTH_SHORT).show();
        }
    }
}
