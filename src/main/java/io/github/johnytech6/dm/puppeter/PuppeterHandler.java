package io.github.johnytech6.dm.puppeter;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.johnytech6.JohnytechPlugin;
import io.github.johnytech6.dm.DMHandler;

public class PuppeterHandler {

    // ---------------------------SINGLETON
    // IMPLEMENTATION-------------------------------------------
    // static variable single_instance of type Singleton
    private static PuppeterHandler single_instance = null;

    // private constructor restricted to this class itself
    private PuppeterHandler() {
    }

    public static PuppeterHandler getInstance() {
        if (single_instance == null)
            single_instance = new PuppeterHandler();

        return single_instance;
    }
    // --------------------------------------------------------------------------------------------

    private static DMHandler dmh = DMHandler.getInstance();

    // list of morphed puppeter
    private ArrayList<Puppeter> puppeters = new ArrayList<Puppeter>();
    // list of puppeter
    private ArrayList<Puppeter> morphedPuppeters = new ArrayList<Puppeter>();

    /**
     * @param ep
     */
    public void Unmorph(Player ep) {
        Puppet pu = GetMorphPuppeter(ep.getName()).getPuppet();
        Entity e = pu.getEntity();
        if (e instanceof ArmorStand) {
            UnmorphOfArmorStand(ep);
        } else {
            UnmorphMob(ep);
        }
    }

    /**
     * UnMorph the puppeter
     *
     * @param ep
     */
    public void UnmorphMob(Player ep) {

        Puppeter Morphedpuppeter = GetMorphPuppeter(ep.getName());
        Player p = Morphedpuppeter.getPlayer();
        Puppet pu = Morphedpuppeter.getPuppet();
        Entity e = pu.getEntity();

        p.chat("/unmorph");

        // remove item of entity of the player
        p.getInventory().setItemInMainHand(((LivingEntity) e).getEquipment().getItemInMainHand());
        p.getInventory().setItemInOffHand((((LivingEntity) e).getEquipment().getItemInOffHand()));
        p.getInventory().setHelmet(((LivingEntity) e).getEquipment().getHelmet());
        p.getInventory().setChestplate(((LivingEntity) e).getEquipment().getChestplate());
        p.getInventory().setLeggings(((LivingEntity) e).getEquipment().getLeggings());
        p.getInventory().setBoots(((LivingEntity) e).getEquipment().getBoots());

        // give item to the entity
        ((LivingEntity) e).getEquipment().setItemInMainHand(pu.getItemInMainHand());
        ((LivingEntity) e).getEquipment().setItemInOffHand(pu.getItemInOffHand());
        ((LivingEntity) e).getEquipment().setHelmet(pu.getHelmet());
        ((LivingEntity) e).getEquipment().setChestplate(pu.getChestplate());
        ((LivingEntity) e).getEquipment().setLeggings(pu.getLeggings());
        ((LivingEntity) e).getEquipment().setBoots(pu.getBoots());

        e.teleport(p.getLocation());
        e.setInvulnerable(false);
        ((LivingEntity) e).removePotionEffect(PotionEffectType.INVISIBILITY);
        e.setSilent(false);

        p.removePassenger(e);

        dmh.dmInvisibility(dmh.getDm(p.getName()), true);
        dmh.dmVision(dmh.getDm(p.getName()), true);

        RemoveMorphPlayer(Morphedpuppeter);
    }

    public void UnmorphOfArmorStand(Player ep) {

        Puppeter Morphedpuppeter = GetMorphPuppeter(ep.getName());
        Player p = Morphedpuppeter.getPlayer();
        Puppet pu = Morphedpuppeter.getPuppet();
        Entity e = pu.getEntity();
        EntityEquipment armorStandEquipement = ((ArmorStand) e).getEquipment();

        // remove item of entity of the player
        p.getInventory().setItemInMainHand(armorStandEquipement.getItemInMainHand());
        p.getInventory().setItemInOffHand(armorStandEquipement.getItemInOffHand());
        p.getInventory().setHelmet(armorStandEquipement.getHelmet());
        p.getInventory().setChestplate(armorStandEquipement.getChestplate());
        p.getInventory().setLeggings(armorStandEquipement.getLeggings());
        p.getInventory().setBoots(armorStandEquipement.getBoots());

        // give item to the entity
        armorStandEquipement.setItemInMainHand(pu.getItemInMainHand());
        armorStandEquipement.setItemInOffHand(pu.getItemInOffHand());
        armorStandEquipement.setHelmet(pu.getHelmet());
        armorStandEquipement.setChestplate(pu.getChestplate());
        armorStandEquipement.setLeggings(pu.getLeggings());
        armorStandEquipement.setBoots(pu.getBoots());

        e.teleport(p.getLocation());
        ((ArmorStand) e).setCollidable(true);
        e.setInvulnerable(false);
        ((ArmorStand) e).setVisible(true);

        RemoveMorphPlayer(Morphedpuppeter);
    }

