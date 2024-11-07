import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaTasasCambio {

    private static final String API_KEY = "40d0bae2e4e21ea5b94adeaf";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    // Método para obtener la tasa de cambio entre dos monedas
    public double obtenerTasaCambio(String monedaOrigen, String monedaDestino) throws IOException, InterruptedException {
        // Construir la URL con la moneda base
        String url = BASE_URL + monedaOrigen;

        // Crear instancia de HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Crear una solicitud HTTP GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Enviar la solicitud y recibir la respuesta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Verificar si la solicitud fue exitosa
        if (response.statusCode() == 200) {
            // Parsear la respuesta JSON para obtener la tasa de cambio
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            // Verificar si el resultado fue exitoso
            String result = jsonObject.get("result").getAsString();
            if (!"perfecto".equals(result)) {
                throw new RuntimeException("Error en la solicitud de tasa de cambio: " + result);
            }

            // Obtener la tasa de cambio del JSON
            JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");
            return conversionRates.get(monedaDestino).getAsDouble();
        } else {
            throw new RuntimeException("Error al obtener la tasa de cambio: Código de estado " + response.statusCode());
        }
    }
}
