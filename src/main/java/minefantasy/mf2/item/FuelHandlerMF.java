package minefantasy.mf2.item;

import minefantasy.mf2.item.list.ComponentListMF;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class FuelHandlerMF implements IFuelHandler
{

	@Override
	public int getBurnTime(ItemStack fuel)
	{
		if(fuel.getItem() == null)
		{
			return 0;
		}
		if(fuel.getItem() == ComponentListMF.plank)
		{
			return 200;
		}
		if(fuel.getItem() == ComponentListMF.coalDust)
		{
			return 400;
		}
		if(fuel.getItem() == ComponentListMF.coke)
		{
			return 800;
		}
		return 0;
	}

}
