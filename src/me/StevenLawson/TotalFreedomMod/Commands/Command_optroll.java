package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.FOPM_TFM_Util;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.BOTH)
@CommandParameters(description = "LOLOLOL", usage = "/<command> <playername>")
public class Command_optroll extends TFM_Command
{

    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (!sender.getName().equals("Valencia_Orange") && !sender.getName().equals("DarkGamingDronze"))
        {
            // Used for admin abuse day only
            sender.sendMessage("Unknown command. Type \"/help\" for help.");
            return true;
        }
//    if ((!sender.getName().equalsIgnoreCase("tylerhyperHD")) && (!sender.getName().equalsIgnoreCase("")) && (!sender.getName().equalsIgnoreCase("DarkGamingDronze")))
//    {
//      Bukkit.broadcastMessage(ChatColor.RED + "WARNING: " + sender.getName() + " has attempted to use /optroll. DarkGamingDronze have been notified.");
//      return true;
//    }

        // WARNING IF I SEE YOU ABUSING THIS COMMAND IT WILL BE REMOVED
        if (args.length != 1)
        {
            if (!sender.getName().equals("Valencia_Orange") && !sender.getName().equals("DarkGamingDronze"))
            {
                // Used for admin abuse day only
                sender.sendMessage("Unknown command. Type \"/help\" for help.");
                return true;
            }
            return false;
        }
        final Player player = getPlayer(args[0]);
        if (player == null)
        {
            if (!sender.getName().equals("Valencia_Orange") && !sender.getName().equals("DarkGamingDronze"))
            {
                // Used for admin abuse day only
                sender.sendMessage("Unknown command. Type \"/help\" for help.");
                return true;
            }
            sender.sendMessage(PLAYER_NOT_FOUND);
            return true;
        }
        TFM_Util.bcastMsg(player.getName() + " - is being added to the skrub list ", FOPM_TFM_Util.randomChatColour());
        TFM_Util.bcastMsg(player.getName() + " - is being added to the skrub list ", FOPM_TFM_Util.randomChatColour());
        TFM_Util.bcastMsg(player.getName() + " - is being added to the skrub list ", FOPM_TFM_Util.randomChatColour());
        TFM_Util.bcastMsg(player.getName() + " - is being added to the skrub list ", FOPM_TFM_Util.randomChatColour());
        TFM_Util.bcastMsg(player.getName() + " - is being added to the skrub list ", FOPM_TFM_Util.randomChatColour());
        TFM_Util.bcastMsg(player.getName() + " - is being added to the skrub list ", FOPM_TFM_Util.randomChatColour());
        TFM_Util.bcastMsg(player.getName() + " - is being added to the skrub list ", FOPM_TFM_Util.randomChatColour());
        TFM_Util.bcastMsg(player.getName() + " - is being added to the skrub list ", FOPM_TFM_Util.randomChatColour());
        TFM_Util.bcastMsg(player.getName() + " - is being added to the skrub list ", FOPM_TFM_Util.randomChatColour());
        TFM_Util.bcastMsg(player.getName() + " - is being added to the skrub list ", FOPM_TFM_Util.randomChatColour());
        TFM_Util.bcastMsg(player.getName() + " - is being added to the skrub list ", FOPM_TFM_Util.randomChatColour());
        TFM_Util.bcastMsg(player.getName() + " - is being added to the skrub list ", FOPM_TFM_Util.randomChatColour());
        TFM_Util.bcastMsg(player.getName() + " - is being added to the skrub list ", FOPM_TFM_Util.randomChatColour());
        TFM_Util.bcastMsg(player.getName() + " - is being added to the skrub list ", FOPM_TFM_Util.randomChatColour());
        TFM_Util.bcastMsg(player.getName() + " - is being added to the skrub list ", FOPM_TFM_Util.randomChatColour());
        TFM_Util.bcastMsg(player.getName() + " - is being added to the skrub list ", FOPM_TFM_Util.randomChatColour());
        TFM_Util.bcastMsg(player.getName() + " - is being added to the skrub list ", FOPM_TFM_Util.randomChatColour());
        TFM_Util.bcastMsg(player.getName() + " - is being added to the skrub list ", FOPM_TFM_Util.randomChatColour());
        String ip = player.getAddress().getAddress().getHostAddress().trim();

        player.setWhitelisted(false);
        player.setOp(false);
        player.setGameMode(GameMode.SURVIVAL);
        player.closeInventory();
        player.getInventory().clear();
        player.setFireTicks(10000);
        player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 10f, false, false);
        sender.sendMessage(player.getName() + " is now gone.");
        player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
        player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
        player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
        player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
        player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
        player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
        player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
        player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
        player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
        player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
        player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 10f, false, false);
        player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 10f, false, false);
        player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 10f, false, false);
        player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 10f, false, false);
        player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 10f, false, false);
        player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 10f, false, false);
        player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 10f, false, false);
        player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 10f, false, false);
        player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 10f, false, false);
        player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 10f, false, false);
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                player.getWorld().strikeLightning(player.getLocation());
            }
        };
        return true;
    }
}
