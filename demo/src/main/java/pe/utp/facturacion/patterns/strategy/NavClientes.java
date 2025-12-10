package pe.utp.facturacion.patterns.strategy;

import java.io.IOException;

import pe.utp.facturacion.service.PermisosNavegacion;

public class NavClientes implements NavegacionStrategy {

    private PermisosNavegacion fachada;

    public NavClientes() {
        this.fachada = PermisosNavegacion.getInstance();
    }

    @Override
    public void navegar() throws IOException {
        System.out.println("[PATRÓN STRATEGY] Ejecutando estrategia de navegación: NavClientes");
        System.out.println("[GRASP: Polymorphism] Implementación polimórfica de NavegacionStrategy");
        fachada.navegarA("GenClientes");
    }

    @Override
    public boolean tienePermiso() {
        return fachada.getInstance().tienePermiso("GenClientes");
    }

    @Override
    public String getNombrePantalla() {
        return "Gestion de Clientes";
    }
}
