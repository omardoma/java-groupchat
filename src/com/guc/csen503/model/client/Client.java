package com.guc.csen503.model.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import com.guc.csen503.exceptions.NameExistsException;
import com.guc.csen503.exceptions.ServerCapacityFullException;
import com.guc.csen503.exceptions.ServerUnreachableException;
import com.guc.csen503.model.message.ChatMessage;
import com.guc.csen503.model.message.ChatMessageType;

public class Client
{
	private Socket socket;
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;
	private ClientListener listener;
	private String name;
	private int id;
	private boolean alive;
	private int memberListResponseCount;
	private ArrayList<String> availableClients;

	public Client(String serverAddress, int port) throws ServerUnreachableException
	{
		try
		{
			try
			{
				socket = new Socket(serverAddress, port);
			}
			catch (SocketException e)
			{
				throw new ServerUnreachableException(
						"The server you are trying to reach is either offline or does not exist!");
			}
			fromServer = new ObjectInputStream(socket.getInputStream());
			toServer = new ObjectOutputStream(socket.getOutputStream());
			availableClients = new ArrayList<String>();
			alive = true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public int getMemberListResponseCount()
	{
		return memberListResponseCount;
	}

	public ArrayList<String> getAvailableClients()
	{
		return availableClients;
	}

	public ClientListener getListener()
	{
		return listener;
	}

	public boolean isAlive()
	{
		return alive;
	}

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public void setListener(ClientListener listener)
	{
		this.listener = listener;
	}

	public Socket getSocket()
	{
		return socket;
	}

	public ObjectInputStream getFromServer()
	{
		return fromServer;
	}

	public ObjectOutputStream getToServer()
	{
		return toServer;
	}

	public boolean join(String name) throws NameExistsException, ServerCapacityFullException
	{
		try
		{
			toServer.writeObject(name);
			toServer.flush();
			String messageFromServer = (String) fromServer.readObject();
			if (messageFromServer.equals("!!!!Rejected"))
			{
				fromServer.close();
				toServer.close();
				socket.close();
				throw new NameExistsException("Connection Refused because the name you chose already exists!");
			}	
			else
				if(messageFromServer.equals("!!!!Full"))
				{
					fromServer.close();
					toServer.close();
					socket.close();
					throw new ServerCapacityFullException("Connection Refused because the maximum number of clients on this server has been reached!");
				}
			this.name = name;

			new Thread(new Runnable()
			{
				public void run()
				{
					ListenToServer();
				}
			}).start();
		}
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return true;
	}

	private void ListenToServer()
	{
		try
		{
			while (alive)
			{
				ChatMessage messageFromServer = (ChatMessage) fromServer.readObject();
				if (messageFromServer.getType() == ChatMessageType.MEMBERLISTSIZE)
				{
					availableClients.clear();
					memberListResponseCount = Integer.parseInt(messageFromServer.getMessage());
					if(memberListResponseCount == 0)
						notifyListenerWithMembersRefresh();
				}
				else
					if (messageFromServer.getType() == ChatMessageType.MEMBERNAME)
					{
						if (memberListResponseCount-- > 0)
						{
							availableClients.add(messageFromServer.getMessage());
							notifyListenerWithMembersRefresh();
						}
					}
					else
						notifyListenerWithMessage(messageFromServer);
			}
			toServer.close();
			fromServer.close();
			socket.close();
			notifyListenerWithLeaving();
		}
		catch (SocketException e)
		{
			try
			{
				toServer.close();
				fromServer.close();
				socket.close();
				notifyListenerWithServerStopping();
			}
			catch (IOException e1)
			{
				e.printStackTrace();
			}
		}
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void broadcast(ChatMessage msg)
	{
		try
		{
			toServer.writeObject(msg);
			toServer.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void chat(int source, int destination, int ttl, String message)
	{
		try
		{
			toServer.writeObject(new ChatMessage(name, source, destination, ttl, message, ChatMessageType.CHAT));
			toServer.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void getMemberList()
	{
		try
		{
			toServer.writeObject(new ChatMessage("", ChatMessageType.MEMBERLISTREQUEST));
			toServer.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void quit()
	{
		try
		{
			toServer.writeObject(new ChatMessage("", ChatMessageType.LOGOUT));
			toServer.flush();
			alive = false;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void notifyListenerWithMessage(ChatMessage message)
	{
		if (listener != null)
			listener.onIncomingMessage(message);
	}

	private void notifyListenerWithLeaving()
	{
		if (listener != null)
			listener.onLeavingTheRoom();
	}

	private void notifyListenerWithServerStopping()
	{
		if (listener != null)
			listener.onServerDown();
	}

	public void notifyListenerWithMembersRefresh()
	{
		if (listener != null)
			if(memberListResponseCount == 0)
				listener.onMemberListRefresh(availableClients);
	}
}