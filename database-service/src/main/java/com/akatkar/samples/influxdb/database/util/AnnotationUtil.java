package com.akatkar.samples.influxdb.database.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class AnnotationUtil {

    private AnnotationUtil(){
        // prevent instance
    }
    
    public static <T> T getAnnotationValue(Class<?> classType, Class<? extends Annotation> annotationClass, String element) 
            throws InvalidAnnotationException {
        
        try {
            Annotation annotation = classType.getAnnotation(annotationClass);
            Method method = annotationClass.getMethod(element, (Class[]) null);
            if (annotation == null) {
                return (T) method.getDefaultValue();
            }
            return (T) method.invoke(annotation, (Object[]) null);
        } catch (NoSuchMethodException | SecurityException ex) {
            throw new InvalidAnnotationException("Annotation No Such Method", ex);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new InvalidAnnotationException("Annotation Invalid Access", ex);
        }
    }
}
