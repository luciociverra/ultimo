package base.utility;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;

public class ImageGenerator {
    public static byte[] generateImageNotFoundBytes() throws Exception {
        // Create image with white background
        int width = 300;
        int height = 150;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        
        // Fill background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        
        // Draw black border
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawRect(0, 0, width - 1, height - 1);
        
        // Add red text
        g.setColor(Color.RED);
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 24);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        String text = "Nessuna immagine";
        
        // Calculate text positioning
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int x = (width - textWidth) / 2;
        int y = (height - textHeight) / 2 + fm.getAscent();
        
        g.drawString(text, x, y);
        g.dispose();
        
        // Convert to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", baos);
        return baos.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        byte[] imageData = generateImageNotFoundBytes();
        System.out.println("Generated " + imageData.length + " bytes");
    }
}