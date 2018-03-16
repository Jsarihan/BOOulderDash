import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteLoader {

	private BufferedImage spriteSheet;

	private int width;
	private int height;
	public static final int rowNum = 8;
	public static final int colNum = 8;
	public final BufferedImage[] sprites = new BufferedImage[rowNum * colNum];

	public SpriteLoader(boolean isHD) {
		try {
			if (isHD) {
				this.width = 128;
				this.height= 128;
				spriteSheet = ImageIO.read(new File("sprites.png"));
			} else {
				this.width = 64;
				this.height= 64;
				spriteSheet = ImageIO.read(new File("smallSprites.png"));
			}		
		} catch (IOException e) {
			System.out.println("Could not load sprites");
		} finally {
			for (int i = 0; i < rowNum; i++) {
			    for (int j = 0; j < colNum; j++) {
			        sprites[(i * colNum) + j] = spriteSheet.getSubimage(
			            j * width, i * height, width, height);
			    }
			}
		}
	}
}
