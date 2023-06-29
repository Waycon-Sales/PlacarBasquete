package com.example.placarbasquete.models;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ConexaoDB extends SQLiteOpenHelper {
    Context context;
    private static final String NOME_BANCO = "placarbasquete.sqlite";

    private static final String TABELA01 = "historico";
    private static final String tab01Id = "id";
    private static final String data = "data";
    private static final String times = "times";
    private static final String placar = "placar";
    private static final String duracao = "duracao";


    private static final int VERSAO = 1;

    public SQLiteDatabase db;

    public ConexaoDB(@Nullable Context context) {
        super(context, NOME_BANCO, null, VERSAO);
        context = context;
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String tableHistorico = "CREATE TABLE IF NOT EXISTS "+TABELA01+"("
                + tab01Id + " integer primary key autoincrement,"
                + data + " text,"
                + times + " text,"
                + placar + " text,"
                + duracao + " text"
                + ")";

        try{
            db.execSQL(tableHistorico);
            Log.e("DB_LOG", "Tabelas criadas com sucesso!");
        }catch (SQLException e){
            Log.e("DB_LOG", "onCreate: "+e.getLocalizedMessage());
            Toast.makeText(context, "Erro ao criar tabelas de dados", Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA01);
        onCreate(db);
    }
}
