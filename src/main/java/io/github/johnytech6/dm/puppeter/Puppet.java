package io.github.johnytech6.dm.puppeter;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class Puppet {

	private Entity entityRef;

	private ItemStack itemInMainHand;
	private ItemStack iteminOffHand;
	private ItemStack helmet;
	private ItemStack chestplate;
	private ItemStack leggins;
	private ItemStack boots;

	/**
	 * Constructor of a reference of a entity controlled by a Puppeter.
	 * @param e The Entity controlled.
	 */
	public Puppet(Entity e) {
		this.entityRef = e;
	}

	/**
	 * Return the reference a the stored entity.
	 * @return Entity (the stored entity)
	 */
	public Entity getEntity() {
		return this.entityRef;
	}

	/**
	 * Store armor and item in main hands of the entity.
	 * @param item
	 */
	public void setItemInMainHand(ItemStack item) {
		this.itemInMainHand = item;
	}

	/**
	 * Store armor and item in off hands of the entity.
	 * @param item
	 */
	public void setItemInOffHand(ItemStack item) {
		this.iteminOffHand = item;
	}

	/**
	 * Store helmet of the entity.
	 * @param helmet
	 */
	public void setHelmet(ItemStack helmet) {
		this.helmet = helmet;
	}

	/**
	 * Store chestplate of the entity.
	 * @param chestplate
	 */
	public void setChestplate(ItemStack chestplate) {
		this.chestplate = chestplate;
	}

	/**
	 * Store leggins of the entity.
	 * @param leggins
	 */
	public void setLeggings(ItemStack leggins) {
		this.leggins = leggins;
	}

	/**
	 * Store boots of the entity.
	 * @param boots
	 */
	public void setBoots(ItemStack boots) {
		this.boots = boots;
	}

	/**
	 * Get item in main hand of the entity.
	 * @return ItemStack (item in main hand)
	 */
	public ItemStack getItemInMainHand() {
		return itemInMainHand;
	}

	/**
	 * Get item in off hand of the entity.
	 * @return ItemStack (item off hand)
	 */
	public ItemStack getItemInOffHand() {
		return iteminOffHand;
	}

	/**
	 * Get helmet of the entity.
	 * @return ItemStack (the helmet)
	 */
	public ItemStack getHelmet() {
		return helmet;
	}

	/**
	 * Get the chestplate of the entity
	 * @return ItemStack (the chestplate)
	 */
	public ItemStack getChestplate() {
		return chestplate;
	}

	/**
	 * Get the leggings of the entity.
	 * @return ItemStack (the leggins)
	 */
	public ItemStack getLeggings() {
		return leggins;
	}

	/**
	 * Get the boots of the entity.
	 * @return ItemStack (the boots)
	 */
	public ItemStack getBoots() {
		return boots;
	}


}
