import java.awt.Graphics;

public class Exit extends GameObj {
	
	public Exit (int x, int y) {
		super(x,y);
	}

	public void draw(Graphics g) {
		g.drawImage(GameCourt.SPRITE_SHEET.sprites[37], this.getPx() * GameCourt.getBlockSize(), this.getPy() * GameCourt.getBlockSize(), null);
	}

	public int destroy() {
		return 100;
	}

	@Override
	public void onImpact() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return "X";
	}
}
