package minefantasy.mf2.item.weapon;

import java.util.List;

import minefantasy.mf2.api.stamina.StaminaBar;
import mod.battlegear2.api.shield.IShield;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Anonymous Productions
 */
public class ItemLance extends ItemSpearMF
{
	private float joustDamage;
	/**
	 */
    public ItemLance(String name, ToolMaterial material, int rarity, float weight)
    {
    	super(name, material, rarity, weight);
    	setMaxDamage(getMaxDamage()*2);
    	joustDamage = baseDamage*2.5F;
    	baseDamage = 2.0F;
    }
    
    @Override
    public void addInformation(ItemStack weapon, EntityPlayer user, List list, boolean extra) 
    {
    	super.addInformation(weapon, user, list, extra);
    	
    	list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus."+ 0, decimal_format.format(joustDamage), StatCollector.translateToLocal("attribute.weapon.joustDam")));
    }

    @Override
	public boolean allowOffhand(ItemStack mainhand, ItemStack offhand) 
    {
		return offhand==null || offhand.getItem() instanceof IShield;
	}
    
	@Override
	public float getReachModifierInBlocks(ItemStack stack)
	{
		return 3.0F;
	}
	
	@Override
	public float modifyDamage(ItemStack item, EntityLivingBase wielder, Entity hit, float initialDam, boolean properHit)
	{
		float dam = super.modifyDamage(item, wielder, hit, initialDam, properHit);
		if(hit instanceof EntityLivingBase)
		{
			return joust((EntityLivingBase)hit, wielder, dam);
		}
		return dam;
	}
	
	@Override
	public boolean canWeaponParry()
	{
		return false;
	}
	//Higher stamina means more precice hits: Full stamina hits are perfect
	@Override
	public float getBalance(EntityLivingBase user)
	{
		if(StaminaBar.isSystemActive)
		{
			return 0.0F + (2*(1-StaminaBar.getStaminaDecimal(user)));
		}
		return 0.0F;
	}
	
	@Override
	protected float getKnockbackStrength() 
	{
		return 5.0F;
	}
	@Override
	protected float getStaminaMod() 
	{
		return 5.0F;
	}
	@Override
	public boolean canBlock() 
	{
		return false;
	}
	
	public float joust(EntityLivingBase target, EntityLivingBase attacker, float dam)
	{
		float speedMod = 20F;
		float speedCap = 5F;
		
    	if(attacker.isRiding())
    	{
    		Entity mount = attacker.ridingEntity;
    		float speed = (float)Math.hypot(mount.motionX, mount.motionZ) * speedMod;
    		if(speed > speedCap)speed = speedCap;
    		
    		dam += joustDamage / speedCap * speed;
    		
            if(attacker instanceof EntityPlayer)
            {
            	((EntityPlayer) attacker).onCriticalHit(target);
            }
            
    		if(target.isRiding() && speed > (speedCap/2F))
    		{
    			target.dismountEntity(target.ridingEntity);
    			target.mountEntity(null);
    		}
    	}
    	return dam;
	}
	@Override
	public int modifyHitTime(EntityLivingBase user, ItemStack item)
	{
		return super.modifyHitTime(user, item) + speedModSpear*2;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item parItem, CreativeTabs parTab, 
          List parListSubItems)
    {
        parListSubItems.add(new ItemStack(this, 1));
     }
}
