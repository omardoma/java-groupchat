package com.guc.csen503.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AppMainPanel extends JPanel
{
	private JPanel tmp;
	private JPanel grid;
	private JLabel logo;
	private IconButton server;
	private IconButton client;
	
	public AppMainPanel()
	{
		super(new BorderLayout(50, 50));
		preparePanel();
	}
	
	public JPanel getTmp()
	{
		return tmp;
	}

	public JPanel getGrid()
	{
		return grid;
	}

	public JLabel getLogo()
	{
		return logo;
	}

	public IconButton getServer()
	{
		return server;
	}

	public IconButton getClient()
	{
		return client;
	}

	private void preparePanel()
	{
		logo = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("res/AppMainPanel/logo.png")));
		server = new IconButton("Server", "res/AppMainPanel/server.png", 200, 200);
		server.setBorderPainted(true);
		server.setOpaque(true);
		server.setFocusable(false);
		server.setContentAreaFilled(true);
		server.setBorder(BorderFactory.createLineBorder(new Color(0x3598db), 3, true));
		server.setVerticalTextPosition(JButton.TOP);
		server.setForeground(new Color(0x3598db));
		server.setFont(new Font("Ubuntu Condensed", Font.PLAIN, 22));
		client = new IconButton("Client", "res/AppMainPanel/client.png", 180, 180);
		client.setBorderPainted(true);
		client.setContentAreaFilled(true);
		client.setOpaque(true);
		client.setFocusable(false);
		client.setBorder(BorderFactory.createLineBorder(new Color(0x3598db), 3, true));
		client.setPreferredSize(new Dimension(250, 250));
		client.setVerticalTextPosition(JButton.TOP);
		client.setForeground(new Color(0x3598db));
		client.setFont(new Font("Ubuntu Condensed", Font.PLAIN, 22));
		grid = new JPanel(new GridLayout(1, 2, 60, 60));
		grid.setOpaque(false);
		grid.add(client);
		grid.add(server);
		tmp = new JPanel();
		tmp.setOpaque(false);
		tmp.add(grid);
		add(logo, BorderLayout.NORTH);
		add(tmp, BorderLayout.CENTER);
	}
}