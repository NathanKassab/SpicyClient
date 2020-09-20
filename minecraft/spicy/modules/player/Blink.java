package spicy.modules.player;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import spicy.events.Event;
import spicy.events.listeners.EventSendPacket;
import spicy.events.listeners.EventUpdate;
import spicy.modules.Module;

public class Blink extends Module {
	
	public static ArrayList<Packet> packets = new ArrayList<Packet>();
	
	public Blink() {
		super("Blink", Keyboard.KEY_NONE, Category.PLAYER);
	}
	
	public void onEnable() {
		packets.clear();
	}
	
	public void onDisable() {
		
		for (Packet p : packets) {
			mc.getNetHandler().addToSendQueue(p);
		}
		packets.clear();
		
	}
	
	public void onEvent(Event e) {
		
		if (e instanceof EventSendPacket) {
			
			if (e.isPre()) {
				
				EventSendPacket sendPacket = (EventSendPacket) e;
				packets.add(sendPacket.packet);
				sendPacket.setCanceled(true);
				
			}
			
		}
		
	}
	
}