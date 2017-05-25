package com.java.russion;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyKeyAdapter extends KeyAdapter{
	Block bl;

	public MyKeyAdapter(Block bl) {
		this.bl = bl;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			bl.leftMove();
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			bl.rightMove();
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
			bl.fallMove();
		else if (e.getKeyCode() == KeyEvent.VK_SPACE)
			bl.leftTurn();
	}
}
