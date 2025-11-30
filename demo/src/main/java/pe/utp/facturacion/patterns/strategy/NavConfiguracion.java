package pe.utp.facturacion.patterns.strategy;

import pe.utp.facturacion.service.PermisosNavegacion;

public class NavConfiguracion implements NavegacionStrategy {
    private PermisosNavegacion fachada;

    public NavConfiguracion() {
        this.fachada = PermisosNavegacion.getInstance();
    }

    @Override
    public void navegar() throws java.io.IOException {
        fachada.navegarA("Configurations");
    }

    @Override
    public boolean tienePermiso() {
        return fachada.getInstance().tienePermiso("Configurations");
    }

    @Override
    public String getNombrePantalla() {
        return "Configuración";
    }

}
