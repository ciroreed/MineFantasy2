package minefantasy.mf2.block.tileentity;

import java.util.Random;

import minefantasy.mf2.api.refine.Alloy;
import minefantasy.mf2.api.refine.AlloyRecipes;
import minefantasy.mf2.api.refine.SmokeMechanics;
import minefantasy.mf2.block.refining.BlockCrucible;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCrucible extends TileEntity implements IInventory
{
	private ItemStack[] inv = new ItemStack[10];
	public float progress, progressMax;
	public float temperature;
	private Random rand = new Random();
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		boolean isHot = temperature > 0;
		temperature = getTemperature();
		
		int time = 400;
		for(int a = 1; a < getSizeInventory()-1; a ++)
		{
			if(inv[a] != null)
			{
				time += 50;
			}
		}
		if(!worldObj.isRemote)
			progressMax = time;
		
		if (isHot && canSmelt()) 
		{
			progress += (temperature/60F);
			if (progress >= progressMax) 
			{
				progress = 0;
				smeltItem();
			}
		} 
		else
		{
			progress = 0;
		}
		if(progress > 0 && rand.nextInt(4) == 0)
		{
			SmokeMechanics.emitSmoke(worldObj, xCoord, yCoord, zCoord, 1);
		}
		
		if(isHot != getTemperature() >0)
		{
			BlockCrucible.updateFurnaceBlockState(getTemperature() > 0, worldObj, xCoord, yCoord, zCoord);
		}
	}
	
	public void smeltItem() {
		if (!canSmelt()) 
		{
			return;
		}
		
		ItemStack itemstack = getRecipe();
		
		if (inv[getOutSlot()] == null) 
		{
			inv[getOutSlot()] = itemstack.copy();
		} 
		
		else if (inv[getOutSlot()].isItemEqual(itemstack)) 
		{
			inv[getOutSlot()].stackSize += itemstack.stackSize;
		}
		
		for(int a = 0; a < (getSizeInventory()-1); a ++)
		{
			if (inv[a] != null) 
			{
				inv[a].stackSize--;
				if (inv[a].stackSize <= 0) {
					inv[a] = null;
				}
			}
		}
	}
	
	private boolean canSmelt() 
	{
		if(temperature <= 0)
		{
			return false;
		}
		
		ItemStack result = getRecipe();
		
		if (result == null)
		{
			return false;
		}
		
		if (inv[getOutSlot()] == null)
			return true;
		if (inv[getOutSlot()] != null
				&& inv[getOutSlot()].isItemEqual(result)
				&& inv[getOutSlot()].stackSize < (inv[getOutSlot()]
						.getMaxStackSize() - (result.stackSize - 1)))
			return true;
		return false;
	}
	
	private int getOutSlot() {
		return 9;
	}
	
	private ItemStack getRecipe() 
	{
		ItemStack[] input = new ItemStack[getSizeInventory()-1];
		for(int a = 0; a < 9; a ++)
		{
			input[a] = inv[a];
		}
		Alloy alloy = AlloyRecipes.getResult(input);
		if(alloy != null)
		{
			if(alloy.getLevel() <= getTier())
			{
				return AlloyRecipes.getResult(input).getRecipeOutput();
			}
		}
		return null;
	}
	
	public int getTier()
	{
		if(this.blockType != null && blockType instanceof BlockCrucible)
		{
			return ((BlockCrucible)blockType).tier;
		}
		return 0;
	}
	public float getTemperature()
	{
		Block under = worldObj.getBlock(xCoord, yCoord-1, zCoord);
		
		if(under.getMaterial() == Material.fire)
		{
			return 100F;
		}
		if(under.getMaterial() == Material.lava)
		{
			return 500F;
		}
		return 0F;
	}
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setFloat("progress", progress);
		nbt.setFloat("progressMax", progressMax);
		
		NBTTagList savedItems = new NBTTagList();

        for (int i = 0; i < this.inv.length; ++i)
        {
            if (this.inv[i] != null)
            {
                NBTTagCompound savedSlot = new NBTTagCompound();
                savedSlot.setByte("Slot", (byte)i);
                this.inv[i].writeToNBT(savedSlot);
                savedItems.appendTag(savedSlot);
            }
        }

        nbt.setTag("Items", savedItems);
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		progress = nbt.getFloat("progress");
		progressMax = nbt.getFloat("progressMax");
		
		NBTTagList savedItems = nbt.getTagList("Items", 10);
        this.inv = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < savedItems.tagCount(); ++i)
        {
            NBTTagCompound savedSlot = savedItems.getCompoundTagAt(i);
            byte slotNum = savedSlot.getByte("Slot");

            if (slotNum >= 0 && slotNum < this.inv.length)
            {
                this.inv[slotNum] = ItemStack.loadItemStackFromNBT(savedSlot);
            }
        }
	}
	//INVENTORY

	@Override
	public int getSizeInventory()
	{
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return inv[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int num)
    {
		onInventoryChanged();
        if (this.inv[slot] != null)
        {
            ItemStack itemstack;

            if (this.inv[slot].stackSize <= num)
            {
                itemstack = this.inv[slot];
                this.inv[slot] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.inv[slot].splitStack(num);

                if (this.inv[slot].stackSize == 0)
                {
                    this.inv[slot] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return inv[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack item)
	{
		onInventoryChanged();
		inv[slot] = item;
	}

	public void onInventoryChanged() 
	{
	}

	@Override
	public String getInventoryName()
	{
		return "gui.crucible.name";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer user)
	{
		return user.getDistance(xCoord+0.5D, yCoord+0.5D, zCoord+0.5D) < 8D;
	}

	@Override
	public void openInventory()
	{
	}

	@Override
	public void closeInventory()
	{
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item)
	{
		return true;
	}
}