package com.zhang.net

import com.android.build.gradle.BaseExtension;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class NetMonitorPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
       def extention =  project.extensions.create("zhang",Extension)
        project.afterEvaluate {
            println("hello ${extention.name}")
        }
        def transform = new TransformDemo()
        def baseExtension = project.extensions.getByType(BaseExtension)
//        println "bootClassPath:${baseExtension.bootClasspath}"
//        baseExtension.registerTransform(transform)
    }
}
