import java.awt.Graphics;

public class Wall extends GameObj {
	
	public Wall (int x, int y) {
		super(x,y);
	}

	public void draw(Graphics g) {
		g.drawImage(GameCourt.SPRITE_SHEET.sprites[24], this.getPx() * GameCourt.getBlockSize(), this.getPy() * GameCourt.getBlockSize(), null);
	}

	@Override
	public void onImpact() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int destroy() {
		return -2;
	}
	
	@Override
	public String toString() {
		return "W";
	}
}
