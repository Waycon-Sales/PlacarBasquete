package com.example.placarbasquete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.placarbasquete.control.DBController;
import com.example.placarbasquete.dialog.DialogFinishGameActivity;
import com.example.placarbasquete.interfaces.PlacarInterface;
import com.example.placarbasquete.models.ConfigModel;
import com.example.placarbasquete.models.HistoricoModel;
import com.example.placarbasquete.models.PlacarPartida;
import com.example.placarbasquete.utils.SharedPreferenceUtil;
import com.example.placarbasquete.utils.StaticKeys;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PlacarActivity extends AppCompatActivity implements PlacarInterface {

    private int qtdQuarto = 3;
    private TextView timePeriod, tvQto, tvTimeTotal, tvPointA, tvPointB, tvEquipeA, tvEquipeB;

    private Button btnFinish, btnRebobinar;

    private LinearLayout btn01A,btn02A,btn03A,btn01B,btn02B,btn03B;
    private ImageView btnPlayAndPause;

    private CountDownTimer restartTimer;
    private int restartCount = 0;
    private long timeRemaining;
    private long initialMillis;
    private boolean isPaused = true;

    private long timeTotal = 0;

    private boolean timeAddActivite = false;

    private int pointA = 0;
    private int pointB = 0;

    private Context context;

    private long valorTempoAdicional = 300000; // padrao 5 min

    private ArrayList<PlacarPartida> placarList = new ArrayList<>();

    private SharedPreferenceUtil preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placar);

        //só lembrei de usar binding quando fui fazer o pop-up ;-;
        tvEquipeA = findViewById(R.id.tvGroup01);
        tvEquipeB = findViewById(R.id.tvGroup02);
        timePeriod = findViewById(R.id.tvTimePeriod);
        tvQto = findViewById(R.id.tvQto);
        tvTimeTotal = findViewById(R.id.tvTotalTime);
        tvPointA = findViewById(R.id.tvPointA);
        tvPointB = findViewById(R.id.tvPointB);
        btnPlayAndPause = findViewById(R.id.btnPlayAndPause);
        btnFinish = findViewById(R.id.btnGameOver);
        btn01A = findViewById(R.id.btn01A);
        btn02A = findViewById(R.id.btn02A);
        btn03A = findViewById(R.id.btn03A);
        btn01B = findViewById(R.id.btn01B);
        btn02B = findViewById(R.id.btn02B);
        btn03B = findViewById(R.id.btn03B);
        btnRebobinar = findViewById(R.id.btnRebobinar);


        tvPointA.setText(String.format("%02d",pointA));
        tvPointB.setText(String.format("%02d",pointB));

        placarList.add(new PlacarPartida(0,0));


        preferences = new SharedPreferenceUtil(getApplicationContext());


        initialMillis = 10000;
        timeRemaining = initialMillis;

        context = this;

        tvQto.setText("Qto "+String.format("%02d/%02d",(restartCount+1), qtdQuarto));

        btnFinish.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(tvTimeTotal.getText().toString().equals("00:00") && placarList.size() <= 1 || tvTimeTotal.getText().toString().equals("00:00:00") && placarList.size() <=1){
                            finish();
                        }else{
                            DialogFinishGameActivity dialog = new DialogFinishGameActivity("Deseja finalizar a partida?", context,true, true);
                            dialog.show(getSupportFragmentManager(),"FinishDialogGame");
                        }
                    }
                }
        );
        btnPlayAndPause.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(isPaused){
                            restartTimer = recursiveTimePeriod(false, "Tempo esgotado!");
                            restartTimer.start();
                            btnPlayAndPause.setImageResource(R.drawable.round_pause_circle_outline_24);
                            isPaused = false;

                        }else{
                            restartTimer.cancel();
                            btnPlayAndPause.setImageResource(R.drawable.round_play_circle_outline_24);
                            isPaused = true;
                        }
                    }
                }
        );


        btn01A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pointA += 1;
                tvPointA.setText(String.format("%02d",pointA));
                placarList.add(new PlacarPartida(pointA,pointB));
            }
        });

        btn02A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pointA += 2;
                tvPointA.setText(String.format("%02d",pointA));
                placarList.add(new PlacarPartida(pointA,pointB));
            }
        });
        btn03A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pointA += 3;
                tvPointA.setText(String.format("%02d",pointA));
                placarList.add(new PlacarPartida(pointA,pointB));
            }
        });

        btn01B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pointB += 1;
                tvPointB.setText(String.format("%02d",pointB));
                placarList.add(new PlacarPartida(pointA,pointB));
            }
        });

        btn02B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pointB += 2;
                tvPointB.setText(String.format("%02d",pointB));
                placarList.add(new PlacarPartida(pointA,pointB));
            }
        });

        btn03B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pointB += 3;
                tvPointB.setText(String.format("%02d",pointB));
                placarList.add(new PlacarPartida(pointA,pointB));
            }
        });


        btnRebobinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipe();
            }
        });

        Intent intent = getIntent();

        if (intent.hasExtra("config"
        )) {
            ConfigModel config = (ConfigModel) intent.getSerializableExtra("config");
            valorTempoAdicional = config.getDuracaoTempoAdicional();
            initialMillis = config.getDuracaoQuarto();
            qtdQuarto = config.getQtdQuarto();
            tvEquipeA.setText(config.getEquipeA());
            tvEquipeB.setText(config.getEquipeB());
            tvQto.setText("Qto "+String.format("%02d/%02d",(restartCount+1), qtdQuarto));

            timeRemaining = initialMillis;

        }




    }



    CountDownTimer recursiveTimePeriod(boolean newTemp, String message){
        long initTime = timeRemaining;
        if(newTemp){
            initTime = initialMillis;
        }

        if(timeAddActivite){
            initTime = valorTempoAdicional;
        }
        return new CountDownTimer(initTime, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeTotal +=1000;
                timeRemaining = millisUntilFinished;
                long totalSeconds = millisUntilFinished / 1000;
                long hours = totalSeconds / 3600;
                long minutes = (totalSeconds % 3600) / 60;
                long seconds = totalSeconds % 60;


                String formattedTime = String.format("%02d:%02d",minutes, seconds);
                if(hours >= 1){
                    formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                }
                timePeriod.setText(formattedTime);

                long totalSecondsTimeTotal = timeTotal / 1000;
                long hoursTotal = totalSecondsTimeTotal / 3600;
                long minutesTotal = (totalSecondsTimeTotal % 3600) / 60;
                long secondsTotal = totalSecondsTimeTotal % 60;

                String formattedTimeTotal = String.format("%02d:%02d",minutesTotal, secondsTotal);
                if(hoursTotal >= 1){
                    formattedTimeTotal = String.format("%02d:%02d:%02d", hoursTotal, minutesTotal, secondsTotal);
                }
                tvTimeTotal.setText(formattedTimeTotal);
            }

            @Override
            public void onFinish() {
                // Ação executada quando o tempo chegar a zero
                restartCount++;
                if (restartCount < qtdQuarto) {
                    Log.i("Temporizador","Temporizador reiniciou");
                    tvQto.setText("Qto "+String.format("%02d/%02d",(restartCount+1), qtdQuarto));
                    timeRemaining = initialMillis;
                    restartTimer = recursiveTimePeriod(true, message);
                    restartTimer.start();
                } else {

                    DialogFinishGameActivity dialog = new DialogFinishGameActivity(message, context, timeAddActivite, false );
                    dialog.show(getSupportFragmentManager(), "FinishDialog");
                    //acabou pt
                    isPaused = true;
                    btnPlayAndPause.setImageResource(R.drawable.round_play_circle_outline_24);
                }
            }
        };
    }




    @Override
    public void finishAndSave() {
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = dataAtual.format(formatador);

        HistoricoModel playAtual = new HistoricoModel();
        playAtual.setData(dataFormatada);
        playAtual.setPlacar(String.format("%02d vs %02d",pointA, pointB));
        playAtual.setDuracao(tvTimeTotal.getText().toString());
        playAtual.setTimes(tvEquipeA.getText().toString()+" vs "+tvEquipeB.getText().toString());

        DBController dbController = new DBController(getApplicationContext());
        dbController.inserirPartida(playAtual);

        finish();
    }

    @Override
    public void finishNotSave() {
        finish();
    }

    @Override
    public void addTime() {
        timeAddActivite = true;
        timeRemaining = initialMillis;
        restartTimer = recursiveTimePeriod(true, "Tempo Adicional esgotado!");
    }

    @Override
    public void swipe() {
        int id = placarList.size() - 2;
        if(id >= 0){
            pointA = placarList.get(id).getEquipeAlfa();
            pointB = placarList.get(id).getEquipeBeta();
            tvPointA.setText(String.format("%02d",pointA));
            tvPointB.setText(String.format("%02d",pointB));

            placarList.remove(placarList.size() - 1);

        }
    }


}
