
import java.awt.Color;
import java.awt.Graphics2D;

// add import
import java.awt.Toolkit;
import java.awt.Image;


public class SpaceShip extends Sprite{

	int step = 8;
	
	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		
	}

	@Override
	public void draw(Graphics2D g) {
		
		Image img = Toolkit.getDefaultToolkit().getImage("sps.png");
        g.drawImage(img, x, y, 50, 70, null);
		
	}

	public void move(int direction_x, int direction_y){
		x += (step * direction_x);
		if(x < 0)
			x = 0;
		if(x > 400 - width)
			x = 400 - width;
		
		y += (step * direction_y);
		if(y < 0)
			y = 0;
		if(y > 600 - height)
			y = 600 - height;
	}

}
