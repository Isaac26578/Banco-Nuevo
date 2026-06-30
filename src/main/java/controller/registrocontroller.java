package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Banco;
import model.Singleton;

import java.net.URL;
import java.util.ResourceBundle;

public class registrocontroller implements Initializable {

    @FXML
    private PasswordField pswContraseña;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtIdentificacion;

    @FXML
    private TextField txtNombre;

    @FXML
    private ComboBox<String> cbx1;
    @FXML
    private Button btnRegistrarse;

    @FXML
    private Button btnVolverInicio;

    @FXML
    void Registarse(ActionEvent event) {
        try{

            banco.agregarUsuario(txtNombre.getText(), txtDireccion.getText(), txtIdentificacion.getText(), txtCorreo.getText(), pswContraseña.getText());

            banco.agregarCuentaAhorros(txtIdentificacion.getText(), 0F);
            crearAlerta("Usuario registrado correctamente", Alert.AlertType.INFORMATION);

            navegarVentana("/inicio.fxml");
            cerrarVentana(event);

        }catch (Exception e)
        {
            mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);

        }

    }

    @FXML
    void volverAInicio(ActionEvent event) {
        navegarVentana("/inicio.fxml");
        cerrarVentana(event);
    }

    // Me permite acceder a cualquier metodo del banco
    private final Banco banco = Banco.getInstancia();


    // Metodo para cerrar una ventana
    public void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }


    // Metodo para navegar una ventana
    public void navegarVentana(String nombreArchivoFxml) {
        try {

            // Cargar la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();

            // Crear la escena
            Scene scene = new Scene(root);

            // Crear un nuevo escenario (ventana)
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("");

            // Mostrar la nueva ventana
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    // Este metodo me inicializa listar documentos...
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbx1.setItems(FXCollections.observableList(banco.listarDocumento()));
    }

    // Metodo para mostrar una alerta
    private void mostrarAlerta(String mensaje, Alert.AlertType tipo){

        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();

    }

    // metodo para crear una alerta
    public void crearAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
