package strategy;

public class NavConfiguracion implements NavegacionStrategy {
    private service.PermisosNavegacion fachada;

    public NavConfiguracion() {
        this.fachada = service.PermisosNavegacion.getInstance();
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
