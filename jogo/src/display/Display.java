package jogo.src.display;

import java.awt.Canvas;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;
import java.awt.event.KeyListener;

public class Display {

	private Canvas canvas;
	private JFrame jframe;

	public Display(String title, int width, int height) {

		jframe = new JFrame(title);
		canvas = new Canvas();

		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setFocusable(false);
		jframe.add(canvas);
		jframe.pack();
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setResizable(false);
		jframe.setLocationRelativeTo(null);
		jframe.setVisible(true);
	}

	public BufferStrategy getBufferedStrategy() {
		return canvas.getBufferStrategy();
	}

	public void createBufferStrategy() {
		canvas.createBufferStrategy(1);
	}

	public void setKeyListener(KeyListener kl) {
		jframe.addKeyListener(kl);
	}

}
