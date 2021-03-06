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

package com.sun.scenario.effect.impl.sw.java;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.sun.scenario.effect.Effect;
import com.sun.scenario.effect.FilterContext;
import com.sun.scenario.effect.FloatMap;
import com.sun.scenario.effect.ImageData;
import com.sun.scenario.effect.DisplacementMap;
import com.sun.scenario.effect.impl.BufferUtil;
import com.sun.scenario.effect.impl.HeapImage;
import com.sun.scenario.effect.impl.Renderer;
import com.sun.scenario.effect.impl.state.*;
import com.sun.javafx.geom.Rectangle;
import com.sun.javafx.geom.transform.BaseTransform;

public class JSWDisplacementMapPeer extends JSWEffectPeer  {

    public JSWDisplacementMapPeer(FilterContext fctx, Renderer r, String uniqueName) {
        super(fctx, r, uniqueName);
    }

    @Override
    protected final DisplacementMap getEffect() {
        return (DisplacementMap)super.getEffect();
    }


    private float[] getSampletx() {
        return new float[] {
            getEffect().getOffsetX(),
            getEffect().getOffsetY(),
            getEffect().getScaleX(),
            getEffect().getScaleY(),
        };
    }
    private float[] getImagetx() {
        float inset = getEffect().getWrap() ? 0.5f : 0f;
        return new float[] {
            inset / getInputNativeBounds(0).width,
            inset / getInputNativeBounds(0).height,
            (getInputBounds(0).width-2*inset) / getInputNativeBounds(0).width,
            (getInputBounds(0).height-2*inset) / getInputNativeBounds(0).height,
        };
    }
    private float getWrap() {
        return getEffect().getWrap() ? 1f : 0f;
    }

    @Override
    protected Object getSamplerData(int i) {
        return getEffect().getMapData();
    }

