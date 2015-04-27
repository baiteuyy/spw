

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Image;


public class GamePanel extends JPanel {
	
	private BufferedImage bi;
 	private Image imgBg;	
	Graphics2D big;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	public GamePanel() {
		bi = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		imgBg = Toolkit.getDefaultToolkit().getImage("bg.jpg");
		big.drawImage(imgBg, 0, 0, 400, 600,null);
	}

	public void updateGameUI(GameReporter reporter){
		big.clearRect(0, 0, 400, 600);
		
		big.setColor(Color.YELLOW);		
		big.drawString(String.format("%08d", reporter.getScore()), 250, 20);
		big.setFont(big.getFont().deriveFont(12.0f));	
		//EXTEND  DrawImage 
        big.drawImage(imgBg, 0, 0, 400, 600,null);
		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}
	
	public void startUI(GameReporter reporter){
		big.clearRect(0, 0, 400, 600);
		//big.drawImage(img,0, 0, 400, 600, null);
		big.setColor(Color.RED);
		big.setFont(big.getFont().deriveFont(16.0f));	
		big.drawString(String.format("Press \"Enter\" to start game"), 120, 300);
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}

}
