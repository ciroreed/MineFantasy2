package minefantasy.mf2.block.crafting;

import java.util.Random;

import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.block.tileentity.TileEntityResearch;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockResearchStation extends BlockContainer
{
    private int tier = 0;
    
    private static String NAME = "researchStation";
    
    public BlockResearchStation()
    {
        super(Material.wood);
        
        GameRegistry.registerBlock(this, "MF_Research");
        setUnlocalizedName("minefantasy2:" +NAME);
		this.setStepSound(Block.soundTypeWood);
		this.setHardness(5F);
		this.setResistance(2F);
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabMF.tabUtil);
    }
    
public String getName()
{
	return NAME;
}
    
    @Override
    public boolean isFullCube()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state,EntityLivingBase user, ItemStack item)
    {
        int direction = MathHelper.floor_double(user.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        world.setBlockState(pos, getStateFromMeta(direction), 2);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer user, EnumFacing side, float xOffset, float yOffset, float zOffset)
    {
    	TileEntityResearch tile = getTile(world, pos);
    	if(tile != null && (world.isAirBlock(pos.add(0, 1, 0)) || !world.isSideSolid(pos.add(0, 1, 0), EnumFacing.DOWN)))
    	{
    		if(!(side == EnumFacing.UP && tile.interact(user)))
    		{
    			user.openGui(MineFantasyII.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
    		}
    	}
        return true;
    }
    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer user)
    {
    	TileEntityResearch tile = getTile(world, pos);
    	if(tile != null && (world.isAirBlock(pos.add(0, 1, 0)) || !world.isSideSolid(pos.add(0, 1, 0), EnumFacing.DOWN)))
    	{
			tile.interact(user);
    	}
    }

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityResearch();
	}

	private TileEntityResearch getTile(World world, BlockPos pos)
	{
		return (TileEntityResearch)world.getTileEntity(pos);
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        super.breakBlock(world, pos, state);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return Blocks.crafting_table.getIcon(side, meta);
	}
	@Override
	public int getRenderType()
	{
		return BlockListMF.research_RI;
	}
	private Random rand = new Random();
}