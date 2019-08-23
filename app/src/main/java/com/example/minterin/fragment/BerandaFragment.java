package com.example.minterin.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minterin.MembershipActivity;
import com.example.minterin.R;
import com.example.minterin.User;
import com.example.minterin.tab.PagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BerandaFragment extends Fragment implements Tab1.OnFragmentInteractionListener, Tab2.OnFragmentInteractionListener, Tab3.OnFragmentInteractionListener {

    TextView tv_namauser, tv_statususer;
    Button btn_upgrade;
    private ImageView img_profil;
    private String namaUser;
    private String iduser;
    private String email;
    private String image;
    private Handler handler;
    private DatabaseReference databaseReference;
    private FirebaseUser Fuser;
    private FirebaseAuth Fauth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ui_beranda, container, false);
        btn_upgrade = view.findViewById(R.id.btn_upgrade);

        Fauth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        tv_namauser = view.findViewById(R.id.nama_profil);
        tv_statususer = view.findViewById(R.id.status_profil);
        img_profil = view.findViewById(R.id.img_profile);

        //get user yang teruautentikasi
        Fuser = Fauth.getCurrentUser();
        iduser = Fuser.getUid();

        //get nama , email, dan jurusan
        getUser();

        //set nama user, dan status
        handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    //update
                    tv_namauser.setText(namaUser);
                    Picasso.get().load(image).into(img_profil);
                }
            }
        };

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Semua"));
        tabLayout.addTab(tabLayout.newTab().setText("Dalam progress"));
        tabLayout.addTab(tabLayout.newTab().setText("Selesai"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = view.findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        btn_upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MembershipActivity.class);
                startActivity(i);
            }
        });

        return view;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

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

