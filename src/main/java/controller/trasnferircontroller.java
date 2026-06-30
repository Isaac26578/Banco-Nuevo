package controller;

import controller.observador.Observador;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Banco;
import model.CuentaAhorros;
import model.Enums.CategoriaTransaccion;
import model.Singleton;
import model.Usuario;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class trasnferircontroller implements Initializable {


    @FXML
    private ComboBox<String> cbxCategoria;

    @FXML
    private TextField txtMontoTransferir;

    @FXML
    private TextField txtNumeroCuenta;


    // Implementamos el metodo inicializable
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbxCategoria.setItems( FXCollections.observableArrayList(banco.listarCategorias()));
    }

    @FXML
    void Tranferir(ActionEvent event) {
        try {

            List<CuentaAhorros> cuentas = new ArrayList<>();
            // tenemos el usuario
            Usuario usuario = singleton.getUsuario();
            cuentas= banco.consultarCuentasUsario(usuario.getNumeroId(),usuario.getContrasena());


            banco.realizarTransferencia(cuentas.get(0).getNumeroCuenta(), txtNumeroCuenta.getText(), Float.parseFloat(txtMontoTransferir.getText()), CategoriaTransaccion.valueOf(cbxCategoria.getValue()));
            observable.notificar();
            txtNumeroCuenta.clear();
            txtMontoTransferir.clear();
            cbxCategoria.setValue(null);


            mostrarAlerta("La transferencia se realizó correctamente", Alert.AlertType.INFORMATION);
            // metodo navegar ventana


        } catch (Exception e){

            mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);

        }



    }


    // patron de diseño singleton para acceder a cualquiera metodo del modelo banco
    private final Banco banco = Banco.getInstancia();

    private final Singleton singleton = Singleton.getInstancia();

    private Observador observable;
    // Metodo para mostrar una alerta
    private void mostrarAlerta(String mensaje, Alert.AlertType tipo){

        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();


    }

    public void inicializarValores(Observador observador){
        this.observable = observador;
    }



}
