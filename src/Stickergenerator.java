import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.awt.font.TextLayout;
import java.awt.BasicStroke;

import javax.imageio.ImageIO;

public class Stickergenerator {

    public void createSticker(InputStream inputStream, String nameFile, String text) throws Exception{

        //leitura da imagem
        //InputStream inputStream = new FileInputStream(new File ("files/file.jpg"));
        // InputStream inputStream = 
        //     new URL("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies_1.jpg")
        //     .openStream();
        BufferedImage imageOriginal = ImageIO.read(inputStream);

        // criar nova imagem em memoória com transparência e com tamanho novo
        int width = imageOriginal.getWidth();
        int height = imageOriginal.getHeight();
        int newHeight = height + 200;
        BufferedImage newImage = new BufferedImage(width, newHeight, BufferedImage.TRANSLUCENT);
        
        //copiar a imagem original para nova imagem em memória
        Graphics2D graphics = (Graphics2D) newImage.getGraphics();
        graphics.drawImage(imageOriginal, 0, 0, null);

        //configurar a font dos memes
        var font = new Font("Impact", Font.BOLD, 100);
        graphics.setColor(Color.YELLOW);
        graphics.setFont(font);

        //escrever uma frase na nova imagem e centralizá-la
        FontMetrics metrics = graphics.getFontMetrics();
        Rectangle2D rectangle = metrics.getStringBounds(text, graphics);
        int widthText = (int) rectangle.getWidth();
        int positionTextX = (width - widthText) / 2;
        int positionTextY = (newHeight - 100);
        graphics.drawString(text, positionTextX, positionTextY);

        FontRenderContext contextFont = graphics.getFontRenderContext();
        var textLayout = new TextLayout(text, font, contextFont);

        Shape outline = textLayout.getOutline(null);
        AffineTransform transform = graphics.getTransform();
        transform.translate(positionTextX, positionTextY);
        graphics.setTransform(transform);

        var outlineStroke = new BasicStroke(width * 0.004f);
        graphics.setStroke(outlineStroke);

        graphics.setColor(Color.BLACK);
        graphics.draw(outline);
        graphics.setClip(outline);



        //escrever a nova imagem em um arquivo
        ImageIO.write(newImage, "png", new File(nameFile));
    }
   
}
