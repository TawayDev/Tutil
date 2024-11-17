package dev.taway.tutil.logging.buffer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogObject {
    private String filePath;
    private String message;
}
