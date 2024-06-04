package main;

import javax.swing.JFrame;

public class GameWindow {
	private JFrame jframe;
	
	public GameWindow(GamePanel gamePanel) {
		jframe = new JFrame();
		int width = 400;
		int height = 400;
		
		jframe.setSize(width, height);
		jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
		jframe.add(gamePanel);
		
		jframe.setVisible(true);
	}
}
