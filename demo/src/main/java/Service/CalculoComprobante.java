package service;

import java.math.BigDecimal;
import java.util.List;
import DTO.DetalleComprobante;
import DTO.Comprobante;

public class CalculoComprobante {

    /**
     * Calcula el subtotal, total de impuestos, total final y devengado del comprobante.
     * 
     * @param comprobante Objeto donde se almacenan los resultados
     * @param detalles Lista de DetalleComprobante asociados
     * 
     */
    public static void calcularTotales(Comprobante comprobante, List<DetalleComprobante> detalles) {
        BigDecimal subtotalGeneral = BigDecimal.ZERO;
        BigDecimal totalImpuestos = BigDecimal.ZERO;
        BigDecimal totalFinal = BigDecimal.ZERO;

        for (DetalleComprobante det : detalles) {
            // 1️⃣ Calcular subtotal por producto
            BigDecimal subtotal = det.getPrecioUnitario().multiply(BigDecimal.valueOf(det.getCantidadProductos()));
            det.setSubtotal(subtotal);
        }

        // ✅ Actualizar comprobante
        comprobante.setDevengado(subtotalGeneral); // suma sin impuestos
        comprobante.setTotalFinal(totalFinal);     // suma total con impuestos
    }

}
