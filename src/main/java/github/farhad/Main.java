package github.farhad;

import java.io.File;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("path not specified");
        }

        var path = new File(args[0]);
        traverseDirectory(path);
    }

    private static void traverseDirectory(File path) {
        if (!path.exists()) {
            throw new IllegalArgumentException("path not found");
        } else if (!path.isDirectory()) {
            throw new IllegalArgumentException("path is not a directory");
        } else {
            for (File file : Objects.requireNonNull(path.listFiles())) {
                if (file.isDirectory()) {
                    traverseDirectory(file);
                } else {
                    checkAndDeleteIfSubtitle(file);
                }
            }
        }
    }

    private static boolean checkAndDeleteIfSubtitle(File file) {
        var extension = getExtension(file);
        if (extension.equals(".srt") || extension.equals(".vtt")) {
            System.out.println("TRUE: " + file.getName());
            return file.delete();
        }

        return false;
    }

    private static String getExtension(File path) {
        var fullPath = path.getAbsolutePath();
        if (!fullPath.contains(".")) {
            return "UNKNOWN";
        }
        return fullPath.substring(fullPath.lastIndexOf("."));
    }
}