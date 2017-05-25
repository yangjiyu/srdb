package com.java.russion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyActionListener implements ActionListener {
	RightPanel rp;
	Block bl;

	LeftShowCanvas lsc;

	public MyActionListener(RightPanel rp, Block bl, LeftShowCanvas lsc) {
		this.rp = rp;
		this.bl = bl;
		this.lsc = lsc;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(rp.jbt3)) {
			// 这样子则按几次开始按钮就创建几个相同的线程，控制着相同的数据
			Thread th = new Thread(bl);
			th.start();
		}
		for (int i = 0; i < Block.type; i++)
			if (e.getSource().equals(rp.jbt[i])) // 看是画哪个
			{
				bl.reInitRowCol();
				bl.drawBlock(i);
				lsc.requestFocusInWindow(); // 获得焦点
				return;
			}
		if (e.getSource().equals(rp.jbt2[0]))
			bl.leftMove();
		else if (e.getSource().equals(rp.jbt2[1]))
			bl.rightMove();
		else if (e.getSource().equals(rp.jbt2[2]))
			bl.fallMove();
		else if (e.getSource().equals(rp.jbt2[3]))
			bl.leftTurn();
		lsc.requestFocusInWindow(); // 获得焦点
	}
}
