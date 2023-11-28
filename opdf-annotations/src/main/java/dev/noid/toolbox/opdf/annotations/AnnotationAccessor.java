package dev.noid.toolbox.opdf.annotations;

import java.lang.annotation.Annotation;

public class AnnotationAccessor {

    public <A extends Annotation> A getAnnotation(Object object, Class<A> annotationClass) {
        Class<?> clazz = object.getClass();
        A annotation = clazz.getAnnotation(annotationClass);
        if (annotation == null) {
            throw new IllegalStateException("The class " + clazz.getSimpleName() + " is not annotated with " + annotationClass.getSimpleName());
        }
        return annotation;
    }
}
