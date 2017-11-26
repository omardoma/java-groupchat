package com.guc.csen503.view;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NumberField extends JTextField
{
	private boolean allowDot;
	
	public NumberField()
	{
		super();
		allowDot=true;
		setFont(new Font("Ubuntu Condensed", Font.PLAIN, 18));
		addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) || (c == '.' && allowDot)))
					e.consume();
			}
		});
	}

	public void setAllowDot(boolean allowDot)
	{
		this.allowDot = allowDot;
	}
}