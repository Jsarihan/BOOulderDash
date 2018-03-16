import java.awt.Graphics;

public class InternalWall extends Wall {

	public InternalWall(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	public void draw(Graphics g) {
		g.drawImage(GameCourt.SPRITE_SHEET.sprites[26], this.getPx() * GameCourt.getBlockSize(), this.getPy() * GameCourt.getBlockSize(), null);
	}

	@Override
	public String toString() {
		return "w";
	}
}
