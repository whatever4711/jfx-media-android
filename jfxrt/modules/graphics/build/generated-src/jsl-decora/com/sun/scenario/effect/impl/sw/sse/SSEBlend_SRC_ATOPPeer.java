/*
 * Copyright (c) 2008, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
 * This file was generated by JSLC -- DO NOT EDIT MANUALLY!
 */

package com.sun.scenario.effect.impl.sw.sse;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.sun.scenario.effect.Effect;
import com.sun.scenario.effect.FilterContext;
import com.sun.scenario.effect.FloatMap;
import com.sun.scenario.effect.ImageData;
import com.sun.scenario.effect.Blend;
import com.sun.scenario.effect.impl.BufferUtil;
import com.sun.scenario.effect.impl.HeapImage;
import com.sun.scenario.effect.impl.Renderer;
import com.sun.scenario.effect.impl.state.*;
import com.sun.javafx.geom.Rectangle;
import com.sun.javafx.geom.transform.BaseTransform;

public class SSEBlend_SRC_ATOPPeer extends SSEEffectPeer  {

    public SSEBlend_SRC_ATOPPeer(FilterContext fctx, Renderer r, String uniqueName) {
        super(fctx, r, uniqueName);
    }

    @Override
    protected final Blend getEffect() {
        return (Blend)super.getEffect();
    }


    private float getOpacity() {
        return getEffect().getOpacity();
    }


    @Override
    public ImageData filter(Effect effect,
                            BaseTransform transform,
                            Rectangle outputClip,
                            ImageData... inputs)
    {
        setEffect(effect);
        Rectangle dstBounds = getResultBounds(transform, outputClip, inputs);
        setDestBounds(dstBounds);

        // TODO: for now, all input images must be TYPE_INT_ARGB_PRE
        HeapImage src0 = (HeapImage)inputs[0].getTransformedImage(dstBounds);
        int src0x = 0;
        int src0y = 0;
        int src0w = src0.getPhysicalWidth();
        int src0h = src0.getPhysicalHeight();
        int src0scan = src0.getScanlineStride();
        int[] botImg =
            src0.getPixelArray();
        Rectangle src0Bounds = new Rectangle(src0x, src0y, src0w, src0h);
        Rectangle src0InputBounds = inputs[0].getTransformedBounds(dstBounds);
        BaseTransform src0Transform = BaseTransform.IDENTITY_TRANSFORM;
        setInputBounds(0, src0InputBounds);
        setInputNativeBounds(0, src0Bounds);
        HeapImage src1 = (HeapImage)inputs[1].getTransformedImage(dstBounds);
        int src1x = 0;
        int src1y = 0;
        int src1w = src1.getPhysicalWidth();
        int src1h = src1.getPhysicalHeight();
        int src1scan = src1.getScanlineStride();
        int[] topImg =
            src1.getPixelArray();
        Rectangle src1Bounds = new Rectangle(src1x, src1y, src1w, src1h);
        Rectangle src1InputBounds = inputs[1].getTransformedBounds(dstBounds);
        BaseTransform src1Transform = BaseTransform.IDENTITY_TRANSFORM;
        setInputBounds(1, src1InputBounds);
        setInputNativeBounds(1, src1Bounds);


        float[] src0Rect = new float[4];
        getTextureCoordinates(0, src0Rect,
                              src0InputBounds.x, src0InputBounds.y,
                              src0w, src0h,
                              dstBounds, src0Transform);
        float[] src1Rect = new float[4];
        getTextureCoordinates(1, src1Rect,
                              src1InputBounds.x, src1InputBounds.y,
                              src1w, src1h,
                              dstBounds, src1Transform);


        final int dstx = 0;
        final int dsty = 0;
        final int dstw = dstBounds.width;
        final int dsth = dstBounds.height;

        HeapImage dst = (HeapImage)getRenderer().getCompatibleImage(dstw, dsth);
        setDestNativeBounds(dst.getPhysicalWidth(), dst.getPhysicalHeight());
        int dstscan = dst.getScanlineStride();
        int[] dstPixels = dst.getPixelArray();
        
        float opacity = getOpacity();


        filter(dstPixels, dstx, dsty, dstw, dsth, dstscan,
botImg,
src0Rect[0], src0Rect[1],
src0Rect[2], src0Rect[3],
src0w, src0h, src0scan,
opacity,
topImg,
src1Rect[0], src1Rect[1],
src1Rect[2], src1Rect[3],
src1w, src1h, src1scan);

        inputs[0].releaseTransformedImage(src0);
        inputs[1].releaseTransformedImage(src1);


        return new ImageData(getFilterContext(), dst, dstBounds);
    }

    private static native void filter(int[] dstPixels,
                                      int dstx, int dsty,
                                      int dstw, int dsth,
                                      int dstscan,
int[] botImg_arr,
float src0Rect_x1, float src0Rect_y1,
float src0Rect_x2, float src0Rect_y2,
int src0w, int src0h, int src0scan,
float opacity,
int[] topImg_arr,
float src1Rect_x1, float src1Rect_y1,
float src1Rect_x2, float src1Rect_y2,
int src1w, int src1h, int src1scan);
}
