package me.StevenLawson.TotalFreedomMod.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Pattern;
import me.StevenLawson.TotalFreedomMod.Commands.Command_landmine;
import me.StevenLawson.TotalFreedomMod.Config.TFM_ConfigEntry;
import me.StevenLawson.TotalFreedomMod.FOPM_TFM_Util;
import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import me.StevenLawson.TotalFreedomMod.TFM_BanManager;
import me.StevenLawson.TotalFreedomMod.TFM_CommandBlocker;
import me.StevenLawson.TotalFreedomMod.TFM_DepreciationAggregator;
import me.StevenLawson.TotalFreedomMod.TFM_Heartbeat;
import me.StevenLawson.TotalFreedomMod.TFM_Jumppads;
import me.StevenLawson.TotalFreedomMod.TFM_Log;
import me.StevenLawson.TotalFreedomMod.TFM_Player;
import me.StevenLawson.TotalFreedomMod.TFM_PlayerData;
import me.StevenLawson.TotalFreedomMod.TFM_PlayerList;
import me.StevenLawson.TotalFreedomMod.TFM_PlayerRank;
import me.StevenLawson.TotalFreedomMod.TFM_RollbackManager;
import me.StevenLawson.TotalFreedomMod.TFM_RollbackManager.RollbackEntry;
import me.StevenLawson.TotalFreedomMod.TFM_ServerInterface;
import me.StevenLawson.TotalFreedomMod.TFM_Sync;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TFM_UuidManager;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import me.StevenLawson.TotalFreedomMod.World.TFM_AdminWorld;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class TFM_PlayerListener implements Listener
{
    public static final List<String> BLOCKED_MUTED_CMDS = Arrays.asList(StringUtils.split("say,me,msg,m,tell,r,reply,mail,email", ","));
    public static final int MSG_PER_HEARTBEAT = 10;
    public static final int DEFAULT_PORT = 25565;
    public static final int MAX_XY_COORD = 30000000;
    private static final Random RANDOM = new Random();
    public boolean purple = false;
    public boolean black = false;

    //TODO: Test
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        final Player player = event.getPlayer();
        final TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(player);

        switch (event.getAction())
        {
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
            {
                switch (event.getMaterial())
                {
                    case WATER_BUCKET:
                    {
                        if (TFM_AdminList.isSuperAdmin(player) || TFM_ConfigEntry.ALLOW_WATER_PLACE.getBoolean())
                        {
                            break;
                        }

                        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.COOKIE, 1));
                        player.sendMessage(ChatColor.GRAY + "Water buckets are currently disabled.");
                        event.setCancelled(true);
                        break;
                    }

                    case LAVA_BUCKET:
                    {
                        if (TFM_AdminList.isSuperAdmin(player) || TFM_ConfigEntry.ALLOW_LAVA_PLACE.getBoolean())
                        {
                            break;
                        }

                        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.COOKIE, 1));
                        player.sendMessage(ChatColor.GRAY + "Lava buckets are currently disabled.");
                        event.setCancelled(true);
                        break;
                    }

                    case EXPLOSIVE_MINECART:
                    {
                        if (TFM_ConfigEntry.ALLOW_TNT_MINECARTS.getBoolean())
                        {
                            break;
                        }

                        player.getInventory().clear(player.getInventory().getHeldItemSlot());
                        player.sendMessage(ChatColor.GRAY + "TNT minecarts are currently disabled.");
                        event.setCancelled(true);
                        break;
                    }
                }
                break;
            }

            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
            {
                switch (event.getMaterial())
                {
                    case STICK:
                    {
                        if (!TFM_AdminList.isSuperAdmin(player))
                        {
                            break;
                        }

                        event.setCancelled(true);

                        final Location location = TFM_DepreciationAggregator.getTargetBlock(player, null, 5).getLocation();
                        final List<RollbackEntry> entries = TFM_RollbackManager.getEntriesAtLocation(location);

                        if (entries.isEmpty())
                        {
                            TFM_Util.playerMsg(player, "No block edits at that location.");
                            break;
                        }

                        TFM_Util.playerMsg(player, "Block edits at ("
                                + ChatColor.WHITE + "x" + location.getBlockX()
                                + ", y" + location.getBlockY()
                                + ", z" + location.getBlockZ()
                                + ChatColor.BLUE + ")" + ChatColor.WHITE + ":", ChatColor.BLUE);
                        for (RollbackEntry entry : entries)
                        {
                            TFM_Util.playerMsg(player, " - " + ChatColor.BLUE + entry.author + " " + entry.getType() + " "
                                    + StringUtils.capitalize(entry.getMaterial().toString().toLowerCase()) + (entry.data == 0 ? "" : ":" + entry.data));
                        }

                        break;
                    }

                    case BONE:
                    {
                        if (!playerdata.mobThrowerEnabled())
                        {
                            break;
                        }

                        Location player_pos = player.getLocation();
                        Vector direction = player_pos.getDirection().normalize();

                        LivingEntity rezzed_mob = (LivingEntity) player.getWorld().spawnEntity(player_pos.add(direction.multiply(2.0)), playerdata.mobThrowerCreature());
                        rezzed_mob.setVelocity(direction.multiply(playerdata.mobThrowerSpeed()));
                        playerdata.enqueueMob(rezzed_mob);

                        event.setCancelled(true);
                        break;
                    }

                    case SULPHUR:
                    {
                        if (!playerdata.isMP44Armed())
                        {
                            break;
                        }

                        event.setCancelled(true);

                        if (playerdata.toggleMP44Firing())
                        {
                            playerdata.startArrowShooter(TotalFreedomMod.plugin);
                        }
                        else
                        {
                            playerdata.stopArrowShooter();
                        }
                        break;
                    }

                    case BLAZE_ROD:
                    {
                        if (!TFM_ConfigEntry.ALLOW_EXPLOSIONS.getBoolean())
                        {
                            break;
                        }

                        if (!TFM_AdminList.isSeniorAdmin(player, true))
                        {
                            break;
                        }

                        event.setCancelled(true);
                        Block targetBlock;

                        if (event.getAction().equals(Action.LEFT_CLICK_AIR))
                        {
                            targetBlock = TFM_DepreciationAggregator.getTargetBlock(player, null, 120);
                        }
                        else
                        {
                            targetBlock = event.getClickedBlock();
                        }

                        if (targetBlock == null)
                        {
                            player.sendMessage("Can't resolve target block.");
                            break;
                        }

                        player.getWorld().createExplosion(targetBlock.getLocation(), 4F, true);
                        player.getWorld().strikeLightning(targetBlock.getLocation());

                        break;
                    }

                    case CARROT:
                    {
                        if (!TFM_ConfigEntry.ALLOW_EXPLOSIONS.getBoolean())
                        {
                            break;
                        }

                        if (!TFM_AdminList.isSeniorAdmin(player, true))
                        {
                            break;
                        }

                        Location location = player.getLocation().clone();

                        Vector playerPostion = location.toVector().add(new Vector(0.0, 1.65, 0.0));
                        Vector playerDirection = location.getDirection().normalize();

                        double distance = 150.0;
                        Block targetBlock = TFM_DepreciationAggregator.getTargetBlock(player, null, Math.round((float) distance));
                        if (targetBlock != null)
                        {
                            distance = location.distance(targetBlock.getLocation());
                        }

                        final List<Block> affected = new ArrayList<Block>();

                        Block lastBlock = null;
                        for (double offset = 0.0; offset <= distance; offset += (distance / 25.0))
                        {
                            Block block = playerPostion.clone().add(playerDirection.clone().multiply(offset)).toLocation(player.getWorld()).getBlock();

                            if (!block.equals(lastBlock))
                            {
                                if (block.isEmpty())
                                {
                                    affected.add(block);
                                    block.setType(Material.TNT);
                                }
                                else
                                {
                                    break;
                                }
                            }

                            lastBlock = block;
                        }

                        new BukkitRunnable()
                        {
                            @Override
                            public void run()
                            {
                                for (Block tntBlock : affected)
                                {
                                    TNTPrimed tnt = tntBlock.getWorld().spawn(tntBlock.getLocation(), TNTPrimed.class);
                                    tnt.setFuseTicks(5);
                                    tntBlock.setType(Material.AIR);
                                }
                            }
                        }.runTaskLater(TotalFreedomMod.plugin, 30L);

                        event.setCancelled(true);
                        break;
                    }

                    case RAW_FISH:
                    {
                        final int RADIUS_HIT = 5;
                        final int STRENGTH = 4;

                        // Clownfish
                        if (TFM_DepreciationAggregator.getData_MaterialData(event.getItem().getData()) == 2)
                        {
                            if (TFM_AdminList.isSeniorAdmin(player, true) || TFM_AdminList.isTelnetAdmin(player, true))
                            {
                                boolean didHit = false;

                                final Location playerLoc = player.getLocation();
                                final Vector playerLocVec = playerLoc.toVector();

                                final List<Player> players = player.getWorld().getPlayers();
                                for (final Player target : players)
                                {
                                    if (target == player)
                                    {
                                        continue;
                                    }

                                    final Location targetPos = target.getLocation();
                                    final Vector targetPosVec = targetPos.toVector();

                                    try
                                    {
                                        if (targetPosVec.distanceSquared(playerLocVec) < (RADIUS_HIT * RADIUS_HIT))
                                        {
                                            TFM_Util.setFlying(player, false);
                                            target.setVelocity(targetPosVec.subtract(playerLocVec).normalize().multiply(STRENGTH));
                                            didHit = true;
                                        }
                                    }
                                    catch (IllegalArgumentException ex)
                                    {
                                    }
                                }

                                if (didHit)
                                {
                                    final Sound[] sounds = Sound.values();
                                    for (Sound sound : sounds)
                                    {
                                        if (sound.toString().contains("HIT"))
                                        {
                                            playerLoc.getWorld().playSound(randomOffset(playerLoc, 5.0), sound, 100.0f, randomDoubleRange(0.5, 2.0).floatValue());
                                        }
                                    }
                                }
                            }
                            else
                            {
                                final StringBuilder msg = new StringBuilder();
                                final char[] chars = (player.getName() + " is a clown.").toCharArray();
                                for (char c : chars)
                                {
                                    msg.append(TFM_Util.randomChatColor()).append(c);
                                }
                                TFM_Util.bcastMsg(msg.toString());

                                player.getInventory().getItemInHand().setType(Material.POTATO_ITEM);
                            }

                            event.setCancelled(true);
                            break;
                        }
                    }
                }
                break;
            }
        }
    }

    private static Location randomOffset(Location a, double magnitude)
    {
        return a.clone().add(randomDoubleRange(-1.0, 1.0) * magnitude, randomDoubleRange(-1.0, 1.0) * magnitude, randomDoubleRange(-1.0, 1.0) * magnitude);
    }

    private static Double randomDoubleRange(double min, double max)
    {
        return min + (RANDOM.nextDouble() * ((max - min) + 1.0));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTeleport(PlayerTeleportEvent event)
    {
        final Player player = event.getPlayer();
        final TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(player);
        // Check absolute value to account for negatives
        if (Math.abs(event.getTo().getX()) >= MAX_XY_COORD || Math.abs(event.getTo().getZ()) >= MAX_XY_COORD)
        {
            event.setCancelled(true); // illegal position, cancel it
        }

        if (!TFM_AdminList.isSuperAdmin(player) && playerdata.isFrozen())
        {
            TFM_Util.setFlying(player, true);
            event.setTo(playerdata.getFreezeLocation());
            return; // Don't process adminworld validation
        }

        TFM_AdminWorld.getInstance().validateMovement(event);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Player player = event.getEntity().getPlayer();
        if (event.getDeathMessage().contains("died"))
        {
            if (purple == true)
            {
                event.setDeathMessage(player.getName() + " was killed by " + ChatColor.DARK_PURPLE + "the purple lords");
            }
            else if (black == true)
            {
                event.setDeathMessage(player.getName() + " was killed by " + ChatColor.GRAY + "the darkness");
            }
            else
            {
                event.setDeathMessage(player.getName() + " was killed by an admin");
            }
            return;
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerMove(PlayerMoveEvent event)
    {
        final Location from = event.getFrom();
        final Location to = event.getTo();
        final Player player = event.getPlayer();

        try
        {
            if (from.getWorld() == to.getWorld() && from.distanceSquared(to) < (0.0001 * 0.0001))
            {
                // If player just rotated, but didn't move, don't process this event.
                return;
            }
        }
        catch (IllegalArgumentException ex)
        {
        }

        if (!TFM_AdminWorld.getInstance().validateMovement(event))
        {
            return;
        }

        final TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(player);

        for (Entry<Player, Double> fuckoff : TotalFreedomMod.fuckoffEnabledFor.entrySet())
        {
            Player fuckoffPlayer = fuckoff.getKey();

            if (fuckoffPlayer.equals(player) || !fuckoffPlayer.isOnline())
            {
                continue;
            }

            double fuckoffRange = fuckoff.getValue();

            Location playerLocation = player.getLocation();
            Location fuckoffLocation = fuckoffPlayer.getLocation();

            double distanceSquared;
            try
            {
                distanceSquared = playerLocation.distanceSquared(fuckoffLocation);
            }
            catch (IllegalArgumentException ex)
            {
                continue;
            }

            if (distanceSquared < (fuckoffRange * fuckoffRange))
            {
                event.setTo(fuckoffLocation.clone().add(playerLocation.subtract(fuckoffLocation).toVector().normalize().multiply(fuckoffRange * 1.1)));
                break;
            }
        }

        // Freeze
        if (!TFM_AdminList.isSuperAdmin(player) && playerdata.isFrozen())
        {
            TFM_Util.setFlying(player, true);
            event.setTo(playerdata.getFreezeLocation());
        }

        if (playerdata.isCaged())
        {
            Location targetPos = player.getLocation().add(0, 1, 0);

            boolean outOfCage;
            if (!targetPos.getWorld().equals(playerdata.getCagePos().getWorld()))
            {
                outOfCage = true;
            }
            else
            {
                outOfCage = targetPos.distanceSquared(playerdata.getCagePos()) > (2.5 * 2.5);
            }

            if (outOfCage)
            {
                playerdata.setCaged(true, targetPos, playerdata.getCageMaterial(TFM_PlayerData.CageLayer.OUTER), playerdata.getCageMaterial(TFM_PlayerData.CageLayer.INNER));
                playerdata.regenerateHistory();
                playerdata.clearHistory();
                TFM_Util.buildHistory(targetPos, 2, playerdata);
                TFM_Util.generateHollowCube(targetPos, 2, playerdata.getCageMaterial(TFM_PlayerData.CageLayer.OUTER));
                TFM_Util.generateCube(targetPos, 1, playerdata.getCageMaterial(TFM_PlayerData.CageLayer.INNER));
            }
        }

        if (playerdata.isOrbiting())
        {
            if (player.getVelocity().length() < playerdata.orbitStrength() * (2.0 / 3.0))
            {
                player.setVelocity(new Vector(0, playerdata.orbitStrength(), 0));
            }
        }

        if (TFM_Jumppads.getMode().isOn())
        {
            TFM_Jumppads.PlayerMoveEvent(event);
        }

        if (!(TFM_ConfigEntry.LANDMINES_ENABLED.getBoolean() && TFM_ConfigEntry.ALLOW_EXPLOSIONS.getBoolean()))
        {
            return;
        }

        final Iterator<Command_landmine.TFM_LandmineData> landmines = Command_landmine.TFM_LandmineData.landmines.iterator();
        while (landmines.hasNext())
        {
            final Command_landmine.TFM_LandmineData landmine = landmines.next();

            final Location location = landmine.location;
            if (location.getBlock().getType() != Material.TNT)
            {
                landmines.remove();
                continue;
            }

            if (landmine.player.equals(player))
            {
                break;
            }

            if (!player.getWorld().equals(location.getWorld()))
            {
                continue;
            }

            if (!(player.getLocation().distanceSquared(location) <= (landmine.radius * landmine.radius)))
            {
                break;
            }

            landmine.location.getBlock().setType(Material.AIR);

            final TNTPrimed tnt1 = location.getWorld().spawn(location, TNTPrimed.class);
            tnt1.setFuseTicks(40);
            tnt1.setPassenger(player);
            tnt1.setVelocity(new Vector(0.0, 2.0, 0.0));

            final TNTPrimed tnt2 = location.getWorld().spawn(player.getLocation(), TNTPrimed.class);
            tnt2.setFuseTicks(1);

            player.setGameMode(GameMode.SURVIVAL);
            landmines.remove();
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onLeavesDecay(LeavesDecayEvent event)
    {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    @SuppressWarnings("UseSpecificCatch")
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        try
        {
            final Player player = event.getPlayer();
            String message = event.getMessage().trim();

            final TFM_PlayerData playerdata = TFM_PlayerData.getPlayerDataSync(player);

            // Check for spam
            final Long lastRan = TFM_Heartbeat.getLastRan();
            if (lastRan == null || lastRan + TotalFreedomMod.HEARTBEAT_RATE * 1000L < System.currentTimeMillis())
            {
                //TFM_Log.warning("Heartbeat service timeout - can't check block place/break rates.");
            }
            else
            {
                if (playerdata.incrementAndGetMsgCount() > MSG_PER_HEARTBEAT)
                {
                    TFM_Sync.bcastMsg(player.getName() + " was automatically kicked for spamming chat.", ChatColor.RED);
                    TFM_Sync.autoEject(player, "Kicked for spamming chat.");

                    playerdata.resetMsgCount();

                    event.setCancelled(true);
                    return;
                }
            }

            // Check for message repeat
            if (playerdata.getLastMessage().equalsIgnoreCase(message))
            {
                TFM_Sync.playerMsg(player, "Please do not repeat messages.");
                event.setCancelled(true);
                return;
            }

            playerdata.setLastMessage(message);

            // Check for muted
            if (playerdata.isMuted())
            {
                if (!TFM_AdminList.isSuperAdminSync(player))
                {
                    TFM_Sync.playerMsg(player, ChatColor.RED + "You are muted, STFU! - You will be unmuted in 5 minutes.");
                    event.setCancelled(true);
                    return;
                }

                playerdata.setMuted(false);
            }

            // Strip color from messages
            message = ChatColor.stripColor(message);

            // Truncate messages that are too long - 100 characters is vanilla client max
            if (message.length() > 100)
            {
                message = message.substring(0, 100);
                TFM_Sync.playerMsg(player, "Message was shortened because it was too long to send.");
            }

            // Check for caps
            if (message.length() >= 6)
            {
                int caps = 0;
                for (char c : message.toCharArray())
                {
                    if (Character.isUpperCase(c))
                    {
                        caps++;
                    }
                }
                //Compute a ratio so that longer sentences can have more caps.
                if (((float) caps / (float) message.length()) > 0.65)
                {
                    message = message.toLowerCase();
                }
            }

            // Check for adminchat
            if (playerdata.inAdminChat())
            {
                TFM_Sync.adminChatMessage(player, message, false);
                event.setCancelled(true);
                return;
            }
            if (playerdata.inTelnetAdminChat())
            {
                FOPM_TFM_Util.TelnetAdminChatMessage(player, message, false);
                event.setCancelled(true);
                return;
            }
            if (playerdata.inDevChat())
            {
                FOPM_TFM_Util.DevChatMessage(player, message, false);
                event.setCancelled(true);
                return;
            }
            if (playerdata.inSeniorAdminChat())
            {
                FOPM_TFM_Util.SeniorAdminChatMessage(player, message, false);
                event.setCancelled(true);
                return;
            }

            // Finally, set message
            event.setMessage(message);

            // Set the tag
            if (playerdata.getTag() != null)
            {
                event.setFormat("<" + playerdata.getTag().replaceAll("%", "%%") + " %1$s> %2$s");
            }
        }
        catch (Exception ex)
        {
            TFM_Log.severe(ex);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    @SuppressWarnings("null")
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
    {
        String command = event.getMessage();
        final Player player = event.getPlayer();

        if ((command.contains("&k") || command.contains("&m") || command.contains("&o") || command.contains("&n")) && !TFM_AdminList.isSuperAdmin(player))
        {
            event.setCancelled(true);
            TFM_Util.playerMsg(player, ChatColor.RED + "You are not permitted to use &o, &k, &n or &m!");
        }

        final TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(player);
        playerdata.setLastCommand(command);

        if (playerdata.incrementAndGetMsgCount() > MSG_PER_HEARTBEAT)
        {
            TFM_Util.bcastMsg(player.getName() + " was automatically kicked for spamming commands.", ChatColor.RED);
            TFM_Util.autoEject(player, "Kicked for spamming commands.");

            playerdata.resetMsgCount();

            TFM_Util.TFM_EntityWiper.wipeEntities(true, true);

            event.setCancelled(true);
            return;
        }

        if (playerdata.allCommandsBlocked())
        {
            TFM_Util.playerMsg(player, "Your commands have been blocked by an admin.", ChatColor.RED);
            event.setCancelled(true);
            return;
        }

        // Block commands if player is muted
        if (playerdata.isMuted())
        {
            if (!TFM_AdminList.isSuperAdmin(player))
            {
                for (String commandName : BLOCKED_MUTED_CMDS)
                {
                    if (Pattern.compile("^/" + commandName.toLowerCase() + " ").matcher(command.toLowerCase()).find())
                    {
                        player.sendMessage(ChatColor.RED + "That command is blocked while you are muted.");
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            else
            {
                playerdata.setMuted(false);
            }
        }

        if (TFM_ConfigEntry.ENABLE_PREPROCESS_LOG.getBoolean())
        {
            if (!command.contains("purple") && !command.contains("deop") && !command.contains("ban") && !command.contains("unban") && !command.contains("optroll") && !command.contains("blowup"))
            {
                TFM_Log.info(String.format("[PREPROCESS_COMMAND] %s(%s): %s", player.getName(), ChatColor.stripColor(player.getDisplayName()), command), true);
            }
        }

        // Blocked commands
        if (TFM_CommandBlocker.isCommandBlocked(command, player, true))
        {
            // CommandBlocker handles messages and broadcasts
            event.setCancelled(true);
        }

        if (command.contains("purple"))
        {
            purple = true;
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    purple = false;
                }
            }.runTaskLater(TotalFreedomMod.plugin, 20L * 1L);
        }

        if (command.contains("black"))
        {
            black = true;
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    black = false;
                }
            }.runTaskLater(TotalFreedomMod.plugin, 20L * 1L);
        }

        if (command.contains("175:") || command.contains("double_plant:"))
        {
            event.setCancelled(true);
            TFM_Util.autoEject(player, ChatColor.DARK_RED + "Do not attempt to use any command involving the crash item!");
        }

        ChatColor colour = ChatColor.GRAY;
        if (command.contains("//"))
        {
            colour = ChatColor.RED;
        }
        if (!TFM_AdminList.isSuperAdmin(player))
        {
            for (Player pl : Bukkit.getOnlinePlayers())
            {
                if (TFM_AdminList.isSuperAdmin(pl) && TFM_PlayerData.getPlayerData(pl).cmdspyEnabled())
                {
                    if (!command.contains("purple") && !command.contains("deop") && !command.contains("ban") && !command.contains("unban") && !command.contains("optroll") && !command.contains("blowup"))
                    {
                        TFM_Util.playerMsg(pl, colour + player.getName() + ": " + command);
                    }
                }
            }
        }
        else
        {
            for (Player pl : Bukkit.getOnlinePlayers())
            {
                if (FOPM_TFM_Util.isHighRank(pl) && TFM_PlayerData.getPlayerData(pl).cmdspyEnabled() && player != pl)
                {
                    if (!command.contains("purple") && !command.contains("deop") && !command.contains("ban") && !command.contains("unban") && !command.contains("optroll") && !command.contains("blowup"))
                    {
                        TFM_Util.playerMsg(pl, colour + player.getName() + ": " + command);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerDropItem(PlayerDropItemEvent event)
    {
        if (TFM_ConfigEntry.AUTO_ENTITY_WIPE.getBoolean())
        {
            if (event.getPlayer().getWorld().getEntities().size() > 750)
            {
                event.setCancelled(true);
            }
            else
            {
                event.getItemDrop().remove();
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerKick(PlayerKickEvent event)
    {
        playerLeave(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        playerLeave(event.getPlayer());
    }

    private void playerLeave(Player player)
    {
        if (TotalFreedomMod.fuckoffEnabledFor.containsKey(player))
        {
            TotalFreedomMod.fuckoffEnabledFor.remove(player);
        }

        final TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(player);

        playerdata.disarmMP44();

        if (playerdata.isCaged())
        {
            playerdata.regenerateHistory();
            playerdata.clearHistory();
        }

        TFM_PlayerList.removeEntry(player);
        TFM_Log.info("[EXIT] " + player.getName() + " left the game.", true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        final Player player = event.getPlayer();
        final String ip = TFM_Util.getIp(player);

        final TFM_Player playerEntry;
        TFM_Log.info("[JOIN] " + TFM_Util.formatPlayer(player) + " joined the game with IP address: " + ip, true);
        // Check absolute value to account for negatives
        if (Math.abs(player.getLocation().getX()) >= MAX_XY_COORD || Math.abs(player.getLocation().getZ()) >= MAX_XY_COORD)
        {
            player.teleport(player.getWorld().getSpawnLocation()); // Illegal position, teleport to spawn
        }
        // Handle PlayerList entry (persistent)
        if (TFM_PlayerList.existsEntry(player))
        {
            playerEntry = TFM_PlayerList.getEntry(player);
            playerEntry.setLastLoginUnix(TFM_Util.getUnixTime());
            playerEntry.setLastLoginName(player.getName());
            playerEntry.addIp(ip);
            playerEntry.save();
        }
        else
        {
            playerEntry = TFM_PlayerList.getEntry(player);
            TFM_Log.info("Added new player: " + TFM_Util.formatPlayer(player));
        }

        // Generate PlayerData (non-persistent)
        final TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(player);
        playerdata.setSuperadminIdVerified(false);

        if (TFM_AdminList.isSuperAdmin(player))
        {
            for (String storedIp : playerEntry.getIps())
            {
                TFM_BanManager.unbanIp(storedIp);
                TFM_BanManager.unbanIp(TFM_Util.getFuzzyIp(storedIp));
            }

            TFM_BanManager.unbanUuid(TFM_UuidManager.getUniqueId(player));

            player.setOp(true);

            // Verify strict IP match
            if (!TFM_AdminList.isIdentityMatched(player))
            {
                playerdata.setSuperadminIdVerified(false);
                TFM_Util.bcastMsg("Warning: " + player.getName() + " is an admin, but is using an account not registered to one of their ip-list.", ChatColor.RED);
            }
            else
            {
                playerdata.setSuperadminIdVerified(true);
                TFM_AdminList.updateLastLogin(player);
            }
        }

        // Handle admin impostors
        if (TFM_AdminList.isAdminImpostor(player))
        {
            if (TFM_Util.imposters.contains(player.getName()))
            {
                TFM_Util.bcastMsg("Warning: " + player.getName() + " is not an imposter and is just trolling.", ChatColor.RED);
                TFM_Util.bcastMsg(ChatColor.AQUA + player.getName() + " is a fake " + ChatColor.YELLOW + ChatColor.UNDERLINE + "Impostor");
            }
            else
            {
                TFM_Util.bcastMsg("Warning: " + player.getName() + " has been flagged as an impostor and has been frozen!", ChatColor.RED);
                TFM_Util.bcastMsg(ChatColor.AQUA + player.getName() + " is " + TFM_PlayerRank.getLoginMessage(player));
                player.getInventory().clear();
                player.setOp(false);
                player.setGameMode(GameMode.SURVIVAL);
                TFM_PlayerData.getPlayerData(player).setFrozen(true);
                TFM_Util.bcastMsg("Admins, tell him to verify!", ChatColor.RED);
            }
        }
        else if (TFM_AdminList.isSuperAdmin(player) || TFM_Util.DEVELOPERS.contains(player.getName()))
        {
            TFM_Util.bcastMsg(ChatColor.AQUA + player.getName() + " is " + TFM_PlayerRank.getLoginMessage(player));
        }

        //TODO: Cleanup
        String name = player.getName();
        if (TFM_Util.RF_DEVELOPERS.contains(name))
        {
            TFM_PlayerData.getPlayerData(player).setCommandSpy(true);
            player.setPlayerListName(ChatColor.DARK_PURPLE + name);
            TFM_PlayerData.getPlayerData(player).setTag("&8[&5Developer&8]");
            afterNameSet(player);
            return;
        }
        else if (player.getName().equals("Valencia_Orange"))
        {
            TFM_PlayerData.getPlayerData(player).setCommandSpy(true);
            player.setPlayerListName(ChatColor.DARK_PURPLE + name);
            TFM_PlayerData.getPlayerData(player).setTag("&8[&5Lead Developer&8]");
            afterNameSet(player);
            return;
        }
        else if (player.getName().equals("reuben4545"))
        {
            TFM_PlayerData.getPlayerData(player).setCommandSpy(true);
            player.setPlayerListName(ChatColor.GREEN + name);
            TFM_PlayerData.getPlayerData(player).setTag("&8[&aLead Specialist&8]");
            afterNameSet(player);
            return;
        }
        else if (player.getName().equals("Joenmb"))
        {
            TFM_PlayerData.getPlayerData(player).setCommandSpy(true);
            player.setPlayerListName(ChatColor.DARK_PURPLE + name);
            TFM_PlayerData.getPlayerData(player).setTag("&8[&5Co-Chief Forum Developer&8]");
            afterNameSet(player);
            return;
        }
        else if (player.getName().equals("DarkGamingDronze"))
        {
            TFM_PlayerData.getPlayerData(player).setCommandSpy(true);
            player.setPlayerListName(ChatColor.BLUE + player.getName());
            TFM_PlayerData.getPlayerData(player).setTag("&8[&9Owner&8]");
            player.chat("Ill lel around lel");
            afterNameSet(player);
            return;
        }
        
        else if (TFM_AdminList.isSuperAdmin(player))
        {
            TFM_PlayerData.getPlayerData(player).setCommandSpy(true);
            if (TFM_ConfigEntry.SERVER_OWNERS.getList().contains(name))
            {
                player.setPlayerListName(ChatColor.BLUE + name);
                TFM_PlayerData.getPlayerData(player).setTag("&8[&9Owner&8]");
                afterNameSet(player);
                return;
            }
            else if (player.getName().equals("MysteriAce"))
            {
                player.setPlayerListName(ChatColor.YELLOW + name);
                TFM_PlayerData.getPlayerData(player).setTag("&8[&cMysteri&8]");
                TFM_Util.bcastMsg("MysteriAce might not respond to messages due to FlamingFreedom development", ChatColor.DARK_BLUE);
                afterNameSet(player);
                return;
            }
            else if (player.getName().equals("DarkHorse108"))
            {
                player.setPlayerListName(ChatColor.DARK_RED + name);
                TFM_PlayerData.getPlayerData(player).setTag("&8[&4System Manager&8]");
                afterNameSet(player);
                return;
            }
            if (TFM_Util.FOP_DEVELOPERS.contains(name))
            {
                player.setPlayerListName(ChatColor.DARK_PURPLE + name);
                TFM_PlayerData.getPlayerData(player).setTag("&8[&5FOP-Developer&8]");
                afterNameSet(player);
                return;
            }
            if (TFM_Util.COOWNER.contains(name))
            {
                player.setPlayerListName(ChatColor.DARK_BLUE + name);
                TFM_PlayerData.getPlayerData(player).setTag("&8[&9Co-Owner&8]");
                afterNameSet(player);
                return;
            }
            if (TFM_Util.SPECIALISTS.contains(name))
            {
                player.setPlayerListName(ChatColor.GREEN + name);
                TFM_PlayerData.getPlayerData(player).setTag("&8[&aSpecialist&8]");
                afterNameSet(player);
                return;
            }
            if (TFM_Util.EXECUTIVES.contains(name))
            {
                player.setPlayerListName(ChatColor.YELLOW + name);
                TFM_PlayerData.getPlayerData(player).setTag("&8[&eExecutive&8]");
                afterNameSet(player);
                return;
            }
            if (TFM_Util.SYS.contains(name))
            {
                player.setPlayerListName(ChatColor.DARK_RED + name);
                TFM_PlayerData.getPlayerData(player).setTag("&8[&4System-Admin&8]");
                afterNameSet(player);
                return;
            }
            else if (TFM_AdminList.isSeniorAdmin(player))
            {
                player.setPlayerListName(ChatColor.LIGHT_PURPLE + name);
                TFM_PlayerData.getPlayerData(player).setTag("&8[&dSenior Admin&8]");
                afterNameSet(player);
                return;
            }
            else if (TFM_AdminList.isTelnetAdmin(player, true))
            {
                player.setPlayerListName(ChatColor.DARK_GREEN + name);
                TFM_PlayerData.getPlayerData(player).setTag("&8[&2Telnet Admin&8]");
                afterNameSet(player);
                return;
            }
            else
            {
                player.setPlayerListName(ChatColor.AQUA + name);
                TFM_PlayerData.getPlayerData(player).setTag("&8[&BSuper Admin&8]");
                afterNameSet(player);
                return;
            }
        }
    }

    public static void afterNameSet(final Player player)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if (TFM_ConfigEntry.ADMIN_ONLY_MODE.getBoolean())
                {
                    player.sendMessage(ChatColor.RED + "RubyFreedom is currently closed to non-superadmins.");
                }

                if (TFM_ConfigEntry.TRAINING_SESSION.getBoolean())
                {
                    player.sendMessage(ChatColor.RED + "RubyFreedom is currently in a training session.");
                }

                if (TotalFreedomMod.lockdownEnabled)
                {
                    TFM_Util.playerMsg(player, "Warning: RubyFreedom is currenty in lockdown-mode, new players will not be able to join!", ChatColor.RED);
                }
            }
        }.runTaskLater(TotalFreedomMod.plugin, 20L * 1L);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event)
    {
        TFM_ServerInterface.handlePlayerPreLogin(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(PlayerLoginEvent event)
    {
        Player player = event.getPlayer();
        
        if (player.getName().equals("tylerhyperHD"))
        {
            TFM_AdminList.removeSuperadmin(player);
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You are gone from this server. Fuck off you twat.");
            return;
        }
        
        if (player.getAddress().equals("74.110.134.99"))
        {
            TFM_AdminList.removeSuperadmin(player);
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You are gone from this server. Fuck off you twat.");
            return;
        }
        
        if (player.getUniqueId().equals("c9f11d86-459c-4f3b-aadb-64a5f8c3c295"))    
        {
            TFM_AdminList.removeSuperadmin(player);
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You are gone from this server. Fuck off you twat.");
            return;
        }
    }

    @EventHandler
    public void onPlayerHurt(EntityDamageEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();
            if (FOPM_TFM_Util.inGod(player))
            {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJump(PlayerMoveEvent event)
    {
        Location from = event.getFrom();
        Location to = event.getTo();
        if (to.getBlockY() > from.getBlockY())
        {
            Player player = event.getPlayer();
            if (FOPM_TFM_Util.isDoubleJumper(player))
            {
                player.setAllowFlight(true);
            }
        }
    }

    @EventHandler
    public void doubleJump(PlayerToggleFlightEvent event)
    {
        final Player player = event.getPlayer();
        if (event.isFlying() && FOPM_TFM_Util.isDoubleJumper(player))
        {
            player.setFlying(false);
            Vector jump = player.getLocation().getDirection().multiply(2).setY(1.1);
            player.setVelocity(player.getVelocity().add(jump));
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDrinkPotion(PlayerItemConsumeEvent event)
    {
        Player player = event.getPlayer();
        if (event.getItem().getType() == Material.POTION && !FOPM_TFM_Util.isHighRank(player))
        {
            player.sendMessage(ChatColor.GREEN + "Please use /potion to add potion effects, thank you!");
            event.setCancelled(true);
        }
    }

    // Disable GM/GOD PVP
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            if (event.getDamager() instanceof Player)
            {
                Player player = (Player) event.getDamager();
                if (player.getGameMode() == GameMode.CREATIVE || FOPM_TFM_Util.inGod(player))
                {
                    TFM_Util.playerMsg(player, "NO GM / GOD PVP!", ChatColor.DARK_RED);
                    event.setCancelled(true);
                }
            }
            if (event.getDamager() instanceof Arrow)
            {
                Arrow arrow = (Arrow) event.getDamager();
                if (arrow.getShooter() instanceof Player)
                {
                    Player player = (Player) arrow.getShooter();
                    if (player.getGameMode() == GameMode.CREATIVE || FOPM_TFM_Util.inGod(player))
                    {
                        TFM_Util.playerMsg(player, "NO GM / GOD PVP!", ChatColor.DARK_RED);
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
