package xyz.brassgoggledcoders.moarlibs.proxies;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import xyz.brassgoggledcoders.moarlibs.api.IContainerHolder;
import xyz.brassgoggledcoders.moarlibs.wrappers.players.EntityPlayerSPWrapper;

public class ClientProxy extends CommonProxy
{
	@Override
	public EntityPlayer getEntityPlayerWrapper(EntityPlayer entityPlayer, IContainerHolder containerHolder)
	{
		if(entityPlayer instanceof EntityPlayerSP)
		{
			return new EntityPlayerSPWrapper((EntityPlayerSP)entityPlayer, containerHolder);
		}
		return super.getEntityPlayerWrapper(entityPlayer, containerHolder);
	}
}
