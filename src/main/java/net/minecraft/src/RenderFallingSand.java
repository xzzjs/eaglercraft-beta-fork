package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglerAdapter;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class RenderFallingSand extends Render {

	public RenderFallingSand() {
		field_197_d = new RenderBlocks();
		shadowSize = 0.5F;
	}

	public void func_156_a(EntityFallingSand entityfallingsand, double d, double d1, double d2, float f, float f1) {
		EaglerAdapter.glPushMatrix();
		EaglerAdapter.glTranslatef((float) d, (float) d1, (float) d2);
		loadTexture("/terrain.png");
		Block block = Block.blocksList[entityfallingsand.blockID];
		World world = entityfallingsand.func_465_i();
		EaglerAdapter.glDisable(2896 /* GL_LIGHTING */);
		field_197_d.renderBlockFallingSand(block, world, MathHelper.floor_double(entityfallingsand.posX),
				MathHelper.floor_double(entityfallingsand.posY), MathHelper.floor_double(entityfallingsand.posZ));
		EaglerAdapter.glEnable(2896 /* GL_LIGHTING */);
		EaglerAdapter.glPopMatrix();
	}

	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		func_156_a((EntityFallingSand) entity, d, d1, d2, f, f1);
	}

	private RenderBlocks field_197_d;
}
