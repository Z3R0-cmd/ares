package dev.tigr.ares.fabric.impl.util;

import com.mojang.authlib.exceptions.AuthenticationException;
import dev.tigr.ares.core.Ares;
import dev.tigr.ares.core.event.client.SystemChatMessageEvent;
import dev.tigr.ares.core.util.AbstractAccount;
import dev.tigr.ares.core.util.IUtils;
import dev.tigr.ares.core.util.render.TextColor;
import dev.tigr.ares.fabric.impl.modules.hud.EditHudGui;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.io.IOException;

import static dev.tigr.ares.Wrapper.MC;

/**
 * @author Tigermouthbear 11/23/20
 */
public class CustomUtils implements IUtils {
    @Override
    public void printMessage(String message) {
        Text textComponentString = new LiteralText(message);

        Ares.EVENT_MANAGER.post(new SystemChatMessageEvent(textComponentString.getString()));

        Text text;
        text = new LiteralText(TextColor.DARK_GRAY + "[" + TextColor.DARK_RED + "Ares" + TextColor.DARK_GRAY + "]" + TextColor.WHITE + " ").append(textComponentString);

        MC.inGameHud.addChatMessage(MessageType.SYSTEM, text, null);
    }

    @Override
    public void executeBaritoneCommand(String string) {
        printMessage(TextColor.RED + "Baritone is not compatible with " + Ares.MC_VERSION);
    }

    @Override
    public String getPlayerName() {
        return MC.getSession().getUsername();
    }

    @Override
    public void openHUDEditor() {
        MC.openScreen(new EditHudGui(MC.currentScreen));
    }

    @Override
    public AbstractAccount createAccount(String email, String password, String uuid) throws IOException {
        return new CustomAccount(email, password, uuid);
    }

    @Override
    public AbstractAccount createAccount(String email, String password) throws IOException, AuthenticationException {
        return new CustomAccount(email, password);
    }
}
