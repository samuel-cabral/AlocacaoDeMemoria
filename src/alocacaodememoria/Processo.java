/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alocacaodememoria;

import java.util.logging.Logger;
import javafx.scene.layout.StackPane;

public class Processo {
    int id,tamanho, tempoCriacao, tempoDuracao, tempoAlocado, tempoConclusao;
    int posInicio = 0, posFim = 0, tamFisico;
    int tempoEspera = (tempoConclusao - tempoCriacao);
    float porcentagem=0;
    String status;
    Frame frame;
    StackPane desenho;

    public Processo(int id, int tamanho, int tempoCriacao, int tempoDuracao, int tamFisico, String status) {
        this.id = id;
        this.tamanho = tamanho;
        this.tempoCriacao = tempoCriacao;
        this.tempoDuracao = tempoDuracao;
        this.porcentagem = porcentagem;
        this.tamFisico = tamFisico;
        this.status = status;
    }
    private static final Logger LOG = Logger.getLogger(Processo.class.getName());

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public int getTempoCriacao() {
        return tempoCriacao;
    }

    public void setTempoCriacao(int tempoCriacao) {
        this.tempoCriacao = tempoCriacao;
    }

    public int getTempoDuracao() {
        return tempoDuracao;
    }

    public void setTempoDuracao(int tempoDuracao) {
        this.tempoDuracao = tempoDuracao;
    }

    public int getTempoAlocado() {
        return tempoAlocado;
    }

    public void setTempoAlocado(int tempoAlocado) {
        this.tempoAlocado = tempoAlocado;
    }

    public int getTempoConclusao() {
        return tempoConclusao;
    }

    public void setTempoConclusao(int tempoConclusao) {
        this.tempoConclusao = tempoConclusao;
    }

    public int getTempoEspera() {
        return tempoEspera;
    }

    public void setTempoEspera(int tempoEspera) {
        this.tempoEspera = tempoEspera;
    }

    public float getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(float porcentagem) {
        this.porcentagem = porcentagem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPosInicio(int posInicio) {
        this.posInicio = posInicio;
    }

    public void setPosFim(int posFim) {
        this.posFim = posFim;
    }
    
    
    
    
}
