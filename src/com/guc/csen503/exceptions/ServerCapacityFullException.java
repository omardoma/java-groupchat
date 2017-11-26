package com.guc.csen503.exceptions;

@SuppressWarnings("serial")
public class ServerCapacityFullException extends Exception
{
	public ServerCapacityFullException()
	{
		super();
	}
	
	public ServerCapacityFullException(String message)
	{
		super(message);
	}
}