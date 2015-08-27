package minefantasy.mf2.client.render.block;

import java.util.Random;

import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.block.tileentity.TileEntityTrough;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

public class TileEntityTroughRenderer extends TileEntitySpecialRenderer
{
    public TileEntityTroughRenderer() 
    {
        model = new ModelTrough();
    }

    public void renderAModelAt(TileEntityTrough tile, double d, double d1, double d2, float f) 
    {
    	int i = 0;
		if (tile.getWorldObj() != null)
		{
        	i = tile.getBlockMetadata();
        }
		this.renderModelAt(tile, i, d, d1, d2, f);
    }
    
	public void renderModelAt(TileEntityTrough tile, int meta, double d, double d1, double d2, float f) 
    {
		int i = meta;
        

		int j = 90 * i;

        if (i == 1) {
            j = 0;
        }

        if (i == 2) {
            j = 270;
        }

        if (i == 3) {
            j = 180;
        }

        if (i == 0) {
            j = 90;
        }
        if (i == 0) {
            j = 90;
        }
    	bindTextureByName("textures/models/tileentity/Trough_"+tile.getName()+".png"); //texture
        
        GL11.glPushMatrix(); //start
        float scale = 1.0F;
        float yOffset = 1/16F;
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F); //size
        GL11.glRotatef(j, 0.0F, 1.0F, 0.0F); //rotate based on metadata
        GL11.glScalef(scale, -scale, -scale); //if you read this comment out this line and you can see what happens
        GL11.glPushMatrix();
        float level = 0F;
        model.renderModel(0.0625F); 
        float height = (float)tile.fill / (float)tile.getCapacity();
        if(tile.fill > 0)
        {
        	GL11.glColor4f(255, 255, 255, 0.5F);
        	GL11.glTranslatef(0F, -height*0.35F, 0F);
        	model.renderWater(0.0625F);
        }
        GL11.glPopMatrix();
        GL11.glColor3f(255, 255, 255);
        GL11.glPopMatrix(); //end

    }
    private void bindTextureByName(String image)
    {
    	Minecraft.getMinecraft().renderEngine.bindTexture(TextureHelperMF.getResource(image));
	}
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        renderAModelAt((TileEntityTrough) tileentity, d, d1, d2, f); //where to render
    }
	
    private ModelTrough model;
    private Random random = new Random();
}
