package github.farhad;

import java.io.File;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
//        if (args.length == 0) {
//            throw new IllegalArgumentException("path not specified");
//        }

        /**
         * /Users/farhad/Downloads
         */
        var path = new File("/Users/farhad/Downloads");
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
                    var deleteOpStatus = checkAndDeleteIfSubtitle(file);
                    if (deleteOpStatus) {
                        System.out.println("DELETED SUBTITLE: " + file.getName());
                    }

                    var deleteJunkOpStatus = checkAndDeleteHiddenJunk(file);
                    if (deleteJunkOpStatus) {
                        System.out.println("DELETED JUNK: " + file.getName());
                    }

                    var deleteDSStore = checkAndDeleteDSStore(file);
                    if (deleteDSStore) {
                        System.out.println("DELETED DS Store: " + file.getName());
                    }
                }
            }
        }
    }


    private static boolean checkAndDeleteIfSubtitle(File file) {
        var extension = getExtension(file);
        if (extension.equals(".srt") || extension.equals(".vtt")) {
            return file.delete();
        }

        return false;
    }

    private static boolean checkAndDeleteHiddenJunk(File file) {
        if (file.getName().startsWith("._") && file.isHidden()) {
            return file.delete();
        }

        return false;
    }

    private static boolean checkAndDeleteDSStore(File file) {
        if (file.getName().equals(".DS_Store")) {
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