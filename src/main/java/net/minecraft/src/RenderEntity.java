package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import org.lwjgl.opengl.GL11;

public class RenderEntity extends Render
{

    public RenderEntity()
    {
    }

    public void doRender(Entity entity, double d, double d1, double d2, 
            float f, float f1)
    {
        GL11.glPushMatrix();
        renderOffsetAABB(entity.boundingBox, d - entity.lastTickPosX, d1 - entity.lastTickPosY, d2 - entity.lastTickPosZ);
        GL11.glPopMatrix();
    }
}
