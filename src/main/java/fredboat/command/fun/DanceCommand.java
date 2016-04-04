package fredboat.command.fun;

import fredboat.ChannelListener;
import fredboat.commandmeta.Command;
import fredboat.commandmeta.ICommand;
import fredboat.util.HttpUtils;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;
import org.json.JSONObject;

public class DanceCommand extends Command implements ICommand {

    @Override
    public void onInvoke(Guild guild, TextChannel channel, User invoker, Message message, String[] args) {
        Runnable func = new Runnable() {
            @Override
            public void run() {
                synchronized (channel) {
                    Message msg = channel.sendMessage('\u200b' + "\\o\\");
                    ChannelListener.messagesToDeleteIfIdDeleted.put(message.getId(), msg);
                    long start = System.currentTimeMillis();
                    try {
                        synchronized (this) {
                            while (start + 60000 > System.currentTimeMillis()) {
                                wait(1000);
                                msg = msg.updateMessage("/o/");
                                wait(1000);
                                msg = msg.updateMessage("\\o\\");
                            }
                        }
                    } catch (InterruptedException ex) {
                    }
                }
            }
        };

        Thread thread = new Thread(func);
        thread.start();
    }
}
