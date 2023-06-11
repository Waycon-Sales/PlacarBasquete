package com.example.placarbasquete.models;

import java.io.Serializable;

public class ConfigModel implements Serializable {
    String equipeA, equipeB;
    long duracaoQuarto, duracaoTempoAdicional;
    int qtdQuarto;


    public String getEquipeA() {
        return equipeA;
    }

    public void setEquipeA(String equipeA) {
        this.equipeA = equipeA;
    }

    public String getEquipeB() {
        return equipeB;
    }

    public void setEquipeB(String equipeB) {
        this.equipeB = equipeB;
    }

    public long getDuracaoQuarto() {
        return duracaoQuarto;
    }

    public void setDuracaoQuarto(long duracaoQuarto) {
        this.duracaoQuarto = duracaoQuarto;
    }

    public long getDuracaoTempoAdicional() {
        return duracaoTempoAdicional;
    }

    public void setDuracaoTempoAdicional(long duracaoTempoAdicional) {
        this.duracaoTempoAdicional = duracaoTempoAdicional;
    }

    public int getQtdQuarto() {
        return qtdQuarto;
    }

    public void setQtdQuarto(int qtdQuarto) {
        this.qtdQuarto = qtdQuarto;
    }
}
