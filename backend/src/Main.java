import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static final CarreraDAO dao = new CarreraDAO();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // API: lista de escuelas
        server.createContext("/api/escuelas", ex -> {
            try {
                List<Escuela> escuelas = dao.listarEscuelas();
                String json = escuelas.stream()
                    .map(e -> "{\"id\":" + e.id() + ",\"nombre\":\"" + esc(e.nombre()) + "\"}")
                    .collect(Collectors.joining(",", "[", "]"));
                enviar(ex, 200, "application/json; charset=utf-8", json.getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                error(ex, e);
            }
        });

        // API: carreras de una escuela  →  /api/carreras?escuela=1
        server.createContext("/api/carreras", ex -> {
            try {
                String query = ex.getRequestURI().getQuery(); // "escuela=1"
                if (query == null || !query.startsWith("escuela=")) {
                    enviar(ex, 400, "text/plain; charset=utf-8", "Falta el parámetro escuela".getBytes(StandardCharsets.UTF_8));
                    return;
                }
                int idEscuela = Integer.parseInt(query.substring("escuela=".length()));
                List<Carrera> carreras = dao.listarPorEscuela(idEscuela);
                String json = carreras.stream()
                    .map(c -> "{\"id\":" + c.id() + ",\"nombre\":\"" + esc(c.nombre())
                            + "\",\"grado\":\"" + esc(c.grado()) + "\"}")
                    .collect(Collectors.joining(",", "[", "]"));
                enviar(ex, 200, "application/json; charset=utf-8", json.getBytes(StandardCharsets.UTF_8));
            } catch (NumberFormatException e) {
                enviar(ex, 400, "text/plain; charset=utf-8", "Escuela inválida".getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                error(ex, e);
            }
        });

        // Archivos estáticos del front (carpeta /frontend)
        server.createContext("/", Main::servirEstatico);

        server.start();
        System.out.println("Servidor listo en http://localhost:8080");
    }

    private static void servirEstatico(HttpExchange ex) throws IOException {
        String ruta = ex.getRequestURI().getPath();
        if (ruta.equals("/")) ruta = "/index.html";

        Path base = Paths.get("frontend").toAbsolutePath().normalize();
        Path archivo = base.resolve(ruta.substring(1)).normalize();

        // Evita que alguien salga de la carpeta frontend (../)
        if (!archivo.startsWith(base) || !Files.isRegularFile(archivo)) {
            enviar(ex, 404, "text/plain; charset=utf-8", "No encontrado".getBytes(StandardCharsets.UTF_8));
            return;
        }
        String nombre = archivo.getFileName().toString();
        String tipo = nombre.endsWith(".html") ? "text/html; charset=utf-8"
                    : nombre.endsWith(".css")  ? "text/css; charset=utf-8"
                    : nombre.endsWith(".js")   ? "application/javascript; charset=utf-8"
                    : "application/octet-stream";
        enviar(ex, 200, tipo, Files.readAllBytes(archivo));
    }

    private static void enviar(HttpExchange ex, int codigo, String tipo, byte[] cuerpo) throws IOException {
        ex.getResponseHeaders().set("Content-Type", tipo);
        ex.sendResponseHeaders(codigo, cuerpo.length);
        try (var os = ex.getResponseBody()) { os.write(cuerpo); }
    }

    private static void error(HttpExchange ex, Exception e) throws IOException {
        e.printStackTrace();
        enviar(ex, 500, "text/plain; charset=utf-8",
               "Error en el servidor".getBytes(StandardCharsets.UTF_8));
    }

    // Escapa comillas y barras para que el JSON sea válido
    private static String esc(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}