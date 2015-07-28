package minefantasy.mf2.network.packet;

import io.netty.buffer.ByteBuf;
import minefantasy.mf2.api.knowledge.InformationBase;
import minefantasy.mf2.api.knowledge.InformationList;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class ResearchRequest extends PacketMF
{
	public static final String packetName = "MF2_RequestResearch";
	private EntityPlayer user;
	private int researchID;
	private String username;

	public ResearchRequest(EntityPlayer user, int id)
	{
		this.researchID = id;
		this.username = user.getName();
		this.user = user;
	}

	public ResearchRequest() {
	}

	@Override
	public void process(ByteBuf packet, EntityPlayer player) 
	{
		researchID = packet.readInt();
		username = ByteBufUtils.readUTF8String(packet);
		
		if (username != null) 
        {
            EntityPlayer entity = player.worldObj .getPlayerEntityByName(username);
            InformationBase research = InformationList.knowledgeList.get(researchID);
            if(entity != null && research != null)
            {
            	if(!entity.worldObj.isRemote)
            	{
	            	if(research.onPurchase(entity))
	            	{
	            		ResearchLogic.syncData(entity);
	            	}
            	}
            }
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
		packet.writeInt(researchID);
        ByteBufUtils.writeUTF8String(packet, username);
	}
}
