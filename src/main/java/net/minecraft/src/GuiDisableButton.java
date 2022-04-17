package net.minecraft.src;

// Decompiled with: CFR 0.152
// Class Version: 5
public class GuiDisableButton extends Gui {
	private final FontRenderer field_22080_c;
	private final int field_22079_d;
	private final int field_22078_e;
	private final int field_22077_f;
	private final int field_22076_g;
	private String field_22075_h;
	private int field_22074_i;
	private int field_22073_k;
	public boolean field_22082_a = false;
	public boolean field_22081_b = true;

	public GuiDisableButton(FontRenderer fontRenderer, int n, int n2, int n3, int n4, String string) {
		this.field_22080_c = fontRenderer;
		this.field_22079_d = n;
		this.field_22078_e = n2;
		this.field_22077_f = n3;
		this.field_22076_g = n4;
		this.func_22068_a(string);
	}

	public void func_22068_a(String string) {
		this.field_22075_h = string;
	}

	public String func_22071_a() {
		return this.field_22075_h;
	}

	public void func_22070_b() {
		++this.field_22073_k;
	}

	public void func_22072_a(char c, int n) {
		if (!this.field_22081_b || !this.field_22082_a) {
			return;
		}
		if (c == '') {
			int n2;
			String string = GuiScreen.getClipboardString();
			if (string == null) {
				string = "";
			}
			if ((n2 = 32 - this.field_22075_h.length()) > string.length()) {
				n2 = string.length();
			}
			if (n2 > 0) {
				this.field_22075_h = this.field_22075_h + string.substring(0, n2);
			}
		}
		if (n == 14 && this.field_22075_h.length() > 0) {
			this.field_22075_h = this.field_22075_h.substring(0, this.field_22075_h.length() - 1);
		}
		if (FontAllowedCharacters.allowedCharacters.indexOf(c) >= 0
				&& (this.field_22075_h.length() < this.field_22074_i || this.field_22074_i == 0)) {
			this.field_22075_h = this.field_22075_h + c;
		}
	}

	public void func_22069_a(int n, int n2, int n3) {
		boolean bl;
		boolean bl2 = bl = this.field_22081_b && n >= this.field_22079_d && n < this.field_22079_d + this.field_22077_f
				&& n2 >= this.field_22078_e && n2 < this.field_22078_e + this.field_22076_g;
		if (bl && !this.field_22082_a) {
			this.field_22073_k = 0;
		}
		this.field_22082_a = bl;
	}

	public void func_22067_c() {
		this.drawRect(this.field_22079_d - 1, this.field_22078_e - 1, this.field_22079_d + this.field_22077_f + 1,
				this.field_22078_e + this.field_22076_g + 1, -6250336);
		this.drawRect(this.field_22079_d, this.field_22078_e, this.field_22079_d + this.field_22077_f,
				this.field_22078_e + this.field_22076_g, -16777216);
		if (this.field_22081_b) {
			boolean bl = this.field_22082_a && this.field_22073_k / 6 % 2 == 0;
			this.drawString(this.field_22080_c, this.field_22075_h + (bl ? "_" : ""), this.field_22079_d + 4,
					this.field_22078_e + (this.field_22076_g - 8) / 2, 0xE0E0E0);
		} else {
			this.drawString(this.field_22080_c, this.field_22075_h, this.field_22079_d + 4,
					this.field_22078_e + (this.field_22076_g - 8) / 2, 0x707070);
		}
	}

	public void func_22066_a(int n) {
		this.field_22074_i = n;
	}
}
