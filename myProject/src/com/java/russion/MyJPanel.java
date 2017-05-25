package com.java.russion;

import java.awt.Insets;

import javax.swing.JPanel;

//重写MyPanel类，使Panel的四周留空间
public class MyJPanel extends JPanel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Insets getInsets()
    {
       return new Insets(10, 30, 30, 30);
    }
}