    /**
     * Take the inventory of ArmorStand and make this armorStand disapear
     *
     * @param p
     * @param armorStand
     */
    public void MorphInArmorStand(final Player p, ArmorStand armorStand, EquipmentSlot selectedEquipment,
                                  ItemStack itemHold) {

        EntityEquipment armorStandEquipement = armorStand.getEquipment();
        final ItemStack EEitemInMainHand = armorStandEquipement.getItemInMainHand();

        Puppeter puppeter = getPuppeter(p.getName());
        puppeter.setPuppet(new Puppet(armorStand));

        // Puppeter is in list of MorphedPlayer
        AddMorphPuppeter(puppeter);

        // teleport player to entity last location
        p.teleport(armorStand.getLocation());

        // Set armor stand invisible incollidable and invulnerable
        armorStand.setVisible(false);
        armorStand.setCollidable(false);
        armorStand.setInvulnerable(true);

        storeArmorStandEquipment(puppeter.getPuppet(), armorStandEquipement, selectedEquipment, itemHold);

        giveArmorOfAT(p, armorStandEquipement, selectedEquipment, itemHold);

        removeEquipmentFromAS(armorStandEquipement);


        Bukkit.getScheduler().scheduleSyncDelayedTask(JohnytechPlugin.getPlugin(), new Runnable() {
            public void run() {
                itemInHand(p, EEitemInMainHand);
            }
        }, 2L);

    }

    /**
     * Give items of entity to player of ArmorStand
     *
     * @param p
     * @param ee
     * @param selectedEquipment
     */
    private void giveArmorOfAT(Player p, EntityEquipment ee, EquipmentSlot selectedEquipment, ItemStack itemHold) {

        if (selectedEquipment == EquipmentSlot.HAND) {
            p.getInventory().setItemInOffHand(ee.getItemInOffHand());
            p.getInventory().setHelmet(ee.getHelmet());
            p.getInventory().setChestplate(ee.getChestplate());
            p.getInventory().setLeggings(ee.getLeggings());
            p.getInventory().setBoots(ee.getBoots());
        } else if (selectedEquipment == EquipmentSlot.OFF_HAND) {
            p.getInventory().setItemInOffHand(itemHold);
            p.getInventory().setHelmet(ee.getHelmet());
            p.getInventory().setChestplate(ee.getChestplate());
            p.getInventory().setLeggings(ee.getLeggings());
            p.getInventory().setBoots(ee.getBoots());
        } else if (selectedEquipment == EquipmentSlot.HEAD) {
            p.getInventory().setItemInOffHand(ee.getItemInOffHand());
            p.getInventory().setHelmet(itemHold);
            p.getInventory().setChestplate(ee.getChestplate());
            p.getInventory().setLeggings(ee.getLeggings());
            p.getInventory().setBoots(ee.getBoots());
        } else if (selectedEquipment == EquipmentSlot.CHEST) {
            p.getInventory().setItemInOffHand(ee.getItemInOffHand());
            p.getInventory().setHelmet(ee.getHelmet());
            p.getInventory().setChestplate(itemHold);
            p.getInventory().setLeggings(ee.getLeggings());
            p.getInventory().setBoots(ee.getBoots());
        } else if (selectedEquipment == EquipmentSlot.LEGS) {
            p.getInventory().setItemInOffHand(ee.getItemInOffHand());
            p.getInventory().setHelmet(ee.getHelmet());
            p.getInventory().setChestplate(ee.getChestplate());
            p.getInventory().setLeggings(itemHold);
            p.getInventory().setBoots(ee.getBoots());
        } else if (selectedEquipment == EquipmentSlot.FEET) {
            p.getInventory().setItemInOffHand(ee.getItemInOffHand());
            p.getInventory().setHelmet(ee.getHelmet());
            p.getInventory().setChestplate(ee.getChestplate());
            p.getInventory().setLeggings(ee.getLeggings());
            p.getInventory().setBoots(itemHold);
        }
    }

