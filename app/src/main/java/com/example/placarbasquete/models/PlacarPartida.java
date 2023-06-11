package com.example.placarbasquete.models;

public class PlacarPartida {
    private int equipeAlfa, equipeBeta;
    public PlacarPartida(int pointA, int pointB){
        this.equipeAlfa = pointA;
        this.equipeBeta = pointB;
    }



    public int getEquipeAlfa() {
        return equipeAlfa;
    }

    public void setEquipeAlfa(int equipeAlfa) {
        this.equipeAlfa = equipeAlfa;
    }

    public int getEquipeBeta() {
        return equipeBeta;
    }

    public void setEquipeBeta(int equipeBeta) {
        this.equipeBeta = equipeBeta;
    }
}
