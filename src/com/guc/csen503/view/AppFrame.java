package com.guc.csen503.view;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.guc.csen503.view.client.ClientChatPanel;
import com.guc.csen503.view.client.ClientMainPanel;
import com.guc.csen503.view.server.ServerMainPanel;

@SuppressWarnings("serial")
public class AppFrame extends JFrame
{
	private AppMainPanel mainPanel;
	private ClientMainPanel clientMainPanel;
	private ClientChatPanel clientChatPanel;
	private ServerMainPanel serverMainPanel;
	private JPanel currentPanel;
	private Clip click;

	public AppFrame()
	{
		super("CSEN 503 Messenger");
		prepareFrame();
	}
	
	public ServerMainPanel getServerMainPanel()
	{
		return serverMainPanel;
	}

	public ClientChatPanel getClientChatPanel()
	{
		return clientChatPanel;
	}

	public void setClientChatPanel(ClientChatPanel clientChatPanel)
	{
		this.clientChatPanel = clientChatPanel;
	}

	public ClientMainPanel getClientMainPanel()
	{
		return clientMainPanel;
	}

	public AppMainPanel getMainPanel()
	{
		return mainPanel;
	}

	public Clip getClick()
	{
		return click;
	}

	public JPanel getCurrentPanel()
	{
		return currentPanel;
	}

	public void setCurrentPanel(JPanel currentPanel)
	{
		this.currentPanel = currentPanel;
	}

	private void prepareFrame()
	{
		getContentPane().setLayout(new CardLayout());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setMinimumSize(new Dimension(650, 650));
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource("res/ClientFrame/icon.png")).getImage());
		setLocationRelativeTo(null);
		setResizable(false);
		addWindowListener(new CloseDialog(this));
		mainPanel = new AppMainPanel();
		clientMainPanel = new ClientMainPanel();
		serverMainPanel = new ServerMainPanel();
		getContentPane().add(mainPanel, "AppMain");
		getContentPane().add(clientMainPanel, "ClientMain");
		getContentPane().add(serverMainPanel, "ServerMain");
		currentPanel = mainPanel;
	}
	
	public void click()
	{
		try
		{
			click = AudioSystem.getClip();
			click.open(AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("res/Sounds/click2.wav")));
			click.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}