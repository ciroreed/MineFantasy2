package minefantasy.mf2.api.knowledge.client;

import minefantasy.mf2.api.helpers.TextureHelperMF;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.crafting.IRecipe;

public class EntryPageCraft extends EntryPage
{
	private Minecraft mc = Minecraft.getMinecraft();
	private IRecipe[] recipes = new IRecipe[]{};
	public static int switchRate = 15;
	private int recipeID;
	
	public EntryPageCraft(IRecipe... recipes)
	{
		this.recipes = recipes;
	}
	
	@Override
	public void render(GuiScreen parent, int x, int y, float f, int posX, int posY)
	{
		tickRecipes();
		
		int xPoint = (parent.width - universalBookImageWidth) / 2;
        int yPoint = (parent.height - universalBookImageHeight) / 2;
        
		this.mc.getTextureManager().bindTexture(TextureHelperMF.getResource("textures/gui/knowledge/craftGrid.png"));
        parent.drawTexturedModalRect(xPoint, yPoint, 0, 0, this.universalBookImageWidth, this.universalBookImageHeight);
        
        IRecipe recipe = recipes[recipeID];
        renderRecipe(parent, x, y, f, posX, posY, recipe);
        
	}

	private void renderRecipe(GuiScreen parent, int x, int y, float f, int posX, int posY, IRecipe recipe)
	{
		//TODO: Render Grid
	}

	private void tickRecipes()
	{
		long ticks = mc.theWorld.getTotalWorldTime();
		if(ticks % 15 == 0)
		{
			if(recipeID < recipes.length-1)
			{
				++recipeID;
			}
			else
			{
				recipeID = 0;
			}
		}
	}

	@Override
	public void preRender(GuiScreen parent, int x, int y, float f, int posX, int posY)
	{
	}
}
