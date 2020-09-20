package spicy.modules.movement;

import org.lwjgl.input.Keyboard;

import spicy.events.Event;
import spicy.events.listeners.EventMotion;
import spicy.events.listeners.EventUpdate;
import spicy.modules.Module;
import spicy.settings.ModeSetting;
import spicy.settings.SettingChangeEvent;

public class Step extends Module {
	
	private boolean stepped = false;
	
	private ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Vanilla", "NCP");
	
	public Step() {
		super("Step", Keyboard.KEY_NONE, Category.MOVEMENT);
		resetSettings();
	}
	
	@Override
	public void resetSettings() {
		this.settings.clear();
		this.addSettings(mode);
	}
	
	@Override
	public void onSettingChange(SettingChangeEvent e) {
		
		if (e.setting.equals(mode)) {
			
			if (mc.thePlayer == null) {
				
			}
			else if (mode.is("Vanilla") && this.isToggled()) {
				mc.thePlayer.stepHeight = 1f;
				mc.timer.ticksPerSecond = 20f;
			}
			else if (mode.is("NCP") && this.isToggled()) {
				mc.thePlayer.stepHeight = 0.5f;
			}
			
		}
		
	}
	
	public void onEnable() {
		if (mode.is("Vanilla")) {
			mc.thePlayer.stepHeight = 1f;
		}
	}
	
	public void onDisable() {
		mc.thePlayer.stepHeight = 0.5f;
		mc.timer.ticksPerSecond = 20f;
	}
	
	public void onEvent(Event e) {
		
		if (e instanceof EventUpdate) {
			
			if (e.isPre()) {
				
				this.additionalInformation = mode.getMode();
				
			}
			
		}
		
		if (e instanceof EventUpdate) {
			
			if (e.isPre() && mode.is("Vanilla")) {
				mc.thePlayer.stepHeight = 1f;
			}
			else if (e.isPre() && mode.is("NCP")) {
				
				if (mc.thePlayer.isCollidedHorizontally && mc.thePlayer.onGround) {
					mc.timer.ticksPerSecond = 40f;
					mc.thePlayer.jump();
					stepped = true;
					mc.thePlayer.setSprinting(true);
				}
				else if (stepped && !mc.thePlayer.isCollidedHorizontally) {
					mc.timer.ticksPerSecond = 20f;
					mc.thePlayer.motionY = 0;
					stepped = false;
					mc.thePlayer.setSprinting(true);
				}
				
			}
			
		}
		
	}
	
}