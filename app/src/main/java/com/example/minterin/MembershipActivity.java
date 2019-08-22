package com.example.minterin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MembershipActivity extends AppCompatActivity implements View.OnClickListener {

    TextView btn_mulai;
    ImageButton img_prauts, img_prauas, img_full;
    ImageView ic_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_membership);

        btn_mulai = findViewById(R.id.btn_mulai);
        img_prauts = findViewById(R.id.img_prauts);
        img_prauas = findViewById(R.id.img_prauas);
        img_full = findViewById(R.id.img_fullvers);
        ic_arrow = findViewById(R.id.ic_arrowback_m);

        btn_mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MembershipActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });

        img_prauts.setOnClickListener(this);
        img_prauas.setOnClickListener(this);
        img_full.setOnClickListener(this);
        ic_arrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_prauts :{
                Toast.makeText(this, "paket Pra Uts Berhasil", Toast.LENGTH_SHORT).show();
            }break;
            case R.id.img_prauas :{
                Toast.makeText(this, "paket Pra Uas Berhasil", Toast.LENGTH_SHORT).show();
            }break;
            case R.id.img_fullvers :{
                Toast.makeText(this, "Paket Full Version Berhasil", Toast.LENGTH_SHORT).show();
            }break;
            case R.id.ic_arrowback_m :{
                Intent i = new Intent(MembershipActivity.this, ReferalActivity.class);
                startActivity(i);
                finish();
            }
            default:
                //do nothing
        }
    }
}
