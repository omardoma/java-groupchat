package com.guc.csen503.controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import com.guc.csen503.exceptions.NameExistsException;
import com.guc.csen503.exceptions.ServerCapacityFullException;
import com.guc.csen503.exceptions.ServerExistsException;
import com.guc.csen503.exceptions.ServerUnreachableException;
import com.guc.csen503.model.client.Client;
import com.guc.csen503.model.client.ClientListener;
import com.guc.csen503.model.message.ChatMessage;
import com.guc.csen503.model.message.ChatMessageType;
import com.guc.csen503.model.server.Server;
import com.guc.csen503.view.AppFrame;
import com.guc.csen503.view.MemberButton;
import com.guc.csen503.view.client.ClientChatPanel;
import com.guc.csen503.view.client.ConversationFrame;

public class Controller implements ActionListener, ClientListener, KeyListener
{
	private Client client;
	private Server server;
	private AppFrame gui;
	private ArrayList<ConversationFrame> openConversations;

	public Controller()
	{
		openConversations = new ArrayList<ConversationFrame>();
		gui = new AppFrame();
		addAppMainPanelListeners();
		gui.setVisible(true);
	}

	public void keyPressed(KeyEvent e)
	{
		if (gui.getCurrentPanel() == gui.getClientMainPanel())
		{
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				gui.getClientMainPanel().getJoin().doClick();
				e.consume();
			}
		}
		else
			if (gui.getCurrentPanel() == gui.getClientChatPanel())
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					gui.getClientChatPanel().getSend().doClick();
					e.consume();
				}
			}
			else
				if (gui.getCurrentPanel() == gui.getServerMainPanel())
				{
					if (e.getKeyCode() == KeyEvent.VK_ENTER)
					{
						gui.getServerMainPanel().getHostServer().doClick();
						e.consume();
					}
				}
	}

	public void keyReleased(KeyEvent e)
	{

	}

	public void keyTyped(KeyEvent e)
	{

	}

	public void actionPerformed(ActionEvent e)
	{
		if (gui.getCurrentPanel() == gui.getClientChatPanel())
		{
			if (e.getSource() != gui.getClientChatPanel().getSend())
				gui.click();
		}
		else
			gui.click();

		if (gui.getCurrentPanel() == gui.getMainPanel())
		{
			if (e.getSource() == gui.getMainPanel().getClient())
			{
				addClientMainPanelListeners();
				gui.setCurrentPanel(gui.getClientMainPanel());
				SwingUtilities.invokeLater(new Runnable()
				{
					public void run()
					{
						CardLayout cl = (CardLayout) (gui.getContentPane().getLayout());
						cl.show(gui.getContentPane(), "ClientMain");
						gui.getClientMainPanel().getEnterAddress().requestFocusInWindow();
					}
				});
			}
			else
				if (e.getSource() == gui.getMainPanel().getServer())
				{
					addServerMainPanelListeners();
					gui.setCurrentPanel(gui.getServerMainPanel());
					SwingUtilities.invokeLater(new Runnable()
					{
						public void run()
						{
							CardLayout cl = (CardLayout) (gui.getContentPane().getLayout());
							cl.show(gui.getContentPane(), "ServerMain");
							gui.getServerMainPanel().getEnterPort().requestFocusInWindow();
						}
					});
				}
		}
		else
			if (gui.getCurrentPanel() == gui.getClientMainPanel())
			{
				if (e.getSource() == gui.getClientMainPanel().getJoin())
				{
					gui.getClientMainPanel().getJoin().setEnabled(false);
					if (gui.getClientMainPanel().getEnterAddress().getText().trim().isEmpty())
					{
						JOptionPane.showMessageDialog(gui, "Please enter a valid server address!", "Error",
								JOptionPane.ERROR_MESSAGE);
						gui.getClientMainPanel().getEnterAddress().requestFocusInWindow();
						gui.getClientMainPanel().getJoin().setEnabled(true);
						return;
					}
					else
						if (gui.getClientMainPanel().getEnterPort().getText().trim().isEmpty())
						{
							JOptionPane.showMessageDialog(gui, "Please enter a valid port number!", "Error",
									JOptionPane.ERROR_MESSAGE);
							gui.getClientMainPanel().getEnterPort().requestFocusInWindow();
							gui.getClientMainPanel().getJoin().setEnabled(true);
							return;
						}
						else
							if (gui.getClientMainPanel().getEnterName().getText().trim().isEmpty())
							{
								JOptionPane.showMessageDialog(gui, "Name cannot be blank!", "Error",
										JOptionPane.ERROR_MESSAGE);
								gui.getClientMainPanel().getEnterName().requestFocusInWindow();
								gui.getClientMainPanel().getJoin().setEnabled(true);
								return;
							}
							else
							{
								try
								{
									client = new Client(gui.getClientMainPanel().getEnterAddress().getText(),
											Integer.parseInt(gui.getClientMainPanel().getEnterPort().getText()));
									client.setListener(this);
									client.join(gui.getClientMainPanel().getEnterName().getText());
								}
								catch (ServerUnreachableException | NameExistsException
										| ServerCapacityFullException e1)
								{
									JOptionPane.showMessageDialog(gui, e1.getMessage(), "Error",
											JOptionPane.ERROR_MESSAGE);
									gui.getClientMainPanel().getJoin().setEnabled(true);
									return;
								}

								gui.setClientChatPanel(new ClientChatPanel(client));
								addClientChatPanelListeners();
								client.getMemberList();
								gui.setCurrentPanel(gui.getClientChatPanel());
								gui.getContentPane().add(gui.getClientChatPanel(), "Chat");
								SwingUtilities.invokeLater(new Runnable()
								{
									public void run()
									{
										CardLayout cl = (CardLayout) (gui.getContentPane().getLayout());
										cl.show(gui.getContentPane(), "Chat");
										gui.getClientChatPanel().getYourInput().requestFocusInWindow();
										gui.setResizable(true);
									}
								});
							}
				}
			}
			else
				if (gui.getCurrentPanel() == gui.getClientChatPanel())
				{
					if (e.getSource() == gui.getClientChatPanel().getSend())
					{
						if (!gui.getClientChatPanel().getYourInput().getText().trim().equals(""))
							client.broadcast(new ChatMessage(client.getName(),
									gui.getClientChatPanel().getYourInput().getText().trim(),
									ChatMessageType.BROADCAST));
					}
					else
						if (e.getSource() == gui.getClientChatPanel().getLeave())
							client.quit();
						else
						{
							newConversationWindow(((MemberButton) e.getSource()).getText());
						}
				}
				else
					if (gui.getCurrentPanel() == gui.getServerMainPanel())
					{
						if (e.getSource() == gui.getServerMainPanel().getHostServer())
						{
							try
							{
								gui.getServerMainPanel().getHostServer().setEnabled(false);
								server = new Server(
										Integer.parseInt(gui.getServerMainPanel().getEnterPort().getText()));
							}
							catch (ServerExistsException e1)
							{
								JOptionPane.showMessageDialog(gui, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
								gui.getServerMainPanel().getHostServer().setEnabled(true);
								return;
							}
							SwingUtilities.invokeLater(new Runnable()
							{
								public void run()
								{
									gui.getServerMainPanel().showServerInfo(
											server.getServerSocket().getInetAddress().getHostAddress(),
											server.getServerSocket().getLocalPort());
									gui.getServerMainPanel().getHostServer().setEnabled(true);
								}
							});
							new Thread(new Runnable()
							{
								public void run()
								{
									server.connect();
								}
							}).start();
						}
						else
							if (e.getSource() == gui.getServerMainPanel().getCloseServer())
							{
								server.stop();
								SwingUtilities.invokeLater(new Runnable()
								{
									public void run()
									{
										try
										{
											gui.getServerMainPanel().showServerInfo(
													InetAddress.getLocalHost().getHostName(),
													server.getServerSocket().getLocalPort());
										}
										catch (UnknownHostException e)
										{
											e.printStackTrace();
										}
									}
								});
							}
					}
	}

	public void onIncomingMessage(ChatMessage message)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				if (message.getType() == ChatMessageType.WELCOME
						|| message.getType() == ChatMessageType.LEAVING_NOTIFICATION)
				{
					client.getMemberList();
					showBroadcastNotification(message.getMessage());
				}
				else
					if (message.getType() == ChatMessageType.BROADCAST)
						showBroadcastMessage(message.getSenderName(), message.getMessage());
				scrollGroupChatToBottom();
			}
		});
	}

	public void onMemberListRefresh(ArrayList<String> members)
	{
		for (MemberButton b : gui.getClientChatPanel().getMembers())
			b.addActionListener(this);

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				gui.getClientChatPanel().updateMembers(members);
			}
		});
	}

	public void onLeavingTheRoom()
	{
		System.exit(0);
	}

	public void onServerDown()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				JOptionPane.showMessageDialog(gui, "Server has unexpectedly stopped, Messenger will close now!",
						"Error", JOptionPane.ERROR_MESSAGE);
				server = null;
				client = null;
				gui.dispose();
				gui = new AppFrame();
				addAppMainPanelListeners();
				gui.setVisible(true);
			}
		});
		addAppMainPanelListeners();
	}

	private void showBroadcastNotification(String msg)
	{
		gui.getClientChatPanel().getChatText()
				.append("\n(" + new SimpleDateFormat("h:mm a").format(new Date()) + ") " + msg + "\n\n");
	}

	private void showBroadcastMessage(String name, String msg)
	{
		gui.getClientChatPanel().getChatText()
				.append(name + " says (" + new SimpleDateFormat("h:mm a").format(new Date()) + "):\n\t" + msg + "\n");
		gui.getClientChatPanel().getYourInput().setText("");
	}

	private void scrollGroupChatToBottom()
	{
		gui.getClientChatPanel().getChatText()
				.setCaretPosition(gui.getClientChatPanel().getChatText().getDocument().getLength());
	}

	private void newConversationWindow(String name)
	{
		ConversationFrame cF = new ConversationFrame(name);
		cF.getMainPanel().getSend().addActionListener(this);
		cF.setVisible(true);
	}

	private void addClientMainPanelListeners()
	{
		gui.getClientMainPanel().getJoin().addActionListener(this);
		gui.getClientMainPanel().getEnterName().addKeyListener(this);
		gui.getClientMainPanel().getEnterPort().addKeyListener(this);
		gui.getClientMainPanel().getEnterAddress().addKeyListener(this);
		gui.getClientMainPanel().getJoin().addKeyListener(this);
		gui.getClientMainPanel().addKeyListener(this);
	}

	private void addClientChatPanelListeners()
	{
		gui.getClientChatPanel().getSend().addActionListener(this);
		gui.getClientChatPanel().getLeave().addActionListener(this);
		gui.getClientChatPanel().getYourInput().addKeyListener(this);
	}

	private void addAppMainPanelListeners()
	{
		gui.getMainPanel().getClient().addActionListener(this);
		gui.getMainPanel().getServer().addActionListener(this);
	}

	private void addServerMainPanelListeners()
	{
		gui.getServerMainPanel().getHostServer().addActionListener(this);
		gui.getServerMainPanel().getHostServer().addKeyListener(this);
		gui.getServerMainPanel().getCloseServer().addActionListener(this);
		gui.getServerMainPanel().getCloseServer().addKeyListener(this);
		gui.getServerMainPanel().getEnterPort().addKeyListener(this);
	}

	public static void main(String[] args)
	{
		try
		{
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
				if ("Nimbus".equals(info.getName()))
				{
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new Controller();
			}
		});
	}
}