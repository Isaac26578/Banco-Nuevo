package model;

import model.Enums.CategoriaTransaccion;
import model.Enums.TipoTransaccion;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor // me genera el constructor
@Builder // patron de siseño builder
@Getter // me genera los getters
@Setter // me genera los setters

public class Transaccion {
    // clase transaccion

    private final TipoTransaccion tipo; // "Depósito" o "Retiro"
    private final float monto;
    private final Usuario usuario; // Usuario emisor o destinatario de la transacción
    private final LocalDateTime fecha;
    private final CategoriaTransaccion categoria;

}
