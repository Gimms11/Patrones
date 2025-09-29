package DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Comprobante {
    private Long idComprobante;
    private LocalDate fechaEmision;
    private String serie;
    private BigDecimal totalFinal;      // Total con impuestos (ya calculado)
    private BigDecimal devengado;       // Subtotal sin impuestos (ya calculado)
    private String direccionEnvio;
    private Long idTipoComprobante;     // FK → TipoComprobante
    private Long idMedioPago;           // FK → MedioPago
    private Long idCliente;             // FK → Cliente
    private Long idUsuario;             // FK → Usuario

    // Constructor vacío (requerido por frameworks como JPA)
    public Comprobante() {}

    // Constructor completo (útil para pruebas o creación manual)
    public Comprobante(Long idComprobante, LocalDate fechaEmision, String serie,
                       BigDecimal devengado, BigDecimal totalFinal, String direccionEnvio,
                       Long idTipoComprobante, Long idMedioPago, Long idCliente, Long idUsuario) {
        this.idComprobante = idComprobante;
        this.fechaEmision = fechaEmision;
        this.serie = serie; 
        this.devengado = devengado;
        this.totalFinal = totalFinal;
        this.direccionEnvio = direccionEnvio;
        this.idTipoComprobante = idTipoComprobante;
        this.idMedioPago = idMedioPago;
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
    }

    // Getters
    public Long getIdComprobante() { return idComprobante; }
    public LocalDate getFechaEmision() { return fechaEmision; }
    public String getSerie() { return serie; } 
    public BigDecimal getTotalFinal() { return totalFinal; }
    public BigDecimal getDevengado() { return devengado; }
    public String getDireccionEnvio() { return direccionEnvio; }
    public Long getIdTipoComprobante() { return idTipoComprobante; }
    public Long getIdMedioPago() { return idMedioPago; }
    public Long getIdCliente() { return idCliente; }
    public Long getIdUsuario() { return idUsuario; }

    // Setters
    public void setIdComprobante(Long idComprobante) { this.idComprobante = idComprobante; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }
    public void setSerie(String serie) { this.serie = serie; } 
    public void setTotalFinal(BigDecimal totalFinal) { this.totalFinal = totalFinal; }
    public void setDevengado(BigDecimal devengado) { this.devengado = devengado; }
    public void setDireccionEnvio(String direccionEnvio) { this.direccionEnvio = direccionEnvio; }
    public void setIdTipoComprobante(Long idTipoComprobante) { this.idTipoComprobante = idTipoComprobante; }
    public void setIdMedioPago(Long idMedioPago) { this.idMedioPago = idMedioPago; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
}
