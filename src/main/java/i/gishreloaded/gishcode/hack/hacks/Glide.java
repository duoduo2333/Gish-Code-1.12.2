package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.Wrapper;
import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.TimerUtils;
import i.gishreloaded.gishcode.value.BooleanValue;
import i.gishreloaded.gishcode.value.Mode;
import i.gishreloaded.gishcode.value.ModeValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class Glide extends Hack{

	public BooleanValue damage;
	public ModeValue mode;
	
    static int tick;
    static boolean fall;
    static int times;
	
	TimerUtils timer;
	
	public Glide() {
		super("Glide", HackCategory.PLAYER);
		
		damage = new BooleanValue("Damage", false);
		this.mode = new ModeValue("Mode", new Mode("Falling", true), new Mode("Flat", false));
		
		this.addValue(mode, damage);
				
		timer = new TimerUtils();
	}
	
	@Override
	public void onEnable() {
		if(damage.getValue()) {
        	EntityPlayerSP player = Wrapper.INSTANCE.player();
        	Wrapper.INSTANCE.sendPacket(new CPacketPlayer.Position(player.posX, player.posY - 6, player.posZ, true));
        	player.motionX *= 0.2;
        	player.motionZ *= 0.2;
        	Wrapper.INSTANCE.player().swingArm(EnumHand.MAIN_HAND);
		}
		super.onEnable();
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		EntityPlayerSP player = Wrapper.INSTANCE.player();
		if(mode.getMode("Flat").isToggled()) {
			if (!player.capabilities.isFlying && player.fallDistance > 0.0f && !player.isSneaking()) {
				player.motionY = 0.0;
        	}
        	if (Wrapper.INSTANCE.mcSettings().keyBindSneak.isKeyDown()) {
        		player.motionY = -0.11;
        	}
        	if (Wrapper.INSTANCE.mcSettings().keyBindJump.isKeyDown()) {
        		player.motionY = 0.11;
        	}
        	if (timer.delay(50)) {
        		player.onGround = false;
        		timer.setLastMS();
        	}
		}
		else if(mode.getMode("Falling").isToggled())
		{
			if (player.onGround) {
                times = 0;
            }
            if (player.fallDistance > 0.0f && times <= 1) {
                if (tick > 0 && fall) {
                	player.motionY = 0.0;
                    tick = 0;
                } else {
                    ++tick;
                }
                if (player.fallDistance >= 0.1) {
                    fall = false;
                }
                if (player.fallDistance >= 0.4) {
                    fall = true;
                    player.fallDistance = 0.0f;
                }
            }
		}
		super.onClientTick(event);
	}

}
