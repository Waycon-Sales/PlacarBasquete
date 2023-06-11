package com.example.placarbasquete;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.placarbasquete.models.ConfigModel;
import com.example.placarbasquete.utils.SharedPreferenceUtil;
import com.example.placarbasquete.utils.StaticKeys;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ConfigActivity extends AppCompatActivity {

    private EditText equipeA, equipeB, duracaoQuarto, qtdQuarto, tempoAdicional;
    private Button btnSave;

    private ImageView btnBack;

    private SharedPreferenceUtil shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);


        shared = new SharedPreferenceUtil(getApplicationContext());
        equipeA = findViewById(R.id.txEquipe01);
        equipeB = findViewById(R.id.txEquipe02);
        duracaoQuarto = findViewById(R.id.txDuracaoQ);
        qtdQuarto = findViewById(R.id.txQtdQ);
        tempoAdicional = findViewById(R.id.txAddTime);
        btnSave = findViewById(R.id.btnSaveConfig);

        btnBack = findViewById(R.id.btnBackConfig);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        duracaoQuarto.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private final Calendar calendar = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String formatted = formatTime(clean);
                    current = formatted;
                    duracaoQuarto.setText(formatted);
                    duracaoQuarto.setSelection(formatted.length());
                }
            }

            private String formatTime(String time) {
                if (time.length() >= 6) {
                    int hours = Integer.parseInt(time.substring(0, 2));
                    int minutes = Integer.parseInt(time.substring(2, 4));
                    int seconds = Integer.parseInt(time.substring(4, Math.min(time.length(), 6)));
                    if (hours > 23) {
                        hours = 23;
                    }
                    if (minutes > 59) {
                        minutes = 59;
                    }
                    if (seconds > 59) {
                        seconds = 59;
                    }
                    calendar.set(Calendar.HOUR_OF_DAY, hours);
                    calendar.set(Calendar.MINUTE, minutes);
                    calendar.set(Calendar.SECOND, seconds);
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                    return sdf.format(calendar.getTime());
                }
                return time;
            }
        });


        tempoAdicional.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private final Calendar calendar = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String formatted = formatTime(clean);
                    current = formatted;
                    tempoAdicional.setText(formatted);
                    tempoAdicional.setSelection(formatted.length());
                }
            }

            private String formatTime(String time) {
                if (time.length() >= 6) {
                    int hours = Integer.parseInt(time.substring(0, 2));
                    int minutes = Integer.parseInt(time.substring(2, 4));
                    int seconds = Integer.parseInt(time.substring(4, Math.min(time.length(), 6)));
                    if (hours > 23) {
                        hours = 23;
                    }
                    if (minutes > 59) {
                        minutes = 59;
                    }
                    if (seconds > 59) {
                        seconds = 59;
                    }
                    calendar.set(Calendar.HOUR_OF_DAY, hours);
                    calendar.set(Calendar.MINUTE, minutes);
                    calendar.set(Calendar.SECOND, seconds);
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                    return sdf.format(calendar.getTime());
                }
                return time;
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField();
            }
        });

        fullField();

    }


    void checkField(){
        String duracao, duracaoadd, equipe01, equipe02, qtdQ;

        equipe01 =  equipeA.getText().toString();
        equipe02 = equipeB.getText().toString();
        duracao = duracaoQuarto.getText().toString();
        qtdQ  = qtdQuarto.getText().toString().trim();
        duracaoadd =  tempoAdicional.getText().toString();
        boolean allOk = true;

        if(equipe01.equals("") || equipe01.equals(null)){
            equipeA.setError("Preencha o campo corretamente");
            allOk = false;
        }

        if(equipe02.equals("") || equipe02.equals(null)){
            equipeB.setError("Preencha o campo corretamente");
            allOk = false;
        }

        if(duracao.equals("") || duracao.equals(null) || duracao.length() != 8){
            duracaoQuarto.setError("Preencha o campo corretamente, seguindo esse formato 00:00:00");
            allOk = false;
        }

        if(qtdQ.equals("") || qtdQ.equals(null) || qtdQ.equals("0")){
            qtdQuarto.setError("Preencha o campo corretamente");
            allOk = false;
        }

        if(duracaoadd.equals("") || duracaoadd.equals(null) || duracaoadd.length() != 8){
            tempoAdicional.setError("Preencha o campo corretamente, seguindo esse formato 00:00:00");
            allOk = false;
        }

        if(allOk){
            ConfigModel configModel = new ConfigModel();
            configModel.setEquipeA(equipe01);
            configModel.setEquipeB(equipe02);
            configModel.setDuracaoQuarto(convertTimeToMillisecond(duracao));
            configModel.setQtdQuarto(Integer.parseInt(qtdQ));
            configModel.setDuracaoTempoAdicional(convertTimeToMillisecond(duracaoadd));
            shared.setAcessConfig(StaticKeys.CONFIG_OBJECT, new Gson().toJson(configModel));
            Toast.makeText(this, "Configurações Salvas com SUCESSO!", Toast.LENGTH_SHORT).show();


        }else{
            Toast.makeText(this, "Preencha os campos corretamente", Toast.LENGTH_SHORT).show();
        }


    }

    void fullField(){
        String configs = shared.getAccess(StaticKeys.CONFIG_OBJECT);

        if(!configs.equals("") && !configs.equals(null)){
            ConfigModel config =  new Gson().fromJson(configs, ConfigModel.class);
            equipeA.setText(config.getEquipeA());
            equipeB.setText(config.getEquipeB());
            duracaoQuarto.setText(convertTimeToString(config.getDuracaoQuarto()));
            qtdQuarto.setText(config.getQtdQuarto()+"");
            tempoAdicional.setText(convertTimeToString(config.getDuracaoTempoAdicional()));
        }
    }


    long convertTimeToMillisecond(String tempoString){
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
        long tempoMill = 0;
        try {
            Date tempo = formato.parse(tempoString);
            tempoMill = tempo.getTime();
            System.out.println("Tempo em milissegundos: " + tempoMill);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tempoMill;


    }

    String convertTimeToString(long milissegundos){
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
        String tempoString = "";
        try {
            Date tempo = new Date(milissegundos);
            tempoString = formato.format(tempo);
            System.out.println("Tempo formatado: " + tempoString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  tempoString;
    }




}