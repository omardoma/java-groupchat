package com.guc.csen503.view.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import com.guc.csen503.model.client.Client;
import com.guc.csen503.view.IconButton;
import com.guc.csen503.view.MemberButton;

@SuppressWarnings("serial")
public class ClientChatPanel extends JPanel
{
	private Client client;
	private JPanel antiResize;
	private JPanel tmp;
	private JPanel tmp2;
	private JPanel buttonsPanel;
	private JPanel membersGrid;
	private JTextArea chatText;
	private JTextArea yourInput;
	private IconButton leave;
	private IconButton send;
	private JScrollPane scrollConversation;
	private JScrollPane scrollMembers;
	private ArrayList<MemberButton> members;

	public ClientChatPanel(Client client)
	{
		super(new BorderLayout(5, 5));
		this.client = client;
		members = new ArrayList<MemberButton>();
		preparePanel();
	}

	public ArrayList<MemberButton> getMembers()
	{
		return members;
	}

	public JPanel getMembersGrid()
	{
		return membersGrid;
	}

	public Client getClient()
	{
		return client;
	}

	public JPanel getTmp2()
	{
		return tmp2;
	}

	public JPanel getAntiResize()
	{
		return antiResize;
	}

	public JPanel getTmp()
	{
		return tmp;
	}

	public JPanel getButtonsPanel()
	{
		return buttonsPanel;
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

	public JScrollPane getScrollMembers()
	{
		return scrollMembers;
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
		scrollConversation.setViewportBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),
				"Group Conversation", TitledBorder.CENTER, TitledBorder.CENTER,
				new Font("Ubuntu Condensed", Font.PLAIN, 16), new Color(0x3598db)));
		tmp = new JPanel(new BorderLayout());
		tmp.setOpaque(false);
		tmp.add(scrollConversation);
		add(tmp, BorderLayout.CENTER);
		membersGrid = new JPanel(new GridLayout(20, 1));
		membersGrid.setFont(new Font("Ubuntu Condensed", Font.PLAIN, 12));
		membersGrid.setForeground(new Color(0x3598db));
		scrollMembers = new JScrollPane(membersGrid);
		scrollMembers.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollMembers.setViewportBorder(
				BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Members", TitledBorder.CENTER,
						TitledBorder.CENTER, new Font("Ubuntu Condensed", Font.PLAIN, 16), new Color(0x3598db)));
		tmp = new JPanel(new BorderLayout());
		tmp.setOpaque(false);
		tmp.add(scrollMembers);
		tmp2 = new JPanel(new BorderLayout());
		tmp2.setOpaque(false);
		tmp2.add(tmp, BorderLayout.CENTER);
		leave = new IconButton("Leave Chat", "res/Buttons/button.png", 120, 30);
		leave.setPreferredSize(new Dimension(120, 30));
		leave.setFocusable(false);
		antiResize = new JPanel();
		antiResize.setOpaque(false);
		antiResize.add(leave);
		tmp.add(antiResize, BorderLayout.SOUTH);
		antiResize = new JPanel(new BorderLayout());
		antiResize.setOpaque(false);
		antiResize.setPreferredSize(new Dimension(160, 0));
		antiResize.add(tmp, BorderLayout.CENTER);
		tmp2 = new JPanel();
		tmp2.setOpaque(false);
		tmp2.setPreferredSize(new Dimension(5, 0));
		antiResize.add(tmp2, BorderLayout.EAST);
		add(antiResize, BorderLayout.EAST);
		send = new IconButton("Send", "res/Buttons/button.png", 80, 30);
		send.setPreferredSize(new Dimension(80, 30));
		send.setFocusable(false);
		yourInput = new JTextArea();
		yourInput.setFont(new Font("Ubuntu Condensed", Font.PLAIN, 18));
		yourInput.setLineWrap(true);
		yourInput.setWrapStyleWord(true);
		antiResize = new JPanel(new FlowLayout(FlowLayout.LEFT));
		antiResize.setOpaque(false);
		antiResize.add(send);
		tmp2 = new JPanel(new BorderLayout());
		tmp2.setOpaque(false);
		tmp2.setPreferredSize(new Dimension(165, 0));
		tmp2.add(antiResize);
		tmp = new JPanel(new BorderLayout());
		tmp.setOpaque(false);
		tmp.setPreferredSize(new Dimension(0, 130));
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
		add(tmp, BorderLayout.NORTH);
	}

	public void updateMembers(ArrayList<String> members)
	{
		this.members.clear();
		membersGrid.removeAll();
		if (members.size() == 0)
		{
			JLabel label = new JLabel("Empty Room");
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setFont(new Font("Ubuntu Condensed", Font.PLAIN, 18));
			membersGrid.add(label);
		}
		else
			for (String s : members)
			{
				MemberButton button = new MemberButton(s);
				button.setPreferredSize(new Dimension(120, 30));
				this.members.add(button);
				membersGrid.add(button);
			}
		repaint();
		revalidate();
	}
}