package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.adapter.Tessellator;

public class GuiMainMenu extends GuiScreen {

	public GuiMainMenu() {
		updateCounter = 0.0F;
		splashText = "missingno";
		try {
			ArrayList arraylist = new ArrayList();
			BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(
					(GuiMainMenu.class).getResourceAsStream("/title/splashes.txt"), Charset.forName("UTF-8")));
			String s = "";
			do {
				String s1;
				if ((s1 = bufferedreader.readLine()) == null) {
					break;
				}
				s1 = s1.trim();
				if (s1.length() > 0) {
					arraylist.add(s1);
				}
			} while (true);
			splashText = (String) arraylist.get(rand.nextInt(arraylist.size()));
		} catch (Exception exception) {
		}
	}

	public void updateScreen() {
		updateCounter++;
		if (logoEffects != null) {
			for (int i = 0; i < logoEffects.length; i++) {
				for (int j = 0; j < logoEffects[i].length; j++) {
					logoEffects[i][j].func_875_a();
				}

			}

		}
	}

	protected void keyTyped(char c, int i) {
	}

	public void initGui() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		if (calendar.get(2) + 1 == 11 && calendar.get(5) == 9) {
			splashText = "Happy birthday, ez!";
		} else if (calendar.get(2) + 1 == 6 && calendar.get(5) == 1) {
			splashText = "Happy birthday, Notch!";
		} else if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
			splashText = "Merry X-mas!";
		} else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
			splashText = "Happy new year!";
		}
		StringTranslate stringtranslate = StringTranslate.getInstance();
		int i = height / 4 + 48;
		controlList.add(new GuiButton(1, width / 2 - 100, i, stringtranslate.translateKey("menu.singleplayer")));
		controlList.add(new GuiButton(2, width / 2 - 100, i + 24, stringtranslate.translateKey("menu.multiplayer")));
		controlList.add(new GuiButton(3, width / 2 - 100, i + 48, stringtranslate.translateKey("menu.mods")));
		if (mc.hideQuitButton) {
			controlList.add(new GuiButton(0, width / 2 - 100, i + 72, stringtranslate.translateKey("menu.options")));
		} else {
			controlList.add(new GuiButton(0, width / 2 - 100, i + 72 + 12, 98, 20,
					stringtranslate.translateKey("menu.options")));
			controlList.add(
					new GuiButton(4, width / 2 + 2, i + 72 + 12, 98, 20, stringtranslate.translateKey("menu.quit")));
		}
		if (mc.session == null) {
			((GuiButton) controlList.get(1)).enabled = false;
		}
	}

	protected void actionPerformed(GuiButton guibutton) {
		if (guibutton.id == 0) {
			mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
		}
		if (guibutton.id == 1) {
			mc.displayGuiScreen(new GuiSelectWorld(this));
		}
		if (guibutton.id == 2) {
			mc.displayGuiScreen(new GuiMultiplayer(this));
		}
		if (guibutton.id == 3) {
			mc.displayGuiScreen(new GuiTexturePacks(this));
		}
		if (guibutton.id == 4) {
			mc.shutdown();
		}
	}

	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		Tessellator tessellator = Tessellator.instance;
		drawLogo(f);
		EaglerAdapter.glBindTexture(3553 /* GL_TEXTURE_2D */, mc.renderEngine.getTexture("/gui/logo.png"));
		EaglerAdapter.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		tessellator.setColorOpaque_I(0xffffff);
		EaglerAdapter.glPushMatrix();
		EaglerAdapter.glTranslatef(width / 2 + 90, 70F, 0.0F);
		EaglerAdapter.glRotatef(-20F, 0.0F, 0.0F, 1.0F);
		float f1 = 1.8F - MathHelper
				.abs(MathHelper.sin(((float) (System.currentTimeMillis() % 1000L) / 1000F) * 3.141593F * 2.0F) * 0.1F);
		f1 = (f1 * 100F) / (float) (fontRenderer.getStringWidth(splashText) + 32);
		EaglerAdapter.glScalef(f1, f1, f1);
		drawCenteredString(fontRenderer, splashText, 0, -8, 0xffff00);
		EaglerAdapter.glPopMatrix();
		drawString(fontRenderer, "Minecraft Beta 1.3_01", 2, 2, 0x505050);
		String s = "Copyright Mojang AB. Do not distribute.";
		drawString(fontRenderer, s, width - fontRenderer.getStringWidth(s) - 2, height - 10, 0xffffff);
		super.drawScreen(i, j, f);
	}

	private void drawLogo(float f) {
		if (logoEffects == null) {
			logoEffects = new LogoEffectRandomizer[minecraftLogo[0].length()][minecraftLogo.length];
			for (int i = 0; i < logoEffects.length; i++) {
				for (int j = 0; j < logoEffects[i].length; j++) {
					logoEffects[i][j] = new LogoEffectRandomizer(this, i, j);
				}

			}

		}
		EaglerAdapter.glMatrixMode(5889 /* GL_PROJECTION */);
		EaglerAdapter.glPushMatrix();
		EaglerAdapter.glLoadIdentity();
		ScaledResolution scaledresolution = new ScaledResolution(mc.displayWidth, mc.displayHeight);
		int k = 120 * scaledresolution.scaleFactor;
		EaglerAdapter.gluPerspective(70F, (float) mc.displayWidth / (float) k, 0.05F, 100F);
		EaglerAdapter.glViewport(0, mc.displayHeight - k, mc.displayWidth, k);
		EaglerAdapter.glMatrixMode(5888 /* GL_MODELVIEW0_ARB */);
		EaglerAdapter.glPushMatrix();
		EaglerAdapter.glLoadIdentity();
		EaglerAdapter.glDisable(2884 /* GL_CULL_FACE */);
		EaglerAdapter.glCullFace(1029 /* GL_BACK */);
		EaglerAdapter.glDepthMask(true);
		RenderBlocks renderblocks = new RenderBlocks();
		for (int l = 0; l < 3; l++) {
			EaglerAdapter.glPushMatrix();
			EaglerAdapter.glTranslatef(0.4F, 0.6F, -13F);
			if (l == 0) {
				EaglerAdapter.glClear(256);
				EaglerAdapter.glTranslatef(0.0F, -0.4F, 0.0F);
				EaglerAdapter.glScalef(0.98F, 1.0F, 1.0F);
				EaglerAdapter.glEnable(3042 /* GL_BLEND */);
				EaglerAdapter.glBlendFunc(770, 771);
			}
			if (l == 1) {
				EaglerAdapter.glDisable(3042 /* GL_BLEND */);
				EaglerAdapter.glClear(256);
			}
			if (l == 2) {
				EaglerAdapter.glEnable(3042 /* GL_BLEND */);
				EaglerAdapter.glBlendFunc(768, 1);
			}
			EaglerAdapter.glScalef(1.0F, -1F, 1.0F);
			EaglerAdapter.glRotatef(15F, 1.0F, 0.0F, 0.0F);
			EaglerAdapter.glScalef(0.89F, 1.0F, 0.4F);
			EaglerAdapter.glTranslatef((float) (-minecraftLogo[0].length()) * 0.5F, (float) (-minecraftLogo.length) * 0.5F,
					0.0F);
			EaglerAdapter.glBindTexture(3553 /* GL_TEXTURE_2D */, mc.renderEngine.getTexture("/terrain.png"));
			if (l == 0) {
				EaglerAdapter.glBindTexture(3553 /* GL_TEXTURE_2D */, mc.renderEngine.getTexture("/title/black.png"));
			}
			for (int i1 = 0; i1 < minecraftLogo.length; i1++) {
				for (int j1 = 0; j1 < minecraftLogo[i1].length(); j1++) {
					char c = minecraftLogo[i1].charAt(j1);
					if (c == ' ') {
						continue;
					}
					EaglerAdapter.glPushMatrix();
					LogoEffectRandomizer logoeffectrandomizer = logoEffects[j1][i1];
					float f1 = (float) (logoeffectrandomizer.field_1311_b
							+ (logoeffectrandomizer.field_1312_a - logoeffectrandomizer.field_1311_b) * (double) f);
					float f2 = 1.0F;
					float f3 = 1.0F;
					float f4 = 0.0F;
					if (l == 0) {
						f2 = f1 * 0.04F + 1.0F;
						f3 = 1.0F / f2;
						f1 = 0.0F;
					}
					EaglerAdapter.glTranslatef(j1, i1, f1);
					EaglerAdapter.glScalef(f2, f2, f2);
					EaglerAdapter.glRotatef(f4, 0.0F, 1.0F, 0.0F);
					renderblocks.func_1238_a(Block.stone, f3);
					EaglerAdapter.glPopMatrix();
				}

			}

			EaglerAdapter.glPopMatrix();
		}

		EaglerAdapter.glDisable(3042 /* GL_BLEND */);
		EaglerAdapter.glMatrixMode(5889 /* GL_PROJECTION */);
		EaglerAdapter.glPopMatrix();
		EaglerAdapter.glMatrixMode(5888 /* GL_MODELVIEW0_ARB */);
		EaglerAdapter.glPopMatrix();
		EaglerAdapter.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
		EaglerAdapter.glEnable(2884 /* GL_CULL_FACE */);
	}

	static Random getRand() {
		return rand;
	}

	private static final Random rand = new Random();
	String minecraftLogo[] = {  " *   * * *   * *** *** *** *** *** ***",
			                    " ** ** * **  * *   *   * * * * *    * ",
								" * * * * * * * **  *   **  *** **   * ",
								" *   * * *  ** *   *   * * * * *    * ",
								" *   * * *   * *** *** * * * * *    * " };
	private LogoEffectRandomizer logoEffects[][];
	private float updateCounter;
	private String splashText;

}
