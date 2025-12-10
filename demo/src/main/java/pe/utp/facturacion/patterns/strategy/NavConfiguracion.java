package pe.utp.facturacion.patterns.strategy;

import pe.utp.facturacion.service.PermisosNavegacion;

public class NavConfiguracion implements NavegacionStrategy {
    private PermisosNavegacion fachada;

    public NavConfiguracion() {
        this.fachada = PermisosNavegacion.getInstance();
    }

    @Override
    public void navegar() throws java.io.IOException {
        System.out.println("[PATRÓN STRATEGY] Ejecutando estrategia de navegación: NavConfiguracion");
        System.out.println("[GRASP: Polymorphism] Implementación polimórfica de NavegacionStrategy");
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
