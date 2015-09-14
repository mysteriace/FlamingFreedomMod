package me.StevenLawson.TotalFreedomMod.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.OP, source = SourceType.BOTH)
@CommandParameters(description = "Rules for ALL ADMINS!", usage = "/<command>", aliases = "ar")
public class Command_adminrules extends TFM_Command
{

    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        playerMsg(ChatColor.RED + "§1[1]§c Respect other admins and OPS.");
        playerMsg(ChatColor.RED + "§1[3]§c Don't smite admin period I don't give a fuck if you dont fucking like them -_-.");
        playerMsg(ChatColor.RED + "§1[4]§c Admins CAN tp to any admin + OP!");
        playerMsg(ChatColor.RED + "§1[5]§c Don't brag. Don't disrespect things about what they have.");
        playerMsg(ChatColor.RED + "§1[6]§c Be nice to other admins!");
        playerMsg(ChatColor.RED + "§1[7]§c Don't say any thing negitive about an admin! or OP");
        playerMsg(ChatColor.RED + "§1[8]§c Don't try to boss the founder or developers around! DONT try to boss anyone around!");
        playerMsg(ChatColor.RED + "§1[9]§c Don't Gtfo, kick, ect with that admins permissions!");
        playerMsg(ChatColor.DARK_RED + "§1[10]§c Do NOT spam or abuse!");
        playerMsg(ChatColor.RED + "Only DarkGamingDronze, falceso, DarkHorse108 and AndySixx can accept/deny applications!");
        playerMsg(ChatColor.RED + "A SrA can not reject an Executive/Sys/Specialist!");
        playerMsg(ChatColor.DARK_RED + "Rules 3, and 9 do not apply to founders, Chief Dev!");
        playerMsg(ChatColor.DARK_RED + "§1[11]§c DONT TELL THE FOUNDER OR PLUGIN DEVS WHAT TO DO!");
        playerMsg(ChatColor.DARK_RED + "Failure to follow these rules will result in suspension!");
        return true;
    }
}
