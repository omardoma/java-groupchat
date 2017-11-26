package com.guc.csen503.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class MemberButton extends JButton
{
	public MemberButton(String text)
	{
		super(text);
		setFocusable(false);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setToolTipText("Press to have a private chat with this member!");
		setFont(new Font("Ubuntu Condensed", Font.BOLD, 18));
		//setForeground(new Color(0x3598db));
	}
}