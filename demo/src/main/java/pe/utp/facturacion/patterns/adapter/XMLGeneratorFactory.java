package pe.utp.facturacion.patterns.adapter;

/**
 * Factory Method Pattern - Crea instancias de XMLGenerator
 * según el tipo de comprobante
 */
public class XMLGeneratorFactory {

    /**
     * Obtiene el generador XML apropiado según el tipo de comprobante
     * 
     * @param idTipoComprobante ID del tipo de comprobante
     *                          1 = Factura
     *                          2 = Boleta
     * @return XMLGenerator apropiado para el tipo
     * @throws IllegalArgumentException si el tipo no es soportado
     */
    public static XMLGenerator get(long idTipoComprobante) {
        System.out.println(
                "[PATRÓN FACTORY METHOD] XMLGeneratorFactory - Creando generador XML según tipo de comprobante");
        System.out.println("[GRASP: Creator] XMLGeneratorFactory tiene la responsabilidad de crear XMLGenerators");
        System.out.println(
                "[GRASP: Protected Variations] Factory protege contra cambios en las implementaciones de XMLGenerator");

        switch ((int) idTipoComprobante) {
            case 1:
                System.out.println("[FACTORY METHOD] Creando FacturaXMLGenerator (tipo=1: Factura)");
                return new FacturaXMLGenerator();
            case 2:
                System.out.println("[FACTORY METHOD] Creando BoletaXMLGenerator (tipo=2: Boleta)");
                return new BoletaXMLGenerator();
            default:
                throw new IllegalArgumentException(
                        "Tipo de comprobante no soportado: " + idTipoComprobante);
        }
    }
}
