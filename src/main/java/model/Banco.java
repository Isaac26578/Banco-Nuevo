package model;

import model.Enums.CategoriaTransaccion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Banco {
    // patron de diseño singlenton
    public static Banco INSTANCIA;
    private final ArrayList<Usuario> usuarios;
    private final ArrayList<CuentaAhorros> cuentasAhorros;

    // Creamos el contructor vacio  he inicializamos los usuarios
    private Banco() {
        usuarios = new ArrayList<>();
        cuentasAhorros = new ArrayList<>();
        llenarDatosPrueba();
    }

    // Patron de diseño singleton
    public static Banco getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new Banco();
        }
        return INSTANCIA;
    }

    // Llenar datos del usuario.
    public void llenarDatosPrueba() {
        try {

            agregarUsuario("Isaac", "Calle 80", "123", "isaac@email.com", "123");
            agregarUsuario("Pablo", "Calle 77", "456", "pablo@email.com", "456");

            agregarCuentaAhorros("123", 1000);
            agregarCuentaAhorros("456", 2000);


            System.out.println(cuentasAhorros);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void agregarUsuario(String nombre, String direccion, String numeroIdentificacion, String correoElectronico, String contrasena) throws Exception{
        // validamos que el numero de identicacion se ingrese correctamente
        if (numeroIdentificacion == null || numeroIdentificacion.isBlank()){
            throw new Exception("El numero de identificacion es obligatorio: ");
        }

        // validamos que el usuario ya exista y mandarle un mensaje de error.
        if (obtenerUsuario(numeroIdentificacion) != null){
            throw new Exception("Ya existe un usuario con ese mismo numero de identificacion: "+numeroIdentificacion);

        }

        // Verifica que el nombre no este en blanco
        if (nombre == null || nombre.isBlank()){
            throw new Exception("El Nombre es obligario:: ");
        }

        // Verificamos que la direccion no este en blanco
        if (direccion == null || direccion.isBlank()){
            throw new Exception("Es obligatorio la direccion: ");
        }

        // Verificamos la contraseña
        if (contrasena == null || contrasena.isBlank()){
            throw new Exception("La contraseña es obligatoria");
        }

        // verificamos el correco electronico
        if (correoElectronico == null || correoElectronico.isBlank()){
            throw new Exception("El correco electronico es obligatario");
        }

        // Creamos el usuario con builder
        Usuario usuario = Usuario.builder()
                .nombre(nombre)
                .direccion(direccion)
                .numeroId(numeroIdentificacion)
                .correoElectronico(correoElectronico)
                .contrasena(contrasena)
                .build();


        // Agregamos este usuario ya creado a una lista de usuarios
        usuarios.add(usuario);

    }


    /**
     * Método que agrega una cuenta de ahorros a un usuario
     *
     * @param numeroIdentificacion número de identificación del usuario
     * @param numeroIdentificacion número de identificación del usuario
     * @param saldoInicial         saldo inicial de la cuenta
     * @return número de cuenta
     * @throws Exception si no se encuentra el usuario
     */
    public String agregarCuentaAhorros(String numeroIdentificacion, float saldoInicial) throws Exception {
        Usuario propietario = obtenerUsuario(numeroIdentificacion);

        if (propietario != null) {
            String numeroCuenta = crearNumeroCuenta();
            CuentaAhorros cuentaAhorros = new CuentaAhorros(numeroCuenta, propietario, saldoInicial);
            cuentasAhorros.add(cuentaAhorros);

            return numeroCuenta;
        }

        throw new Exception("No se encontró el usuario con el número de identificación: " + numeroIdentificacion);

    }


    /**
     * Método que crea un número de cuenta aleatorio y verifica que no exista en el sistema para evitar duplicados
     *
     * @return número de cuenta
     */
    private String crearNumeroCuenta() {

        String numeroCuenta = generarNumeroCuenta();

        while (obtenerCuentaAhorros(numeroCuenta) != null) {
            numeroCuenta = generarNumeroCuenta();
        }

        return numeroCuenta;
    }


    /**
     * Método que genera un número de cuenta aleatorio de 10 dígitos
     *
     * @return número de cuenta
     */
    private String generarNumeroCuenta() {
        StringBuilder numeroCuenta = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int numero = new Random().nextInt(10);
            numeroCuenta.append(numero);
        }

        return numeroCuenta.toString();
    }



    // Metodo que actualiza un usuario


    /**
     * Método que obtiene un usuario de acuerdo a su número de identificación
     *
     * @param numeroIdentificacion número de identificación del usuario
     * @return usuario o null si no existe
     */
    public Usuario obtenerUsuario(String numeroIdentificacion) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNumeroId().equals(numeroIdentificacion)) {
                return usuarios.get(i);
            }
        }
        return null;
    }


    /**
     * Método que obtiene una cuenta de ahorros de acuerdo a su número de cuenta
     *
     * @param numeroCuenta número de cuenta
     * @return cuenta de ahorros o null si no existe
     */
    public CuentaAhorros obtenerCuentaAhorros(String numeroCuenta) {
        for (int i = 0; i < cuentasAhorros.size(); i++) {
            if (cuentasAhorros.get(i).getNumeroCuenta().equals(numeroCuenta)) {
                return cuentasAhorros.get(i);
            }
        }
        return null;
    }

    /**
     * Método que realiza la validación de un usuario de acuerdo a su número de identificación y contraseña para el acceso al sistema
     *
     * @param numeroIdentificacion número de identificación del usuario
     * @param contrasena           contraseña del usuario
     * @return usuario
     * @throws Exception si los datos de acceso son incorrectos
     */
    public Usuario validarUsuario(String numeroIdentificacion, String contrasena) throws Exception {
        Usuario usuario = obtenerUsuario(numeroIdentificacion);
        if (usuario != null) {
            if (usuario.getContrasena().equals(contrasena)) {
                return usuario;
            }
        }
        throw new Exception("Los datos de acceso son incorrectos");
    }

    // metodo para inicializar las categorias
    public ArrayList<String> listarCategorias() {
        ArrayList<String> categorias = new ArrayList<>();
        categorias.add("VIAJES");
        categorias.add("FACTURAS");
        categorias.add("GASOLINA");
        categorias.add("ROPA");
        categorias.add("PAGO");
        categorias.add("OTROS");

        return categorias;

    }

    // metodo para inicializar listar el tipo de documento
    public ArrayList<String>listarDocumento (){
        ArrayList<String> categorias = new ArrayList<>();
        categorias.add("CC");
        categorias.add("TI");
        categorias.add("EXTRANJERIA");

        return categorias;
    }


    // Metodo para consultar el nombre
    public String ConsultarNombre(String identificacion, String contrasena){
        String resultadoConsulta = null;
        for(int i = 0; i < cuentasAhorros.size(); i++){
            if(cuentasAhorros.get(i).getPropietario().getNumeroId().equals(identificacion)){
                for(Usuario usuarios : usuarios){
                    resultadoConsulta ="Hola "+ usuarios.getNombre()+" su número de cuenta es "+cuentasAhorros.get(i).getNumeroCuenta();
                }
            }
        }

        return resultadoConsulta;
    }


    // Consultar las cuentas del usuario
    /**
     * Método que consulta el saldo de las cuentas de ahorros de un usuario
     *
     * @param identificacion número de identificación del usuario
     * @param contrasena     contraseña del usuario
     * @return lista de cuentas de ahorros
     * @throws Exception si los datos de acceso son incorrectos
     */
    public List<CuentaAhorros> consultarCuentasUsario(String identificacion, String contrasena) throws Exception {

        Usuario usuario = validarUsuario(identificacion, contrasena);

        if (usuario != null) {
            List<CuentaAhorros> cuentas = new ArrayList<>();

            for (int i = 0; i < cuentasAhorros.size(); i++) {
                if (cuentasAhorros.get(i).getPropietario().getNumeroId().equals(identificacion)) {
                    cuentas.add(cuentasAhorros.get(i));
                }
            }

            return cuentas;
        }

        return null;
    }


    // Metodo para validar un usuario 2
    // metodo nuevo
    // validar usuario 2
    public Usuario validarUsuario2 (String numeroIdentificacion)throws Exception {
        Usuario usuario = obtenerUsuario(numeroIdentificacion);
        if (usuario != null) {
            if (usuario.getNumeroId().equals(numeroIdentificacion)) {
                return usuario;
            }
        }
        throw new Exception("Los datos de acceso son incorrectos");
    }


    /**
     * Método que realiza una transferencia entre cuentas de ahorros
     *
     * @param numeroCuentaOrigen  número de cuenta de origen
     * @param numeroCuentaDestino número de cuenta de destino
     * @param monto               monto a transferir
     * @param categoria           categoría de la transacción
     * @throws Exception si los números de cuenta no existen
     */
    public void realizarTransferencia(String numeroCuentaOrigen, String numeroCuentaDestino, float monto, CategoriaTransaccion categoria) throws Exception {
        CuentaAhorros cuentaOrigen = obtenerCuentaAhorros(numeroCuentaOrigen);
        CuentaAhorros cuentaDestino = obtenerCuentaAhorros(numeroCuentaDestino);

        if (cuentaOrigen != null && cuentaDestino != null) {
            cuentaOrigen.transferir(monto, cuentaDestino, categoria);


        } else {
            throw new Exception("Error con los números de cuenta");
        }
    }


    public CuentaAhorros consultarCuentasUsario(String identificacion) throws Exception {

        Usuario usuario = validarUsuario2(identificacion);

        if (usuario != null) {
            for (int i = 0; i < cuentasAhorros.size(); i++) {
                if (cuentasAhorros.get(i).getPropietario().getNumeroId().equals(identificacion)) {
                    return cuentasAhorros.get(i);
                }
            }

        }
        return null;
    }


}
