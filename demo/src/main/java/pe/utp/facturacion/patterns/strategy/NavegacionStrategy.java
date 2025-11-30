package pe.utp.facturacion.patterns.strategy;

import java.io.IOException;

public interface NavegacionStrategy {
    void navegar() throws IOException;
    boolean tienePermiso();
    String getNombrePantalla();
}
