/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alocacaodememoria;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author puckmanxy
 */
public class ProcessTableController implements Initializable {

    @FXML
    private TableView<?> tabProcessos;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colTamanho;

    @FXML
    private TableColumn<?, ?> colPorcent;

    @FXML
    private TableColumn<?, ?> colDuracao;

    @FXML
    private TableColumn<?, ?> colInstCriado;

    @FXML
    private TableColumn<?, ?> colInstAlocado;

    @FXML
    private TableColumn<?, ?> colInstConcluido;

    @FXML
    private TableColumn<?, ?> colTEspera;

    @FXML
    private TableColumn<?, ?> colStatus;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
}
