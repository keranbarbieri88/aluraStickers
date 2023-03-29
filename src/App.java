
import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;



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
        List<Map<String, String>> listaMovies = parser.parse(body);
        
        
        //exibir dados
        for (Map<String,String> movie : listaMovies) {           
            
            System.out.println("\u001b[1mTítulo:\u001b[m " + movie.get("fullTitle"));
            System.out.println("\u001b[1mImagem:\u001b[m " + movie.get("image"));
            System.out.println("\u001b[1mBanner URL:\u001b[m " + movie.get("image"));
            System.out.println("\u001b[1mClassificação:\u001b[m " + movie.get("imDbRating"));
            
            double rating = Double.parseDouble(movie.get("imDbRating"));
            int starNumber = (int) rating;
            for(int n = 1; n <= starNumber; n++){
                System.out.print("🌟");
            }
            if (rating > 9) {
                System.out.print("  Mais bem avaliado \uD83D\uDC4F");
            }
            System.out.println("\n");

            // Imprime a imagem
            String capaUrl = movie.get("image");
            if (capaUrl != null && !capaUrl.isEmpty()) {
                System.out.println("Imagem da capa:");
                URI capaUri = URI.create(capaUrl);
                BufferedImage img = ImageIO.read(capaUri.toURL());
                System.out.println(img);
            }
        }

    }
}