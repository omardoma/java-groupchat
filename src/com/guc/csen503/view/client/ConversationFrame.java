package com.guc.csen503.view.client;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.guc.csen503.view.CloseDialog;

@SuppressWarnings("serial")
public class ConversationFrame extends JFrame
{
	private String clientName;
	private ConversationPanel mainPanel;

	public ConversationFrame(String clientName)
	{
		super("Conversation");
		this.clientName = clientName;
		prepareFrame();
	}

	public String getClientName()
	{
		return clientName;
	}

	public ConversationPanel getMainPanel()
	{
		return mainPanel;
	}

	private void prepareFrame()
	{
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setMinimumSize(new Dimension(500, 500));
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource("res/ClientFrame/icon.png")).getImage());
		setLocationRelativeTo(null);
		addWindowListener(new CloseDialog(this));
		mainPanel = new ConversationPanel(clientName);
		add(mainPanel);
	}
}