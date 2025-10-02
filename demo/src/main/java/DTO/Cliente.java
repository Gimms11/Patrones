package DTO;

public class Cliente {
    private Long idCliente;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String correo;
    private Long idDocumento;        // FK → TipoDocumento (ej: 1 = DNI, 2 = RUC)
    private String numeroDocumento;  // Ej: "10456789012" o "20600000001"
    private String direccion;
    private Long idDistrito;         // FK → Distrito

    // Constructor vacío (requerido por JPA, frameworks, etc.)
    public Cliente(long l, String string, String string2, String string3, String string4, String string5, String string6, long m, long n) {}

    // Constructor con los campos esenciales
    public Cliente(String nombre, String apellidos, String numeroDocumento, String correo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numeroDocumento = numeroDocumento;
        this.correo = correo;
    }

    // Constructor completo
    public Cliente(Long idCliente, String nombre, String apellidos, String telefono,
                    String correo,String direccion, String numeroDocumento,
                    Long idDistrito,Long idDocumento) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.numeroDocumento = numeroDocumento;
        this.idDistrito = idDistrito;
        this.idDocumento = idDocumento;
    }

    // Getters
    public Long getIdCliente() { return idCliente; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public Long getIdDocumento() { return idDocumento; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public String getDireccion() { return direccion; }
    public Long getIdDistrito() { return idDistrito; }

    // Setters
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setIdDocumento(Long idDocumento) { this.idDocumento = idDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setIdDistrito(Long idDistrito) { this.idDistrito = idDistrito; }
}
