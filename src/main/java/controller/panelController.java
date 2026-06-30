package controller;

import controller.observador.Observador;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class panelController implements Initializable, Observador {

    @FXML
    private TableColumn<Transaccion,String>colCategoria;

    @FXML
    private TableColumn<Transaccion,String> colFecha;

    @FXML
    private TableColumn<Transaccion,String> colTipo;

    @FXML
    private TableColumn<Transaccion,String> colUsuario;

    @FXML
    private TableColumn<Transaccion,String> colValor;

    @FXML
    private TableView<Transaccion> tablaMovimientos;

    @FXML
    private Label txtNombre;

    @FXML
    void Cerrar(ActionEvent event) throws IOException {
        navegarVentana("/inicio.fxml");
        cerrarVentana(event);

    }

    @FXML
    void Consultar(ActionEvent event) {

    }

    @FXML
    void Trasnferir(ActionEvent event) throws Exception {
        irTransferencia();

    }

    // Instanciamos el singleton para poder acceder a cualquier metodo del banco

    private final Banco banco = Banco.getInstancia();
    private final Singleton sesion = Singleton.getInstancia();

    private CuentaAhorros cuenta;
    private String numeroCuentaOrigen;


    // este es el metodo sobreescrito para implementar el inicializable
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Usuario us = sesion.getUsuario();
        inicializarValoresCuenta(us);
        tablaMovimientos.setItems(FXCollections.observableArrayList(cuenta.getTransacciones()));
        inicializarValores();

        colTipo.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getTipo().toString()));
        colValor.setCellValueFactory(cellData -> new SimpleStringProperty( ""+cellData.getValue().getMonto()) );
        colUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuario().getNombre()));
        colFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha().toString()));
        colCategoria.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getCategoria().toString() ));


    }


    // metodo para obtener la cuenta de ahorros del usuario
    public void inicializarValoresCuenta (Usuario us ){

        // lanzamos una exepcion

        try {

            if(us != null){
                cuenta= banco.consultarCuentasUsario(us.getNumeroId());
                sesion.setCuenta(cuenta);
                System.out.println(cuenta.getTransacciones().get(0));
                tablaMovimientos.setItems(FXCollections.observableArrayList(cuenta.getTransacciones()));

            }

        } catch (Exception ex){

            mostrarAlerta("la tabla esta vacia", Alert.AlertType.INFORMATION);

        }

    }


    public void inicializarValores(){
        String resultadoConsulta;

        Usuario usuario = sesion.getUsuario();
        resultadoConsulta = banco.ConsultarNombre(usuario.getNumeroId(), usuario.getContrasena());
        //el txtNombre también se coloca en e label en el movimientosFxml
        txtNombre.setText(resultadoConsulta);
    }




    // Metodo para navegacion entre ventanas
    public FXMLLoader navegarVentana(String nombreArchivoFxml) throws IOException {

        // Cargar la vista
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
        Parent root = loader.load();

        // Crear la escena
        Scene scene = new Scene(root);

        // Crear un nuevo escenario (ventana)
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("panel");

        // Mostrar la nueva ventana
        stage.show();
        return loader;

    }


    // Metodo para cerrar una ventana
    public void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

    // metodo para capturar el loader
    public void irTransferencia () throws Exception{
        FXMLLoader loader = navegarVentana("/tranferir.fxml");
        trasnferircontroller controlador = loader.getController();
        controlador.inicializarValores(this);

    }


    // creamos una alerta
    private void mostrarAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

    private void consultarTransacciones (){
        tablaMovimientos.setItems(FXCollections.observableList(cuenta.getTransacciones()));
    }


    @Override
    public void notificar() {
        consultarTransacciones();
    }
}
