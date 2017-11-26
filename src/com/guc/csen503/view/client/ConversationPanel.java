package com.guc.csen503.view.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import com.guc.csen503.view.IconButton;

@SuppressWarnings("serial")
public class ConversationPanel extends JPanel
{
	private JPanel antiResize;
	private JPanel tmp;
	private JPanel tmp2;
	private JTextArea chatText;
	private JTextArea yourInput;
	private IconButton leave;
	private IconButton send;
	private JScrollPane scrollConversation;
	private JLabel clientInfo;
	private String clientName;

	public ConversationPanel(String clientName)
	{
		super(new BorderLayout(5, 5));
		this.clientName = clientName;
		preparePanel();
	}

	public JPanel getTmp2()
	{
		return tmp2;
	}

	public JLabel getServerInfo()
	{
		return clientInfo;
	}

	public JPanel getAntiResize()
	{
		return antiResize;
	}

	public JPanel getTmp()
	{
		return tmp;
	}
	
	public JTextArea getChatText()
	{
		return chatText;
	}

	public JTextArea getYourInput()
	{
		return yourInput;
	}

	public IconButton getLeave()
	{
		return leave;
	}

	public IconButton getSend()
	{
		return send;
	}

	public JScrollPane getScrollConversation()
	{
		return scrollConversation;
	}

	private void preparePanel()
	{
		chatText = new JTextArea();
		chatText.setFont(new Font("Ubuntu Condensed", Font.PLAIN, 18));
		chatText.setEditable(false);
		chatText.setFocusable(false);
		chatText.setWrapStyleWord(true);
		chatText.setLineWrap(true);
		chatText.setForeground(new Color(0x3598db));
		scrollConversation = new JScrollPane(chatText);
		scrollConversation.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollConversation.setViewportBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Conversation",
				TitledBorder.CENTER, TitledBorder.CENTER, new Font("Ubuntu Condensed", Font.PLAIN, 16)));
		tmp = new JPanel(new BorderLayout());
		tmp.setOpaque(false);
		tmp.add(scrollConversation);
		add(tmp, BorderLayout.CENTER);
		send = new IconButton("Send", "res/Buttons/button.png", 80, 30);
		send.setPreferredSize(new Dimension(80, 30));
		send.setFocusable(false);
		yourInput = new JTextArea();
		yourInput.setFont(new Font("Ubuntu Condensed", Font.PLAIN, 18));
		yourInput.setLineWrap(true);
		yourInput.setWrapStyleWord(true);
		tmp = new JPanel(new BorderLayout());
		tmp.setOpaque(false);
		tmp.setPreferredSize(new Dimension(0, 130));
		antiResize = new JPanel(new FlowLayout(FlowLayout.LEFT));
		antiResize.setOpaque(false);
		antiResize.add(send);
		tmp2 = new JPanel(new BorderLayout());
		tmp2.setOpaque(false);
		tmp2.setPreferredSize(new Dimension(105, 0));
		tmp2.add(antiResize);
		tmp.add(yourInput, BorderLayout.CENTER);
		tmp.add(tmp2, BorderLayout.EAST);
		antiResize = new JPanel();
		antiResize.setOpaque(false);
		antiResize.setPreferredSize(new Dimension(10, 0));
		tmp.add(antiResize, BorderLayout.WEST);
		antiResize = new JPanel();
		antiResize.setOpaque(false);
		antiResize.setPreferredSize(new Dimension(0, 5));
		tmp.add(antiResize, BorderLayout.SOUTH);
		add(tmp, BorderLayout.SOUTH);
		tmp = new JPanel();
		tmp.setOpaque(false);
		tmp.setPreferredSize(new Dimension(5, 0));
		add(tmp, BorderLayout.WEST);
		tmp = new JPanel();
		tmp.setOpaque(false);
		tmp.setPreferredSize(new Dimension(5, 0));
		add(tmp, BorderLayout.EAST);
		clientInfo = new JLabel(clientName);
		clientInfo.setFont(new Font("Ubuntu Condensed", Font.PLAIN, 25));
		clientInfo.setHorizontalAlignment(JLabel.CENTER);
		add(clientInfo, BorderLayout.NORTH);
	}
}