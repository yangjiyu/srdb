package com.java.russion;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class RightPanel extends JPanel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JButton[] jbt = new JButton[7];

    JButton[] jbt2 = new JButton[4];

    JButton jbt3;

    JTextField jtf;

    JLabel jlb;

    MyJPanel jp1, jp2;
 
    public RightPanel()
      {
        jbt[0] = new JButton("长条");
        jbt[1] = new JButton("方块");
        jbt[2] = new JButton("三角");
        jbt[3] = new JButton("左三");
        jbt[4] = new JButton("右三");
        jbt[5] = new JButton("左二");
        jbt[6] = new JButton("右二");
        jbt2[0] = new JButton("左移");
        jbt2[1] = new JButton("右移");
        jbt2[2] = new JButton("下移");
        jbt2[3] = new JButton("翻转");

        jbt3 = new JButton("开始");
        jtf = new JTextField("0", 5);
        jlb = new JLabel("得分", JLabel.CENTER);

        jp1 = new MyJPanel(); // 左边的上面板
        jp2 = new MyJPanel(); // 左边的下面板
        jp1.setLayout(new GridLayout(4, 2, 20, 10)); // 网格布局
        jp2.setLayout(new GridLayout(4, 2, 20, 10)); // 网格布局
        this.setLayout(new BorderLayout()); // 边界布局
        for (int i = 0; i < 7; i++)
            jp1.add(jbt[i]);

        jp1.add(jbt3);

        for (int i = 0; i < 4; i++)
            jp2.add(jbt2[i]);

        jp2.add(jlb);
        jp2.add(jtf);

        this.add(jp1, "North");
       this.add(jp2, "Center");
    }
}

