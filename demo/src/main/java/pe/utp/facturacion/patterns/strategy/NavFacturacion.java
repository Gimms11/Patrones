package pe.utp.facturacion.patterns.strategy;

import pe.utp.facturacion.service.PermisosNavegacion;

public class NavFacturacion implements NavegacionStrategy {
    private PermisosNavegacion fachada;

    public NavFacturacion() {
        this.fachada = PermisosNavegacion.getInstance();
    }

    @Override
    public void navegar() throws java.io.IOException {
        System.out.println("[PATRÓN STRATEGY] Ejecutando estrategia de navegación: NavFacturacion");
        System.out.println("[GRASP: Polymorphism] Implementación polimórfica de NavegacionStrategy");
        fachada.navegarA("GenFactures");
    }

    @Override
    public boolean tienePermiso() {
        return fachada.getInstance().tienePermiso("GenFactures");
    }

    @Override
    public String getNombrePantalla() {
        return "Generacion de Facturas";
    }

}
