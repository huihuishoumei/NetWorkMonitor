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
        def inputs = transformInvocation.inputs
        def outputProvider = transformInvocation.outputProvider
        inputs.each {
            // jarInputs：各个依赖所编译成的 jar ⽂件
            it.jarInputs.each {
                // dest:
                //./app/build/intermediates/transforms/netMonitorTransform/...
                File dest =
                        outputProvider.getContentLocation(it.name, it.contentTypes,
                                it.scopes, Format.JAR)
                FileUtils.copyFile(it.file, dest)
            }
            // derectoryInputs：本地 project 编译成的多个 class ⽂件存放的⽬录
            it.directoryInputs.each {
                // dest:
                //./app/build/intermediates/transforms/netMonitorTransform/...
                File dest =
                        outputProvider.getContentLocation(it.name, it.contentTypes,
                                it.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(it.file, dest)
            }
        }
    }
}
