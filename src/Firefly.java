import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Firefly extends Enemy {

	private Direction forward;
	private BufferedImage[] icons;
	public static final int ICON_COUNT = 4;
	private int lastIcon;
	
	public Firefly(int x, int y, int dmg) {
		super(x, y, dmg);
		this.forward = Direction.UP;
		this.lastIcon = 0;
		this.icons = new BufferedImage[ICON_COUNT];
		for (int i = 0; i < ICON_COUNT ; i++) {
			icons[i] = GameCourt.SPRITE_SHEET.sprites[37 + 8 * i];
		}
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
		Direction left =  Direction.CCW(forward);
		if (this.isAlive()) {
			if (b.objTypeSide(x, y, left) instanceof Empty) {
				b.move(x,y,this, left);
				this.forward = left;
			} else if ((b.objTypeSide(x, y, forward) instanceof Empty)) {
				b.move(x,y, this, forward);
			} else {
				this.forward = Direction.CW(forward);
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		lastIcon = (lastIcon +1 ) % ICON_COUNT;
		g.drawImage(icons[lastIcon], this.getPx() * GameCourt.getBlockSize(), this.getPy() * GameCourt.getBlockSize(), null);
		
	}

	@Override
	public void onImpact() {
		this.setDead();
	}

	@Override
	public int destroy() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		return "q";
	}
}
