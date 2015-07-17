package minefantasy.mf2.network.packet;

import io.netty.buffer.ByteBuf;
import minefantasy.mf2.block.tileentity.TileEntityResearch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.ByteBufUtils;

public class ResearchTablePacket extends PacketMF
{
	public static final String packetName = "MF2_ResearchTablePacket";
	private int[] coords = new int[3];
	private String resultName;
	private float[] progress = new float[2];

	public ResearchTablePacket(TileEntityResearch tile)
	{
		coords = new int[]{tile.xCoord, tile.yCoord, tile.zCoord};
		resultName = tile.lastName;
		progress = new float[]{tile.progress, tile.lastMaxProgress};
		if(progress[1] <= 0)
		{
			progress[1] = 0;
		}
	}

	public ResearchTablePacket() {
	}

	@Override
	public void process(ByteBuf packet, EntityPlayer player) 
	{
        coords = new int[]{packet.readInt(), packet.readInt(), packet.readInt()};
        TileEntity entity = player.worldObj.getTileEntity(coords[0], coords[1], coords[2]);
        
        if(entity != null && entity instanceof TileEntityResearch)
        {
	        progress[0] = packet.readFloat();
	        progress[1] = packet.readFloat();
	        resultName = ByteBufUtils.readUTF8String(packet);
	        
	        TileEntityResearch carpenter = (TileEntityResearch)entity;
	        carpenter.lastName = resultName;
	        carpenter.progress = progress[0];
	        carpenter.lastMaxProgress = (int)progress[1];
        }
	}

	@Override
	public String getChannel()
	{
		return packetName;
	}

	@Override
	public void write(ByteBuf packet) 
	{
		for(int a = 0; a < coords.length; a++)
		{
			packet.writeInt(coords[a]);
		}
		packet.writeFloat(progress[0]);
		packet.writeFloat(progress[1]);
		ByteBufUtils.writeUTF8String(packet, resultName);
	}
}
