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
package com.oracle.javafx.scenebuilder.kit.editor.job.v2;

import com.oracle.javafx.scenebuilder.kit.editor.EditorController;
import com.oracle.javafx.scenebuilder.kit.editor.job.Job;
import com.oracle.javafx.scenebuilder.kit.fxom.FXOMInstance;
import com.oracle.javafx.scenebuilder.kit.fxom.FXOMProperty;

/**
 *
 */
public class AddPropertyJob extends Job {

    private final FXOMProperty property;
    private final FXOMInstance targetInstance;
    private final int targetIndex;
    
    public AddPropertyJob(FXOMProperty property, 
            FXOMInstance targetInstance,
            int targetIndex,
            EditorController editorController) {
        super(editorController);
        
        assert property != null;
        assert targetInstance != null;
        assert targetIndex >= -1;
        
        this.property = property;
        this.targetInstance = targetInstance;
        this.targetIndex = targetIndex;
    }

    /*
     * Job
     */
    
    @Override
    public boolean isExecutable() {
        return property.getParentInstance() == null;
    }

    @Override
    public void execute() {
        redo();
    }

    @Override
    public void undo() {
        assert property.getParentInstance() == targetInstance;
        
        getEditorController().getSelection().clear();
        getEditorController().getFxomDocument().beginUpdate();
        property.removeFromParentInstance();
        getEditorController().getFxomDocument().endUpdate();
        
        assert property.getParentInstance() == null;
    }

    @Override
    public void redo() {
        assert property.getParentInstance() == null;
        
        getEditorController().getFxomDocument().beginUpdate();
        property.addToParentInstance(targetIndex, targetInstance);
        getEditorController().getFxomDocument().endUpdate();
        
        assert property.getParentInstance() == targetInstance;
    }

    @Override
    public String getDescription() {
        // Should normally not reach the user
        return getClass().getSimpleName();
    }
    
}
