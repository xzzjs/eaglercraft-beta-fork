package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;

import net.lax1dude.eaglercraft.EaglerAdapter;

public class RenderEngine {

	public RenderEngine(TexturePackList texturepacklist, GameSettings gamesettings) {
		textureMap = new HashMap();
		textureNameToImageMap = new HashMap();
		singleIntBuffer = GLAllocation.createDirectIntBuffer(1);
		imageData = GLAllocation.createDirectByteBuffer(0x100000);
		textureList = new ArrayList();
		urlToImageDataMap = new HashMap();
		clampTexture = false;
		blurTexture = false;
		field_6527_k = texturepacklist;
		options = gamesettings;
	}

	public int getTexture(String s) {
		TexturePackBase texturepackbase = field_6527_k.selectedTexturePack;
		Integer integer = (Integer) textureMap.get(s);
		if (integer != null) {
			return integer.intValue();
		}
		try {
			singleIntBuffer.clear();
			GLAllocation.generateTextureNames(singleIntBuffer);
			int i = singleIntBuffer.get(0);
			if (s.startsWith("##")) {
				setupTexture(unwrapImageByColumns(readTextureImage(texturepackbase.func_6481_a(s.substring(2)))), i);
			} else if (s.startsWith("%clamp%")) {
				clampTexture = true;
				setupTexture(readTextureImage(texturepackbase.func_6481_a(s.substring(7))), i);
				clampTexture = false;
			} else if (s.startsWith("%blur%")) {
				blurTexture = true;
				setupTexture(readTextureImage(texturepackbase.func_6481_a(s.substring(6))), i);
				blurTexture = false;
			} else {
				setupTexture(readTextureImage(texturepackbase.func_6481_a(s)), i);
			}
			textureMap.put(s, Integer.valueOf(i));
			return i;
		} catch (IOException ioexception) {
			throw new RuntimeException("!!");
		}
	}

	private BufferedImage unwrapImageByColumns(BufferedImage bufferedimage) {
		int i = bufferedimage.getWidth() / 16;
		BufferedImage bufferedimage1 = new BufferedImage(16, bufferedimage.getHeight() * i, 2);
		Graphics g = bufferedimage1.getGraphics();
		for (int j = 0; j < i; j++) {
			g.drawImage(bufferedimage, -j * 16, j * bufferedimage.getHeight(), null);
		}

		g.dispose();
		return bufferedimage1;
	}

	public int allocateAndSetupTexture(BufferedImage bufferedimage) {
		singleIntBuffer.clear();
		GLAllocation.generateTextureNames(singleIntBuffer);
		int i = singleIntBuffer.get(0);
		setupTexture(bufferedimage, i);
		textureNameToImageMap.put(Integer.valueOf(i), bufferedimage);
		return i;
	}

