package com.example.placarbasquete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.placarbasquete.models.ConfigModel;
import com.example.placarbasquete.utils.SharedPreferenceUtil;
import com.example.placarbasquete.utils.StaticKeys;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    Button btnHistorico, btnPlacar, btnCreditos, btnConfig;
    SharedPreferenceUtil preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = new SharedPreferenceUtil(getApplicationContext());

        btnConfig = findViewById(R.id.btnConfig);
        btnCreditos = findViewById(R.id.btnAboutHome);
        btnHistorico = findViewById(R.id.btnHist);
        btnPlacar = findViewById(R.id.btnNewGame);

        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ConfigActivity.class);
                startActivity(intent);
            }
        });
        btnPlacar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String configs = preferences.getAccess(StaticKeys.CONFIG_OBJECT);

                if(!configs.equals("") && !configs.equals(null)){
                    ConfigModel config =  new Gson().fromJson(configs, ConfigModel.class);


                    if(config.getEquipeA().equals("") || config.getEquipeA().equals(null) ||
                            config.getEquipeB().equals("") || config.getEquipeB().equals(null) ||
                            config.getDuracaoQuarto() == 0||
                    config.getDuracaoTempoAdicional() == 0||
                    config.getQtdQuarto() == 0){
                        Toast.makeText(MainActivity.this, "Preencha corretamente as configurações antes de jogar", Toast.LENGTH_LONG).show();
                    }else{
                        Intent intent = new Intent(getApplicationContext(), PlacarActivity.class);
                        intent.putExtra("config", config);
                        startActivity(intent);
                    }
                }
            }
        });
        btnCreditos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
            }
        });
        btnHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HistoricoActivity.class);
                startActivity(intent);
            }
        });
    }
}