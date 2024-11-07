import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConversorApp {

    private final List<String> historial = new ArrayList<>();

    public void ejecutar() {
        Scanner scanner = new Scanner(System.in);
        ConsultaTasasCambio consultaTasasCambio = new ConsultaTasasCambio();
        boolean continuar = true;

        while (continuar) {
            mostrarMenu();
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    realizarConversion(consultaTasasCambio, "USD", "ARS");
                    break;
                case 2:
                    realizarConversion(consultaTasasCambio, "ARS", "USD");
                    break;
                case 3:
                    realizarConversion(consultaTasasCambio, "USD", "BRL");
                    break;
                case 4:
                    realizarConversion(consultaTasasCambio, "BRL", "USD");
                    break;
                case 5:
                    realizarConversion(consultaTasasCambio, "USD", "COP");
                    break;
                case 6:
                    realizarConversion(consultaTasasCambio, "COP", "USD");
                    break;
                case 7:
                    mostrarHistorial();
                    break;
                case 8:
                    continuar = false;
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }

        scanner.close();
    }

    private void mostrarMenu() {
        System.out.println("*********************************************");
        System.out.println("** Sea bienvenido/a al Conversor de Moneda =] **");
        System.out.println("*********************************************");
        System.out.println("1) Dólar => Peso argentino");
        System.out.println("2) Peso argentino => Dólar");
        System.out.println("3) Dólar => Real brasileño");
        System.out.println("4) Real brasileño => Dólar");
        System.out.println("5) Dólar => Peso colombiano");
        System.out.println("6) Peso colombiano => Dólar");
        System.out.println("7) Ver historial de conversiones");
        System.out.println("8) Salir");
        System.out.print("Elija una opción válida: ");
    }

    private void realizarConversion(ConsultaTasasCambio consulta, String monedaOrigen, String monedaDestino) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad en " + monedaOrigen + ": ");
        double cantidad = scanner.nextDouble();

        try {
            double tasaCambio = consulta.obtenerTasaCambio(monedaOrigen, monedaDestino);
            double resultado = cantidad * tasaCambio;
            String conversion = String.format("Convertido %.2f %s a %.2f %s", cantidad, monedaOrigen, resultado, monedaDestino);
            System.out.println(conversion);

            // Guardar la conversión en el historial
            historial.add(conversion);
        } catch (IOException | InterruptedException e) {
            System.out.println("Error al obtener la tasa de cambio: " + e.getMessage());
        }
    }

    private void mostrarHistorial() {
        System.out.println("Historial de conversiones:");
        if (historial.isEmpty()) {
            System.out.println("No hay conversiones en el historial.");
        } else {
            for (String conversion : historial) {
                System.out.println(conversion);
            }
        }
    }
}
