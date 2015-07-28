package minefantasy.mf2.client.render;

/**
 *
 * @author Anonymous Productions
 */
import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.api.weapon.IParryable;
import minefantasy.mf2.item.weapon.ItemWeaponMF;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.fml.client.FMLClientHandler;

import org.lwjgl.opengl.GL11;

public class RenderHeavyWeapon implements IItemRenderer
{
	private Minecraft mc;
    private RenderItem itemRenderer;
    private boolean rotate;
    private float scale;
    private float offset;
	private boolean doesRenderParry = false;
    
	public RenderHeavyWeapon setParryable()
    {
		doesRenderParry = true;
		return this;
    }
    public RenderHeavyWeapon setGreatsword()
    {
    	offset = 1.0F;
    	return this;
    }
    public RenderHeavyWeapon setAxe()
    {
    	offset = 1.0F;
    	return this;
    }
    public RenderHeavyWeapon setBlunt()
    {
    	offset = 1.2F;
    	return this;
    }
    public RenderHeavyWeapon setScythe()
    {
    	offset = 1.8F;
    	return this;
    }
    public RenderHeavyWeapon()
    {
    	this(true);
    }
    public RenderHeavyWeapon(boolean rot)
    {
    	this(rot, 2.0F);
    }
    public RenderHeavyWeapon(boolean rot, float sc)
    {
    	rotate = rot;
    	scale = sc;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type.equals(ItemRenderType.EQUIPPED) || type.equals(ItemRenderType.EQUIPPED_FIRST_PERSON);
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
            ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

        GL11.glPushMatrix();

        boolean hasParried = false;
        if (mc == null) {
            mc = FMLClientHandler.instance().getClient();
            itemRenderer = Minecraft.getMinecraft().getRenderItem();
        }
        EntityLivingBase user = null;
        if(doesRenderParry)
        {
	        user = ((EntityLivingBase)data[1]);
	        
	        if(user instanceof EntityPlayer && item != null)
	    	{
	    		hasParried = ItemWeaponMF.getParry(item) > 0;
	    	}
	    	else if(!(user instanceof EntityPlayer) && item != null && item.getItem() instanceof IParryable)
	    	{
	    		hasParried = user.hurtResistantTime > 0;
	    	}
        }
        
        this.mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
        Tessellator tessellator = Tessellator.getInstance();

        if (type == ItemRenderType.EQUIPPED)
        {
        	if(user != null && hasParried)
        	{
        		if(user instanceof EntityPlayer)
        		{
        			GL11.glRotatef(-45, 0, 0, 1);
		        	GL11.glTranslatef(-0.5F, 0.5F, 0);
        		}
        		else
        		{
		        	GL11.glRotatef(-90, -1, 0, 0);
		        	GL11.glTranslatef(0F, 0F, -0.25F);
        		}
        	}
        	GL11.glTranslatef(0.25F*offset, -0.25F*offset, 0);
            GL11.glTranslatef(-1.0F, 0F, 0);
            GL11.glScalef(scale,scale,1);
            IIcon icon = item.getIconIndex();

            ItemRenderer.renderItemIn2D(tessellator,
            		icon.getMaxU(),
                    icon.getMinV(),
                    icon.getMinU(),
                    icon.getMaxV(),
                    icon.getIconWidth(),
                    icon.getIconHeight(), 1F/16F);
            if (item != null && item.hasEffect()) 
            {
            	TextureHelperMF.renderEnchantmentEffects(item);
            }

        }else if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) 
        {
        	GL11.glTranslatef(0.05F*offset, -0.05F*offset, 0);
        	GL11.glTranslatef(-0.75F, -0.25F, 0);
            GL11.glScalef(scale,scale,1);
            IIcon icon = item.getIconIndex();
            Minecraft.getMinecraft().getRenderItem().renderItemModel(item);
            ItemRenderer.renderItemIn2D(tessellator,
            		icon.getMaxU(),
                    icon.getMinV(),
                    icon.getMinU(),
                    icon.getMaxV(),
                    icon.getIconWidth(),
                    icon.getIconHeight(), 1F/16F);

            if (item != null && item.hasEffect()) {
               TextureHelperMF.renderEnchantmentEffects(item);
            }
        }

        GL11.glPopMatrix();
    }
}