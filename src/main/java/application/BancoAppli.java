package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BancoAppli extends Application {
    // aplicacion para ejecutar todo el programa principal
    public static void main (String [] args){
        System.out.println("Hola mundo::: ");
        launch();

    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(BancoAppli.class.getResource("/inicio.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Banco Seguro-Sistema de Gestion");
        stage.setResizable(false);
        stage.show();
    }
}
