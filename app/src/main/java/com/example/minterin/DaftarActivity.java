package com.example.minterin;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class DaftarActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private User user = new User();

    EditText username, email, password;
    Button btnSignUp;
    TextView tvToLogin;
    FirebaseAuth mFirebaseAuth;
    CircleImageView civProfPict;
    Uri pickedImgUri;
    ProgressBar pb_daftar;
    ImageView ic_arrow;

    private DatabaseReference databaseReference;
    private StorageTask mUploadTask;

    static int PReqCode = 1;
    static int REQUESTCODE = 1;

    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(DaftarActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(DaftarActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(DaftarActivity.this, "Please accept for required permission", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(DaftarActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESTCODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_daftar);

        mFirebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        username = findViewById(R.id.et_usernamedaftar);
        email = findViewById(R.id.et_emaildaftar);
        password = findViewById(R.id.et_passworddaftar);
        btnSignUp = findViewById(R.id.btn_daftar);
        tvToLogin = findViewById(R.id.tv_masuk);
        civProfPict = findViewById(R.id.civ_ProfPict);
        pb_daftar = findViewById(R.id.pb_daftar);
        ic_arrow = findViewById(R.id.ic_arrowback_d);

        civProfPict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
                if (Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestForPermission();
                } else {
                    //openGallery()
                }
            }
        });

        ic_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DaftarActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usernameValue = username.getText().toString();
                final String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();

                if (usernameValue.isEmpty()) {
                    username.setError("Username Tidak Boleh Kosong");
                    username.requestFocus();
                } else if (emailValue.isEmpty()) {
                    email.setError("Email Tidak Boleh Kosong");
                    email.requestFocus();
                } else if (passwordValue.isEmpty()) {
                    password.setError("Password Tidak Boleh Kosong");
                    password.requestFocus();
                } else if(pickedImgUri == null){
                    Toast.makeText(DaftarActivity.this, "Silahkan upload gambar", Toast.LENGTH_SHORT).show();
                }else if (usernameValue.isEmpty() && emailValue.isEmpty() && passwordValue.isEmpty()) {
                    Toast.makeText(DaftarActivity.this, "Isi Seluruh Data", Toast.LENGTH_SHORT)
                            .show();
                } else if (!(usernameValue.isEmpty()) && !(emailValue.isEmpty()) && !(passwordValue.isEmpty())) {
                    pb_daftar.setVisibility(View.VISIBLE);
                    btnSignUp.setVisibility(View.GONE);
                    mFirebaseAuth.createUserWithEmailAndPassword(emailValue, passwordValue).addOnCompleteListener(DaftarActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                user.setUsername(usernameValue);
                                user.setEmail(emailValue);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            updateUserInfo(usernameValue, pickedImgUri, mFirebaseAuth.getCurrentUser());
                                            Toast.makeText(DaftarActivity.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                                            pb_daftar.setVisibility(View.GONE);
                                            btnSignUp.setVisibility(View.VISIBLE);
                                            Intent intent = new Intent(DaftarActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(DaftarActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(DaftarActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT)
                                        .show();
                                pb_daftar.setVisibility(View.GONE);
                                btnSignUp.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                } else {
                    Toast.makeText(DaftarActivity.this, "Error Occured!", Toast.LENGTH_SHORT).show();
                    pb_daftar.setVisibility(View.GONE);
                    btnSignUp.setVisibility(View.VISIBLE);
                }
            }
        });

        tvToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DaftarActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void updateUserInfo(final String usernameValue, final Uri pickedImgUri, final FirebaseUser currentUser) {
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference().child("user_photos");
        final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                + "." + getFileExtension(pickedImgUri));
        mUploadTask = fileReference.putFile(pickedImgUri);

        Task<Uri> uriTask = mUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return fileReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    user.setImage(downloadUri.toString());
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user);
                }
            }
        });

        //
//        mUploadTask = fileReference.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                user.setImage(taskSnapshot.toString());
//                FirebaseDatabase.getInstance().getReference("Users")
//                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                        .setValue(user);
//            }
//        });

//        imageFilePath.putFile(pickedImgUri)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                //Upload image success
//                //now we can get out image uri
//                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
//                                .setDisplayName(usernameValue)
//                                .setPhotoUri(uri)
//                                .build();
//                        currentUser.updateProfile(profileUpdate)
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                    }
//                                });
//                    }
//                });
//            }
//        });
    }

    //fungsi dipanggil ketika autentikasi berhasil
    private void AuthSuccess(FirebaseUser user) {
        writenewUser(user.getUid(), username.getText().toString(), email.getText().toString());

    }

    //menulis ke database
    private void writenewUser(String userId, String nama, String email) {
        User user = new User(nama, email);

        databaseReference.child("users").child(userId).setValue(user);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null) {
            pickedImgUri = data.getData();
            civProfPict.setImageURI(pickedImgUri);
        }
    }
}
