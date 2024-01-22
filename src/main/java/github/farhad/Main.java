package github.farhad;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("path not specified");
        }

        var path = new File(args[0]);
        if (!path.exists()) {
            throw new IllegalArgumentException("path not found");
        } else if (!path.isDirectory()) {
            throw new IllegalArgumentException("path is not a directory");
        } else {

        }
    }
}