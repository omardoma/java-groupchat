package com.guc.csen503.view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CloseDialog extends WindowAdapter
{
	private JFrame frame;

	public CloseDialog(JFrame frame)
	{
		this.frame = frame;
	}

	public void windowClosing(WindowEvent e)
	{
		int reply = JOptionPane.showOptionDialog(frame, "Are you sure you want to quit?", "Exit Dialog",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (reply == JOptionPane.YES_OPTION)
			System.exit(0);
	}
}