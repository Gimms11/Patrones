package pe.utp.facturacion.patterns.strategy;

import pe.utp.facturacion.service.PermisosNavegacion;

public class NavProductos implements NavegacionStrategy {
    private PermisosNavegacion fachada;

    public NavProductos() {
        this.fachada = PermisosNavegacion.getInstance();
    }

    @Override
    public void navegar() throws java.io.IOException {
        fachada.navegarA("GenProducts");
    }

    @Override
    public boolean tienePermiso() {
        return fachada.getInstance().tienePermiso("GenProducts");
    }

    @Override
    public String getNombrePantalla() {
        return "Generacion de Productos";
    }

}
