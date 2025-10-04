package Service;

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
     */
    public static void calcularTotales(Comprobante comprobante, List<DetalleComprobante> detalles) {
        BigDecimal subtotalGeneral = BigDecimal.ZERO;
        BigDecimal totalImpuestos = BigDecimal.ZERO;
        BigDecimal totalFinal = BigDecimal.ZERO;

        for (DetalleComprobante det : detalles) {
            // 1️⃣ Calcular subtotal por producto
            BigDecimal subtotal = det.getPrecioUnitario().multiply(BigDecimal.valueOf(det.getCantidadProductos()));
            det.setSubtotal(subtotal);

            // 2️⃣ Calcular monto del impuesto
            // Si el impuesto es nulo (no aplica), se toma 0
            BigDecimal montoImpuesto = det.getMontoImpuesto() != null ? det.getMontoImpuesto() : BigDecimal.ZERO;
            det.setMontoImpuesto(montoImpuesto);

            // 3️⃣ Total por línea = subtotal + impuesto
            BigDecimal total = subtotal.add(montoImpuesto);
            det.setTotal(total);

            // 4️⃣ Sumar al total general
            subtotalGeneral = subtotalGeneral.add(subtotal);
            totalImpuestos = totalImpuestos.add(montoImpuesto);
            totalFinal = totalFinal.add(total);
        }

        // ✅ Actualizar comprobante
        comprobante.setDevengado(subtotalGeneral); // suma sin impuestos
        comprobante.setTotalFinal(totalFinal);     // suma total con impuestos
    }
}
