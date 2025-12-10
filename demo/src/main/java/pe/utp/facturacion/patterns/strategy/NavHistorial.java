package pe.utp.facturacion.patterns.strategy;

import pe.utp.facturacion.service.PermisosNavegacion;

public class NavHistorial implements NavegacionStrategy {
    private PermisosNavegacion fachada;

    public NavHistorial() {
        this.fachada = PermisosNavegacion.getInstance();
    }

    @Override
    public void navegar() throws java.io.IOException {
        System.out.println("[PATRÓN STRATEGY] Ejecutando estrategia de navegación: NavHistorial");
        System.out.println("[GRASP: Polymorphism] Implementación polimórfica de NavegacionStrategy");
        fachada.navegarA("History");
    }

    @Override
    public boolean tienePermiso() {
        return fachada.getInstance().tienePermiso("History");
    }

    @Override
    public String getNombrePantalla() {
        return "Historial de Facturas";
    }

}
