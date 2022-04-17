package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ColorizerFoliage {

	public ColorizerFoliage() {
	}

	public static int getFoliageColor(double d, double d1) {
		d1 *= d;
		int i = (int) ((1.0D - d) * 255D);
		int j = (int) ((1.0D - d1) * 255D);
		return foliageBuffer[j << 8 | i];
	}

	public static int func_21175_a() {
		return 0x619961;
	}

	public static int func_21174_b() {
		return 0x80a755;
	}

	static Class _mthclass$(String s) {
		try {
			return Class.forName(s);
		} catch (ClassNotFoundException classnotfoundexception) {
			throw new NoClassDefFoundError(classnotfoundexception.getMessage());
		}
	}

	private static final int foliageBuffer[];

	static {
		foliageBuffer = new int[0x10000];
		try {
			BufferedImage bufferedimage = ImageIO.read((ColorizerFoliage.class).getResource("/misc/foliagecolor.png"));
			bufferedimage.getRGB(0, 0, 256, 256, foliageBuffer, 0, 256);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
