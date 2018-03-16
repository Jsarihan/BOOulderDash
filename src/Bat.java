import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Bat extends Enemy {

	private Direction forward;
	private BufferedImage[] icons;
	public static final int ICON_COUNT = 4;
	private int lastIcon;
	
	public Bat (int x, int y, int dmg) {
		super(x,y, dmg);
		this.lastIcon = 0;
		this.forward = Direction.UP;
		this.icons = new BufferedImage[ICON_COUNT];
		for (int i = 0; i < ICON_COUNT ; i++) {
			icons[i] = GameCourt.SPRITE_SHEET.sprites[38 + 8 * i];
		}
	}

	public void draw(Graphics g) {
		lastIcon = (lastIcon +1 ) % ICON_COUNT;
		g.drawImage(icons[lastIcon], this.getPx() * GameCourt.getBlockSize(), this.getPy() * GameCourt.getBlockSize(), null);
	}

	@Override
	public GameObj onDeath() {
		Diamond d = new Diamond(this.getPx(), this.getPy());
		return d;
	}
	
	@Override
	public void move(Board b) {
		int x = this.getPx();
		int y = this.getPy();
		Direction right =  Direction.CW(forward);
		if (this.isAlive()) {
			if (b.objTypeSide(x, y, right) instanceof Empty) {
				b.move(x, y, this, right);
				this.forward = right;
			} else if ((b.objTypeSide(x, y, forward) instanceof Empty)) {
				b.move(x, y, this, forward);
			} else {
				this.forward = Direction.CCW(forward);
			}
		}
	}

	@Override
	public void onImpact() {
		this.setDead();
		
	}

	@Override
	public int destroy() {
		return 5;
	}
	
	@Override
	public String toString() {
		return "B";
	}
}
