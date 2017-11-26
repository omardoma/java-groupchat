package com.guc.csen503.exceptions;

@SuppressWarnings("serial")
public class ServerUnreachableException extends Exception
{
	public ServerUnreachableException()
	{
		super();
	}
	
	public ServerUnreachableException(String message)
	{
		super(message);
	}
}