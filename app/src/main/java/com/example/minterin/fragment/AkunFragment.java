package com.example.minterin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minterin.LoginActivity;
import com.example.minterin.MembershipActivity;
import com.example.minterin.R;
import com.example.minterin.User;
import com.example.minterin.helperS.PrefsHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AkunFragment extends Fragment implements View.OnClickListener {
    TextView tv_editProfil, tv_editEmail, tv_editPassword, tv_logout, tv_namaakun, tv_jurusan, tv_email;
    Button btn_upgrade;
    private ImageView iv_dpakun;
    private DatabaseReference databaseReference;
    private FirebaseUser Fuser;
    private FirebaseAuth Fauth;
    private String namaUser;
    private String jurusan;
    private String image;
    private String email;
    private String iduser;
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ui_akun, container, false);

        tv_namaakun = v.findViewById(R.id.tv_namaakun);
        tv_jurusan = v.findViewById(R.id.tv_jurusan);
        tv_email = v.findViewById(R.id.tv_email);
        tv_editProfil = v.findViewById(R.id.tv_editprofil);
        tv_editEmail = v.findViewById(R.id.tv_editemail);
        tv_editPassword = v.findViewById(R.id.tv_editpassword);
        tv_logout = v.findViewById(R.id.tv_logout);
        btn_upgrade = v.findViewById(R.id.btn_upgradeprofil);
        iv_dpakun = v.findViewById(R.id.iv_profilakun);

        tv_logout.setOnClickListener(this);
        tv_editProfil.setOnClickListener(this);
        tv_editEmail.setOnClickListener(this);
        tv_editPassword.setOnClickListener(this);
        btn_upgrade.setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        Fauth = FirebaseAuth.getInstance();
        //get user yang teruautentikasi
        Fuser = Fauth.getCurrentUser();
        iduser = Fuser.getUid();

        //get nama , email, dan jurusan
        getUser();

        //set Nama User, Email dan Jurusan
        handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    //update
                    tv_namaakun.setText(namaUser);
                    tv_email.setText(email);
                    Picasso.get().load(image).into(iv_dpakun);
                }
            }
        };

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_editprofil: {
                Toast.makeText(getActivity(), "edit profil berhasil", Toast.LENGTH_SHORT).show();
            }break;
            case R.id.tv_editemail: {
                Toast.makeText(getActivity(), "edit email berhasil", Toast.LENGTH_SHORT).show();
            }break;
            case R.id.tv_editpassword: {
                Toast.makeText(getActivity(), "edit password berhasil", Toast.LENGTH_SHORT).show();
            }break;
            case R.id.tv_logout: {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                PrefsHelper.sharedInstance(getActivity()).setStatusLogin(false);
                startActivity(i);
            }break;
            case R.id.btn_upgradeprofil :{
                Intent i = new Intent(getActivity(), MembershipActivity.class);
                startActivity(i);
            }break;
            default:
                // do nothing
        }
    }

    private void getUser() {
        databaseReference.child("Users").child(iduser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        User user = dataSnapshot.getValue(User.class);

                        namaUser = user.getUsername();
                        email = user.getEmail();
                        image = user.getImage();

                        Message message = new Message();
                        message.what = 1;

                        handler.sendMessage(message);
                    }
                }).start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
