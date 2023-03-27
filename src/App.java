import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {


    public static void main(String[] args) throws Exception {
        
        // abrir uma conexão HTTP para consumir a API do IMDb (request);
        // para criar uma request é necessário gerar um builder e criar uma URI(identificação única do recurso);
        // guardar o resultado desta conexão em uma variável/string (body do response);
        // http protocolo para conexão na web;
        // pegar apenas as informações necessárias para minha aplicação: título, poster, classificação (parciar os dados);
        // exibir e manipular os dados na aplicação;

        //fazer uma conexão HTTP e buscar os top 250 filmes
        String urlApi = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI address = URI.create(urlApi);
        HttpClient client= HttpClient.newHttpClient();       
        HttpRequest request = HttpRequest.newBuilder(address).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        //extrair só os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaFilmes = parser.parse(body);
        
        //exibir dados
        for (Map<String,String> filme : listaFilmes) {
            
            System.out.println(filme.get( "fullTitle"));
            System.out.println(filme.get( "image"));
            System.out.println(filme.get( "imDbRating"));

        }

    }
}