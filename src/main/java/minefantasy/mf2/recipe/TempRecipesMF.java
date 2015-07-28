package minefantasy.mf2.recipe;

import java.util.ArrayList;
import java.util.List;

import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.item.food.FoodListMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.knowledge.KnowledgeListMF;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class TempRecipesMF
{
	public static void init()
	{
		GameRegistry.addRecipe(new ItemStack(Items.bucket, 1), new Object[]
		{
			"I I",
			" I ",
			'I', ComponentListMF.ingots[1],
		});
		//RESOURCES
		
		List<ItemStack> steels = OreDictionary.getOres("ingotSteel");
		List<ItemStack> bronzes = OreDictionary.getOres("ingotBronze");
		List<ItemStack> silvers = OreDictionary.getOres("ingotSilver");
		List<ItemStack> blacks = OreDictionary.getOres("ingotBlackSteel");
		
		KnowledgeListMF.pietrayRecipe =
		GameRegistry.addShapedRecipe(new ItemStack(FoodListMF.pie_tray), new Object[]
		{
			"III",
			'I', Items.iron_ingot,
		});
		KnowledgeListMF.caketinRecipe =
		GameRegistry.addShapedRecipe(new ItemStack(FoodListMF.cake_tin), new Object[]
		{
			" I ",
			"I I",
			" I ",
			'I', Items.iron_ingot,
		});
		for(ItemStack steel: steels)
		{
			GameRegistry.addRecipe(new ItemStack(Blocks.rail, 64), new Object[]
			{
				"I I",
				"ISI",
				"I I",
				'I', steel,
				'S', Items.stick,
			});
		}
		GameRegistry.addSmelting(FoodListMF.horse_raw, new ItemStack(FoodListMF.horse_cooked), 0.2F);
		GameRegistry.addSmelting(FoodListMF.wolf_raw, new ItemStack(FoodListMF.wolf_cooked), 0.2F);
		
		GameRegistry.addSmelting(FoodListMF.raw_bread, new ItemStack(Items.bread), 0);
		GameRegistry.addSmelting(FoodListMF.dough, new ItemStack(FoodListMF.breadroll), 0);
		GameRegistry.addSmelting(FoodListMF.generic_meat_uncooked, new ItemStack(FoodListMF.generic_meat_cooked), 0);
		GameRegistry.addSmelting(FoodListMF.generic_meat_strip_uncooked, new ItemStack(FoodListMF.generic_meat_strip_cooked), 0);
		GameRegistry.addSmelting(FoodListMF.generic_meat_chunk_uncooked, new ItemStack(FoodListMF.generic_meat_chunk_cooked), 0);
		GameRegistry.addSmelting(FoodListMF.generic_meat_mince_uncooked, new ItemStack(FoodListMF.generic_meat_mince_cooked), 0);
		GameRegistry.addSmelting(FoodListMF.curds, new ItemStack(BlockListMF.cheese_wheel), 0);
		GameRegistry.addSmelting(FoodListMF.bowl_water_salt, new ItemStack(FoodListMF.salt), 0);
		
		GameRegistry.addShapedRecipe(new ItemStack(Blocks.cobblestone), new Object[]
		{
			"C",
			'C', BlockListMF.cobble_brick
		});
		GameRegistry.addShapedRecipe(new ItemStack(Blocks.cobblestone), new Object[]
		{
			"C",
			'C', BlockListMF.cobble_pavement
		});
		KnowledgeListMF.stoneBricksR.add(
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.cobble_brick, 4), new Object[]
		{
			"C C",
			"   ",
			"C C",
			'C', Blocks.cobblestone
		}));
		KnowledgeListMF.stoneBricksR.add(
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.cobble_pavement, 4), new Object[]
		{
			"CC",
			"CC",
			'C', Blocks.cobblestone
		}));
		
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.reinforced_stone), new Object[]
		{
			"C",
			'C', BlockListMF.reinforced_stone_bricks
		});
		KnowledgeListMF.stoneBricksR.add(
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.reinforced_stone_bricks, 4), new Object[]
		{
			"CC",
			"CC",
			'C', BlockListMF.reinforced_stone
		}));
		
		GameRegistry.addShapedRecipe(new ItemStack(Blocks.dirt), new Object[]
		{
			"C",
			'C', BlockListMF.mud_brick
		});
		GameRegistry.addShapedRecipe(new ItemStack(Blocks.dirt), new Object[]
		{
			"C",
			'C', BlockListMF.mud_pavement
		});
		KnowledgeListMF.stoneBricksR.add(
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.mud_brick, 4), new Object[]
		{
			"C C",
			"   ",
			"C C",
			'C', Blocks.dirt
		}));
		KnowledgeListMF.stoneBricksR.add(
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.mud_pavement, 4), new Object[]
		{
			"CC",
			"CC",
			'C', Blocks.dirt
		}));
		
		KnowledgeListMF.framedGlassR =
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.framed_glass), new Object[]
		{
			"PGP",
			'P', ComponentListMF.plank,
			'G', Blocks.glass
		});
		KnowledgeListMF.windowR =
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.window), new Object[]
		{
			" P ",
			"PGP",
			" P ",
			'P', ComponentListMF.plank,
			'G', Blocks.glass
		});
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.framed_pane, 16), new Object[]
		{
			"GGG",
			"GGG",
			'G', BlockListMF.framed_glass
		});
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.window_pane, 16), new Object[]
		{
			"GGG",
			"GGG",
			'G', BlockListMF.window
		});
		KnowledgeListMF.thatchR =
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.thatch, 1), new Object[]
		{
			"HH",
			"HH",
			'H', new ItemStack(Blocks.tallgrass, 1, 1)
		});
		KnowledgeListMF.clayWallR =
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.clayWall, 4), new Object[]
		{
			" P ",
			"PCP",
			" P ",
			'P', ComponentListMF.plank,
			'C', Blocks.clay
		});
		addFood();
		
	}

	private static void addFood()
	{
		GameRegistry.addSmelting(FoodListMF.sweetroll_raw, new ItemStack(FoodListMF.sweetroll_uniced), 0F);
		GameRegistry.addSmelting(FoodListMF.cake_raw, new ItemStack(FoodListMF.cake_uniced), 0F);
		GameRegistry.addSmelting(FoodListMF.cake_carrot_raw, new ItemStack(FoodListMF.cake_carrot_uniced), 0F);
		GameRegistry.addSmelting(FoodListMF.cake_choc_raw, new ItemStack(FoodListMF.cake_choc_uniced), 0F);
		GameRegistry.addSmelting(FoodListMF.cake_bf_raw, new ItemStack(FoodListMF.cake_bf_uniced), 0F);
		GameRegistry.addSmelting(FoodListMF.pie_meat_uncooked, new ItemStack(FoodListMF.pie_meat_cooked), 0F);
		GameRegistry.addSmelting(FoodListMF.pie_shepard_uncooked, new ItemStack(FoodListMF.pie_shepard_cooked), 0F);
		GameRegistry.addSmelting(FoodListMF.pie_apple_uncooked, new ItemStack(FoodListMF.pie_apple_cooked), 0F);
		GameRegistry.addSmelting(FoodListMF.pie_berry_uncooked, new ItemStack(FoodListMF.pie_berry_cooked), 0F);

		KnowledgeListMF.meatpieOut =
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.pie_meat), new Object[]
		{
			"F",
			'F', FoodListMF.pie_meat_cooked
		});
		KnowledgeListMF.shepardOut =
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.pie_shepards), new Object[]
		{
			"F",
			'F', FoodListMF.pie_shepard_cooked
		});
		KnowledgeListMF.appleOut =
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.pie_apple), new Object[]
		{
			"F",
			'F', FoodListMF.pie_apple_cooked
		});
		KnowledgeListMF.berryOut =
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.pie_berry), new Object[]
		{
			"F",
			'F', FoodListMF.pie_berry_cooked
		});
	}
}
