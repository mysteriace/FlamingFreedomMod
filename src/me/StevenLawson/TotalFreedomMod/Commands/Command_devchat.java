package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.Config.TFM_ConfigEntry;
import me.StevenLawson.TotalFreedomMod.FOPM_TFM_Util;
import me.StevenLawson.TotalFreedomMod.TFM_PlayerData;
import static me.StevenLawson.TotalFreedomMod.TFM_Util.COOWNER;
import static me.StevenLawson.TotalFreedomMod.TFM_Util.RF_DEVELOPERS;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "Dev Chat - Talk privately with developer. Using <command> itself will toggle Dev Chat on and off for all messages.", usage = "/<command> [message...]", aliases = "dev")
public class Command_devchat extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (!sender.getName().equals("tylerhyperHD") && !RF_DEVELOPERS.contains(sender.getName()) && !TFM_ConfigEntry.SERVER_OWNERS.getList().contains(sender.getName()) && !COOWNER.contains(sender.getName()))
        {
            sender.sendMessage(TFM_Command.MSG_NO_PERMS);
            return true;
        }
        if (args.length == 0)
        {
            if (senderIsConsole)
            {
                playerMsg("Only in-game players can toggle DevChat.");
                return true;
            }

            TFM_PlayerData userinfo = TFM_PlayerData.getPlayerData(sender_p);

            if (userinfo.inAdminChat())
            {
                userinfo.setAdminChat(!userinfo.inAdminChat());
            }

            if (userinfo.inSeniorAdminChat())
            {
                userinfo.setSeniorAdminChat(!userinfo.inSeniorAdminChat());
            }

            if (userinfo.inTelnetAdminChat())
            {
                userinfo.setTelnetAdminChat(!userinfo.inTelnetAdminChat());
            }

            userinfo.setDevChat(!userinfo.inDevChat());
            playerMsg("Toggled Dev Chat " + (userinfo.inDevChat() ? "on" : "off") + ".");
        }
        else
        {
            FOPM_TFM_Util.DevChatMessage(sender, StringUtils.join(args, " "), senderIsConsole);
        }

        return true;
    }
}
