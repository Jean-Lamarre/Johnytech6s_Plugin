package com.DjembeInc.johnytechPlugin.dm.puppeter;

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

	public Puppet(Entity e) {
		this.entityRef = e;
	}

	public Entity getEntity() {
		return this.entityRef;
	}

	//set armor and itemInHands
	public void setItemInMainHand(ItemStack item) {
		this.itemInMainHand = item;
	}
	public void setItemInOffHand(ItemStack item) {
		this.iteminOffHand = item;
	}
	public void setHelmet(ItemStack helmet) {
		this.helmet = helmet;
	}
	public void setChestplate(ItemStack chestplate) {
		this.chestplate = chestplate;
	}
	public void setLeggings(ItemStack leggins) {
		this.leggins = leggins;
	}
	public void setBoots(ItemStack boots) {
		this.boots = boots;
	}
	
	//get armor and itemInHands
	public ItemStack getItemInMainHand() {
		return itemInMainHand;
	}
	public ItemStack getItemInOffHand() {
		return iteminOffHand;
	}
	public ItemStack getHelmet() {
		return helmet;
	}
	public ItemStack getChestplate() {
		return chestplate;
	}
	public ItemStack getLeggings() {
		return leggins;
	}
	public ItemStack getBoots() {
		return boots;
	}


}
