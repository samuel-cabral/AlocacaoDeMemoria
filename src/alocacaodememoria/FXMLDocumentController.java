
package alocacaodememoria;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
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
import javafx.stage.Stage;


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
    
    ToggleGroup grupoMetodos = new ToggleGroup();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bestFit.setToggleGroup(grupoMetodos);
        firstFit.setToggleGroup(grupoMetodos);
        worstFit.setToggleGroup(grupoMetodos);
    }
    
    @FXML
    void iniciar() throws IOException {
        render_process_table();
        disable_ui_controllers();
    }
    
    void render_process_table() throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("ProcessTable.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Tabela de processos");
        stage.setResizable(false);
        stage.setScene(new Scene(root, 684, 539));
        stage.show();

    }
    
    void disable_ui_controllers() {
        iniciarSimulacao.setDisable(true);
        tamMem.setDisable(true);
        tamSO.setDisable(true);
        tamProc1.setDisable(true);
        tamProc2.setDisable(true);
        tCriacao1.setDisable(true);
        tCriacao2.setDisable(true);
        tDuracao1.setDisable(true);
        tDuracao2.setDisable(true);
        qteProcessos.setDisable(true);
    }
}
