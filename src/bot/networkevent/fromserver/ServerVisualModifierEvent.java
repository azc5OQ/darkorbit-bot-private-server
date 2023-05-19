package bot.networkevent.fromserver;

import bot.networkevent.NetworkEvent;

//this command is sent as part of ShipInitializationcommand
//luckily, not always
public class ServerVisualModifierEvent extends NetworkEvent {
    private static short ID = 12647;
}
