module dev.taway.tutil {
    requires java.base;
    requires java.net.http;
    requires java.sql;
    requires static lombok;

    requires org.hibernate.validator;
    requires json.simple;
    requires reflections;

    exports dev.taway.tutil;
    exports dev.taway.tutil.crypto;
    exports dev.taway.tutil.data;
    exports dev.taway.tutil.event.annotation;
    exports dev.taway.tutil.event.processor;
    exports dev.taway.tutil.exception;
    exports dev.taway.tutil.exception.event;
    exports dev.taway.tutil.exception.io;
    exports dev.taway.tutil.exception.logging;
    exports dev.taway.tutil.exception.net.api;
    exports dev.taway.tutil.exception.net.sql;
    exports dev.taway.tutil.format;
    exports dev.taway.tutil.io;
    exports dev.taway.tutil.io.directory;
    exports dev.taway.tutil.io.file;
    exports dev.taway.tutil.io.json;
    exports dev.taway.tutil.logging;
    exports dev.taway.tutil.logging.buffer;
    exports dev.taway.tutil.net.api;
    exports dev.taway.tutil.net.sql;
    exports dev.taway.tutil.sorting;
    exports dev.taway.tutil.time;
}