package io.dummyapi.utils.so;

import org.apache.commons.io.FileSystem;
import static org.apache.commons.io.FileSystem.getCurrent;

public class AccordingToOperatingSystem {
    private AccordingToOperatingSystem(){

    }
    public static String replaceLineSeparatorOfPath(String path, String theReferenceOfSeparatorInActualPath){
        FileSystem fileFileSystem = getCurrent();

        switch(fileFileSystem) {
            case LINUX:
            case MAC_OSX:
                path = path.replace(theReferenceOfSeparatorInActualPath, "/");
                break;
            default:
                path = path.replace(theReferenceOfSeparatorInActualPath, "\\");
                break;
        }

        return path;
    }
}
