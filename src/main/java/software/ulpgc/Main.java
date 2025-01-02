package software.ulpgc;

import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        commands.put("divisors", new DivisorsCommand());
        Spark.port(8080);
        Spark.get("/", (request, response) -> {
            response.type("text/html");
            return "<html>" +
                    "<head><title>API divisors</title></head>"+
                    "<body>" +
                    "<h1>Bienvenido a la API de divisores</h1>" +
                    "<p>Para usar la funcionalidad de divisores, accede a:</p>" +
                    "<pre>http://localhost:8080/divisors/:number</pre>" +
                    "<p>Reemplaza <code>:number</code> con el número deseado (rango de 1 a 50).</p>" +
                    "</body>" +
                    "</html>";
        });

        Spark.get("/divisors/:number", ((request, response) -> new CommandExecutor(request,response).execute("divisors")));

    }

    static Map<String,Command> commands = new HashMap<>();

    public static class CommandExecutor {
        private final Request request;
        private final Response response;

        public CommandExecutor(Request request, Response response) {
            this.request = request;
            this.response = response;
        }

        public String execute(String name) {
            Command command = commands.get(name);
            Command.Output execute = command.execute(input());
            response.status(execute.responseCode());
            return execute.result();

        }

        private Command.Input input() {
            return parameter -> oneOf(request.params(parameter),request.queryParams(parameter));
        }

        private String oneOf(String params, String s) {
            return params != null ? params : s;
        }
    }
}
