package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Banco;
import model.Singleton;
import model.Usuario;

public class inicioController {
    // este va hacer el controlador de inicio.fxml
    @FXML
    private AnchorPane Anp1;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Label lbl1;

    @FXML
    private Label lbl2;

    @FXML
    private Label lbl3;

    @FXML
    private Label lbl4;

    @FXML
    private Label lbl41;

    @FXML
    private Label lbl411;

    @FXML
    private TextField txfUsuario;

    @FXML
    private PasswordField txtPassword;


    // Me permite acceder a cualquier metodo del banco
    private final Banco banco = Banco.getInstancia();

    // Este boton me permite iniciar sesion con el suario
    @FXML
    void IniciarSesion(ActionEvent event) throws Exception {
        try{
            Usuario usuario;
            String nombre, contrasena;
            nombre= txfUsuario.getText();
            contrasena = txtPassword.getText();
            usuario= banco.validarUsuario(nombre, contrasena);

            Singleton sesion = Singleton.getInstancia();
            sesion.setUsuario(usuario);

            if (usuario.getNumeroId().equals(nombre)){

                // si el usuario existe dejelo entrar a la ventana principal
                navegarVentana("/panel.fxml");
                cerrarVentana();

            }


        } catch (Exception E){
            mostrarAlerta(E.getMessage(),Alert.AlertType.ERROR);
        }

    }

    //Este boton me lleva a una nueva ventana donde el usuario perimita registrarse
    @FXML
    void Registrarse(ActionEvent event) {
        try{

            navegarVentana("/registro.fxml");
            cerrarVentana();

        }catch (Exception e) {
            mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }

    }



    // Metodo para Navegar una ventana:

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


    // metodo para mostrar un mensaje
    private void mostrarAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }


    // metodo para cerrar una ventana
    public void cerrarVentana(){
        Stage stage = (Stage) txfUsuario.getScene().getWindow();
        stage.close();
    }



} // Ultimo
