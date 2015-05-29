package minefantasy.mf2.client.gui;

import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.block.tileentity.TileEntityForge;
import minefantasy.mf2.container.ContainerForge;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiForge extends GuiContainer
{
    private TileEntityForge tile;
 
    public GuiForge(InventoryPlayer user, TileEntityForge tile)
    {
        super(new ContainerForge(user, tile));
        this.ySize = 186;
        this.tile = tile;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TextureHelperMF.getResource(getTex()));
        int xPoint = (this.width - this.xSize) / 2;
        int yPoint = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(xPoint, yPoint, 0, 0, this.xSize, this.ySize);
        
        if (this.tile.temperature > 0)
        {
            this.drawTexturedModalRect(xPoint + 81, yPoint + 75, 243, 0, 14, 12);
        }
    }
    
    private String getTex() 
    {
		return "textures/gui/crucible.png";
	}

	@Override
    public void drawScreen(int x, int y, float f)
    {
        super.drawScreen(x, y, f);
    }
}