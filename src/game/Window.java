package game;

import util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class Window {

	private JFrame frame;

	public Window() {
		this.frame = new JFrame();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);
		this.frame.setTitle("POF - 99Floors Remastered");
		this.frame.setVisible(true);

		Insets i = this.frame.getInsets();
		this.frame.setSize(Constants.GAME_WIDTH + i.left + i.right, Constants.GAME_HEIGHT + i.top + i.bottom);
	}

	public boolean isRunning() {
		return frame.isVisible();
	}

	public Graphics getGraphics() {
		return frame.getRootPane().getGraphics();
	}

	public void addKeyListener(KeyListener kl) {
		frame.addKeyListener(kl);
	}

	public void removeKeyListener(KeyListener kl) {
		frame.removeKeyListener(kl);
	}
}
