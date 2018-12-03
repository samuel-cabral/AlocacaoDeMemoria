
package alocacaodememoria;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Pane paneMem;
        
    @FXML
    private TextField tamMem;

    @FXML
    private TextField tamSO;

    @FXML
    private TextField tamProc1;

    @FXML
    private TextField tamProc2;

    @FXML
    private TextField tCriacao1;

    @FXML
    private TextField tCriacao2;

    @FXML
    private TextField tDuracao1;

    @FXML
    private TextField tDuracao2;
    
    @FXML
    private TextArea txtLog;

    @FXML
    private Button iniciarSimulacao;

    @FXML
    private Label instante;

    @FXML
    private Label media;

    @FXML
    private Label cpu;

    @FXML
    private ImageView imgMemoria;

    @FXML
    private TextField qteProcessos;

    @FXML
    private RadioButton firstFit;

    @FXML
    private RadioButton bestFit;

    @FXML
    private RadioButton worstFit;

    @FXML
    private TableView<Processo> tabProcessos;

    @FXML
    private TableColumn<Processo, Integer> colId;

    @FXML
    private TableColumn<Processo, Integer> colTamanho;

    @FXML
    private TableColumn<Processo, Float> colPorcent;

    @FXML
    private TableColumn<Processo, Integer> colDuracao;

    @FXML
    private TableColumn<Processo, Integer> colInstCriado;

    @FXML
    private TableColumn<Processo, Integer> colInstAlocado;

    @FXML
    private TableColumn<Processo, Integer> colInstConcluido;

    @FXML
    private TableColumn<Processo, Integer> colTEspera;

    @FXML
    private TableColumn<Processo, String> colStatus;
    
    ObservableList<Frame> framesLivres = FXCollections.observableArrayList();
    ObservableList<Frame> framesOcupados = FXCollections.observableArrayList();
    ObservableList<Processo> processos = FXCollections.observableArrayList();
    ObservableList<Processo> procCriados = FXCollections.observableArrayList();
    ObservableList<Processo> procAlocados = FXCollections.observableArrayList();
    ObservableList<Processo> procFinalizados = FXCollections.observableArrayList();

    ToggleGroup metodos = new ToggleGroup();
    int memoria, tam_SO, m1, m2, tc1, tc2, td1, td2, qtdProc;
    float memCPU = 0;
    Processo sistOp;
    String metodo;
    Timer tempo = new Timer();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bestFit.setToggleGroup(metodos);
        firstFit.setToggleGroup(metodos);
        worstFit.setToggleGroup(metodos);
        
        colId.setCellValueFactory(
            new PropertyValueFactory<>("id"));
        colTamanho.setCellValueFactory(
            new PropertyValueFactory<>("tamanho"));
        colStatus.setCellValueFactory(
            new PropertyValueFactory<>("status"));
        colPorcent.setCellValueFactory(
            new PropertyValueFactory<>("porcentagem"));
        colInstCriado.setCellValueFactory(
            new PropertyValueFactory<>("instCriado"));
        colInstAlocado.setCellValueFactory(
            new PropertyValueFactory<>("instAlocado"));
        colInstConcluido.setCellValueFactory(
            new PropertyValueFactory<>("instConclusao"));
        colDuracao.setCellValueFactory(
            new PropertyValueFactory<>("tempoDuracao"));
        colTEspera.setCellValueFactory(
            new PropertyValueFactory<>("tempoEspera"));
        
        tabProcessos.setItems(procCriados);
    }
    
    public void logMsg(String msg){
        Platform.runLater(() -> {
            txtLog.appendText("\n["+LocalDateTime.now().getHour()+":" + LocalDateTime.now().getMinute()+":"
            + LocalDateTime.now().getSecond()+"] - " + msg);
        });
    }
    
    public void calculaMedia(){
        int soma = 0;
        for(Processo proc: procFinalizados){
            soma += proc.tempoEspera;
        }
        float m = soma/qtdProc;
        media.setText(Float.toString(m));
        iniciarSimulacao.setDisable(false);
    }
    
    public void atualizaCPU(){
        cpu.setText(Float.toString(memCPU));
    }
    
    public void desenhaSO(){
        sistOp.posicaoFim = (600 * tam_SO) / memoria;
        StackPane paneSO;
        paneSO = new StackPane();
        paneSO.setStyle("-fx-background-color: #ff0000; -fx-border-color: black;");
        paneSO.setMinHeight(137);
        paneSO.setMinWidth(sistOp.posicaoFim);
        paneSO.setOpacity(0.7);
        Text id = new Text("SO");
        id.setFont(Font.font("Comic Sans MS", sistOp.tamFisico/2));
        id.setFill(Color.WHITE);
        paneSO.getChildren().add(id);
        
        Frame root = framesLivres.get(0);
        Frame SO = new Frame(0, sistOp.posicaoFim, tam_SO, true);
        Frame resto = new Frame(sistOp.posicaoFim, root.posicaoFim, memoria-tam_SO, false);
        framesOcupados.add(SO);
        framesLivres.set(0,resto);
        sistOp.frame = SO;
        sistOp.desenho = paneSO;
        Platform.runLater(()->{
            paneMem.getChildren().add(paneSO);
            paneSO.relocate(sistOp.posicaoInicio, 101);
        });
    }
    
    public void desenhaProcesso(Processo p){
        StackPane paneProc;
        paneProc = new StackPane();
        paneProc.setStyle("-fx-background-color: #0000ff; -fx-border-color:black");
        paneProc.setMinHeight(137);
        paneProc.setMinWidth(p.tamFisico);
        paneProc.setOpacity(0.9);
        
        Text id = new Text(Integer.toString(p.id));
        id.setFont(Font.font("Comic Sans MS", p.tamFisico/2));
        id.setFill(Color.WHITE);
        paneProc.getChildren().add(id);
        
        p.desenho = paneProc;
        Platform.runLater(()->{
            paneMem.getChildren().add(paneProc);
            paneProc.relocate(p.posicaoInicio, 101);
        });
    }
    
    public Frame getMaisPerto(List<Frame> lista){
        Frame menor = lista.get(0);
        for(int i=0; i<framesLivres.size();i++){
            Frame frame = framesLivres.get(i);
            if(frame.posicaoInicio <= menor.posicaoInicio){
                menor = frame;
            }
        }
        return menor;
    }
    
    public Frame getMenorTam(Frame f, Processo p){ //Captura o frame de menor tamanho - método Best Fit
        Frame menor = f;
        for (Frame frame : framesLivres) {
            if(frame.tamanhoFisico < menor.tamanhoFisico && frame.tamanhoFisico >= p.tamFisico){
                menor = frame;
            }
        }
        return menor;
    }  
    
    public void ordenaFrames(){
        ObservableList<Frame> result = FXCollections.observableArrayList();
        while( !framesLivres.isEmpty() ){
            Frame menor = getMaisPerto(framesLivres);
            framesLivres.remove(menor);
            result.add(menor);
        }
        framesLivres = result;
    }
    
    public void juntaRestos(){
        for(int i=1; i<framesLivres.size(); i++){
            Frame anterior = framesLivres.get(i-1);
            Frame atual = framesLivres.get(i);
            if(anterior.posicaoFim == atual.posicaoInicio){
                Frame novo = new Frame(anterior.posicaoInicio, atual.posicaoFim, anterior.tamanho+atual.tamanho, false);
                framesLivres.remove(atual);
                framesLivres.set(framesLivres.indexOf(anterior), novo);
                i--;
            }
        }
    }
    
    public void alocaProcesso(Processo p, int t){
        tempo.cancel();
        switch(metodo){
            case "First Fit":
                firstFit(p, t);
                break;
            case "Best Fit":
                bestFit(p, t);
                break;
            case "Worst Fit":
                worstFit(p, t);
                break;
        }
        tempo=new Timer();
        tempo.scheduleAtFixedRate( new TimerTask(){
            @Override
            public void run(){
                rotina();
            }
        },1000, 1000);
    }   
    
    public void desalocaProcesso(Processo p, int t){
        tempo.cancel();
        try{
            p.status = "Finalizado";
            p.desenho.setVisible(false);
            paneMem.getChildren().remove(p.desenho);
            p.desenho = null;
            p.instConclusao = t;
            p.tempoEspera = p.instConclusao - p.instCriado;
            tabProcessos.refresh();
            
            procFinalizados.add(p);
            procAlocados.remove(p);
            framesOcupados.remove(p.frame);
            framesLivres.add(p.frame);
            ordenaFrames();
            juntaRestos();
            memCPU -= p.porcentagem;
            atualizaCPU();
        }
        catch(Exception e){
            desalocaProcesso(p, t);
        }
        
        tempo = new Timer();
        tempo.scheduleAtFixedRate( new TimerTask(){
            @Override
            public void run(){
                rotina();
            }
        },1000, 1000);
    }
    
    public void firstFit(Processo p, int t){
        int i = 0, j = framesLivres.size();
        while(i < j){
            Frame frame = framesLivres.get(i);
            if(frame.tamanho > p.tamanho){
                p.tamFisico = (frame.tamanhoFisico*p.tamanho)/frame.tamanho;
                int indice = framesLivres.indexOf(frame);
                Frame alocado = new Frame(frame.posicaoInicio, frame.posicaoInicio+p.tamFisico, p.tamanho, true);
                Frame resto = new Frame(alocado.posicaoFim, frame.posicaoFim, frame.tamanho-p.tamanho, false);
                p.frame = alocado;
                framesOcupados.add(alocado);
                framesLivres.set(indice, resto);
                
                p.posicaoInicio = alocado.posicaoInicio;
                p.posicaoFim = alocado.posicaoFim;
                p.instAlocado = t;
                p.status = "Executando";
                tabProcessos.refresh();
                procAlocados.add(p);
                
                desenhaProcesso(p);
                memCPU += p.porcentagem;
                atualizaCPU();
                i = j;
            }
            else if(frame.tamanho == p.tamanho){
                framesOcupados.add(frame);
                framesLivres.remove(frame);
                p.posicaoInicio = frame.posicaoInicio;
                p.posicaoFim = frame.posicaoFim;
                p.instAlocado = t;
                p.status = "Executando";
                tabProcessos.refresh();
                procAlocados.add(p);
                
                desenhaProcesso(p);
                memCPU += p.porcentagem;
                atualizaCPU();
                i = j;
            }
            i++;
        }
    }
    
    public void bestFit(Processo p, int t){
        Frame best;
        int i = 0, j = framesLivres.size();
        while(i < j) {
            Frame f = framesLivres.get(i);
            if (f.tamanhoFisico >= p.tamFisico) {
                best = f;
                best = getMenorTam(best, p);
                if (best.tamanho > p.tamanho) {
                    p.tamFisico = (best.tamanhoFisico * p.tamanho) / best.tamanho;
                    int indice = framesLivres.indexOf(best);
                    Frame alocado = new Frame(best.posicaoInicio, best.posicaoInicio + p.tamFisico, p.tamanho, true);
                    Frame resto = new Frame(alocado.posicaoFim, best.posicaoFim, best.tamanho - p.tamanho, false);
                    p.frame = alocado;
                    framesOcupados.add(alocado);
                    framesLivres.set(indice, resto);

                    p.posicaoInicio = alocado.posicaoInicio;
                    p.posicaoFim = alocado.posicaoFim;
                    p.instAlocado = t;
                    p.status = "Executando";
                    tabProcessos.refresh();
                    procAlocados.add(p);

                    desenhaProcesso(p);
                    memCPU += p.porcentagem;
                    atualizaCPU();
                    i = j;
                    
                } else if (best.tamanho == p.tamanho) {
                    framesOcupados.add(best);
                    framesLivres.remove(best);
                    p.posicaoInicio = best.posicaoInicio;
                    p.posicaoFim = best.posicaoFim;
                    p.instAlocado = t;
                    p.status = "Executando";
                    tabProcessos.refresh();
                    procAlocados.add(p);

                    desenhaProcesso(p);
                    memCPU += p.porcentagem;
                    atualizaCPU();
                    i = j;
                }
                i = j;
            }
            i++;
        }
    }
    
    public void worstFit(Processo p, int t){
        Frame maior = null;
        boolean igual = false;
        int i = 0;
        int j = framesLivres.size();
        while(i < j){
            Frame f = framesLivres.get(i);
            if(f.tamanhoFisico >= p.tamFisico){
                maior = f;
                i = j;
            }
            i++;
        }
        if(maior != null){
            i = 0;
            j = framesLivres.size();
            while(i < j){
                Frame f = framesLivres.get(i);
                p.tamFisico = (f.tamanhoFisico * p.tamanho) / f.tamanho;
                if(f.tamanhoFisico > maior.tamanhoFisico && f.tamanhoFisico > p.tamFisico){
                    maior = f;
                }
                else if(f.tamanhoFisico >= maior.tamanhoFisico && f.tamanhoFisico == p.tamFisico){
                    maior = f;
                    igual = true;
                    i = j;
                }
                i++;
            }
            if(!igual){
                int indice = framesLivres.indexOf(maior);
                Frame alocado = new Frame(maior.posicaoInicio, maior.posicaoInicio+p.tamFisico, p.tamanho, true);
                Frame resto = new Frame(alocado.posicaoFim, maior.posicaoFim, maior.tamanho-p.tamanho, false);
                p.frame = alocado;
                framesOcupados.add(alocado);
                framesLivres.set(indice, resto);
                p.posicaoInicio = alocado.posicaoInicio;
                p.posicaoFim = alocado.posicaoFim;
                p.instAlocado = t;
                p.status = "Executando";
                tabProcessos.refresh();
                procAlocados.add(p);
                
                desenhaProcesso(p);
                memCPU += p.porcentagem;
                atualizaCPU();
            }
            else{
                framesOcupados.add(maior);
                framesLivres.remove(maior);
                p.posicaoInicio = maior.posicaoInicio;
                p.posicaoFim = maior.posicaoFim;
                p.instAlocado = t;
                p.status = "Executando";
                tabProcessos.refresh();
                procAlocados.add(p);
                
                desenhaProcesso(p);
                memCPU += p.porcentagem;
                atualizaCPU();
            }
        }   
    }
    
    public void gerarProcessos(){
        int anterior = 0;
        for(int i=0; i<qtdProc; i++){
            String status = "Fila";
            int tamanho = (int) (Math.random() * (m2-m1) + m1);
            int tc = (int) ((Math.random() * (tc2-tc1)) + tc1 ) + anterior;
            anterior = tc;
            int td = (int) (Math.random() * (td2-td1)) + td1;
            float porc = ( (float)tamanho/(float)memoria) * 100;
            int tamFisico = (631*tamanho)/memoria;
            Processo p = new Processo(i, tamanho, tc, td, tamFisico, porc, status);
            processos.add(p);
            logMsg(" ID: "+p.id+"  Criação: "+p.instCriado);
        }
    }
    
    public void rotina(){
        Platform.runLater(() -> {
            int instAtual = Integer.parseInt(instante.getText()) + 1;
            instante.setText(Integer.toString(instAtual));
            processos.forEach(p -> {
                if (p.instCriado <= instAtual) {
                    procCriados.add(p);
                    Platform.runLater(() -> {
                        processos.remove(p);
                    });
                }
            });
            procCriados.forEach(p -> {
                if (p.instAlocado <= instAtual && p.status.equals("Fila")) {
                    alocaProcesso(p, instAtual);
                    logMsg("Processo ID " + p.id + " alocado");
                }
            });
            procAlocados.forEach(p -> {
                if ( p.status.equals("Executando") && (p.tempoDuracao + p.instAlocado) <= instAtual) {
                    Platform.runLater(()->{
                        desalocaProcesso(p, instAtual);
                        logMsg("Processo ID " + p.id + " desalocado");
                    });
                }
            });
            if(procAlocados.isEmpty() && processos.isEmpty()){
                tempo.cancel();
                calculaMedia();
            }
        });
    }
    
    @FXML
    public int iniciar() {
        try{
            qtdProc = Integer.parseInt(qteProcessos.getText());
            memoria = Integer.parseInt(tamMem.getText());
            tam_SO = Integer.parseInt(tamSO.getText());
            m1 = Integer.parseInt(tamProc1.getText());
            m2 = Integer.parseInt(tamProc2.getText());
            tc1 = Integer.parseInt(tCriacao1.getText());
            tc2 = Integer.parseInt(tCriacao2.getText());
            td1 = Integer.parseInt(tDuracao1.getText());
            td2 = Integer.parseInt(tDuracao2.getText());
            RadioButton selected = (RadioButton) metodos.getSelectedToggle();
            metodo = selected.getText();
        }
        catch(Exception err){
            logMsg("Preencha todos os campos e apenas com números!");
            return -1;
        }

        if(tam_SO >= memoria){
            logMsg("SO deve ser menor que a memória");
            return -1;
        }else if(m2 > memoria){
            logMsg("M2 não pode ser maior que a memória");
            return -1;
        }else if(qtdProc > 30){
            logMsg("Máximo de processos é 30");
            return -1;
        }else if(m1 >= m2 || tc1 >= tc2 || td1 >= td2){
            logMsg("Valores inválidos. O 1º valor deve ser menor que o 2º valor");
            return -1;
        }else{
            txtLog.clear();
            media.setText("0");
            memCPU = 0;
            atualizaCPU();
            //ultimo = 0;
            processos.clear();
            procCriados.clear();
            procAlocados.clear();
            procFinalizados.clear();
            framesLivres.clear();
            framesOcupados.clear();
            framesLivres.add(new Frame(0,600, memoria, false));
            iniciarSimulacao.setDisable(true);
            logMsg(metodo);
            int tamFisico = (600 * tam_SO)/memoria;
            float porc = ( (float)tam_SO/(float)memoria) * 100;
            if(sistOp != null){
                sistOp.desenho.setVisible(false);
                paneMem.getChildren().remove(sistOp.desenho);
            }
            sistOp = new Processo(99, tam_SO, 0, 0, tamFisico, porc, "infinito");
            desenhaSO();
            memCPU += sistOp.porcentagem;
            atualizaCPU();
            gerarProcessos();
            instante.setText(Integer.toString(0));
            tempo = new Timer();
            tempo.scheduleAtFixedRate(new TimerTask(){
                @Override
                public void run(){
                    rotina();
                }
            },1000, 1000);
        }
        return 0;
    }
}
