package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import org.lwjgl.opengl.GL11;

public class TextureFX
{

    public TextureFX(int i)
    {
        imageData = new byte[1024 /*GL_FRONT_LEFT*/];
        anaglyphEnabled = false;
        field_1130_d = 0;
        tileSize = 1;
        tileImage = 0;
        iconIndex = i;
    }

    public void onTick()
    {
    }

    public void bindImage(RenderEngine renderengine)
    {
        if(tileImage == 0)
        {
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, renderengine.getTexture("/terrain.png"));
        } else
        if(tileImage == 1)
        {
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, renderengine.getTexture("/gui/items.png"));
        }
    }

    public byte imageData[];
    public int iconIndex;
    public boolean anaglyphEnabled;
    public int field_1130_d;
    public int tileSize;
    public int tileImage;
}
