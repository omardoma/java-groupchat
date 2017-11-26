package com.guc.csen503.model.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import com.guc.csen503.model.message.ChatMessage;
import com.guc.csen503.model.message.ChatMessageType;

public class ClientThread implements Runnable
{
	private String name;
	private int id;
	private Socket socket;
	private ObjectInputStream fromClient;
	private ObjectOutputStream toClient;
	private ArrayList<ClientThread> connectedClients;
	private boolean alive;

	public ClientThread(String name, int id, Socket socket, ObjectInputStream fromClient, ObjectOutputStream toClient,
			ArrayList<ClientThread> connectedClients)
	{
		this.name = name;
		this.id = id;
		this.socket = socket;
		this.connectedClients = connectedClients;
		this.toClient = toClient;
		this.fromClient = fromClient;
		alive = true;
	}

	public boolean isAlive()
	{
		return alive;
	}

	public ArrayList<ClientThread> getConnectedClients()
	{
		return connectedClients;
	}

	public String getName()
	{
		return name;
	}

	public int getId()
	{
		return id;
	}

	public Socket getSocket()
	{
		return socket;
	}

	public ObjectInputStream getFromClient()
	{
		return fromClient;
	}

	public ObjectOutputStream getToClient()
	{
		return toClient;
	}

	private synchronized void routeToClient(ChatMessage msg)
	{
		for (ClientThread cT : connectedClients)
			if (cT.id == msg.getDestination())
				try
				{
					cT.toClient.writeObject(msg);
					cT.toClient.flush();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
	}

	private synchronized void broadcastToClients(ChatMessage message)
	{
		for (ClientThread cT : connectedClients)
			try
			{
				cT.toClient.writeObject(message);
				cT.toClient.flush();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
	}

	private synchronized void notifyLeaving()
	{
		try
		{
			for (ClientThread cT : connectedClients)
				if (cT != this)
				{
					cT.toClient.writeObject(
							new ChatMessage(name + " has left the chat!", ChatMessageType.LEAVING_NOTIFICATION));
					cT.toClient.flush();
				}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private synchronized void welcomeNewClient()
	{
		for (ClientThread cT : connectedClients)
			if (cT != this)
				try
				{
					cT.toClient.writeObject(new ChatMessage(name + " has joined the chat!", ChatMessageType.WELCOME));
					cT.toClient.flush();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
	}

	private synchronized void memberListResponse()
	{
		try
		{
			toClient.writeObject(new ChatMessage(connectedClients.size() - 1 + "", ChatMessageType.MEMBERLISTSIZE));
			toClient.flush();
			for (ClientThread cT : connectedClients)
				if (cT != this)
				{
					toClient.writeObject(new ChatMessage(cT.getName(), ChatMessageType.MEMBERNAME));
					toClient.flush();
				}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void stop()
	{
		alive = false;
	}

	public synchronized void run()
	{
		try
		{
			welcomeNewClient();
			while (alive)
			{
				ChatMessage msg = (ChatMessage) fromClient.readObject();
				if (msg.getType() == ChatMessageType.LOGOUT)
					stop();
				else
					if (msg.getType() == ChatMessageType.MEMBERLISTREQUEST)
						memberListResponse();
					else
						if (msg.getType() == ChatMessageType.CHAT)
							routeToClient(msg);
						else
							broadcastToClients(msg);
			}
			notifyLeaving();
			connectedClients.remove(this);
			fromClient.close();
			toClient.close();
			socket.close();
		}
		catch (SocketException e)
		{
			try
			{
				notifyLeaving();
				connectedClients.remove(this);
				fromClient.close();
				toClient.close();
				socket.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}