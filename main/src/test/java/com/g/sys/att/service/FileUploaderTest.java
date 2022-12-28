package com.g.sys.att.service;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class FileUploaderTest {
    @Test
    void pathTest() {
        System.out.println(Path.of("/a", "/ba/c/", "/d", "e"));

        System.out.println(Path.of("/a").normalize());
        System.out.println(Path.of("/a/").normalize());
        System.out.println(Path.of("./a").normalize().resolve("hello.java"));
        System.out.println(Path.of("./a/").normalize().resolve("hello.java"));
        System.out.println(Path.of("./a/").normalize().toAbsolutePath());
        System.out.println(Path.of("../a/").normalize().toAbsolutePath());

        System.out.println(Path.of("/a").normalize().getNameCount());
        System.out.println(Path.of("/a/").normalize().getNameCount());
        System.out.println(Path.of("./a").normalize().getNameCount());
        System.out.println(Path.of("./a/").normalize().getNameCount());
        System.out.println(Path.of("./a").normalize().resolve("hello.java").getNameCount());
        System.out.println(Path.of("./a/").normalize().resolve("hello.java").getNameCount());

        Path p = Path.of("/a", "/ba/c/", "/d", "e");
        Path d = p.resolve("json");
        System.out.println("p = " + p);
        System.out.println("d = " + d);
        final int i = p.getNameCount();
        System.out.println(p.subpath(1, i));
    }
}
