package org.example;

import java.nio.file.Path;

public interface IAcl {
    void setRwxAcl(Path filePath, String user);
}