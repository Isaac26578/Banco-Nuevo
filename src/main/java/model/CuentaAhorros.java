package model;


import lombok.Getter;
import lombok.ToString;
import model.Enums.CategoriaTransaccion;
import model.Enums.TipoTransaccion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString

public class CuentaAhorros {
    // clase de la cuenta de ahorros
    // encapsulamos los datos

    private final String numeroCuenta;
    private float saldo;
    private final List<Transaccion> transacciones;
    private final Usuario propietario;


    // metodo constructor

    public CuentaAhorros(String numeroCuenta, Usuario propietario, float saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.propietario = propietario;
        this.saldo = saldoInicial;
        this.transacciones = new ArrayList<>();
    }

    // metodo que realiza un deposito

    public void depositar(float cantidad, Usuario emisor, CategoriaTransaccion categoria) {
        // Se realiza el depósito
        saldo += cantidad;


        // Se crea la transacción de depósito
        Transaccion transaccion = Transaccion.builder()
                .tipo(TipoTransaccion.DEPOSITO)
                .monto(cantidad)
                .usuario(emisor)
                .fecha(LocalDateTime.now())
                .categoria(categoria)
                .build();

        // Se registra la transacción de depósito
        transacciones.add(transaccion);
    }


    // metodo que realiza el retiro de la cuenta de ahorros y registra la transaccion

    public void transferir(float cantidad, CuentaAhorros cuentaDestino, CategoriaTransaccion categoria) throws Exception {

        // Se cobra 200 por cada transferencia
        cantidad += 200;

        // Se valida que el saldo sea suficiente
        if (saldo >= cantidad) {

            // Se realiza el retiro
            saldo -= cantidad;

            // Se registra la transacción de depósito en la cuenta de destino
            cuentaDestino.depositar(cantidad, propietario, categoria);

            // Se crea la transacción de retiro
            Transaccion transaccion = Transaccion.builder()
                    .tipo(TipoTransaccion.RETIRO)
                    .monto(cantidad)
                    .usuario(cuentaDestino.getPropietario())
                    .fecha(LocalDateTime.now())
                    .categoria(categoria)
                    .build();

            // Se registra la transacción de retiro en la cuenta de origen
            transacciones.add(transaccion);

        } else {
            throw new Exception("Saldo insuficiente");
        }
    }

    // metodo que obtiene las transacciones de un mes o año especifico

    public List<Transaccion> obtenerTransaccionesPeriodo(int mes, int anio) {

        List<Transaccion> transaccionesMes = new ArrayList<>();

        for (int i = 0; i < transacciones.size(); i++) {
            if(transacciones.get(i).getFecha().getMonthValue() == mes && transacciones.get(i).getFecha().getYear() == anio){
                transaccionesMes.add(transacciones.get(i));
            }
        }
        return transaccionesMes;
    }

    // Metodo que obtiene las transacciones

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }



} // Ultimoooo
