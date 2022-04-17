package net.minecraft.src;

import net.lax1dude.eaglercraft.compat.CompatEnum;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

final class EnumOS1 extends CompatEnum {

	public static EnumOS1[] values() {
		return (EnumOS1[]) field_6525_f.clone();
	}

	public static EnumOS1 valueOf(String s) {
		return (EnumOS1) CompatEnum.valueOf(EnumOS1.class, s);
	}

	private EnumOS1(String s, int i) {
		super(s, i);
	}

	public static final EnumOS1 linux;
	public static final EnumOS1 solaris;
	public static final EnumOS1 windows;
	public static final EnumOS1 macos;
	public static final EnumOS1 unknown;
	private static final EnumOS1 field_6525_f[]; /* synthetic field */

	static {
		linux = new EnumOS1("linux", 0);
		solaris = new EnumOS1("solaris", 1);
		windows = new EnumOS1("windows", 2);
		macos = new EnumOS1("macos", 3);
		unknown = new EnumOS1("unknown", 4);
		field_6525_f = (new EnumOS1[] { linux, solaris, windows, macos, unknown });
	}
}
