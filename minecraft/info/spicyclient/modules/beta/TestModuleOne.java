package info.spicyclient.modules.beta;

import org.lwjgl.input.Keyboard;

import info.spicyclient.SpicyClient;
import info.spicyclient.chatCommands.Command;
import info.spicyclient.events.Event;
import info.spicyclient.events.listeners.EventPacket;
import info.spicyclient.events.listeners.EventSendPacket;
import info.spicyclient.events.listeners.EventUpdate;
import info.spicyclient.modules.Module;
import info.spicyclient.modules.movement.Fly;
import info.spicyclient.notifications.Color;
import info.spicyclient.notifications.NotificationManager;
import info.spicyclient.notifications.Type;
import info.spicyclient.util.MovementUtils;
import info.spicyclient.util.Timer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;

public class TestModuleOne extends Module {

	public TestModuleOne() {
		super("TestModuleOne", Keyboard.KEY_NONE, Category.BETA);
		// TODO Auto-generated constructor stub
	}
	
	public static transient Timer timer = new Timer();
	
	public int status = 0, test = 0;
	public double dub = 0;
	public float flo = 0;
	public boolean bool1 = false, bool2 = true;
	
	@Override
	public void onEnable() {
		status = 0;
		dub = 0;
		flo = 0;
		test = 0;
		
        if (MovementUtils.isOnGround(0.001) && mc.thePlayer.isCollidedVertically) {
            double x = mc.thePlayer.posX;
            double y = mc.thePlayer.posY;
            double z = mc.thePlayer.posZ;
            mc.thePlayer.sendQueue.getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.16, z, true));
            mc.thePlayer.sendQueue.getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.07, z, true));
            bool1 = true;
            NotificationManager.getNotificationManager().createNotification("Disabler: Wait 5s.", "", true, 5000, Type.INFO, Color.PINK);
            mc.thePlayer.jump();
            //Notifications.getManager().post("Disabler", "Wait 5s.", Notifications.Type.INFO);
        } else {
        	bool1 = false;
        }
        
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public void onEvent(Event e) {
		
		if (e instanceof EventSendPacket && e.isPre()) {
			
            if (e.isPre()) {
            	
                if (((EventSendPacket)e).packet instanceof C03PacketPlayer) {
                    if (bool1) {
                        e.setCanceled(true);
                    }
                    
                }
			
            }
            
		}
		
		if (e instanceof EventPacket && e.isPre()) {
			
            if (e.isPre()) {
            	
                if (((EventPacket)e).packet instanceof S08PacketPlayerPosLook) {
                	
                    if (bool1) {
                        toggle();
                        NotificationManager.getNotificationManager().createNotification("Disabler: Wait 5s.", "", true, 5000, Type.INFO, Color.PINK);
                    }
                    
                }
			
            }
            
		}
		
		if (e instanceof EventUpdate && e.isPre()) {
			
			if (!bool1) {
                if (MovementUtils.isOnGround(0.001) && mc.thePlayer.isCollidedVertically) {
                    double x = mc.thePlayer.posX;
                    double y = mc.thePlayer.posY;
                    double z = mc.thePlayer.posZ;
                    mc.thePlayer.sendQueue.getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));
                    mc.thePlayer.sendQueue.getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.16, z, true));
                    mc.thePlayer.sendQueue.getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.07, z, true));
                    bool1 = true;
                    NotificationManager.getNotificationManager().createNotification("Disabler: Wait 5s.", "", true, 5000, Type.INFO, Color.PINK);
                    mc.thePlayer.jump();
                }
            } else {
                mc.thePlayer.motionX = 0;
                mc.thePlayer.motionY = 0;
                mc.thePlayer.motionZ = 0;
                mc.thePlayer.jumpMovementFactor = 0;
                mc.thePlayer.noClip = true;
                mc.thePlayer.onGround = false;
            }
			
		}
		
	}
	
	@Override
	public void onEventWhenDisabled(Event e) {
		
	}
	
}
