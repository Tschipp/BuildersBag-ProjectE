package tschipp.bbprojecte;

import moze_intel.projecte.api.item.IItemEmc;
import moze_intel.projecte.gameObjs.ObjHandler;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;
import tschipp.buildersbag.api.AbstractBagModule;
import tschipp.buildersbag.api.IBagCap;
import tschipp.buildersbag.api.ModulePriority;
import tschipp.buildersbag.common.inventory.ItemHandlerWithPredicate;

public class ProjectEBagModule extends AbstractBagModule {

	private ItemStackHandler handler = new ItemHandlerWithPredicate(1, (stack, slot) -> {
		return stack.getItem() instanceof IItemEmc;
	});

	private static final ItemStack DISPLAY = new ItemStack(ObjHandler.philosStone);

	public ProjectEBagModule() {
		super(BBProjectE.MODID + ":projecte");
	}

	@Override
	public NonNullList<ItemStack> getPossibleStacks(IBagCap bag, EntityPlayer player) {
		return NonNullList.create();
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag = super.serializeNBT();
		tag.setTag("Inventory", handler.serializeNBT());
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		super.deserializeNBT(nbt);
		handler.deserializeNBT(nbt.getCompoundTag("Inventory"));
	}

	@Override
	public boolean doesntUseOwnInventory() {
		return false;
	}

	@Override
	public ItemStackHandler getInventory() {
		return handler;
	}

	@Override
	public ItemStack getDisplayItem() {
		return DISPLAY;
	}

	@Override
	public ModulePriority getPriority() {
		return ModulePriority.HIGHEST;
	}

	@Override
	public NonNullList<ItemStack> createStackWithCount(ItemStack stack, int count, IBagCap bag, EntityPlayer player) {

		NonNullList<ItemStack> created = NonNullList.create();
		ItemStack storedItem = handler.getStackInSlot(0);
		if (storedItem.isEmpty())
			return created;

		long emcValue = EMCHelper.getEmcValue(stack);
		if (emcValue == 0)
			return created;

		IItemEmc emcHolder = (IItemEmc) storedItem.getItem();
		long storedEmc = emcHolder.getStoredEmc(storedItem);

		if (emcValue > storedEmc)
			return created;

		for(int i = 0; i < count; i++)
		{
			emcHolder.extractEmc(storedItem, emcValue);
			created.add(stack.copy());
			storedEmc = emcHolder.getStoredEmc(storedItem);
			if(emcValue > storedEmc)
				break;
		}

		return created;
	}
}
