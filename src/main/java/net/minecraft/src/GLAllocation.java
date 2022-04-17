package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.nio.*;
import java.util.ArrayList;
import java.util.List;

import net.lax1dude.eaglercraft.EaglerAdapter;

public class GLAllocation {

	public GLAllocation() {
	}

	public static synchronized int generateDisplayLists(int i) {
		int j = EaglerAdapter.glGenLists(i);
		displayLists.add(Integer.valueOf(j));
		displayLists.add(Integer.valueOf(i));
		return j;
	}

	public static synchronized void generateTextureNames(IntBuffer intbuffer) {
		
		for (int i = intbuffer.position(); i < intbuffer.limit(); i++) {
			int tx = EaglerAdapter.glGenTextures();
			intbuffer.put(i, tx);
			textureNames.add(Integer.valueOf(tx));
		}

	}

	public static synchronized void deleteTexturesAndDisplayLists() {
		for (int i = 0; i < displayLists.size(); i += 2) {
			EaglerAdapter.glDeleteLists(((Integer) displayLists.get(i)).intValue(),
					((Integer) displayLists.get(i + 1)).intValue());
		}
		
		for (int j = 0; j < textureNames.size(); j++) {
			EaglerAdapter.glDeleteTextures(((Integer) textureNames.get(j)).intValue());
		}
		
		displayLists.clear();
		textureNames.clear();
	}

	public static synchronized ByteBuffer createDirectByteBuffer(int i) {
		ByteBuffer bytebuffer = ByteBuffer.allocateDirect(i).order(ByteOrder.nativeOrder());
		return bytebuffer;
	}

	public static IntBuffer createDirectIntBuffer(int i) {
		return createDirectByteBuffer(i << 2).asIntBuffer();
	}

	public static FloatBuffer createDirectFloatBuffer(int i) {
		return createDirectByteBuffer(i << 2).asFloatBuffer();
	}

	private static List displayLists = new ArrayList();
	private static List textureNames = new ArrayList();

}