	public void setupTexture(BufferedImage bufferedimage, int i) {
		EaglerAdapter.glBindTexture(3553 /* GL_TEXTURE_2D */, i);
		if (useMipmaps) {
			EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10241 /* GL_TEXTURE_MIN_FILTER */,
					9987 /* GL_LINEAR_MIPMAP_LINEAR */);
			EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10240 /* GL_TEXTURE_MAG_FILTER */, 9729 /* GL_LINEAR */);
		} else {
			EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10241 /* GL_TEXTURE_MIN_FILTER */, 9728 /* GL_NEAREST */);
			EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10240 /* GL_TEXTURE_MAG_FILTER */, 9728 /* GL_NEAREST */);
		}
		if (blurTexture) {
			EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10241 /* GL_TEXTURE_MIN_FILTER */, 9729 /* GL_LINEAR */);
			EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10240 /* GL_TEXTURE_MAG_FILTER */, 9729 /* GL_LINEAR */);
		}
		if (clampTexture) {
			EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10242 /* GL_TEXTURE_WRAP_S */, 10496 /* GL_CLAMP */);
			EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10243 /* GL_TEXTURE_WRAP_T */, 10496 /* GL_CLAMP */);
		} else {
			EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10242 /* GL_TEXTURE_WRAP_S */, 10497 /* GL_REPEAT */);
			EaglerAdapter.glTexParameteri(3553 /* GL_TEXTURE_2D */, 10243 /* GL_TEXTURE_WRAP_T */, 10497 /* GL_REPEAT */);
		}
		int j = bufferedimage.getWidth();
		int k = bufferedimage.getHeight();
		int ai[] = new int[j * k];
		byte abyte0[] = new byte[j * k * 4];
		bufferedimage.getRGB(0, 0, j, k, ai, 0, j);
		for (int l = 0; l < ai.length; l++) {
			int j1 = ai[l] >> 24 & 0xff;
			int l1 = ai[l] >> 16 & 0xff;
			int j2 = ai[l] >> 8 & 0xff;
			int l2 = ai[l] & 0xff;
			if (options != null && options.anaglyph) {
				int j3 = (l1 * 30 + j2 * 59 + l2 * 11) / 100;
				int l3 = (l1 * 30 + j2 * 70) / 100;
				int j4 = (l1 * 30 + l2 * 70) / 100;
				l1 = j3;
				j2 = l3;
				l2 = j4;
			}
			abyte0[l * 4 + 0] = (byte) l1;
			abyte0[l * 4 + 1] = (byte) j2;
			abyte0[l * 4 + 2] = (byte) l2;
			abyte0[l * 4 + 3] = (byte) j1;
		}

		imageData.clear();
		imageData.put(abyte0);
		imageData.position(0).limit(abyte0.length);
		EaglerAdapter.glTexImage2D(3553 /* GL_TEXTURE_2D */, 0, 6408 /* GL_RGBA */, j, k, 0, 6408 /* GL_RGBA */,
				5121 /* GL_UNSIGNED_BYTE */, imageData);
		if (useMipmaps) {
			for (int i1 = 1; i1 <= 4; i1++) {
				int k1 = j >> i1 - 1;
				int i2 = j >> i1;
				int k2 = k >> i1;
				for (int i3 = 0; i3 < i2; i3++) {
					for (int k3 = 0; k3 < k2; k3++) {
						int i4 = imageData.getInt((i3 * 2 + 0 + (k3 * 2 + 0) * k1) * 4);
						int k4 = imageData.getInt((i3 * 2 + 1 + (k3 * 2 + 0) * k1) * 4);
						int l4 = imageData.getInt((i3 * 2 + 1 + (k3 * 2 + 1) * k1) * 4);
						int i5 = imageData.getInt((i3 * 2 + 0 + (k3 * 2 + 1) * k1) * 4);
						int j5 = weightedAverageColor(weightedAverageColor(i4, k4), weightedAverageColor(l4, i5));
						imageData.putInt((i3 + k3 * i2) * 4, j5);
					}

				}

				EaglerAdapter.glTexImage2D(3553 /* GL_TEXTURE_2D */, i1, 6408 /* GL_RGBA */, i2, k2, 0, 6408 /* GL_RGBA */,
						5121 /* GL_UNSIGNED_BYTE */, imageData);
			}

		}
	}

	public void deleteTexture(int i) {
		EaglerAdapter.glDeleteTextures(i);
	}

	public int getTextureForDownloadableImage(String s, String s1) {
		ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData) urlToImageDataMap.get(s);
		if (threaddownloadimagedata != null && threaddownloadimagedata.image != null
				&& !threaddownloadimagedata.textureSetupComplete) {
			if (threaddownloadimagedata.textureName < 0) {
				threaddownloadimagedata.textureName = allocateAndSetupTexture(threaddownloadimagedata.image);
			} else {
				setupTexture(threaddownloadimagedata.image, threaddownloadimagedata.textureName);
			}
			threaddownloadimagedata.textureSetupComplete = true;
		}
		if (threaddownloadimagedata == null || threaddownloadimagedata.textureName < 0) {
			if (s1 == null) {
				return -1;
			} else {
				return getTexture(s1);
			}
		} else {
			return threaddownloadimagedata.textureName;
		}
	}

	public ThreadDownloadImageData obtainImageData(String s, ImageBuffer imagebuffer) {
		ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData) urlToImageDataMap.get(s);
		if (threaddownloadimagedata == null) {
			urlToImageDataMap.put(s, new ThreadDownloadImageData(s, imagebuffer));
		} else {
			threaddownloadimagedata.referenceCount++;
		}
		return threaddownloadimagedata;
	}

	public void releaseImageData(String s) {
		ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData) urlToImageDataMap.get(s);
		if (threaddownloadimagedata != null) {
			threaddownloadimagedata.referenceCount--;
			if (threaddownloadimagedata.referenceCount == 0) {
				if (threaddownloadimagedata.textureName >= 0) {
					deleteTexture(threaddownloadimagedata.textureName);
				}
				urlToImageDataMap.remove(s);
			}
		}
	}

	public void registerTextureFX(TextureFX texturefx) {
		textureList.add(texturefx);
		texturefx.onTick();
	}

	public void func_1067_a() {
		for (int i = 0; i < textureList.size(); i++) {
			TextureFX texturefx = (TextureFX) textureList.get(i);
			texturefx.anaglyphEnabled = options.anaglyph;
			texturefx.onTick();
			imageData.clear();
			imageData.put(texturefx.imageData);
			imageData.position(0).limit(texturefx.imageData.length);
			texturefx.bindImage(this);
			for (int k = 0; k < texturefx.tileSize; k++) {
				label0: for (int i1 = 0; i1 < texturefx.tileSize; i1++) {
					EaglerAdapter.glTexSubImage2D(3553 /* GL_TEXTURE_2D */, 0, (texturefx.iconIndex % 16) * 16 + k * 16,
							(texturefx.iconIndex / 16) * 16 + i1 * 16, 16, 16, 6408 /* GL_RGBA */,
							5121 /* GL_UNSIGNED_BYTE */, imageData);
					if (!useMipmaps) {
						continue;
					}
					int k1 = 1;
					do {
						if (k1 > 4) {
							continue label0;
						}
						int i2 = 16 >> k1 - 1;
						int k2 = 16 >> k1;
						for (int i3 = 0; i3 < k2; i3++) {
							for (int k3 = 0; k3 < k2; k3++) {
								int i4 = imageData.getInt((i3 * 2 + 0 + (k3 * 2 + 0) * i2) * 4);
								int k4 = imageData.getInt((i3 * 2 + 1 + (k3 * 2 + 0) * i2) * 4);
								int i5 = imageData.getInt((i3 * 2 + 1 + (k3 * 2 + 1) * i2) * 4);
								int k5 = imageData.getInt((i3 * 2 + 0 + (k3 * 2 + 1) * i2) * 4);
								int l5 = averageColor(averageColor(i4, k4), averageColor(i5, k5));
								imageData.putInt((i3 + k3 * k2) * 4, l5);
							}

						}

						EaglerAdapter.glTexSubImage2D(3553 /* GL_TEXTURE_2D */, k1, (texturefx.iconIndex % 16) * k2,
								(texturefx.iconIndex / 16) * k2, k2, k2, 6408 /* GL_RGBA */,
								5121 /* GL_UNSIGNED_BYTE */, imageData);
						k1++;
					} while (true);
				}

			}

		}

		label1: for (int j = 0; j < textureList.size(); j++) {
			TextureFX texturefx1 = (TextureFX) textureList.get(j);
			if (texturefx1.field_1130_d <= 0) {
				continue;
			}
			imageData.clear();
			imageData.put(texturefx1.imageData);
			imageData.position(0).limit(texturefx1.imageData.length);
			EaglerAdapter.glBindTexture(3553 /* GL_TEXTURE_2D */, texturefx1.field_1130_d);
			EaglerAdapter.glTexSubImage2D(3553 /* GL_TEXTURE_2D */, 0, 0, 0, 16, 16, 6408 /* GL_RGBA */,
					5121 /* GL_UNSIGNED_BYTE */, imageData);
			if (!useMipmaps) {
				continue;
			}
			int l = 1;
			do {
				if (l > 4) {
					continue label1;
				}
				int j1 = 16 >> l - 1;
				int l1 = 16 >> l;
				for (int j2 = 0; j2 < l1; j2++) {
					for (int l2 = 0; l2 < l1; l2++) {
						int j3 = imageData.getInt((j2 * 2 + 0 + (l2 * 2 + 0) * j1) * 4);
						int l3 = imageData.getInt((j2 * 2 + 1 + (l2 * 2 + 0) * j1) * 4);
						int j4 = imageData.getInt((j2 * 2 + 1 + (l2 * 2 + 1) * j1) * 4);
						int l4 = imageData.getInt((j2 * 2 + 0 + (l2 * 2 + 1) * j1) * 4);
						int j5 = averageColor(averageColor(j3, l3), averageColor(j4, l4));
						imageData.putInt((j2 + l2 * l1) * 4, j5);
					}

				}

				EaglerAdapter.glTexSubImage2D(3553 /* GL_TEXTURE_2D */, l, 0, 0, l1, l1, 6408 /* GL_RGBA */,
						5121 /* GL_UNSIGNED_BYTE */, imageData);
				l++;
			} while (true);
		}

	}

	private int averageColor(int i, int j) {
		int k = (i & 0xff000000) >> 24 & 0xff;
		int l = (j & 0xff000000) >> 24 & 0xff;
		return ((k + l >> 1) << 24) + ((i & 0xfefefe) + (j & 0xfefefe) >> 1);
	}

	private int weightedAverageColor(int i, int j) {
		int k = (i & 0xff000000) >> 24 & 0xff;
		int l = (j & 0xff000000) >> 24 & 0xff;
		char c = '\377';
		if (k + l == 0) {
			k = 1;
			l = 1;
			c = '\0';
		}
		int i1 = (i >> 16 & 0xff) * k;
		int j1 = (i >> 8 & 0xff) * k;
		int k1 = (i & 0xff) * k;
		int l1 = (j >> 16 & 0xff) * l;
		int i2 = (j >> 8 & 0xff) * l;
		int j2 = (j & 0xff) * l;
		int k2 = (i1 + l1) / (k + l);
		int l2 = (j1 + i2) / (k + l);
		int i3 = (k1 + j2) / (k + l);
		return c << 24 | k2 << 16 | l2 << 8 | i3;
	}

	public void refreshTextures() {
		TexturePackBase texturepackbase = field_6527_k.selectedTexturePack;
		int i;
		BufferedImage bufferedimage;
		for (Iterator iterator = textureNameToImageMap.keySet().iterator(); iterator
				.hasNext(); setupTexture(bufferedimage, i)) {
			i = ((Integer) iterator.next()).intValue();
			bufferedimage = (BufferedImage) textureNameToImageMap.get(Integer.valueOf(i));
		}

		for (Iterator iterator1 = urlToImageDataMap.values().iterator(); iterator1.hasNext();) {
			ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData) iterator1.next();
			threaddownloadimagedata.textureSetupComplete = false;
		}

		for (Iterator iterator2 = textureMap.keySet().iterator(); iterator2.hasNext();) {
			String s = (String) iterator2.next();
			try {
				BufferedImage bufferedimage1;
				if (s.startsWith("##")) {
					bufferedimage1 = unwrapImageByColumns(
							readTextureImage(texturepackbase.func_6481_a(s.substring(2))));
				} else if (s.startsWith("%clamp%")) {
					clampTexture = true;
					bufferedimage1 = readTextureImage(texturepackbase.func_6481_a(s.substring(7)));
				} else if (s.startsWith("%blur%")) {
					blurTexture = true;
					bufferedimage1 = readTextureImage(texturepackbase.func_6481_a(s.substring(6)));
				} else {
					bufferedimage1 = readTextureImage(texturepackbase.func_6481_a(s));
				}
				int j = ((Integer) textureMap.get(s)).intValue();
				setupTexture(bufferedimage1, j);
				blurTexture = false;
				clampTexture = false;
			} catch (IOException ioexception) {
				ioexception.printStackTrace();
			}
		}

	}

	private BufferedImage readTextureImage(InputStream inputstream) throws IOException {
		BufferedImage bufferedimage = ImageIO.read(inputstream);
		inputstream.close();
		return bufferedimage;
	}

	public void bindTexture(int i) {
		if (i < 0) {
			return;
		} else {
			EaglerAdapter.glBindTexture(3553 /* GL_TEXTURE_2D */, i);
			return;
		}
	}

	public static boolean useMipmaps = false;
	private HashMap textureMap;
	private HashMap textureNameToImageMap;
	private IntBuffer singleIntBuffer;
	private ByteBuffer imageData;
	private java.util.List textureList;
	private Map urlToImageDataMap;
	private GameSettings options;
	private boolean clampTexture;
	private boolean blurTexture;
	private TexturePackList field_6527_k;

}
