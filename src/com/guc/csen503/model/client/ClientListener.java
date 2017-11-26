package com.guc.csen503.model.client;

import java.util.ArrayList;
import java.util.EventListener;

import com.guc.csen503.model.message.ChatMessage;

public interface ClientListener extends EventListener
{
	public void onIncomingMessage(ChatMessage message);
	public void onMemberListRefresh(ArrayList<String> members);
	public void onLeavingTheRoom();
	public void onServerDown();
}