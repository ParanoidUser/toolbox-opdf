package dev.noid.toolbox.opdf.annotations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnnotationAccessorTest {

    @Test
    void access_alias_annotation() {
        var help = new HelpCommand();
        var processor = new AnnotationAccessor();
        var annotation = processor.getAnnotation(help, Alias.class);

        assertEquals("help", annotation.value());
        assertEquals("Display help content", annotation.description());
    }

    @Alias(value = "help", description = "Display help content")
    private static class HelpCommand {}
}