package io.github.johnytech6.theft;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
public class InventoryEditor{
	
}
/*
public class InventoryEditor implements PlayerInventory {

	public InventoryEditor(Player src) {
		this.reflectContents(this, this.getContents(), this.getArmorContents(), this.getExtraContents());
	}

	private void reflectContents(PlayerInventory inventory, ItemStack[] items, ItemStack[] armor,
			ItemStack[] extraSlots) {
		try {
			Field modifiers = Field.class.getDeclaredField("modifiers");
			modifiers.setAccessible(true);
			Field field = PlayerInventory.class.getField("items");
			modifiers.setInt(field, field.getModifiers() & -17);
			field.set(inventory, items);
			field = PlayerInventory.class.getField("armor");
			modifiers.setInt(field, field.getModifiers() & -17);
			field.set(inventory, armor);
			field = PlayerInventory.class.getField("extraSlots");
			modifiers.setInt(field, field.getModifiers() & -17);
			field.set(inventory, extraSlots);
			field = PlayerInventory.class.getDeclaredField("f");
			field.setAccessible(true);
			modifiers.setInt(field, field.getModifiers() & -17);
			field.set(inventory, Arrays.asList(items, armor, extraSlots));
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException var7) {
			var7.printStackTrace();
		}

	}

	public Inventory getInventory() {
		return this.inventory;
	}

	private void saveOnExit() {
		if (this.transaction.isEmpty()) {
			this.owner.saveData();
		}

	}

	public void onClose(CraftHumanEntity who) {
		super.onClose(who);
		this.saveOnExit();
	}

	public int getSize() {
		return super.getSize() + 4;
	}

	public ItemStack getItem(int i) {
		NonNullList<ItemStack> list = this.items;
		if (i >= list.size()) {
			i -= list.size();
			list = this.armor;
		} else {
			i = this.getReversedItemSlotNum(i);
		}

		if (i >= list.size()) {
			i -= list.size();
			list = this.extraSlots;
		} else if (list == this.armor) {
			i = this.getReversedArmorSlotNum(i);
		}

		return i >= list.size() ? ItemStack.a : (ItemStack) list.get(i);
	}

	public ItemStack splitStack(int i, int j) {
		NonNullList<ItemStack> list = this.items;
		if (i >= list.size()) {
			i -= list.size();
			list = this.armor;
		} else {
			i = this.getReversedItemSlotNum(i);
		}

		if (i >= list.size()) {
			i -= list.size();
			list = this.extraSlots;
		} else if (list == this.armor) {
			i = this.getReversedArmorSlotNum(i);
		}

		if (i >= list.size()) {
			return ItemStack.a;
		} else {
			return list != null && !((ItemStack) list.get(i)).isEmpty() ? ContainerUtil.a(list, i, j) : ItemStack.a;
		}
	}

	public ItemStack splitWithoutUpdate(int i) {
		NonNullList<ItemStack> list = this.items;
		if (i >= list.size()) {
			i -= list.size();
			list = this.armor;
		} else {
			i = this.getReversedItemSlotNum(i);
		}

		if (i >= list.size()) {
			i -= list.size();
			list = this.extraSlots;
		} else if (list == this.armor) {
			i = this.getReversedArmorSlotNum(i);
		}

		if (i >= list.size()) {
			return ItemStack.a;
		} else if (list != null && !((ItemStack) list.get(i)).isEmpty()) {
			ItemStack itemstack = (ItemStack) list.get(i);
			list.set(i, ItemStack.a);
			return itemstack;
		} else {
			return ItemStack.a;
		}
	}

	public void setItem(int i, ItemStack itemStack) {
		NonNullList<ItemStack> list = this.items;
		if (i >= list.size()) {
			i -= list.size();
			list = this.armor;
		} else {
			i = this.getReversedItemSlotNum(i);
		}

		if (i >= list.size()) {
			i -= list.size();
			list = this.extraSlots;
		} else if (list == this.armor) {
			i = this.getReversedArmorSlotNum(i);
		}

		if (i >= list.size()) {
			this.player.drop(itemStack, true);
		} else {
			if (list != null) {
				list.set(i, itemStack);
			}

		}
	}

	private int getReversedItemSlotNum(int i) {
		return i >= 27 ? i - 27 : i + 9;
	}

	private int getReversedArmorSlotNum(int i) {
		if (i == 0) {
			return 3;
		} else if (i == 1) {
			return 2;
		} else if (i == 2) {
			return 1;
		} else {
			return i == 3 ? 0 : i;
		}
	}

	public boolean hasCustomName() {
		return true;
	}

	public String getName() {
		return "§1§l" + this.player.getName() + "'s inventory";
	}

	public boolean a(EntityHuman entityhuman) {
		return true;
	}

	public void update() {
		super.update();
		this.player.inventory.update();
	}

	@Override
	public int getMaxStackSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMaxStackSize(int var1) {
		// TODO Auto-generated method stub

	}

	@Override
	public @Nullable ItemStack getItem(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull HashMap<Integer, ItemStack> addItem(@NotNull ItemStack... var1) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull HashMap<Integer, ItemStack> removeItem(@NotNull ItemStack... var1) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull ItemStack[] getContents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setContents(@NotNull ItemStack[] var1) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public @NotNull ItemStack[] getStorageContents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStorageContents(@NotNull ItemStack[] var1) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(@NotNull Material var1) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(@Nullable ItemStack var1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(@NotNull Material var1, int var2) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(@Nullable ItemStack var1, int var2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAtLeast(@Nullable ItemStack var1, int var2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public @NotNull HashMap<Integer, ? extends ItemStack> all(@NotNull Material var1) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull HashMap<Integer, ? extends ItemStack> all(@Nullable ItemStack var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int first(@NotNull Material var1) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int first(@NotNull ItemStack var1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int firstEmpty() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void remove(@NotNull Material var1) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(@NotNull ItemStack var1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear(int var1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public @NotNull List<HumanEntity> getViewers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull InventoryType getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull ListIterator<ItemStack> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull ListIterator<ItemStack> iterator(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull ItemStack[] getArmorContents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull ItemStack[] getExtraContents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable ItemStack getHelmet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable ItemStack getChestplate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable ItemStack getLeggings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable ItemStack getBoots() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setItem(int var1, @Nullable ItemStack var2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setArmorContents(@Nullable ItemStack[] var1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setExtraContents(@Nullable ItemStack[] var1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHelmet(@Nullable ItemStack var1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setChestplate(@Nullable ItemStack var1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLeggings(@Nullable ItemStack var1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBoots(@Nullable ItemStack var1) {
		// TODO Auto-generated method stub

	}

	@Override
	public @NotNull ItemStack getItemInMainHand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setItemInMainHand(@Nullable ItemStack var1) {
		// TODO Auto-generated method stub

	}

	@Override
	public @NotNull ItemStack getItemInOffHand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setItemInOffHand(@Nullable ItemStack var1) {
		// TODO Auto-generated method stub

	}

	@Override
	public @NotNull ItemStack getItemInHand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setItemInHand(@Nullable ItemStack var1) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getHeldItemSlot() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setHeldItemSlot(int var1) {
		// TODO Auto-generated method stub

	}

	@Override
	public @Nullable HumanEntity getHolder() {
		// TODO Auto-generated method stub
		return null;
	}
}
*/