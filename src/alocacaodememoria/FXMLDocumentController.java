
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class FXMLDocumentController implements Initializable {
    
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
    
    ToggleGroup grupoMetodos = new ToggleGroup();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bestFit.setToggleGroup(grupoMetodos);
        firstFit.setToggleGroup(grupoMetodos);
        worstFit.setToggleGroup(grupoMetodos);
    }
    
    @FXML
    void iniciar() {

    }
}
