package i.gishreloaded.gishcode.command;

import i.gishreloaded.gishcode.Wrapper;
import i.gishreloaded.gishcode.hack.hacks.AntiBot;
import i.gishreloaded.gishcode.managers.EnemyManager;
import i.gishreloaded.gishcode.managers.FriendManager;
import i.gishreloaded.gishcode.utils.ChatUtils;
import i.gishreloaded.gishcode.utils.EntityBot;
import i.gishreloaded.gishcode.utils.LoginUtils;
import i.gishreloaded.gishcode.utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class Friend extends Command
{
	public Friend()
	{
		super("friend");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{	
			if(args[0].equalsIgnoreCase("add")) {
				if(args[1].equalsIgnoreCase("all")) {
					for(Object object : Wrapper.INSTANCE.world().loadedEntityList) {
						if(object instanceof EntityPlayer) {
							EntityPlayer player = (EntityPlayer) object;
							if(!player.isInvisible()) {
								FriendManager.addFriend(Utils.getPlayerName(player));
							}
						}
					}
				} else {
					FriendManager.addFriend(args[1]);
				}
			}
			else
			if(args[0].equalsIgnoreCase("remove")) {
				FriendManager.removeFriend(args[1]);
			}
			else
			if(args[0].equalsIgnoreCase("clear")) {
				FriendManager.clear();
			}
		}
		catch(Exception e)
		{
			ChatUtils.error("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription()
	{
		return "Friend manager.";
	}

	@Override
	public String getSyntax()
	{
		return "friend <add/remove/clear> <nick>";
	}
}