package com.guc.csen503.view.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.guc.csen503.view.NumberField;

@SuppressWarnings("serial")
public class ClientMainPanel extends JPanel
{
	private JPanel antiResize;
	private JPanel tmp;
	private JPanel grid1;
	private JPanel grid2;
	private JLabel clientName;
	private JLabel logo;
	private JLabel address;
	private JLabel port;
	private NumberField enterAddress;
	private NumberField enterPort;
	private JTextField enterName;
	private JButton join;
	
	public ClientMainPanel()
	{
		super(new BorderLayout());
		preparePanel();
	}
	
	public JPanel getTmp()
	{
		return tmp;
	}

	public JPanel getGrid1()
	{
		return grid1;
	}

	public JPanel getGrid2()
	{
		return grid2;
	}

	public JLabel getClientName()
	{
		return clientName;
	}

	public JTextField getEnterName()
	{
		return enterName;
	}

	public JPanel getAntiResize()
	{
		return antiResize;
	}

	public JLabel getLogo()
	{
		return logo;
	}

	public JLabel getAddress()
	{
		return address;
	}

	public JLabel getPort()
	{
		return port;
	}

	public NumberField getEnterAddress()
	{
		return enterAddress;
	}

	public NumberField getEnterPort()
	{
		return enterPort;
	}

	public JButton getJoin()
	{
		return join;
	}

	private void preparePanel()
	{
		logo = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("res/ClientMainPanel/logo.png")));
		address = new JLabel("Server Address:");
		address.setForeground(new Color(0x3598db));
		address.setFont(new Font("Ubuntu Condensed", Font.PLAIN, 20));
		port = new JLabel("Port Number:");
		port.setFont(new Font("Ubuntu Condensed", Font.PLAIN, 20));
		port.setForeground(new Color(0x3598db));
		clientName = new JLabel("Name:");
		clientName.setFont(new Font("Ubuntu Condensed", Font.PLAIN, 20));
		clientName.setForeground(new Color(0x3598db));
		enterAddress = new NumberField();
		enterAddress.setToolTipText("The server ip address.");
		enterAddress.setPreferredSize(new Dimension(180, 30));
		enterPort = new NumberField();
		enterPort.setAllowDot(false);
		enterPort.setPreferredSize(new Dimension(80, 30));
		enterName = new JTextField();
		enterName.setToolTipText("The name that you will be identified with during the chat session.");
		enterName.setPreferredSize(new Dimension(100, 30));
		enterName.setFont(new Font("Ubuntu Condensed", Font.PLAIN, 18));
		join = new JButton("Join");
		join.setToolTipText("Join the chat on specified server address.");
		join.setFocusable(false);
		join.setFont(new Font("Ubuntu Condensed", Font.PLAIN, 20));
		join.setForeground(Color.WHITE);
		join.setBorderPainted(false);
		join.setHorizontalTextPosition(JButton.CENTER);
		join.setPreferredSize(new Dimension(80, 30));
		join.setContentAreaFilled(false);
		join.setOpaque(false);
		join.setCursor(new Cursor(Cursor.HAND_CURSOR));
		join.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("res/Buttons/button.png")).getImage().getScaledInstance(80, 30, Image.SCALE_SMOOTH)));
		grid1 = new JPanel();
		grid1.setOpaque(false);
		grid1.add(address);
		grid1.add(enterAddress);
		antiResize = new JPanel();
		antiResize.setOpaque(false);
		antiResize.setPreferredSize(new Dimension(50, 0));
		grid1.add(antiResize);
		grid1.add(port);
		grid1.add(enterPort);
		grid2 = new JPanel();
		grid2.setOpaque(false);
		grid2.add(clientName);
		grid2.add(enterName);
		tmp = new JPanel(new GridLayout(2, 1));
		tmp.setOpaque(false);
		tmp.add(grid1);
		tmp.add(grid2);
		add(logo, BorderLayout.NORTH);
		add(tmp, BorderLayout.CENTER);
		antiResize = new JPanel();
		antiResize.setOpaque(false);
		antiResize.add(join);
		antiResize.setPreferredSize(new Dimension(0, 100));
		add(antiResize, BorderLayout.SOUTH);
	}
}