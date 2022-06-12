package com.zhang.net

import com.android.build.api.transform.Format;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.gradle.internal.impldep.org.apache.commons.lang.reflect.FieldUtils
import sun.reflect.misc.FieldUtil

import java.io.IOException;
import java.util.Set;



class TransformDemo extends Transform {
    @Override
    public String getName() {
        return "netMonitorTransform";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        def inputs = transformInvocation.getInputs()
        def outputs = transformInvocation.outputProvider;
        inputs.each {
            it.jarInputs.each {
                outputs.getContentLocation(it.name,it.contentTypes,it.scopes,
                Format.JAR)
                FileUtils.copyFile(it.file,dest)
            }
            it.directoryInputs.each {
                outputs.getContentLocation(it.name,it.contentTypes,it.scopes,
                        Format.DIRECTORY)
                FileUtils.copyDirectory(it.file,dest)
            }
        }
    }
}
