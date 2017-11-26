package com.guc.csen503.model.message;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ChatMessage implements Serializable
{
	private int source;
	private int destination;
	private int ttl;
	private String message;
	private String senderName;
	private ChatMessageType type;
	
	public ChatMessage(String message, ChatMessageType type)
	{
		this.message = message;
		this.type = type;
	}
	
	public ChatMessage(String senderName, String message, ChatMessageType type)
	{
		this.senderName = senderName;
		this.message = message;
		this.type = type;
	}
	
	public ChatMessage(String senderName, int source, int destination, String message, ChatMessageType type)
	{
		this.source = source;
		this.destination = destination;
		this.message = message;
		this.senderName = senderName;
		this.type = type;
		ttl=2;
	}
	
	public ChatMessage(String senderName, int source, int destination, int ttl, String message, ChatMessageType type)
	{
		this(senderName, source, destination, message, type);
		this.ttl = ttl;
	}

	public ChatMessageType getType()
	{
		return type;
	}

	public String getSenderName()
	{
		return senderName;
	}

	public int getSource()
	{
		return source;
	}

	public int getDestination()
	{
		return destination;
	}

	public int getTtl()
	{
		return ttl;
	}

	public String getMessage()
	{
		return message;
	}
}