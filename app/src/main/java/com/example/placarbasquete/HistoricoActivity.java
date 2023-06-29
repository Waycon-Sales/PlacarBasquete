package com.example.placarbasquete;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.placarbasquete.control.DBController;
import com.example.placarbasquete.models.HistoricoModel;
import com.example.placarbasquete.utils.HistoricoAdapter;
import com.example.placarbasquete.utils.SharedPreferenceUtil;
import com.example.placarbasquete.utils.StaticKeys;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HistoricoActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private SharedPreferenceUtil preferences;

    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        preferences = new SharedPreferenceUtil(getApplicationContext());
        recycler = findViewById(R.id.recyclerView);

        btnBack = findViewById(R.id.btnBackHist);

        DBController dbController = new DBController(getApplicationContext());
        ArrayList<HistoricoModel> historico = dbController.selecionarPartidas();

        HistoricoAdapter adapter = new HistoricoAdapter(historico);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        
    }
}