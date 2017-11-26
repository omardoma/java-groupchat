package com.guc.csen503.model.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.guc.csen503.exceptions.ServerExistsException;

public class Server
{
	private ServerSocket serverSocket;
	private Socket socket;
	private ObjectOutputStream serverOutput;
	private ObjectInputStream clientInput;
	private ArrayList<ClientThread> connectedClients;
	private static int counterIDs;
	private boolean alive;

	public Server(int port) throws ServerExistsException
	{
		counterIDs = 0;
		connectedClients = new ArrayList<ClientThread>();
		alive = true;
		try
		{
			serverSocket = new ServerSocket(port);
		}
		catch(BindException e)
		{
			throw new ServerExistsException("A server with the same address and port is already running!");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public boolean isAlive()
	{
		return alive;
	}

	public void setAlive(boolean alive)
	{
		this.alive = alive;
	}

	public int getCounterIDs()
	{
		return counterIDs;
	}

	public ObjectOutputStream getServerOutput()
	{
		return serverOutput;
	}

	public ObjectInputStream getClientInput()
	{
		return clientInput;
	}

	public ArrayList<ClientThread> getConnectedClients()
	{
		return connectedClients;
	}

	public ServerSocket getServerSocket()
	{
		return serverSocket;
	}

	public Socket getSocket()
	{
		return socket;
	}

	public void connect()
	{
		try
		{
			while (alive)
			{
				String clientName = "";
				do
				{
					socket = serverSocket.accept();
					if (!alive)
						break;
					serverOutput = new ObjectOutputStream(socket.getOutputStream());
					clientInput = new ObjectInputStream(socket.getInputStream());
					clientName = (String) clientInput.readObject();
				}
				while (!joinResponse(clientName));
				if (!alive)
					break;
				ClientThread c = new ClientThread(clientName, counterIDs++, socket, clientInput, serverOutput,
						connectedClients);
				connectedClients.add(c);
				new Thread(c).start();
			}

			for (ClientThread cT : connectedClients)
			{
				cT.stop();
				cT.getFromClient().close();
				cT.getToClient().close();
				cT.getSocket().close();
			}
			
			if (clientInput != null)
				clientInput.close();
			if (serverOutput != null)
				serverOutput.close();
			socket.close();
			serverSocket.close();
		}
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void stop()
	{
		alive = false;
		try
		{
			socket = new Socket("localhost", serverSocket.getLocalPort());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private boolean joinResponse(String clientName)
	{
		try
		{
			if (connectedClients.size() >= 20)
			{
				serverOutput.writeObject("!!!!Full");
				serverOutput.flush();
				return false;
			}
			for (ClientThread cT : connectedClients)
				if (cT.getName().equalsIgnoreCase(clientName))
				{
					serverOutput.writeObject("!!!!Rejected");
					serverOutput.flush();
					return false;
				}
			serverOutput.writeObject("!!!!Accepted");
			serverOutput.flush();
			return true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}
}