    @Override
    public int getTextureCoordinates(int inputIndex, float coords[],
                                     float srcX, float srcY,
                                     float srcNativeWidth,
                                     float srcNativeHeight,
                                     Rectangle dstBounds,
                                     com.sun.javafx.geom.transform.BaseTransform transform)
    {
        coords[0] = coords[1] = 0f;
        coords[2] = coords[3] = 1f;
        return 4;
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
        FloatMap src1 = (FloatMap)getSamplerData(1);
        int src1x = 0;
        int src1y = 0;
        int src1w = src1.getWidth();
        int src1h = src1.getHeight();
        int src1scan = src1.getWidth();
        float[] mapImg = src1.getData();
        float mapImg_vals[] = new float[4];
        HeapImage src0 = (HeapImage)inputs[0].getUntransformedImage();
        int src0x = 0;
        int src0y = 0;
        int src0w = src0.getPhysicalWidth();
        int src0h = src0.getPhysicalHeight();
        int src0scan = src0.getScanlineStride();
        int[] origImg =
            src0.getPixelArray();
        Rectangle src0Bounds = new Rectangle(src0x, src0y, src0w, src0h);
        Rectangle src0InputBounds = inputs[0].getUntransformedBounds();
        BaseTransform src0Transform = inputs[0].getTransform();
        setInputBounds(0, src0InputBounds);
        setInputNativeBounds(0, src0Bounds);
        float origImg_vals[] = new float[4];


        float[] src1Rect = new float[] {0,0,1,1};
        float[] src0Rect = new float[4];
        getTextureCoordinates(0, src0Rect,
                              src0InputBounds.x, src0InputBounds.y,
                              src0w, src0h,
                              dstBounds, src0Transform);


        final int dstx = 0;
        final int dsty = 0;
        final int dstw = dstBounds.width;
        final int dsth = dstBounds.height;

        HeapImage dst = (HeapImage)getRenderer().getCompatibleImage(dstw, dsth);
        setDestNativeBounds(dst.getPhysicalWidth(), dst.getPhysicalHeight());
        int dstscan = dst.getScanlineStride();
        int[] dstPixels = dst.getPixelArray();
        
        int dyi;
        float color_x, color_y, color_z, color_w;

        float wrap = getWrap();
        float[] imagetx_arr = getImagetx();
        float imagetx_x = imagetx_arr[0], imagetx_y = imagetx_arr[1], imagetx_z = imagetx_arr[2], imagetx_w = imagetx_arr[3];
        float[] sampletx_arr = getSampletx();
        float sampletx_x = sampletx_arr[0], sampletx_y = sampletx_arr[1], sampletx_z = sampletx_arr[2], sampletx_w = sampletx_arr[3];


        float inc1_x = (src1Rect[2] - src1Rect[0]) / dstw;
        float inc1_y = (src1Rect[3] - src1Rect[1]) / dsth;
        float inc0_x = (src0Rect[2] - src0Rect[0]) / dstw;
        float inc0_y = (src0Rect[3] - src0Rect[1]) / dsth;


        float pos1_y = src1Rect[1] + inc1_y*0.5f;
        float pos0_y = src0Rect[1] + inc0_y*0.5f;

        for (int dy = dsty; dy < dsty+dsth; dy++) {
            float pixcoord_y = (float)dy;

            dyi = dy*dstscan;

            float pos1_x = src1Rect[0] + inc1_x*0.5f;
            float pos0_x = src0Rect[0] + inc0_x*0.5f;

            for (int dx = dstx; dx < dstx+dstw; dx++) {
                float pixcoord_x = (float)dx;


                {
                float sample_res_x, sample_res_y, sample_res_z, sample_res_w;
                {
                float loc_tmp_x = pos1_x;
                float loc_tmp_y = pos1_y;
                fsample(mapImg, loc_tmp_x, loc_tmp_y,
                        src1w, src1h, src1scan,
                        mapImg_vals);
                sample_res_x = mapImg_vals[0];
                sample_res_y = mapImg_vals[1];
                sample_res_z = mapImg_vals[2];
                sample_res_w = mapImg_vals[3];

                }
                float off_x = sample_res_x;
                float off_y = sample_res_y;
                float off_z = sample_res_z;
                float off_w = sample_res_w;
                float loc_x = pos0_x + sampletx_z * (off_x + sampletx_x);
                float loc_y = pos0_y + sampletx_w * (off_y + sampletx_y);
                float floor_res_x, floor_res_y;
                {
                float x_tmp_x = loc_x;
                float x_tmp_y = loc_y;
                floor_res_x = (float)Math.floor(x_tmp_x);
                floor_res_y = (float)Math.floor(x_tmp_y);

                }
                loc_x -= wrap * floor_res_x;
                loc_y -= wrap * floor_res_y;
                loc_x = imagetx_x + (loc_x * imagetx_z);
                loc_y = imagetx_y + (loc_y * imagetx_w);
                {
                float loc_tmp_x = loc_x;
                float loc_tmp_y = loc_y;
                lsample(origImg, loc_tmp_x, loc_tmp_y,
                        src0w, src0h, src0scan,
                        origImg_vals);
                sample_res_x = origImg_vals[0];
                sample_res_y = origImg_vals[1];
                sample_res_z = origImg_vals[2];
                sample_res_w = origImg_vals[3];

                }
                color_x = sample_res_x;
                color_y = sample_res_y;
                color_z = sample_res_z;
                color_w = sample_res_w;
                }


                if (color_w < 0f) color_w = 0f; else if (color_w > 1f) color_w = 1f;
                if (color_x < 0f) color_x = 0f; else if (color_x > color_w) color_x = color_w;
                if (color_y < 0f) color_y = 0f; else if (color_y > color_w) color_y = color_w;
                if (color_z < 0f) color_z = 0f; else if (color_z > color_w) color_z = color_w;
                dstPixels[dyi+dx] =
                    ((int)(color_x * 0xff) << 16) |
                    ((int)(color_y * 0xff) <<  8) |
                    ((int)(color_z * 0xff) <<  0) |
                    ((int)(color_w * 0xff) << 24);

                pos1_x += inc1_x;
                pos0_x += inc0_x;

            }

            pos1_y += inc1_y;
            pos0_y += inc0_y;

        }


        return new ImageData(getFilterContext(), dst, dstBounds);
    }
}
