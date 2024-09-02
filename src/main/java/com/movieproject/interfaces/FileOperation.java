package com.movieproject.interfaces;

import java.io.File;
import java.io.IOException;

public interface FileOperation {
    boolean execute(File file) throws IOException;
}
