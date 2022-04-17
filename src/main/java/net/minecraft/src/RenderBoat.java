package net.minecraft.src;

import net.lax1dude.eaglercraft.EaglerAdapter;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class RenderBoat extends Render {

	public RenderBoat() {
		shadowSize = 0.5F;
		modelBoat = new ModelBoat();
	}

	public void func_157_a(EntityBoat entityboat, double d, double d1, double d2, float f, float f1) {
		EaglerAdapter.glPushMatrix();
		EaglerAdapter.glTranslatef((float) d, (float) d1, (float) d2);
		EaglerAdapter.glRotatef(180F - f, 0.0F, 1.0F, 0.0F);
		float f2 = (float) entityboat.field_806_b - f1;
		float f3 = (float) entityboat.field_807_a - f1;
		if (f3 < 0.0F) {
			f3 = 0.0F;
		}
		if (f2 > 0.0F) {
			EaglerAdapter.glRotatef(((MathHelper.sin(f2) * f2 * f3) / 10F) * (float) entityboat.field_808_c, 1.0F, 0.0F, 0.0F);
		}
		loadTexture("/terrain.png");
		float f4 = 0.75F;
		EaglerAdapter.glScalef(f4, f4, f4);
		EaglerAdapter.glScalef(1.0F / f4, 1.0F / f4, 1.0F / f4);
		loadTexture("/item/boat.png");
		EaglerAdapter.glScalef(-1F, -1F, 1.0F);
		modelBoat.render(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		EaglerAdapter.glPopMatrix();
	}

	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		func_157_a((EntityBoat) entity, d, d1, d2, f, f1);
	}

	protected ModelBase modelBoat;
}
