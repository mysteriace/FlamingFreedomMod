package me.StevenLawson.TotalFreedomMod;

import me.StevenLawson.TotalFreedomMod.Config.TFM_ConfigEntry;
import static me.StevenLawson.TotalFreedomMod.TFM_Util.COOWNER;
import static me.StevenLawson.TotalFreedomMod.TFM_Util.DEVELOPERS;
import static me.StevenLawson.TotalFreedomMod.TFM_Util.EXECUTIVES;
import static me.StevenLawson.TotalFreedomMod.TFM_Util.FOP_DEVELOPERS;
import static me.StevenLawson.TotalFreedomMod.TFM_Util.RF_DEVELOPERS;
import static me.StevenLawson.TotalFreedomMod.TFM_Util.SYS;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public enum TFM_PlayerRank
{
    DEVELOPER("a " + ChatColor.DARK_PURPLE + "TotalFreedom Developer", ChatColor.DARK_PURPLE + "[TF-Dev]"),
    FOP_DEVELOPER("a " + ChatColor.DARK_PURPLE + "Old FreedomOp Developer", ChatColor.DARK_PURPLE + "[FOP-Dev]"),
    RF_DEVELOPER("a " + ChatColor.DARK_PURPLE + "Developer", ChatColor.DARK_PURPLE + "[Dev]"),
    IMPOSTOR("an " + ChatColor.YELLOW + ChatColor.UNDERLINE + "Impostor", ChatColor.YELLOW.toString() + ChatColor.UNDERLINE + "[IMP]"),
    FAKEIMPOSTOR("a " + ChatColor.YELLOW + ChatColor.UNDERLINE + "FAKE Impostor", ChatColor.YELLOW.toString() + ChatColor.UNDERLINE + "[IMP]"),
    NON_OP("a " + ChatColor.GREEN + "Non-OP", ChatColor.GREEN.toString()),
    OP("an " + ChatColor.RED + "OP", ChatColor.RED + "[OP]"),
    LEAD_SPECIALIST("the " + ChatColor.GREEN + "Lead Specialist", ChatColor.GREEN + "[L-Specialist]"),
    SUPER("a " + ChatColor.GOLD + "Super Admin", ChatColor.GOLD + "[SA]"),
    TELNET("a " + ChatColor.DARK_GREEN + "Super Telnet Admin", ChatColor.DARK_GREEN + "[STA]"),
    SENIOR("a " + ChatColor.LIGHT_PURPLE + "Senior Admin", ChatColor.LIGHT_PURPLE + "[SrA]"),
    OWNER("the " + ChatColor.BLUE + "Owner " + ChatColor.AQUA + "and" + ChatColor.BLUE + " Founder " + ChatColor.AQUA + "of " + ChatColor.RED + "RubyFreedom", ChatColor.translateAlternateColorCodes('&', "&8[&4Owner &9+ &9Founder&8]&9")),
    SYS_ADMIN("a " + ChatColor.DARK_RED + "System-Admin", ChatColor.DARK_RED + "[Sys-Admin]"),
    HORSE("a " + ChatColor.DARK_RED + "System-Admin" + ChatColor.AQUA + " and the " + ChatColor.RED + "Admin Manager", ChatColor.DARK_RED + "[Sys-Admin + Admin MGR]"),
    LEAD_DEVELOPER("the " + ChatColor.DARK_PURPLE + "Lead Developer" + ChatColor.AQUA + " of " + ChatColor.RED + "RubyFreedom", ChatColor.DARK_PURPLE + "[L-Dev]"),
    EXEC("an " + ChatColor.YELLOW + "Executive", ChatColor.YELLOW + "[Exec]"),
    CO_OWNER("a " + ChatColor.BLUE + "Co-Owner" + ChatColor.AQUA + " of " + ChatColor.RED + "RubyFreedom", ChatColor.BLUE + "[Co-Owner]"),
    MYSTERI("a " + ChatColor.GOLD + "Potato Caek", ChatColor.RED + "[Mysteri]"),
    DARK("an " + ChatColor.BLUE + "Owner" + ChatColor.AQUA + ", " + ChatColor.BLUE + "Founder" + ChatColor.AQUA + ", and the " + ChatColor.DARK_PURPLE + "Co-Chief Developer", ChatColor.translateAlternateColorCodes('&', "&8[&4Owner &8+ &9Founder&8]&9")),
    CHAR("the " + ChatColor.DARK_PURPLE + "Co-Chief Forum Developer", ChatColor.translateAlternateColorCodes('&', "&8[&5CCFD&8]")),
    CONSOLE("the " + ChatColor.DARK_PURPLE + "Console", ChatColor.DARK_PURPLE + "[Console]"),
    ANDY("a " + ChatColor.translateAlternateColorCodes('&', "&5Developer &b+ the &9Co-Owner&b!"), ChatColor.translateAlternateColorCodes('&', "&8[&5Developer &b+ &9Co-Owner&8]"));
    private final String loginMessage;
    private final String prefix;

    private TFM_PlayerRank(String loginMessage, String prefix)
    {
        this.loginMessage = loginMessage;
        this.prefix = prefix;
    }

    public static String getLoginMessage(CommandSender sender)
    {
        // Handle console
        if (!(sender instanceof Player))
        {
            return fromSender(sender).getLoginMessage();
        }

        // Handle admins
        final TFM_Admin entry = TFM_AdminList.getEntry((Player) sender);
        if (entry == null)
        {
            // Player is not an admin
            return fromSender(sender).getLoginMessage();
        }

        // Custom login message
        final String loginMessage = entry.getCustomLoginMessage();

        if (loginMessage == null || loginMessage.isEmpty())
        {
            return fromSender(sender).getLoginMessage();
        }

        return ChatColor.translateAlternateColorCodes('&', loginMessage);
    }

    public static TFM_PlayerRank fromSender(CommandSender sender)
    {
        if (!(sender instanceof Player))
        {
            return CONSOLE;
        }

        if (TFM_Util.imposters.contains((Player) sender))
        {
            return FAKEIMPOSTOR;
        }

        if (TFM_AdminList.isAdminImpostor((Player) sender))
        {
            return IMPOSTOR;
        }

        else if (DEVELOPERS.contains(sender.getName()))
        {
            return DEVELOPER;
        }

        else if (sender.getName().equals("Joenmb"))
        {
            return CHAR;
        }

        else if (sender.getName().equals("reuben4545"))
        {
            return LEAD_SPECIALIST;
        }

        else if (sender.getName().equals("AndySixx"))
        {
            return ANDY;
        }

        else if (sender.getName().equals("Valencia_Orange"))
        {
            return LEAD_DEVELOPER;
        }

        else if (sender.getName().equals("MysteriAce"))
        {
            return MYSTERI;
        }

        else if (sender.getName().equals("DarkHorse108"))
        {
            return HORSE;
        }

        else if (sender.getName().equals("DarkGamingDronze"))
        {
            return DARK;
        }

        else if (sender.getName().equals("Charlotte474747"))
        {
            return CHAR;
        }

        else if (FOP_DEVELOPERS.contains(sender.getName()))
        {
            return FOP_DEVELOPER;
        }

        else if (RF_DEVELOPERS.contains(sender.getName()))
        {
            return RF_DEVELOPER;
        }

        else if (SYS.contains(sender.getName()))
        {
            return SYS_ADMIN;
        }

        else if (sender.getName().equals("MysteriAce"))
        {
            return MYSTERI;
        }

        else if (EXECUTIVES.contains(sender.getName()))
        {
            return EXEC;
        }

        else if (COOWNER.contains(sender.getName()))
        {
            return CO_OWNER;
        }

        final TFM_Admin entry = TFM_AdminList.getEntryByIp(TFM_Util.getIp((Player) sender));

        final TFM_PlayerRank rank;

        if (entry != null && entry.isActivated())
        {
            if (TFM_ConfigEntry.SERVER_OWNERS.getList().contains(sender.getName()))
            {
                return OWNER;
            }

            if (entry.isSeniorAdmin())
            {
                rank = SENIOR;
            }
            else if (entry.isTelnetAdmin())
            {
                rank = TELNET;
            }
            else
            {
                rank = SUPER;
            }
        }
        else
        {
            if (sender.isOp())
            {
                rank = OP;
            }
            else
            {
                rank = NON_OP;
            }

        }
        return rank;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public String getLoginMessage()
    {
        return loginMessage;
    }
}
