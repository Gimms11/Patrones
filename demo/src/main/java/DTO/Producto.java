package DTO;

import java.math.BigDecimal;

public class Producto {
    private Long idProducto;
    private String nombre;
    private BigDecimal precio;
    private Integer stock;
    private String descripcion;
    private long idCategoria;
    private String unidadMedida;       // Ej: "UND", "KG", "LTR"
    private Long idTipoAfectacion;     // FK → AfectacionProductos (ej: 1 = Gravado, 2 = Exonerado, etc )

    // Constructor vacío
    public Producto() {}

    // Constructor con campos esenciales
    public Producto(String nombre, BigDecimal precio, String unidadMedida) {
        this.nombre = nombre;
        this.precio = precio;
        this.unidadMedida = unidadMedida;
    }

    // Constructor completo
    public Producto(Long idProducto, String nombre, BigDecimal precio, Integer stock,
                    String descripcion,String unidadMedida, Long idTipoAfectacion,long idCategoria) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.descripcion = descripcion;
        this.idCategoria = idCategoria;
        this.unidadMedida = unidadMedida;
        this.idTipoAfectacion = idTipoAfectacion;
    }

    // Getters
    public Long getIdProducto() { return idProducto; }
    public String getNombre() { return nombre; }
    public BigDecimal getPrecio() { return precio; }
    public Integer getStock() { return stock; }
    public String getDescripcion() { return descripcion; }
    public long getIdCategoria() { return idCategoria; }
    public String getUnidadMedida() { return unidadMedida; }
    public Long getIdTipoAfectacion() { return idTipoAfectacion; }

    // Setters
    public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public void setStock(Integer stock) { this.stock = stock; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setIdCategoria(long idCategoria) { this.idCategoria = idCategoria; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }
    public void setIdTipoAfectacion(Long idTipoAfectacion) { this.idTipoAfectacion = idTipoAfectacion; }
}