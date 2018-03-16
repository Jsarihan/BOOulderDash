import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Diamond extends GameObj{

	private BufferedImage[] icons;
	public static final int ICON_COUNT = 8;
	private int lastIcon;
	
	public Diamond (int x, int y) {
		super(x,y);
		Set<List<Integer>> s = new TreeSet<LinkedList<Integer>>();
		List<Integer> lst = new LinkedList<Integer>();
		s.add(lst);
		this.isDestroyable = true;
		this.lastIcon = 0;
		this.icons = new BufferedImage[ICON_COUNT];
		for (int i = 0; i < ICON_COUNT/2 ; i++) {
			icons[i] = GameCourt.SPRITE_SHEET.sprites[32 + 8 * i];
			icons[i + 4] = GameCourt.SPRITE_SHEET.sprites[33 + 8 * i];
		}
	}

	public void draw(Graphics g) {
		lastIcon = (lastIcon + 1) % ICON_COUNT;
		g.drawImage(icons[lastIcon], this.getPx() * GameCourt.getBlockSize(), this.getPy() * GameCourt.getBlockSize(), null);
	}

	@Override
	public void onImpact() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int destroy() {
		return 10;
	}
	
	@Override
	public String toString() {
		return "d";
	}
}
