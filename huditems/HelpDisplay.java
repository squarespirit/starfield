package huditems;

import game.Renderable;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File; import java.io.IOException;

public class HelpDisplay implements Renderable
{
    public final BufferedImage HELP_IMAGE;
    
    public HelpDisplay () throws IOException {
        HELP_IMAGE = ImageIO.read(new File("img/help.png"));
    }
    
    public void render(Graphics2D g) 
    {
        g.drawImage(HELP_IMAGE, -HELP_IMAGE.getWidth() / 2,
                -HELP_IMAGE.getHeight() / 2, null);
    }
}
