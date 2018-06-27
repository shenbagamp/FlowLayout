package com.example.shenbagampalanisamy.flowlayout26;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
FlowLayout f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        f=new FlowLayout(this);
        super.onCreate(savedInstanceState);
        setContentView(f);
        setContentView(R.layout.activity_main);
    }
}
