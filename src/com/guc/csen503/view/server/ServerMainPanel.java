package com.guc.csen503.view.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.guc.csen503.view.IconButton;
import com.guc.csen503.view.NumberField;

@SuppressWarnings("serial")
public class ServerMainPanel extends JPanel
{
	private JPanel antiResize;
	private JPanel grid;
	private JLabel port;
	private JLabel logo;
	private JLabel serverInfo;
	private IconButton hostServer;
	private IconButton closeServer;
	private NumberField enterPort;
	
	public ServerMainPanel()
	{
		super(new BorderLayout(50, 50));
		preparePanel();
	}
	
	public JPanel getAntiResize()
	{
		return antiResize;
	}

	public JLabel getServerInfo()
	{
		return serverInfo;
	}

	public IconButton getCloseServer()
	{
		return closeServer;
	}

	public JPanel getGrid()
	{
		return grid;
	}

	public JLabel getPort()
	{
		return port;
	}

	public JLabel getLogo()
	{
		return logo;
	}

	public IconButton getHostServer()
	{
		return hostServer;
	}

	public NumberField getEnterPort()
	{
		return enterPort;
	}

	private void preparePanel()
	{
		logo = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("res/ServerMainPanel/logo.png")));
		port = new JLabel("Port Number:");
		port.setFont(new Font("Ubuntu Condensed", Font.PLAIN, 20));
		port.setForeground(new Color(0x3598db));
		enterPort = new NumberField();
		enterPort.setAllowDot(false);
		enterPort.setPreferredSize(new Dimension(80, 30));
		serverInfo = new JLabel();
		serverInfo.setFont(new Font("Ubuntu Condensed", Font.PLAIN, 20));
		serverInfo.setForeground(new Color(0x3598db));
		serverInfo.setVisible(false);
		serverInfo.setHorizontalAlignment(JLabel.CENTER);
		grid = new JPanel(new GridLayout(4, 1));
		grid.setOpaque(false);
		antiResize = new JPanel();
		antiResize.setOpaque(false);
		antiResize.add(port);
		antiResize.add(enterPort);
		grid.add(serverInfo);
		grid.add(antiResize);
		closeServer = new IconButton("Shutdown", "res/Buttons/button.png", 100, 30);
		closeServer.setPreferredSize(new Dimension(100, 30));
		closeServer.setVisible(false);
		closeServer.setFocusable(false);
		antiResize = new JPanel();
		antiResize.setOpaque(false);
		antiResize.add(closeServer);
		grid.add(antiResize);
		hostServer = new IconButton("Start", "res/Buttons/button.png", 80, 30);
		hostServer.setPreferredSize(new Dimension(80, 30));
		hostServer.setFocusable(false);
		antiResize = new JPanel();
		antiResize.setOpaque(false);
		antiResize.add(hostServer);
		grid.add(antiResize);
		add(logo, BorderLayout.NORTH);
		add(grid, BorderLayout.CENTER);
	}
	
	public void showServerInfo(String ip, int port)
	{
		boolean show = serverInfo.isVisible() ? false : true;
		serverInfo.setText("Server: "+ip+"       Port: "+port);
		serverInfo.setVisible(show);
		hostServer.setVisible(!show);
		closeServer.setVisible(show);
		enterPort.setVisible(!show);
		this.port.setVisible(!show);
	}
}