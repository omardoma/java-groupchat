package com.guc.csen503.exceptions;

@SuppressWarnings("serial")
public class ServerExistsException extends Exception
{
	public ServerExistsException()
	{
		super();
	}
	
	public ServerExistsException(String msg)
	{
		super(msg);
	}
}