    /**
     * Store items of ArmorStand in Puppet
     *
     * @param pu
     * @param ee
     */
    private void storeArmorStandEquipment(Puppet pu, EntityEquipment ee, EquipmentSlot selectedEquipment,
                                          ItemStack itemhold) {

        if (selectedEquipment == EquipmentSlot.HAND) {
            pu.setItemInMainHand(itemhold);
            pu.setItemInOffHand(ee.getItemInOffHand());
            pu.setHelmet(ee.getHelmet());
            pu.setChestplate(ee.getChestplate());
            pu.setLeggings(ee.getLeggings());
            pu.setBoots(ee.getBoots());
        } else if (selectedEquipment == EquipmentSlot.OFF_HAND) {
            pu.setItemInMainHand(ee.getItemInMainHand());
            pu.setItemInOffHand(itemhold);
            pu.setHelmet(ee.getHelmet());
            pu.setChestplate(ee.getChestplate());
            pu.setLeggings(ee.getLeggings());
            pu.setBoots(ee.getBoots());
        } else if (selectedEquipment == EquipmentSlot.HEAD) {
            pu.setItemInMainHand(ee.getItemInMainHand());
            pu.setItemInOffHand(ee.getItemInOffHand());
            pu.setHelmet(itemhold);
            pu.setChestplate(ee.getChestplate());
            pu.setLeggings(ee.getLeggings());
            pu.setBoots(ee.getBoots());
        } else if (selectedEquipment == EquipmentSlot.CHEST) {
            pu.setItemInMainHand(ee.getItemInMainHand());
            pu.setItemInOffHand(ee.getItemInOffHand());
            pu.setHelmet(ee.getHelmet());
            pu.setChestplate(itemhold);
            pu.setLeggings(ee.getLeggings());
            pu.setBoots(ee.getBoots());
        } else if (selectedEquipment == EquipmentSlot.LEGS) {
            pu.setItemInMainHand(ee.getItemInMainHand());
            pu.setItemInOffHand(ee.getItemInOffHand());
            pu.setHelmet(ee.getHelmet());
            pu.setChestplate(ee.getChestplate());
            pu.setLeggings(itemhold);
            pu.setBoots(ee.getBoots());
        } else if (selectedEquipment == EquipmentSlot.FEET) {
            pu.setItemInMainHand(ee.getItemInMainHand());
            pu.setItemInOffHand(ee.getItemInOffHand());
            pu.setHelmet(ee.getHelmet());
            pu.setChestplate(ee.getChestplate());
            pu.setLeggings(ee.getLeggings());
            pu.setBoots(itemhold);
        }

    }

    /**
     * Delete all items of entity
     *
     * @param ee
     */
    private void removeEquipmentFromAS(EntityEquipment ee) {
        ee.setItemInMainHand(null);
        ee.setItemInOffHand(null);
        ee.setHelmet(null);
        ee.setChestplate(null);
        ee.setLeggings(null);
        ee.setBoots(null);
    }

    /**
     * Set the armor selected onto you
     *
     * @param p
     * @param itemInHand
     */
    private void itemInHand(Player p, ItemStack itemInHand) {
        Bukkit.getConsoleSender().sendMessage(itemInHand.toString());
        p.getInventory().setItemInMainHand(itemInHand);
    }

    /*
     * private boolean isItemArmor( ItemStack itemStack) { if (itemStack == null)
     * return false; final String typeNameString = itemStack.getType().name(); if
     * (typeNameString.endsWith("_HELMET") || typeNameString.endsWith("_CHESTPLATE")
     * || typeNameString.endsWith("_LEGGINGS") || typeNameString.endsWith("_BOOTS"))
     * { return true; }
     *
     * return false; }
     */

