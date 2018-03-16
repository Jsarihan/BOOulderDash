import java.awt.Graphics;

public class Dirt extends GameObj {
	
	public Dirt (int x, int y) {
		super(x,y);
		this.isDestroyable = true;
	}

	public void draw(Graphics g) {
		g.drawImage(GameCourt.SPRITE_SHEET.sprites[28], this.getPx() * GameCourt.getBlockSize(), this.getPy() * GameCourt.getBlockSize(), null);
	}

	@Override
	public void onImpact() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int destroy() {
		return 0;
	}
	
	@Override
	public String toString() {
		return ".";
	}
}
