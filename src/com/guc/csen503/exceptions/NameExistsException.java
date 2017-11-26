package com.guc.csen503.exceptions;

@SuppressWarnings("serial")
public class NameExistsException extends Exception
{
	public NameExistsException()
	{
		super();
	}
	
	public NameExistsException(String message)
	{
		super(message);
	}
}