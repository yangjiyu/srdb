package com.java.russion;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class FinalElsBlock extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Block bl;

	LeftShowCanvas lsc;

	RightPanel rp;

	public FinalElsBlock() {
		super("ELSBlock Study");
		setBounds(130, 130, 500, 450);
		setLayout(new GridLayout(1, 2, 50, 30));
		rp = new RightPanel();
		lsc = new LeftShowCanvas(rp);
		bl = new Block(lsc);
		rp.setSize(80, 400);
		for (int i = 0; i < 7; i++)
			// 为每个按钮添加消息监听
			rp.jbt[i].addActionListener(new MyActionListener(rp, bl, lsc));
		rp.jbt3.addActionListener(new MyActionListener(rp, bl, lsc));
		for (int i = 0; i < 4; i++)
			rp.jbt2[i].addActionListener(new MyActionListener(rp, bl, lsc));
		lsc.addKeyListener(new MyKeyAdapter(bl));
		this.add(lsc);
		this.add(rp);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});
		setVisible(true);
	}

	public static void main(String[] args) {
		new FinalElsBlock();
	}
}
