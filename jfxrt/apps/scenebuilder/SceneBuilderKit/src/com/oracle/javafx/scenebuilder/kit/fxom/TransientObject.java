/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.oracle.javafx.scenebuilder.kit.fxom;

import com.oracle.javafx.scenebuilder.kit.fxom.glue.GlueElement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 
 */
class TransientObject extends TransientNode {
    
    private final Class<?> declaredClass;
    private final String unknownClassName;
    private final GlueElement glueElement;
    private final List<FXOMProperty> properties = new ArrayList<>();
    private final List<FXOMObject> collectedItems = new ArrayList<>();
    private String fxRootType;
    
    public TransientObject(
            TransientNode parentNode, 
            Class<?> declaredClass, 
            GlueElement glueElement) {
        super(parentNode);
        
        assert declaredClass != null;
        assert glueElement != null;
        assert glueElement.getTagName().equals(declaredClass.getSimpleName());
        
        this.declaredClass = declaredClass;
        this.unknownClassName = null;
        this.glueElement = glueElement;
    }

    public TransientObject(
            TransientNode parentNode, 
            String unknownClassName, 
            GlueElement glueElement) {
        super(parentNode);
        
        assert unknownClassName != null;
        assert glueElement != null;
        assert glueElement.getTagName().equals(unknownClassName);
        
        this.declaredClass = null;
        this.unknownClassName = unknownClassName;
        this.glueElement = glueElement;
    }

    public TransientObject(
            TransientNode parentNode, 
            GlueElement glueElement) {
        super(parentNode);
        
        assert glueElement != null;
        assert glueElement.getTagName().equals("fx:root");
        
        this.declaredClass = null;
        this.unknownClassName = null;
        this.glueElement = glueElement;
    }

    public Class<?> getDeclaredClass() {
        return declaredClass;
    }

    public String getUnknownClassName() {
        return unknownClassName;
    }

    public GlueElement getGlueElement() {
        return glueElement;
    }
    
    public List<FXOMProperty> getProperties() {
        return properties;
    }

    public List<FXOMObject> getCollectedItems() {
        return collectedItems;
    }

    public String getFxRootType() {
        return fxRootType;
    }

    public void setFxRootType(String fxRootType) {
        this.fxRootType = fxRootType;
    }
    
    
    public FXOMObject makeFxomObject(FXOMDocument fxomDocument) {
        final FXOMObject result;
        
        if (collectedItems.isEmpty()) {
            if (declaredClass != null) {
                assert getSceneGraphObject() != null;
                assert fxRootType == null;
                result = new FXOMInstance(fxomDocument, glueElement, 
                                          declaredClass, getSceneGraphObject(),
                                          properties);
            } else if (unknownClassName != null) {
                // This is an unresolved instance
                assert glueElement.getTagName().equals(unknownClassName);
                assert fxRootType == null;
                result = new FXOMInstance(fxomDocument, glueElement, properties);
            } else {
                // This is an fx:root'ed instance
                assert glueElement.getTagName().equals("fx:root");
                assert fxRootType != null;
                
                final Class<?> rootClass = getSceneGraphObject().getClass();
                assert fxRootType.equals(rootClass.getName());
                result = new FXOMInstance(fxomDocument, glueElement, 
                                          rootClass, getSceneGraphObject(),
                                          properties);
            }
        } else if (properties.isEmpty()) {
            assert getSceneGraphObject() != null;
            result = new FXOMCollection(fxomDocument, glueElement,
                                      declaredClass, getSceneGraphObject(), 
                                      collectedItems);
        } else {
            assert false;
            throw new IllegalStateException();
        }
        
        return result;
    }
}
