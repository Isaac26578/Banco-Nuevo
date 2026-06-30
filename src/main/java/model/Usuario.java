package model;
import lombok.*;    // Importamos la clase lombok para crear objetos sin necesidad de intanciarlos
@AllArgsConstructor  // me genera automaticamente el constructor
@Builder             // me genera el patron de diseño build
@Getter              // me genera los getters de esta clase
@Setter              // me genera los metodos setters de esta clase
@ToString

@EqualsAndHashCode(onlyExplicitlyIncluded = true) // encapsulamos los datos

public class Usuario {
    // este es la clase para el usuario
    @EqualsAndHashCode.Include
    // encapsulamos los datos
    private final String numeroId;
    private final String nombre;
    private final String direccion;
    private final String correoElectronico;
    private final String contrasena;



}
