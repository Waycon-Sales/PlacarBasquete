package com.example.placarbasquete.control;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.example.placarbasquete.models.ConexaoDB;
import com.example.placarbasquete.models.HistoricoModel;

import java.util.ArrayList;

public class DBController {
    private ConexaoDB bank;
    private Context contxt;

    public DBController(Context context){
        contxt = context;
        bank = new ConexaoDB(context);
    }

    public Boolean inserirPartida(HistoricoModel historicoModel){
        ContentValues values = new ContentValues();
        values.put("data", historicoModel.getData());
        values.put("times", historicoModel.getTimes());
        values.put("placar", historicoModel.getPlacar());
        values.put("duracao", historicoModel.getDuracao());
        return bank.db.insert("historico", null, values) > 0;
    }

    @SuppressLint("Range")
    public ArrayList<HistoricoModel> selecionarPartidas(){
        ArrayList<HistoricoModel> result = new ArrayList<>();
        Cursor cursor;
        String sqlSelect = "SELECT * FROM historico";
        cursor = bank.db.rawQuery(sqlSelect,null);
        if(cursor.moveToFirst()){
            do{
                try {
                    HistoricoModel his = new HistoricoModel();
                    his.setData(cursor.getString(cursor.getColumnIndex("data")));
                    his.setTimes(cursor.getString(cursor.getColumnIndex("times")));
                    his.setPlacar(cursor.getString(cursor.getColumnIndex("placar")));
                    his.setDuracao(cursor.getString(cursor.getColumnIndex("duracao")));
                    result.add(his);
                }catch (Exception e){
                    Toast.makeText(contxt, "Erro ao selecionar fatura do banco de dados", Toast.LENGTH_SHORT).show();
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }



}
