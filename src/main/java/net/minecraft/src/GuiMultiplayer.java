package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class GuiMultiplayer extends GuiScreen {

	public GuiMultiplayer(GuiScreen guiscreen) {
		parentScreen = guiscreen;
	}

	public void updateScreen() {
		field_22111_h.func_22070_b();
	}

	public void initGui() {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		controlList.clear();
		controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12,
				stringtranslate.translateKey("multiplayer.connect")));
		controlList.add(
				new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.translateKey("gui.cancel")));
		String s = mc.gameSettings.lastServer.replaceAll("_", ":");
		((GuiButton) controlList.get(0)).enabled = s.length() > 0;
		field_22111_h = new GuiDisableButton(fontRenderer, width / 2 - 100, (height / 4 - 10) + 50 + 18, 200, 20, s);
		field_22111_h.field_22082_a = true;
		field_22111_h.func_22066_a(32);
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	protected void actionPerformed(GuiButton guibutton) {
		if (!guibutton.enabled) {
			return;
		}
		if (guibutton.id == 1) {
			mc.displayGuiScreen(parentScreen);
		} else if (guibutton.id == 0) {
			String s = field_22111_h.func_22071_a();
			mc.gameSettings.lastServer = s.replaceAll(":", "_");
			mc.gameSettings.saveOptions();
			String as[] = s.split(":");
			mc.displayGuiScreen(new GuiConnecting(mc, as[0], as.length <= 1 ? 25565 : func_4067_a(as[1], 25565)));
		}
	}

	private int func_4067_a(String s, int i) {
		try {
			return Integer.parseInt(s.trim());
		} catch (Exception exception) {
			return i;
		}
	}

	protected void keyTyped(char c, int i) {
		field_22111_h.func_22072_a(c, i);
		if (c == '\r') {
			actionPerformed((GuiButton) controlList.get(0));
		}
		((GuiButton) controlList.get(0)).enabled = field_22111_h.func_22071_a().length() > 0;
	}

	protected void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		field_22111_h.func_22069_a(i, j, k);
	}

	public void drawScreen(int i, int j, float f) {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		drawDefaultBackground();
		drawCenteredString(fontRenderer, stringtranslate.translateKey("multiplayer.title"), width / 2,
				(height / 4 - 60) + 20, 0xffffff);
		drawString(fontRenderer, stringtranslate.translateKey("multiplayer.info1"), width / 2 - 140,
				(height / 4 - 60) + 60 + 0, 0xa0a0a0);
		drawString(fontRenderer, stringtranslate.translateKey("multiplayer.info2"), width / 2 - 140,
				(height / 4 - 60) + 60 + 9, 0xa0a0a0);
		drawString(fontRenderer, stringtranslate.translateKey("multiplayer.ipinfo"), width / 2 - 140,
				(height / 4 - 60) + 60 + 36, 0xa0a0a0);
		field_22111_h.func_22067_c();
		super.drawScreen(i, j, f);
	}

	private GuiScreen parentScreen;
	private GuiDisableButton field_22111_h;
}
