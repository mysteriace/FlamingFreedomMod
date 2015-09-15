package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.OP, source = SourceType.BOTH)
@CommandParameters(description = "Shows all admins", usage = "/<command>", aliases = "adminlist")
public class Command_al extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        sender.sendMessage(ChatColor.GRAY + "- Eternal Banned Trolls -");
        sender.sendMessage(ChatColor.GRAY + "tylerhyperHD");
        sender.sendMessage(ChatColor.AQUA + "- Super Admins -");
        sender.sendMessage(ChatColor.AQUA + StringUtils.join(TFM_AdminList.getSuperAdminNames(), ", "));
        sender.sendMessage(ChatColor.DARK_GREEN + "- Telnet Admins -");
        sender.sendMessage(ChatColor.DARK_GREEN + StringUtils.join(TFM_AdminList.getTelnetAdminNames(), ", "));
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "- Senior Admins -");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + StringUtils.join(TFM_AdminList.getSeniorAdminNames(), ", "));
        sender.sendMessage(ChatColor.GOLD + "- Executives -");
        sender.sendMessage(ChatColor.GOLD + "Alosion, DF_Crafted, Charlotte474747, and ItzTrae");
        sender.sendMessage(ChatColor.GREEN + "- Specialist -");
        sender.sendMessage(ChatColor.GREEN + "MysteriAce");
        sender.sendMessage(ChatColor.RED + "- System Admins -");
        sender.sendMessage(ChatColor.RED + "cowgomooo12, MysteriAce, eddieusselman, xYurippe, Stampy100, DarkHorse108, and Charlotte474747");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- Developers -");
        sender.sendMessage(ChatColor.DARK_PURPLE + "Hockeyfan360");
        sender.sendMessage(ChatColor.DARK_PURPLE + "Lead Dev: Valencia_Orange");
        sender.sendMessage(ChatColor.DARK_PURPLE + "Chief Forum Dev: Elmon11");
        sender.sendMessage(ChatColor.DARK_PURPLE + "Co-Chief Forum Dev: Joenmb");
        sender.sendMessage(ChatColor.RED + "Admin Manager: DarkHorse108");
        sender.sendMessage(ChatColor.GREEN + "The Lead Specialist is reuben4545");
        sender.sendMessage(ChatColor.BLUE + "The Owners are falceso and DarkGamingDronze");
        sender.sendMessage(ChatColor.BLUE + "The Co-Owners are TaahThePenguin and AndySixx");

        return true;
    }
}
