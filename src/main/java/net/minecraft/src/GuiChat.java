package net.minecraft.src;

// Decompiled with: CFR 0.152
// Class Version: 5
import org.lwjgl.input.Keyboard;

public class GuiChat extends GuiScreen {
	protected String field_985_a = "";
	private int field_986_h = 0;
	private static final String field_20082_i = FontAllowedCharacters.allowedCharacters;

	public void initGui() {
		Keyboard.enableRepeatEvents(true);
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	public void updateScreen() {
		++this.field_986_h;
	}

	protected void keyTyped(char c, int n) {
		if (n == 1) {
			this.mc.displayGuiScreen(null);
			return;
		}
		if (n == 28) {
			String string;
			String string2 = this.field_985_a.trim();
			if (string2.length() > 0 && !this.mc.func_22003_b(string = this.field_985_a.trim())) {
				this.mc.thePlayer.sendChatMessage(string);
			}
			this.mc.displayGuiScreen(null);
			return;
		}
		if (n == 14 && this.field_985_a.length() > 0) {
			this.field_985_a = this.field_985_a.substring(0, this.field_985_a.length() - 1);
		}
		if (field_20082_i.indexOf(c) >= 0 && this.field_985_a.length() < 100) {
			this.field_985_a = this.field_985_a + c;
		}
	}

	public void drawScreen(int n, int n2, float f) {
		this.drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
		this.drawString(this.fontRenderer, "> " + this.field_985_a + (this.field_986_h / 6 % 2 == 0 ? "_" : ""), 4,
				this.height - 12, 0xE0E0E0);
		super.drawScreen(n, n2, f);
	}

	protected void mouseClicked(int n, int n2, int n3) {
		if (n3 == 0) {
			if (this.mc.ingameGUI.field_933_a != null) {
				if (this.field_985_a.length() > 0 && !this.field_985_a.endsWith(" ")) {
					this.field_985_a = this.field_985_a + " ";
				}
				this.field_985_a = this.field_985_a + this.mc.ingameGUI.field_933_a;
				int n4 = 100;
				if (this.field_985_a.length() > n4) {
					this.field_985_a = this.field_985_a.substring(0, n4);
				}
			} else {
				super.mouseClicked(n, n2, n3);
			}
		}
	}
}
