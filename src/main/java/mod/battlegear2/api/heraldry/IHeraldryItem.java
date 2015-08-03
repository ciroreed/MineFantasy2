package mod.battlegear2.api.heraldry;

import net.minecraft.item.ItemStack;


public interface IHeraldryItem {

    public static final String heraldryTag = "hc1";

    public enum HeraldyRenderPassess{
        Pattern,
        SecondaryColourTrim,
        PostRenderIcon
    }

    /**
     * Returns the "base" icon. This icon will be coloured the primary colour
     */

    /**
     * Returns true if the given itemstack has heraldy attached
     */
    public boolean hasHeraldry(ItemStack stack);
    /**
     * Returns the current heraldy code, this will only be called on ItemStacks that have been found to have heraldry using the hasHeraldryMethod
     */
    public byte[] getHeraldry(ItemStack stack);

    /**
     * Saves the given heraldry code in the given stack. It is recommended to save the Byte array representation of the crest in the NBT
     */
    public void setHeraldry(ItemStack stack, byte[] data);

    /**
     * Removes the heraldry code from the item
     */
    public void removeHeraldry(ItemStack item);

    /**
     * Returns true if the default renderer should perform the given render pass
     */
    public boolean shouldDoPass(HeraldyRenderPassess pass);

    /**
     * Returns true if the default renderer should be used.
     * If the method returns true, the default renderer will be attached.
     * If this method returns false it is the modders responsibility to attach an appropriate renderer
     */
    public boolean useDefaultRenderer();

}