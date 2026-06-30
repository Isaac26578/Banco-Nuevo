package model;

import lombok.Getter;
import lombok.Setter;

public class Singleton {
    // esta sesion es la que se va a utilizar para crear el patron de diseño singleton:
    // creamos el singlenton
    public static Singleton INSTANCIA;


    @Getter
    @Setter

    // encapsulamos los datos

    private Usuario usuario;
    private CuentaAhorros cuenta;

    // constructor vacio y privado para evitar instancias en todo el codigo//

    public Singleton() {
    }

    // este es el metodo singleton para no crear mas intancias

    public static Singleton getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new Singleton();
        }
        return INSTANCIA;
    }


    // Metodo para cerrar la sesion
    public void cerrarSesion() {
        usuario = null;
        // variable nueva
        cuenta= null;
    }

    // Metodo para modificar la cuenta de ahorros:

    public void setCuenta(CuentaAhorros cuenta) {
        this.cuenta = cuenta;
    }

    //  metodo para obtener la cuenta de ahorros
    public CuentaAhorros getCuenta() {
        return cuenta;
    }





} // ultimo
