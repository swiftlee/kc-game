package net.jmdev.ai;

import net.jmdev.Main;
import net.jmdev.entity.BasicMinion;
import net.jmdev.entity.RangedMinion;
import net.jmdev.entity.SiegeMinion;
import net.jmdev.entity.SuperMinion;
import net.jmdev.game.GameMode;
import net.jmdev.game.Nexus;
import net.jmdev.game.TurretPriorityHandler;
import net.minecraft.server.v1_9_R2.EntityZombie;
import net.minecraft.server.v1_9_R2.MinecraftServer;
import net.minecraft.server.v1_9_R2.NBTTagCompound;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftZombie;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class MinionAI {

    private static int waveCount = 0;
    private final Object guard = new Object();
    private java.util.Map<Integer, Location> blueNodes = new HashMap<>();
    private java.util.Map<Integer, Location> redNodes = new HashMap<>();
    private ArrayList<String> blueMinions = new ArrayList<>();
    private ArrayList<String> redMinions = new ArrayList<>();
    private java.util.Map<UUID, BukkitTask> blueTasks = new HashMap<>(); // I'm too lazy to add an object-based implementation
    private java.util.Map<UUID, BukkitTask> blueAttackTasks = new HashMap<>();
    private java.util.Map<UUID, BukkitTask> redTasks = new HashMap<>();
    private java.util.Map<UUID, BukkitTask> redAttackTasks = new HashMap<>();
    private Main plugin;
    private List<String> toAdd = new ArrayList<>();
    private List<String> toAddRed = new ArrayList<>();

    public MinionAI(Main plugin) {

        this.plugin = plugin;

    }

    private Entity getEntityByUniqueId(UUID uniqueId) {
        for (World world : Bukkit.getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                for (Entity entity : chunk.getEntities()) {
                    if (entity.getUniqueId().equals(uniqueId))
                        return entity;
                }
            }
        }

        return null;
    }

    private Map.Entry<UUID, Double> getNearestRedEnemy(EntityZombie minion) {

        Map<UUID, Double> nearestEntities = new HashMap<>();

        Collection<Entity> nearbyEntities = minion.getBukkitEntity().getNearbyEntities(10, 10, 10);
        Iterator<Entity> iterator1 = nearbyEntities.iterator();

        entityLoop:
        while (iterator1.hasNext()) {
            Entity e = iterator1.next();

            if (!e.isDead()) {

                nearestEntities.put(e.getUniqueId(), minion.getBukkitEntity().getLocation().distanceSquared(e.getLocation()));
                double closest = Collections.min(nearestEntities.values());

                for (Map.Entry<UUID, Double> entry : nearestEntities.entrySet()) {

                    if (entry.getValue().equals(closest)) {

                        EntityType type = getEntityByUniqueId(entry.getKey()).getType();

                        if (type == EntityType.PLAYER) {

                            if (!Main.teams.get(0).contains(entry.getKey())) {
                                iterator1.remove();
                                continue entityLoop;
                            } else
                                return entry;


                        } else if (type == EntityType.ZOMBIE) {

                            int counter = 0;

                            for (String redMinion : redMinions) {

                                if (redMinion.split(",")[0].equalsIgnoreCase(entry.getKey().toString()))
                                    return entry;

                                counter++;
                                if (counter == redMinions.size()) {
                                    iterator1.remove();
                                    continue entityLoop;
                                }

                            }

                        } else if (type == EntityType.SLIME) {

                            return entry;

                        }

                    }

                }

            } else {

                iterator1.remove();

            }

        }

        return null;

    }

    private Map.Entry<UUID, Double> getNearestBlueEnemy(EntityZombie minion) {

        Map<UUID, Double> nearestEntities = new HashMap<>();

        Collection<Entity> nearbyEntities = minion.getBukkitEntity().getNearbyEntities(10, 10, 10);
        Iterator<Entity> iterator1 = nearbyEntities.iterator();

        entityLoop:
        while (iterator1.hasNext()) {
            Entity e = iterator1.next();

            if (e != null && !e.isDead()) {

                nearestEntities.put(e.getUniqueId(), minion.getBukkitEntity().getLocation().distanceSquared(e.getLocation()));
                double closest = Collections.min(nearestEntities.values());

                for (Map.Entry<UUID, Double> entry : nearestEntities.entrySet()) {

                    if (entry.getValue().equals(closest)) {

                        EntityType type = getEntityByUniqueId(entry.getKey()).getType();

                        if (type == EntityType.PLAYER) {

                            if (!Main.teams.get(1).contains(entry.getKey())) {
                                iterator1.remove();
                                continue entityLoop;
                            } else
                                return entry;


                        } else if (type == EntityType.ZOMBIE) {

                            int counter = 0;

                            for (String blueMinion : blueMinions) {

                                if (blueMinion.split(",")[0].equalsIgnoreCase(entry.getKey().toString()))
                                    return entry;

                                counter++;
                                if (counter == blueMinions.size()) {
                                    iterator1.remove();
                                    continue entityLoop;
                                }


                            }

                        } else if (type == EntityType.SLIME) {

                            return entry;

                        }

                    }

                }

            } else {

                iterator1.remove();

            }

        }

        return null;

    }

    public void updateBlueMinions() {

        new BukkitRunnable() {

            @Override
            public void run() {

                Iterator<String> iterator = blueMinions.iterator();

                outer:
                while (iterator.hasNext()) {
                    synchronized (guard) {
                        String data = iterator.next();

                        UUID entityUUID = UUID.fromString(data.split(",")[0]);
                        EntityZombie minion;

                        if (getEntityByUniqueId(entityUUID) != null) {

                            minion = ((CraftZombie) getEntityByUniqueId(entityUUID)).getHandle();
                            minion.activatedTick = MinecraftServer.currentTick;

                        } else {
                            blueMinions.removeAll(Collections.singleton((String) null));
                            blueTasks.keySet().removeAll(Collections.singleton((UUID) null));
                            blueTasks.values().removeAll(Collections.singleton((BukkitTask) null));
                            blueAttackTasks.keySet().removeAll(Collections.singleton((UUID) null));
                            blueAttackTasks.values().removeAll(Collections.singleton((BukkitTask) null));
                            iterator.remove();
                            continue;
                        }

                        if (minion.getBukkitEntity().isDead()) {

                            if (blueTasks.containsKey(entityUUID))
                                blueTasks.remove(entityUUID);
                            if (blueAttackTasks.containsKey(entityUUID))
                                blueAttackTasks.remove(entityUUID);

                            blueMinions.removeAll(Collections.singleton((String) null));
                            blueTasks.keySet().removeAll(Collections.singleton((UUID) null));
                            blueTasks.values().removeAll(Collections.singleton((BukkitTask) null));
                            blueAttackTasks.keySet().removeAll(Collections.singleton((UUID) null));
                            blueAttackTasks.values().removeAll(Collections.singleton((BukkitTask) null));

                            iterator.remove();
                            continue;

                        }

                        Entity nearestEntity = null;
                        int toNode = Integer.valueOf(data.split(",")[1]); //gets the data for the node the entity is currently headed for

                        try {
                            nearestEntity = getEntityByUniqueId(getNearestRedEnemy(minion).getKey());
                        } catch (NullPointerException e1) {

                            if (minion.getBukkitEntity().getLocation().distanceSquared(blueNodes.get(toNode)) <= 25) {

                                if (blueAttackTasks.containsKey(minion.getUniqueID())) {

                                    blueAttackTasks.remove(minion.getUniqueID());

                                }

                                if (blueNodes.containsKey(toNode + 2)) {
                                    blueTasks.get(minion.getUniqueID()).cancel();
                                    blueTasks.remove(minion.getUniqueID());
                                    moveTo(blueNodes.get(toNode + 2), minion, "blue");
                                    addToBlue(minion);
                                    iterator.remove();
                                    continue;
                                }

                            } else {

                                if (blueAttackTasks.containsKey(minion.getUniqueID())) {

                                    blueAttackTasks.remove(minion.getUniqueID());

                                }
                                blueTasks.get(minion.getUniqueID()).cancel();
                                blueTasks.remove(minion.getUniqueID());
                                moveTo(blueNodes.get(toNode), minion, "blue");
                                continue;

                            }

                        }

                        if (nearestEntity instanceof Player) {

                            Player player = (Player) nearestEntity;

                            if (player.getGameMode() != org.bukkit.GameMode.SURVIVAL) {
                                if (blueAttackTasks.containsKey(minion.getUniqueID()))
                                    blueAttackTasks.remove(minion.getUniqueID());
                                continue;
                            }

                            blueTasks.get(minion.getUniqueID()).cancel();
                            blueTasks.remove(minion.getUniqueID());

                            if (blueAttackTasks.containsKey(minion.getUniqueID())) {

                                blueAttackTasks.remove(minion.getUniqueID());

                            }

                            attack(minion, player, "blue");

                        } else if (nearestEntity instanceof Zombie) {

                            blueTasks.get(minion.getUniqueID()).cancel();
                            blueTasks.remove(minion.getUniqueID());

                            if (blueAttackTasks.containsKey(minion.getUniqueID())) {

                                blueAttackTasks.remove(minion.getUniqueID());

                            }

                            attack(minion, nearestEntity, "blue");

                        } else if (nearestEntity instanceof Slime) {

                            Slime v = (Slime) nearestEntity;
                            if (!v.getUniqueId().equals(Nexus.redUUID) && !v.getUniqueId().equals(Nexus.blueUUID)) {

                                if (TurretPriorityHandler.canAttack("red", v.getUniqueId())) {

                                    blueTasks.get(minion.getUniqueID()).cancel();
                                    blueTasks.remove(minion.getUniqueID());

                                    if (blueAttackTasks.containsKey(minion.getUniqueID())) {

                                        blueAttackTasks.remove(minion.getUniqueID());

                                    }

                                    attack(minion, v, "blue");
                                    continue outer;

                                }

                                if (minion.getBukkitEntity().getLocation().distanceSquared(blueNodes.get(toNode)) <= 25) {

                                    if (blueAttackTasks.containsKey(minion.getUniqueID())) {

                                        blueAttackTasks.remove(minion.getUniqueID());

                                    }

                                    if (blueNodes.containsKey(toNode + 2)) {
                                        blueTasks.get(minion.getUniqueID()).cancel();
                                        blueTasks.remove(minion.getUniqueID());
                                        moveTo(blueNodes.get(toNode + 2), minion, "blue");
                                        addToBlue(minion);
                                        iterator.remove();
                                    }

                                } else {

                                    if (blueAttackTasks.containsKey(minion.getUniqueID())) {

                                        blueAttackTasks.remove(minion.getUniqueID());

                                    }
                                    blueTasks.get(minion.getUniqueID()).cancel();
                                    blueTasks.remove(minion.getUniqueID());
                                    moveTo(blueNodes.get(toNode), minion, "blue");

                                }

                            } else if (v.getUniqueId().equals(Nexus.redUUID)) {

                                blueTasks.get(minion.getUniqueID()).cancel();
                                blueTasks.remove(minion.getUniqueID());

                                if (blueAttackTasks.containsKey(minion.getUniqueID())) {

                                    blueAttackTasks.remove(minion.getUniqueID());

                                }

                                attack(minion, v, "blue");

                            } else {

                                if (minion.getBukkitEntity().getLocation().distanceSquared(blueNodes.get(toNode)) <= 25) {

                                    if (blueAttackTasks.containsKey(minion.getUniqueID())) {

                                        blueAttackTasks.remove(minion.getUniqueID());

                                    }

                                    if (blueNodes.containsKey(toNode + 2)) {
                                        blueTasks.get(minion.getUniqueID()).cancel();
                                        blueTasks.remove(minion.getUniqueID());
                                        moveTo(blueNodes.get(toNode + 2), minion, "blue");
                                        addToBlue(minion);
                                        iterator.remove();
                                    }

                                } else {

                                    if (blueAttackTasks.containsKey(minion.getUniqueID())) {

                                        blueAttackTasks.remove(minion.getUniqueID());

                                    }
                                    blueTasks.get(minion.getUniqueID()).cancel();
                                    blueTasks.remove(minion.getUniqueID());
                                    moveTo(blueNodes.get(toNode), minion, "blue");

                                }

                            }

                        }

                    }

                }

                blueMinions.addAll(toAdd);
                toAdd.clear();

            }

        }.runTaskTimer(plugin, 0, 3);

    }

    public void updateRedMinions() {

        new BukkitRunnable() {

            @Override
            public void run() {

                Iterator<String> iterator = redMinions.iterator();

                outer:
                while (iterator.hasNext()) {
                    synchronized (guard) {
                        String data = iterator.next();

                        UUID entityUUID = UUID.fromString(data.split(",")[0]);
                        EntityZombie minion;

                        if (getEntityByUniqueId(entityUUID) != null) {

                            minion = ((CraftZombie) getEntityByUniqueId(entityUUID)).getHandle();
                            minion.activatedTick = MinecraftServer.currentTick;

                        } else {
                            redMinions.removeAll(Collections.singleton((String) null));
                            redTasks.keySet().removeAll(Collections.singleton((UUID) null));
                            redTasks.values().removeAll(Collections.singleton((BukkitTask) null));
                            redAttackTasks.keySet().removeAll(Collections.singleton((UUID) null));
                            redAttackTasks.values().removeAll(Collections.singleton((BukkitTask) null));
                            iterator.remove();
                            continue;
                        }

                        if (minion.getBukkitEntity().isDead()) {

                            if (redTasks.containsKey(entityUUID))
                                redTasks.remove(entityUUID);
                            if (redAttackTasks.containsKey(entityUUID))
                                redAttackTasks.remove(entityUUID);

                            redMinions.removeAll(Collections.singleton((String) null));
                            redTasks.keySet().removeAll(Collections.singleton((UUID) null));
                            redTasks.values().removeAll(Collections.singleton((BukkitTask) null));
                            redAttackTasks.keySet().removeAll(Collections.singleton((UUID) null));
                            redAttackTasks.values().removeAll(Collections.singleton((BukkitTask) null));

                            iterator.remove();
                            continue;

                        }

                        Entity nearestEntity = null;
                        int toNode = Integer.valueOf(data.split(",")[1]); //gets the data for the node the entity is currently headed for

                        try {
                            nearestEntity = getEntityByUniqueId(getNearestBlueEnemy(minion).getKey());
                        } catch (NullPointerException e1) {

                            if (minion.getBukkitEntity().getLocation().distanceSquared(redNodes.get(toNode)) <= 25) {

                                if (redAttackTasks.containsKey(minion.getUniqueID())) {

                                    redAttackTasks.remove(minion.getUniqueID());

                                }

                                if (redNodes.containsKey(toNode + 2)) {
                                    redTasks.get(minion.getUniqueID()).cancel();
                                    redTasks.remove(minion.getUniqueID());
                                    moveTo(redNodes.get(toNode + 2), minion, "red");
                                    addToRed(minion);
                                    iterator.remove();
                                    continue;
                                }

                            } else {

                                if (redAttackTasks.containsKey(minion.getUniqueID())) {

                                    redAttackTasks.remove(minion.getUniqueID());

                                }
                                redTasks.get(minion.getUniqueID()).cancel();
                                redTasks.remove(minion.getUniqueID());
                                moveTo(redNodes.get(toNode), minion, "red");
                                continue;

                            }

                        }

                        if (nearestEntity instanceof Player) {

                            Player player = (Player) nearestEntity;

                            if (player.getGameMode() != org.bukkit.GameMode.SURVIVAL) {
                                if (redAttackTasks.containsKey(minion.getUniqueID()))
                                    redAttackTasks.remove(minion.getUniqueID());
                                continue;
                            }

                            redTasks.get(minion.getUniqueID()).cancel();
                            redTasks.remove(minion.getUniqueID());

                            if (redAttackTasks.containsKey(minion.getUniqueID())) {

                                redAttackTasks.remove(minion.getUniqueID());

                            }

                            attack(minion, player, "red");

                        } else if (nearestEntity instanceof Zombie) {

                            redTasks.get(minion.getUniqueID()).cancel();
                            redTasks.remove(minion.getUniqueID());

                            if (redAttackTasks.containsKey(minion.getUniqueID())) {

                                redAttackTasks.remove(minion.getUniqueID());

                            }

                            attack(minion, nearestEntity, "red");

                        } else if (nearestEntity instanceof Slime) {

                            Slime v = (Slime) nearestEntity;
                            if (!v.getUniqueId().equals(Nexus.redUUID) && !v.getUniqueId().equals(Nexus.blueUUID)) {

                                if (TurretPriorityHandler.canAttack("blue", v.getUniqueId())) {

                                    redTasks.get(minion.getUniqueID()).cancel();
                                    redTasks.remove(minion.getUniqueID());

                                    if (redAttackTasks.containsKey(minion.getUniqueID())) {

                                        redAttackTasks.remove(minion.getUniqueID());

                                    }

                                    attack(minion, v, "red");
                                    continue;

                                }

                                if (minion.getBukkitEntity().getLocation().distanceSquared(redNodes.get(toNode)) <= 25) {

                                    if (redAttackTasks.containsKey(minion.getUniqueID())) {

                                        redAttackTasks.remove(minion.getUniqueID());

                                    }

                                    if (redNodes.containsKey(toNode + 2)) {
                                        redTasks.get(minion.getUniqueID()).cancel();
                                        redTasks.remove(minion.getUniqueID());
                                        moveTo(redNodes.get(toNode + 2), minion, "red");
                                        addToRed(minion);
                                        iterator.remove();
                                    }

                                } else {

                                    if (redAttackTasks.containsKey(minion.getUniqueID())) {

                                        redAttackTasks.remove(minion.getUniqueID());

                                    }
                                    redTasks.get(minion.getUniqueID()).cancel();
                                    redTasks.remove(minion.getUniqueID());
                                    moveTo(redNodes.get(toNode), minion, "red");

                                }

                            } else if (v.getUniqueId().equals(Nexus.blueUUID)) {

                                redTasks.get(minion.getUniqueID()).cancel();
                                redTasks.remove(minion.getUniqueID());

                                if (redAttackTasks.containsKey(minion.getUniqueID())) {

                                    redAttackTasks.remove(minion.getUniqueID());

                                }

                                attack(minion, v, "red");

                            } else {

                                if (minion.getBukkitEntity().getLocation().distanceSquared(redNodes.get(toNode)) <= 25) {

                                    if (redAttackTasks.containsKey(minion.getUniqueID())) {

                                        redAttackTasks.remove(minion.getUniqueID());

                                    }

                                    if (redNodes.containsKey(toNode + 2)) {
                                        redTasks.get(minion.getUniqueID()).cancel();
                                        redTasks.remove(minion.getUniqueID());
                                        moveTo(redNodes.get(toNode + 2), minion, "red");
                                        addToRed(minion);
                                        iterator.remove();
                                    }

                                } else {

                                    if (redAttackTasks.containsKey(minion.getUniqueID())) {

                                        redAttackTasks.remove(minion.getUniqueID());

                                    }
                                    redTasks.get(minion.getUniqueID()).cancel();
                                    redTasks.remove(minion.getUniqueID());
                                    moveTo(redNodes.get(toNode), minion, "red");

                                }

                            }

                        }

                    }

                }

                redMinions.addAll(toAddRed);
                toAddRed.clear();

            }

        }.runTaskTimer(plugin, 0, 3);

    }

    public void spawn(World world) {

        org.bukkit.inventory.ItemStack helmet = new ItemStack(Material.CARPET, 1, (short) 0);
        org.bukkit.inventory.ItemStack rangedHelmet = new ItemStack(Material.CARPET, 1, (short) 4);
        org.bukkit.inventory.ItemStack siegeHelmet = new ItemStack(Material.CARPET, 1, (short) 3);
        org.bukkit.inventory.ItemStack superHelmet = new ItemStack(Material.CARPET, 1, (short) 2);
        org.bukkit.inventory.ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta chestMeta = (LeatherArmorMeta) chest.getItemMeta();
        org.bukkit.inventory.ItemStack pants = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta pantsMeta = (LeatherArmorMeta) pants.getItemMeta();
        org.bukkit.inventory.ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        org.bukkit.inventory.ItemStack weapon = new ItemStack(Material.DIAMOND_HOE, 1, (short) 1560);
        net.minecraft.server.v1_9_R2.ItemStack stack = CraftItemStack.asNMSCopy(weapon);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("Unbreakable", true);
        stack.setTag(tag);
        weapon = CraftItemStack.asBukkitCopy(stack);
        org.bukkit.inventory.ItemStack rangedWeapon = new ItemStack(Material.DIAMOND_HOE, 1, (short) 1559);
        net.minecraft.server.v1_9_R2.ItemStack rangedStack = CraftItemStack.asNMSCopy(rangedWeapon);
        rangedStack.setTag(tag);
        rangedWeapon = CraftItemStack.asBukkitCopy(rangedStack);
        org.bukkit.inventory.ItemStack siegeWeapon = new ItemStack(Material.DIAMOND_HOE, 1, (short) 1561);
        net.minecraft.server.v1_9_R2.ItemStack siegeStack = CraftItemStack.asNMSCopy(siegeWeapon);
        siegeStack.setTag(tag);
        siegeWeapon = CraftItemStack.asBukkitCopy(siegeStack);
        org.bukkit.inventory.ItemStack superWeapon = new ItemStack(Material.DIAMOND_HOE, 1, (short) 1558);
        net.minecraft.server.v1_9_R2.ItemStack superStack = CraftItemStack.asNMSCopy(superWeapon);
        superStack.setTag(tag);
        superWeapon = CraftItemStack.asBukkitCopy(superStack);

        if (waveCount != 0 && waveCount % 3 == 0) { //special minion wave

            chestMeta.setColor(Color.BLUE);
            pantsMeta.setColor(Color.BLUE);
            bootsMeta.setColor(Color.BLUE);
            chest.setItemMeta(chestMeta);
            pants.setItemMeta(pantsMeta);
            boots.setItemMeta(bootsMeta);

            Location loc = blueNodes.get(0);

            /**
             * BLUE REGULAR MINIONS
             */
            for (int i = 0; i < 3; i++) {

                BasicMinion regular = new BasicMinion(((CraftWorld) world).getHandle());
                regular.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
                ((Zombie) regular.getBukkitEntity()).getEquipment().setHelmet(helmet);
                ((Zombie) regular.getBukkitEntity()).getEquipment().setChestplate(chest);
                ((Zombie) regular.getBukkitEntity()).getEquipment().setLeggings(pants);
                ((Zombie) regular.getBukkitEntity()).getEquipment().setBoots(boots);
                ((Zombie) regular.getBukkitEntity()).getEquipment().setItemInMainHand(weapon);
                ((CraftWorld) world).getHandle().addEntity(regular, CreatureSpawnEvent.SpawnReason.CUSTOM);
                ((Zombie) regular.getBukkitEntity()).setRemoveWhenFarAway(false);
                ((Zombie) regular.getBukkitEntity()).setVillagerProfession(null);
                ((Zombie) regular.getBukkitEntity()).setTarget(null);
                ((Zombie) regular.getBukkitEntity()).setMaxHealth(210);
                ((Zombie) regular.getBukkitEntity()).setHealth(210);
                regular.getBukkitEntity().setInvulnerable(false);

                if (i <= 1) {
                    moveTo(blueNodes.get(1), regular, "blue");
                    blueMinions.add(regular.getUniqueID().toString() + ",1,blue"); // have them walk to the left lane

                } else {

                    moveTo(blueNodes.get(2), regular, "blue");
                    blueMinions.add(regular.getUniqueID().toString() + ",2,blue"); // have them walk to the right lane

                }

            }

            /**
             *  BLUE SUPER MINIONS
             */

            if (!TurretPriorityHandler.leftLaneExists("red")) {

                SuperMinion superMinion = new SuperMinion(((CraftWorld) world).getHandle());
                superMinion.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
                ((Zombie) superMinion.getBukkitEntity()).getEquipment().setHelmet(helmet);
                ((Zombie) superMinion.getBukkitEntity()).getEquipment().setChestplate(chest);
                ((Zombie) superMinion.getBukkitEntity()).getEquipment().setLeggings(pants);
                ((Zombie) superMinion.getBukkitEntity()).getEquipment().setBoots(boots);
                ((Zombie) superMinion.getBukkitEntity()).getEquipment().setItemInMainHand(weapon);
                ((CraftWorld) world).getHandle().addEntity(superMinion, CreatureSpawnEvent.SpawnReason.CUSTOM);
                ((Zombie) superMinion.getBukkitEntity()).setRemoveWhenFarAway(false);
                ((Zombie) superMinion.getBukkitEntity()).setVillagerProfession(null);
                ((Zombie) superMinion.getBukkitEntity()).setTarget(null);
                ((Zombie) superMinion.getBukkitEntity()).setMaxHealth(400);
                ((Zombie) superMinion.getBukkitEntity()).setHealth(400);
                superMinion.getBukkitEntity().setInvulnerable(false);

                moveTo(blueNodes.get(1), superMinion, "blue");
                blueMinions.add(superMinion.getUniqueID().toString() + ",1,blue"); // have them walk to the left lane

            }

            if (!TurretPriorityHandler.rightLaneExists("red")) {

                SuperMinion superMinion = new SuperMinion(((CraftWorld) world).getHandle());
                superMinion.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
                ((Zombie) superMinion.getBukkitEntity()).getEquipment().setHelmet(helmet);
                ((Zombie) superMinion.getBukkitEntity()).getEquipment().setChestplate(chest);
                ((Zombie) superMinion.getBukkitEntity()).getEquipment().setLeggings(pants);
                ((Zombie) superMinion.getBukkitEntity()).getEquipment().setBoots(boots);
                ((Zombie) superMinion.getBukkitEntity()).getEquipment().setItemInMainHand(weapon);
                ((CraftWorld) world).getHandle().addEntity(superMinion, CreatureSpawnEvent.SpawnReason.CUSTOM);
                ((Zombie) superMinion.getBukkitEntity()).setRemoveWhenFarAway(false);
                ((Zombie) superMinion.getBukkitEntity()).setVillagerProfession(null);
                ((Zombie) superMinion.getBukkitEntity()).setTarget(null);
                ((Zombie) superMinion.getBukkitEntity()).setMaxHealth(400);
                ((Zombie) superMinion.getBukkitEntity()).setHealth(400);
                superMinion.getBukkitEntity().setInvulnerable(false);

                moveTo(blueNodes.get(2), superMinion, "blue");
                blueMinions.add(superMinion.getUniqueID().toString() + ",2,blue"); // have them walk to the right lane

            }



            /**
             * BLUE RANGED MINIONS
             */
            for (int i = 0; i < 3; i++) {

                RangedMinion rangedMinion = new RangedMinion(((CraftWorld) world).getHandle());
                rangedMinion.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
                ((Zombie) rangedMinion.getBukkitEntity()).getEquipment().setHelmet(rangedHelmet);
                ((Zombie) rangedMinion.getBukkitEntity()).getEquipment().setChestplate(chest);
                ((Zombie) rangedMinion.getBukkitEntity()).getEquipment().setLeggings(pants);
                ((Zombie) rangedMinion.getBukkitEntity()).getEquipment().setBoots(boots);
                ((Zombie) rangedMinion.getBukkitEntity()).getEquipment().setItemInMainHand(rangedWeapon);
                ((CraftWorld) world).getHandle().addEntity(rangedMinion, CreatureSpawnEvent.SpawnReason.CUSTOM);
                ((Zombie) rangedMinion.getBukkitEntity()).setRemoveWhenFarAway(false);
                ((Zombie) rangedMinion.getBukkitEntity()).setVillagerProfession(null);
                ((Zombie) rangedMinion.getBukkitEntity()).setTarget(null);
                ((Zombie) rangedMinion.getBukkitEntity()).setMaxHealth(180);
                ((Zombie) rangedMinion.getBukkitEntity()).setHealth(180);
                rangedMinion.getBukkitEntity().setInvulnerable(false);

                if (i == 0) {
                    moveTo(blueNodes.get(1), rangedMinion, "blue");
                    blueMinions.add(rangedMinion.getUniqueID().toString() + ",1,blue"); // have them walk to the left lane

                } else {

                    moveTo(blueNodes.get(2), rangedMinion, "blue");
                    blueMinions.add(rangedMinion.getUniqueID().toString() + ",2,blue"); // have them walk to the right lane

                }

            }

            /**
             * BLUE SIEGE MINION
             */
            for (int i = 0; i < 2; i++) {

                SiegeMinion siegeMinion = new SiegeMinion(((CraftWorld) world).getHandle());
                siegeMinion.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
                ((Zombie) siegeMinion.getBukkitEntity()).getEquipment().setHelmet(siegeHelmet);
                ((Zombie) siegeMinion.getBukkitEntity()).getEquipment().setChestplate(chest);
                ((Zombie) siegeMinion.getBukkitEntity()).getEquipment().setLeggings(pants);
                ((Zombie) siegeMinion.getBukkitEntity()).getEquipment().setBoots(boots);
                ((Zombie) siegeMinion.getBukkitEntity()).getEquipment().setItemInMainHand(siegeWeapon);
                ((CraftWorld) world).getHandle().addEntity(siegeMinion, CreatureSpawnEvent.SpawnReason.CUSTOM);
                ((Zombie) siegeMinion.getBukkitEntity()).setRemoveWhenFarAway(false);
                ((Zombie) siegeMinion.getBukkitEntity()).setVillagerProfession(null);
                ((Zombie) siegeMinion.getBukkitEntity()).setTarget(null);
                ((Zombie) siegeMinion.getBukkitEntity()).setMaxHealth(240);
                ((Zombie) siegeMinion.getBukkitEntity()).setHealth(240);
                siegeMinion.getBukkitEntity().setInvulnerable(false);

                if (i == 0) {
                    moveTo(blueNodes.get(1), siegeMinion, "blue");
                    blueMinions.add(siegeMinion.getUniqueID().toString() + ",1,blue"); // have them walk to the left lane

                } else {

                    moveTo(blueNodes.get(2), siegeMinion, "blue");
                    blueMinions.add(siegeMinion.getUniqueID().toString() + ",2,blue"); // have them walk to the right lane

                }

            }


            chestMeta.setColor(Color.RED);
            pantsMeta.setColor(Color.RED);
            bootsMeta.setColor(Color.RED);
            chest.setItemMeta(chestMeta);
            pants.setItemMeta(pantsMeta);
            boots.setItemMeta(bootsMeta);

            Location loc1 = redNodes.get(0);

            /**
             * RED REGULAR MINIONS
             */
            for (int i = 0; i < 3; i++) {

                BasicMinion regular = new BasicMinion(((CraftWorld) world).getHandle());
                regular.setPositionRotation(loc1.getX(), loc1.getY(), loc1.getZ(), loc1.getYaw(), loc1.getPitch());
                ((Zombie) regular.getBukkitEntity()).getEquipment().setHelmet(helmet);
                ((Zombie) regular.getBukkitEntity()).getEquipment().setChestplate(chest);
                ((Zombie) regular.getBukkitEntity()).getEquipment().setLeggings(pants);
                ((Zombie) regular.getBukkitEntity()).getEquipment().setBoots(boots);
                ((Zombie) regular.getBukkitEntity()).getEquipment().setItemInMainHand(weapon);
                ((CraftWorld) world).getHandle().addEntity(regular, CreatureSpawnEvent.SpawnReason.CUSTOM);
                ((Zombie) regular.getBukkitEntity()).setRemoveWhenFarAway(false);
                ((Zombie) regular.getBukkitEntity()).setVillagerProfession(null);
                ((Zombie) regular.getBukkitEntity()).setTarget(null);
                ((Zombie) regular.getBukkitEntity()).setMaxHealth(210);
                ((Zombie) regular.getBukkitEntity()).setHealth(210);
                regular.getBukkitEntity().setInvulnerable(false);

                if (i <= 1) {
                    moveTo(redNodes.get(1), regular, "red");
                    redMinions.add(regular.getUniqueID().toString() + ",1,red"); // have them walk to the left lane

                } else {

                    moveTo(redNodes.get(2), regular, "red");
                    redMinions.add(regular.getUniqueID().toString() + ",2,red"); // have them walk to the right lane

                }

            }

            /**
             * RED SUPER MINIONS
             */

            if (!TurretPriorityHandler.leftLaneExists("blue")) {

                SuperMinion superMinion = new SuperMinion(((CraftWorld) world).getHandle());
                superMinion.setPositionRotation(loc1.getX(), loc1.getY(), loc1.getZ(), loc1.getYaw(), loc1.getPitch());
                ((Zombie) superMinion.getBukkitEntity()).getEquipment().setHelmet(superHelmet);
                ((Zombie) superMinion.getBukkitEntity()).getEquipment().setChestplate(chest);
                ((Zombie) superMinion.getBukkitEntity()).getEquipment().setLeggings(pants);
                ((Zombie) superMinion.getBukkitEntity()).getEquipment().setBoots(boots);
                ((Zombie) superMinion.getBukkitEntity()).getEquipment().setItemInMainHand(superWeapon);
                ((CraftWorld) world).getHandle().addEntity(superMinion, CreatureSpawnEvent.SpawnReason.CUSTOM);
                ((Zombie) superMinion.getBukkitEntity()).setRemoveWhenFarAway(false);
                ((Zombie) superMinion.getBukkitEntity()).setVillagerProfession(null);
                ((Zombie) superMinion.getBukkitEntity()).setTarget(null);
                ((Zombie) superMinion.getBukkitEntity()).setMaxHealth(400);
                ((Zombie) superMinion.getBukkitEntity()).setHealth(400);
                superMinion.getBukkitEntity().setInvulnerable(false);

                moveTo(redNodes.get(1), superMinion, "red");
                redMinions.add(superMinion.getUniqueID().toString() + ",1,red"); // have them walk to the left lane

            }

            if (!TurretPriorityHandler.rightLaneExists("blue")) {

                SuperMinion superMinion = new SuperMinion(((CraftWorld) world).getHandle());
                superMinion.setPositionRotation(loc1.getX(), loc1.getY(), loc1.getZ(), loc1.getYaw(), loc1.getPitch());
                ((Zombie) superMinion.getBukkitEntity()).getEquipment().setHelmet(superHelmet);
                ((Zombie) superMinion.getBukkitEntity()).getEquipment().setChestplate(chest);
                ((Zombie) superMinion.getBukkitEntity()).getEquipment().setLeggings(pants);
                ((Zombie) superMinion.getBukkitEntity()).getEquipment().setBoots(boots);
                ((Zombie) superMinion.getBukkitEntity()).getEquipment().setItemInMainHand(superWeapon);
                ((CraftWorld) world).getHandle().addEntity(superMinion, CreatureSpawnEvent.SpawnReason.CUSTOM);
                ((Zombie) superMinion.getBukkitEntity()).setRemoveWhenFarAway(false);
                ((Zombie) superMinion.getBukkitEntity()).setVillagerProfession(null);
                ((Zombie) superMinion.getBukkitEntity()).setTarget(null);
                ((Zombie) superMinion.getBukkitEntity()).setMaxHealth(400);
                ((Zombie) superMinion.getBukkitEntity()).setHealth(400);
                superMinion.getBukkitEntity().setInvulnerable(false);

                moveTo(redNodes.get(2), superMinion, "red");
                redMinions.add(superMinion.getUniqueID().toString() + ",2,red"); // have them walk to the right lane

            }

            /**
             * RED RANGED MINIONS
             */
            for (int i = 0; i < 3; i++) {

                RangedMinion rangedMinion = new RangedMinion(((CraftWorld) world).getHandle());
                rangedMinion.setPositionRotation(loc1.getX(), loc1.getY(), loc1.getZ(), loc1.getYaw(), loc1.getPitch());
                ((Zombie) rangedMinion.getBukkitEntity()).getEquipment().setHelmet(superHelmet);
                ((Zombie) rangedMinion.getBukkitEntity()).getEquipment().setChestplate(chest);
                ((Zombie) rangedMinion.getBukkitEntity()).getEquipment().setLeggings(pants);
                ((Zombie) rangedMinion.getBukkitEntity()).getEquipment().setBoots(boots);
                ((Zombie) rangedMinion.getBukkitEntity()).getEquipment().setItemInMainHand(rangedWeapon);
                ((CraftWorld) world).getHandle().addEntity(rangedMinion, CreatureSpawnEvent.SpawnReason.CUSTOM);
                ((Zombie) rangedMinion.getBukkitEntity()).setRemoveWhenFarAway(false);
                ((Zombie) rangedMinion.getBukkitEntity()).setVillagerProfession(null);
                ((Zombie) rangedMinion.getBukkitEntity()).setTarget(null);
                ((Zombie) rangedMinion.getBukkitEntity()).setMaxHealth(180);
                ((Zombie) rangedMinion.getBukkitEntity()).setHealth(180);
                rangedMinion.getBukkitEntity().setInvulnerable(false);

                if (i == 0) {
                    moveTo(redNodes.get(1), rangedMinion, "red");
                    redMinions.add(rangedMinion.getUniqueID().toString() + ",1,red"); // have them walk to the left lane

                } else {

                    moveTo(redNodes.get(2), rangedMinion, "red");
                    redMinions.add(rangedMinion.getUniqueID().toString() + ",2,red"); // have them walk to the right lane

                }

            }

            /**
             * RED SIEGE MINION
             */
            for (int i = 0; i < 2; i++) {

                SiegeMinion siegeMinion = new SiegeMinion(((CraftWorld) world).getHandle());
                siegeMinion.setPositionRotation(loc1.getX(), loc1.getY(), loc1.getZ(), loc1.getYaw(), loc1.getPitch());
                ((Zombie) siegeMinion.getBukkitEntity()).getEquipment().setHelmet(siegeHelmet);
                ((Zombie) siegeMinion.getBukkitEntity()).getEquipment().setChestplate(chest);
                ((Zombie) siegeMinion.getBukkitEntity()).getEquipment().setLeggings(pants);
                ((Zombie) siegeMinion.getBukkitEntity()).getEquipment().setBoots(boots);
                ((Zombie) siegeMinion.getBukkitEntity()).getEquipment().setItemInMainHand(siegeWeapon);
                ((CraftWorld) world).getHandle().addEntity(siegeMinion, CreatureSpawnEvent.SpawnReason.CUSTOM);
                ((Zombie) siegeMinion.getBukkitEntity()).setRemoveWhenFarAway(false);
                ((Zombie) siegeMinion.getBukkitEntity()).setVillagerProfession(null);
                ((Zombie) siegeMinion.getBukkitEntity()).setTarget(null);
                ((Zombie) siegeMinion.getBukkitEntity()).setMaxHealth(240);
                ((Zombie) siegeMinion.getBukkitEntity()).setHealth(240);
                siegeMinion.getBukkitEntity().setInvulnerable(false);

                if (i == 0) {
                    moveTo(redNodes.get(1), siegeMinion, "red");
                    redMinions.add(siegeMinion.getUniqueID().toString() + ",1,red"); // have them walk to the left lane

                } else {

                    moveTo(redNodes.get(2), siegeMinion, "red");
                    redMinions.add(siegeMinion.getUniqueID().toString() + ",2,red"); // have them walk to the right lane

                }

            }

        } else {

            chestMeta.setColor(Color.BLUE);
            pantsMeta.setColor(Color.BLUE);
            bootsMeta.setColor(Color.BLUE);
            chest.setItemMeta(chestMeta);
            pants.setItemMeta(pantsMeta);
            boots.setItemMeta(bootsMeta);

            Location loc = blueNodes.get(0);

            /**
             * BLUE REGULAR MINIONS
             */
            for (int i = 0; i < 3; i++) {

                BasicMinion regular = new BasicMinion(((CraftWorld) world).getHandle());
                regular.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
                ((Zombie) regular.getBukkitEntity()).getEquipment().setHelmet(helmet);
                ((Zombie) regular.getBukkitEntity()).getEquipment().setChestplate(chest);
                ((Zombie) regular.getBukkitEntity()).getEquipment().setLeggings(pants);
                ((Zombie) regular.getBukkitEntity()).getEquipment().setBoots(boots);
                ((Zombie) regular.getBukkitEntity()).getEquipment().setItemInMainHand(weapon);
                ((CraftWorld) world).getHandle().addEntity(regular, CreatureSpawnEvent.SpawnReason.CUSTOM);
                ((Zombie) regular.getBukkitEntity()).setRemoveWhenFarAway(false);
                ((Zombie) regular.getBukkitEntity()).setVillagerProfession(null);
                ((Zombie) regular.getBukkitEntity()).setTarget(null);
                ((Zombie) regular.getBukkitEntity()).setMaxHealth(210);
                ((Zombie) regular.getBukkitEntity()).setHealth(210);
                regular.getBukkitEntity().setInvulnerable(false);
                boolean left = new Random().nextFloat() < 0.5F;

                if (i <= 1) {
                    moveTo(blueNodes.get(1), regular, "blue");
                    blueMinions.add(regular.getUniqueID().toString() + ",1,blue"); // have them walk to the left lane

                } else {

                    moveTo(blueNodes.get(2), regular, "blue");
                    blueMinions.add(regular.getUniqueID().toString() + ",2,blue"); // have them walk to the right lane

                }

            }

            /**
             * BLUE RANGED MINIONS
             */
            for (int i = 0; i < 3; i++) {

                RangedMinion rangedMinion = new RangedMinion(((CraftWorld) world).getHandle());
                rangedMinion.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
                ((Zombie) rangedMinion.getBukkitEntity()).getEquipment().setHelmet(rangedHelmet);
                ((Zombie) rangedMinion.getBukkitEntity()).getEquipment().setChestplate(chest);
                ((Zombie) rangedMinion.getBukkitEntity()).getEquipment().setLeggings(pants);
                ((Zombie) rangedMinion.getBukkitEntity()).getEquipment().setBoots(boots);
                ((Zombie) rangedMinion.getBukkitEntity()).getEquipment().setItemInMainHand(rangedWeapon);
                ((CraftWorld) world).getHandle().addEntity(rangedMinion, CreatureSpawnEvent.SpawnReason.CUSTOM);
                ((Zombie) rangedMinion.getBukkitEntity()).setRemoveWhenFarAway(false);
                ((Zombie) rangedMinion.getBukkitEntity()).setVillagerProfession(null);
                ((Zombie) rangedMinion.getBukkitEntity()).setTarget(null);
                ((Zombie) rangedMinion.getBukkitEntity()).setMaxHealth(180);
                ((Zombie) rangedMinion.getBukkitEntity()).setHealth(180);
                rangedMinion.getBukkitEntity().setInvulnerable(false);

                if (i == 0) {
                    moveTo(blueNodes.get(1), rangedMinion, "blue");
                    blueMinions.add(rangedMinion.getUniqueID().toString() + ",1,blue"); // have them walk to the left lane

                } else {

                    moveTo(blueNodes.get(2), rangedMinion, "blue");
                    blueMinions.add(rangedMinion.getUniqueID().toString() + ",2,blue"); // have them walk to the right lane

                }

            }


            chestMeta.setColor(Color.RED);
            pantsMeta.setColor(Color.RED);
            bootsMeta.setColor(Color.RED);
            chest.setItemMeta(chestMeta);
            pants.setItemMeta(pantsMeta);
            boots.setItemMeta(bootsMeta);

            Location loc1 = redNodes.get(0);

            /**
             * RED REGULAR MINIONS
             */
            for (int i = 0; i < 3; i++) {

                BasicMinion regular = new BasicMinion(((CraftWorld) world).getHandle());
                regular.setPositionRotation(loc1.getX(), loc1.getY(), loc1.getZ(), loc1.getYaw(), loc1.getPitch());
                ((Zombie) regular.getBukkitEntity()).getEquipment().setHelmet(helmet);
                ((Zombie) regular.getBukkitEntity()).getEquipment().setChestplate(chest);
                ((Zombie) regular.getBukkitEntity()).getEquipment().setLeggings(pants);
                ((Zombie) regular.getBukkitEntity()).getEquipment().setBoots(boots);
                ((Zombie) regular.getBukkitEntity()).getEquipment().setItemInMainHand(weapon);
                ((CraftWorld) world).getHandle().addEntity(regular, CreatureSpawnEvent.SpawnReason.CUSTOM);
                ((Zombie) regular.getBukkitEntity()).setRemoveWhenFarAway(false);
                ((Zombie) regular.getBukkitEntity()).setVillagerProfession(null);
                ((Zombie) regular.getBukkitEntity()).setTarget(null);
                ((Zombie) regular.getBukkitEntity()).setMaxHealth(210);
                ((Zombie) regular.getBukkitEntity()).setHealth(210);
                regular.getBukkitEntity().setInvulnerable(false);

                if (i <= 1) {
                    moveTo(redNodes.get(1), regular, "red");
                    redMinions.add(regular.getUniqueID().toString() + ",1,red"); // have them walk to the left lane

                } else {

                    moveTo(redNodes.get(2), regular, "red");
                    redMinions.add(regular.getUniqueID().toString() + ",2,red"); // have them walk to the right lane

                }

            }

            /**
             * RED RANGED MINIONS
             */
            for (int i = 0; i < 3; i++) {

                RangedMinion rangedMinion = new RangedMinion(((CraftWorld) world).getHandle());
                rangedMinion.setPositionRotation(loc1.getX(), loc1.getY(), loc1.getZ(), loc1.getYaw(), loc1.getPitch());
                ((Zombie) rangedMinion.getBukkitEntity()).getEquipment().setHelmet(rangedHelmet);
                ((Zombie) rangedMinion.getBukkitEntity()).getEquipment().setChestplate(chest);
                ((Zombie) rangedMinion.getBukkitEntity()).getEquipment().setLeggings(pants);
                ((Zombie) rangedMinion.getBukkitEntity()).getEquipment().setBoots(boots);
                ((Zombie) rangedMinion.getBukkitEntity()).getEquipment().setItemInMainHand(rangedWeapon);
                ((CraftWorld) world).getHandle().addEntity(rangedMinion, CreatureSpawnEvent.SpawnReason.CUSTOM);
                ((Zombie) rangedMinion.getBukkitEntity()).setRemoveWhenFarAway(false);
                ((Zombie) rangedMinion.getBukkitEntity()).setVillagerProfession(null);
                ((Zombie) rangedMinion.getBukkitEntity()).setTarget(null);
                ((Zombie) rangedMinion.getBukkitEntity()).setMaxHealth(180);
                ((Zombie) rangedMinion.getBukkitEntity()).setHealth(180);
                rangedMinion.getBukkitEntity().setInvulnerable(false);

                if (i == 0) {
                    moveTo(redNodes.get(1), rangedMinion, "red");
                    redMinions.add(rangedMinion.getUniqueID().toString() + ",1,red"); // have them walk to the left lane

                } else {

                    moveTo(redNodes.get(2), rangedMinion, "red");
                    redMinions.add(rangedMinion.getUniqueID().toString() + ",2,red"); // have them walk to the right lane

                }

            }

        }

        waveCount++;

    }

    public void setupNodes(World world) {

        if (GameMode.getMode() == GameMode._1V1) {

            blueNodes.put(0, new Location(world, 882, 6, 38));
            blueNodes.put(1, new Location(world, 871, 6, 27)); //split here
            blueNodes.put(2, new Location(world, 871, 6, 48));
            blueNodes.put(3, new Location(world, 860, 6, 39));
            blueNodes.put(4, new Location(world, 840, 6, 34)); //first tower
            blueNodes.put(5, new Location(world, 824, 6, 32));
            blueNodes.put(6, new Location(world, 808, 6, 33)); //second tower
            blueNodes.put(7, new Location(world, 787, 6, 35));
            blueNodes.put(8, new Location(world, 778, 6, 38));
            blueNodes.put(9, new Location(world, 759, 6, 38)); //enemy tower 1, stop and check
            blueNodes.put(10, new Location(world, 729, 6, 38)); //enemy tower 2, stop and check
            blueNodes.put(11, new Location(world, 696, 6, 38)); //nexus, attack

        } else if (GameMode.getMode() == GameMode._3V3) {

            blueNodes.put(0, new Location(world, 689, 8, 37));
            redNodes.put(0, new Location(world, 871, 8, 37));

            blueNodes.put(2, new Location(world, 696, 8, 52)); //all right sides are odd numbers
            blueNodes.put(4, new Location(world, 693, 8, 71)); //all right sides are odd numbers
            blueNodes.put(6, new Location(world, 696, 8, 82)); //all right sides are odd numbers
            blueNodes.put(8, new Location(world, 708, 8, 94)); //all right sides are odd numbers
            blueNodes.put(10, new Location(world, 719, 8, 99)); //all right sides are odd numbers
            blueNodes.put(12, new Location(world, 726, 8, 99)); //all right sides are odd numbers
            blueNodes.put(14, new Location(world, 734, 8, 99)); //all right sides are odd numbers
            blueNodes.put(16, new Location(world, 744, 8, 99)); //all right sides are odd numbers
            blueNodes.put(18, new Location(world, 757, 8, 99)); //all right sides are odd numbers
            blueNodes.put(20, new Location(world, 769, 8, 99)); //all right sides are odd numbers
            blueNodes.put(22, new Location(world, 781, 8, 99)); //all right sides are odd numbers
            blueNodes.put(24, new Location(world, 796, 8, 99)); //all right sides are odd numbers
            blueNodes.put(26, new Location(world, 803, 8, 99)); //all right sides are odd numbers
            blueNodes.put(28, new Location(world, 816, 8, 99)); //all right sides are odd numbers
            blueNodes.put(30, new Location(world, 826, 8, 99)); //all right sides are odd numbers
            blueNodes.put(32, new Location(world, 835, 8, 99)); //all right sides are odd numbers
            blueNodes.put(34, new Location(world, 841, 8, 99)); //all right sides are odd numbers
            blueNodes.put(36, new Location(world, 852, 8, 84)); //all right sides are odd numbers
            blueNodes.put(38, new Location(world, 864, 8, 82)); //all right sides are odd numbers
            blueNodes.put(40, new Location(world, 867, 8, 71)); //all right sides are odd numbers
            blueNodes.put(42, new Location(world, 864, 8, 52)); //all right sides are odd numbers
            blueNodes.put(44, new Location(world, 871, 8, 37)); //all right sides are odd numbers

            blueNodes.put(1, new Location(world, 696, 8, 22)); //all left sides are even numbers
            blueNodes.put(3, new Location(world, 693, 8, 3)); //all left sides are even numbers
            blueNodes.put(5, new Location(world, 693, 8, -8)); //all left sides are even numbers
            blueNodes.put(7, new Location(world, 707, 8, -19)); //all left sides are even numbers
            blueNodes.put(9, new Location(world, 719, 8, -25)); //all left sides are even numbers
            blueNodes.put(11, new Location(world, 726, 8, -25)); //all left sides are even numbers
            blueNodes.put(13, new Location(world, 734, 8, -25)); //all left sides are even numbers
            blueNodes.put(15, new Location(world, 744, 8, -25)); //all left sides are even numbers
            blueNodes.put(17, new Location(world, 757, 8, -25)); //all left sides are even numbers
            blueNodes.put(19, new Location(world, 769, 8, -25)); //all left sides are even numbers
            blueNodes.put(21, new Location(world, 782, 8, -25)); //all left sides are even numbers
            blueNodes.put(23, new Location(world, 796, 8, -25)); //all left sides are even numbers
            blueNodes.put(25, new Location(world, 803, 8, -25)); //all left sides are even numbers
            blueNodes.put(27, new Location(world, 816, 8, -25)); //all left sides are even numbers
            blueNodes.put(29, new Location(world, 826, 8, -25)); //all left sides are even numbers
            blueNodes.put(31, new Location(world, 834, 8, -25)); //all left sides are even numbers
            blueNodes.put(33, new Location(world, 841, 8, -25)); //all left sides are even numbers
            blueNodes.put(35, new Location(world, 855, 8, -19)); //all left sides are even numbers
            blueNodes.put(37, new Location(world, 867, 8, -8)); //all left sides are even numbers
            blueNodes.put(39, new Location(world, 867, 8, 3)); //all left sides are even numbers
            blueNodes.put(41, new Location(world, 864, 8, 22)); //all left sides are even numbers
            blueNodes.put(43, new Location(world, 871, 8, 37)); //all left sides are even numbers

            redNodes.put(1, new Location(world, 871, 8, 37)); //all left sides are even numbers
            redNodes.put(3, new Location(world, 864, 8, 52)); //all left sides are even numbers
            redNodes.put(5, new Location(world, 867, 8, 71)); //all left sides are even numbers
            redNodes.put(7, new Location(world, 864, 8, 82)); //all left sides are even numbers
            redNodes.put(9, new Location(world, 852, 8, 84)); //all left sides are even numbers
            redNodes.put(11, new Location(world, 841, 8, 99)); //all left sides are even numbers
            redNodes.put(13, new Location(world, 835, 8, 99)); //all left sides are even numbers
            redNodes.put(15, new Location(world, 826, 8, 99)); //all left sides are even numbers
            redNodes.put(17, new Location(world, 816, 8, 99)); //all left sides are even numbers
            redNodes.put(19, new Location(world, 803, 8, 99)); //all left sides are even numbers
            redNodes.put(21, new Location(world, 796, 8, 99)); //all left sides are even numbers
            redNodes.put(23, new Location(world, 781, 8, 99)); //all left sides are even numbers
            redNodes.put(25, new Location(world, 769, 8, 99)); //all left sides are even numbers
            redNodes.put(27, new Location(world, 757, 8, 99)); //all left sides are even numbers
            redNodes.put(29, new Location(world, 744, 8, 99)); //all left sides are even numbers
            redNodes.put(31, new Location(world, 734, 8, 99)); //all left sides are even numbers
            redNodes.put(33, new Location(world, 726, 8, 99)); //all left sides are even numbers
            redNodes.put(35, new Location(world, 719, 8, 99)); //all left sides are even numbers
            redNodes.put(37, new Location(world, 708, 8, 94)); //all left sides are even numbers
            redNodes.put(39, new Location(world, 696, 8, 82)); //all left sides are even numbers
            redNodes.put(41, new Location(world, 693, 8, 71)); //all left sides are even numbers
            redNodes.put(43, new Location(world, 696, 8, 52)); //all left sides are even numbers

            redNodes.put(2, new Location(world, 871, 8, 37)); ///all right sides are odd numbers
            redNodes.put(4, new Location(world, 864, 8, 22)); ///all right sides are odd numbers
            redNodes.put(6, new Location(world, 867, 8, 3)); //all right sides are odd numbers
            redNodes.put(8, new Location(world, 867, 8, -8)); //all right sides are odd numbers
            redNodes.put(10, new Location(world, 855, 8, -19)); //all right sides are odd numbers
            redNodes.put(12, new Location(world, 841, 8, -25)); //all right sides are odd numbers
            redNodes.put(14, new Location(world, 834, 8, -25)); //all right sides are odd numbers
            redNodes.put(16, new Location(world, 826, 8, -25)); //all right sides are odd numbers
            redNodes.put(18, new Location(world, 816, 8, -25)); //all right sides are odd numbers
            redNodes.put(20, new Location(world, 803, 8, -25)); //all right sides are odd numbers
            redNodes.put(22, new Location(world, 796, 8, -25)); //all right sides are odd numbers
            redNodes.put(24, new Location(world, 782, 8, -25)); //all right sides are odd numbers
            redNodes.put(26, new Location(world, 769, 8, -25)); //all right sides are odd numbers
            redNodes.put(28, new Location(world, 757, 8, -25)); //all right sides are odd numbers
            redNodes.put(30, new Location(world, 744, 8, -25)); //all right sides are odd numbers
            redNodes.put(32, new Location(world, 734, 8, -25)); //all right sides are odd numbers
            redNodes.put(34, new Location(world, 726, 8, -25)); //all right sides are odd numbers
            redNodes.put(36, new Location(world, 719, 8, -25)); //all right sides are odd numbers
            redNodes.put(38, new Location(world, 707, 8, -19)); //all right sides are odd numbers
            redNodes.put(40, new Location(world, 693, 8, -8)); ///all right sides are odd numbers
            redNodes.put(42, new Location(world, 693, 8, 3)); //all right sides are odd numbers
            redNodes.put(44, new Location(world, 696, 8, 22)); //all right sides are odd numbers

        }

    }

    private void addToBlue(EntityZombie zombie) {

        synchronized (guard) {

            int currentNode = 0;

            for (String data : blueMinions) {

                if (data.matches("(?i)" + zombie.getUniqueID().toString() + ".*")) {

                    currentNode = Integer.valueOf(data.split(",")[1]);
                    break;

                }

            }

            String value = zombie.getUniqueID().toString() + "," + (currentNode + 2) + ",blue";
            toAdd.add(value);

        }
    }

    private void addToRed(EntityZombie zombie) {

        synchronized (guard) {

            int currentNode = 0;

            for (String data : redMinions) {

                if (data.matches("(?i)" + zombie.getUniqueID().toString() + ".*")) {

                    currentNode = Integer.valueOf(data.split(",")[1]);
                    break;

                }

            }

            String value = zombie.getUniqueID().toString() + "," + (currentNode + 2) + ",red";
            toAddRed.add(value);

        }
    }

    private void moveTo(Location loc, EntityZombie zombie, String color) {

        final BukkitTask task = plugin.getServer().getScheduler().runTaskTimer(plugin,
                () -> {

                    zombie.getNavigation().a(loc.getX(), loc.getY(), loc.getZ(), 1.3);
                    zombie.setGoalTarget(null, EntityTargetEvent.TargetReason.FORGOT_TARGET, true);

                }, 0L, 2L);
        if (color.equalsIgnoreCase("blue"))
            blueTasks.put(zombie.getUniqueID(), task);
        else
            redTasks.put(zombie.getUniqueID(), task);


    }

    public void attack(EntityZombie attacker, Entity toAttack, String color) {

        final BukkitTask task = plugin.getServer().getScheduler().runTaskTimer(plugin,
                () -> attacker.setGoalTarget(((CraftLivingEntity) toAttack).getHandle(), EntityTargetEvent.TargetReason.CUSTOM, true), 0, 2);
        if (color.equalsIgnoreCase("blue"))
            blueTasks.put(attacker.getUniqueID(), task);
        else
            redTasks.put(attacker.getUniqueID(), task);

    }

}
