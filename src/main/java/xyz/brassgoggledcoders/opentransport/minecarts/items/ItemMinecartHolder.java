package xyz.brassgoggledcoders.opentransport.minecarts.items;

import com.teamacronymcoders.base.items.minecarts.ItemMinecartBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.opentransport.api.OpenTransportAPI;
import xyz.brassgoggledcoders.opentransport.api.wrappers.block.BlockWrapper;
import xyz.brassgoggledcoders.opentransport.api.wrappers.block.IBlockWrapper;
import xyz.brassgoggledcoders.opentransport.minecarts.MinecartTransport;
import xyz.brassgoggledcoders.opentransport.minecarts.entities.EntityMinecartHolder;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

public class ItemMinecartHolder extends ItemMinecartBase {
    boolean creativeTabSet = false;

    public ItemMinecartHolder(CreativeTabs creativeTabs) {
        super("minecart.holder");
        this.setCreativeTab(creativeTabs);
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos blockPos, EnumHand hand,
                                      EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemStack = player.getHeldItem(hand);
        EntityMinecartHolder entityFromItem = this.getEntityFromItem(world, itemStack);
        EnumActionResult placed = placeCart(itemStack, world, blockPos, entityFromItem);
        if(placed == EnumActionResult.SUCCESS) {
            entityFromItem.getBlockWrapper().onPlace(player, hand, itemStack);
        }
        return placed;
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(@Nonnull ItemStack cartItemStack) {
        String displayName = "";

        displayName += Items.MINECART.getItemStackDisplayName(cartItemStack);

        ItemStack wrapperItemStack = this.getBlockWrapper(cartItemStack).getItemStack();
        displayName += " " + I18n.format("separator.with") + " ";
        displayName += wrapperItemStack.getItem().getItemStackDisplayName(wrapperItemStack);

        return displayName;
    }

    public IBlockWrapper getBlockWrapper(ItemStack itemStack) {
        return OpenTransportAPI.getBlockWrapperRegistry().getLoadedBlockWrapper(itemStack);
    }

    @Nonnull
    @Override
    public EntityMinecartHolder getEntityFromItem(World world, ItemStack itemStack) {
        EntityMinecartHolder minecart = new EntityMinecartHolder(world);
        minecart.setBlockWrapper(this.getBlockWrapper(itemStack).copy());
        minecart.setItemCart(itemStack);
        return minecart;
    }

    @Override
    @Nonnull
    public Item setCreativeTab(@Nonnull CreativeTabs tab) {
        if (!creativeTabSet) {
            super.setCreativeTab(tab);
            this.creativeTabSet = true;
        }
        return this;
    }

    @Override
    public List<String> getModelNames(List<String> modelNames) {
        modelNames.add("minecart.holder");
        return modelNames;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(@Nonnull Item item, CreativeTabs tab, NonNullList<ItemStack> itemStacks) {
        itemStacks.addAll(this.getAllSubItems(new ArrayList<>()));
    }

    @Override
    public List<ItemStack> getAllSubItems(List<ItemStack> itemStacks) {
        OpenTransportAPI.getBlockWrapperRegistry().getAllBlockWrappers().forEach((name, blockWrapper) ->
                itemStacks.add(getStackForBlockWrapper(blockWrapper)));
        return itemStacks;
    }

    public static ItemStack getStackForBlockWrapper(IBlockWrapper blockWrapper) {
        ItemStack itemStack = new ItemStack(MinecartTransport.itemMinecartHolder, 1, 0);
        NBTTagCompound nbtTagCompound = itemStack.getOrCreateSubCompound("blockWrapper");
        nbtTagCompound.setString("name", blockWrapper.getUnlocalizedName());
        return itemStack;
    }

    @Override
    public Item getItem() {
        return this;
    }
}