    /**
     * Transform dm Puppeter into the entity right clicked
     *
     * @param p
     * @param e
     */
    public void Morph(Player p, Entity e) {

        Puppeter puppeter = getPuppeter(p.getName());
        puppeter.setPuppet(new Puppet(e));
        Puppet pu = puppeter.getPuppet();

        // Puppeter is in list of MorphedPlayer
        AddMorphPuppeter(puppeter);

        // teleport player to entity last location
        p.teleport(e.getLocation());

        // make entity invincible, silent and invisible
        ((LivingEntity) e)
                .addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 10, false, false));
        e.setInvulnerable(true);
        e.setSilent(true);

        p.addPassenger(e);

        // give items of entity to player
        p.getInventory().setItemInMainHand(((LivingEntity) e).getEquipment().getItemInMainHand());
        p.getInventory().setItemInOffHand((((LivingEntity) e).getEquipment().getItemInOffHand()));
        p.getInventory().setHelmet(((LivingEntity) e).getEquipment().getHelmet());
        p.getInventory().setChestplate(((LivingEntity) e).getEquipment().getChestplate());
        p.getInventory().setLeggings(((LivingEntity) e).getEquipment().getLeggings());
        p.getInventory().setBoots(((LivingEntity) e).getEquipment().getBoots());

        // store items of entity
        pu.setItemInMainHand(((LivingEntity) e).getEquipment().getItemInMainHand());
        pu.setItemInOffHand((((LivingEntity) e).getEquipment().getItemInOffHand()));
        pu.setHelmet(((LivingEntity) e).getEquipment().getHelmet());
        pu.setChestplate(((LivingEntity) e).getEquipment().getChestplate());
        pu.setLeggings(((LivingEntity) e).getEquipment().getLeggings());
        pu.setBoots(((LivingEntity) e).getEquipment().getBoots());

        // Delete all items of entity
        ((LivingEntity) e).getEquipment().setItemInMainHand(null);
        ((LivingEntity) e).getEquipment().setItemInOffHand(null);
        ((LivingEntity) e).getEquipment().setHelmet(null);
        ((LivingEntity) e).getEquipment().setChestplate(null);
        ((LivingEntity) e).getEquipment().setLeggings(null);
        ((LivingEntity) e).getEquipment().setBoots(null);

        // -----Morphy plugin handling-----
        String entityName = e.toString();
        entityName = entityName.replace("Craft", "");
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        entityName = entityName.replaceAll(regex, replacement).toLowerCase();
        p.chat("/morph " + entityName); // MORPH
        // -----Morph plugin handling-----
    }

    /**
     * Toggle Puppeter Mode(call by dm only)
     *
     * @param player
     * @return
     */
    public boolean TogglePuppeterMode(Player player, boolean verbose) {

        if (!isPlayerPuppeter(player.getName())) {
            AddPuppeter(new Puppeter(player));
            if (verbose) {
                player.sendMessage("You have puppeter power.");
            }
        } else if (isPlayerPuppeter(player.getName())) {
            if (isPlayerMorph(player.getName())) {
                Unmorph(player);
            }
            RemovePuppeter(getPuppeter(player.getName()));
            if (verbose) {
                player.sendMessage("You dont have puppeter power anymore");
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean setPuppeterMode(Player p, boolean hasPuppeterPower, boolean verbose) {

        if (hasPuppeterPower) {
            AddPuppeter(new Puppeter(p));
            if (verbose) {
                p.sendMessage("You have puppeter power.");
            }
        } else {
            if (isPlayerMorph(p.getName())) {
                Unmorph(p);
            }
            RemovePuppeter(getPuppeter(p.getName()));
            if (verbose) {
                p.sendMessage("You dont have puppeter power anymore");
            }
        }
        return true;
    }

    /**
     * Add morphing puppeter
     *
     * @param puppeter
     */
    public void AddMorphPuppeter(Puppeter puppeter) {

        if (!(isPlayerMorph(puppeter.getName()))) {
            morphedPuppeters.add(puppeter);
            puppeter.setIndex(morphedPuppeters.size() - 1);
        }
    }

    /*
     * Remove morphed puppeter
     */
    public void RemoveMorphPlayer(Puppeter pp) {
        morphedPuppeters.remove(pp);
    }

    /*
     * Check if puppeter already morph
     */
    public boolean isPlayerMorph(String name) {
        if (morphedPuppeters.size() > 0) {
            for (Puppeter pp : morphedPuppeters) {
                if (pp.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Get morphed puppeter reference with his name
     */
    public Puppeter GetMorphPuppeter(String name) {
        if (morphedPuppeters.size() > 0) {
            for (Puppeter pp : morphedPuppeters) {
                if (pp.getName() == name) {
                    return pp;
                }
            }
        }
        return null;
    }

    /*
     * Add a puppeter
     */
    public void AddPuppeter(Puppeter pp) {
        if (!(isPlayerPuppeter(pp.getName()))) {
            puppeters.add(pp);
            pp.setIndex(puppeters.size() - 1);
        }
    }

    /*
     * Remove puppeter
     */
    public void RemovePuppeter(Puppeter pp) {
        puppeters.remove(pp);
    }

    /*
     * Check if the player is in the list of all the puppeters
     */
    public boolean isPlayerPuppeter(String name) {
        if (puppeters.size() > 0) {
            for (Puppeter pp : puppeters) {
                if (pp.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Get puppeter reference with his name
     */
    public Puppeter getPuppeter(String name) {
        if (puppeters.size() > 0) {
            for (Puppeter pp : puppeters) {
                if (pp.getName().equals(name)) {
                    return pp;
                }
            }
        }
        return null;
    }

    /*
     * Get reference of the list of all morphed puppeter
     */
    public ArrayList<Puppeter> getMorphPlayers() {
        return morphedPuppeters;
    }

    /*
     * Get reference of the list of all puppeters
     */
    public ArrayList<Puppeter> getPuppeters() {
        return puppeters;
    }
}
