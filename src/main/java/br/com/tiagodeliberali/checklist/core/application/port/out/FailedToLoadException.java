package br.com.tiagodeliberali.checklist.core.application.port.out;

public class FailedToLoadException extends Exception {
    public FailedToLoadException(String path, Throwable ex) {
        super("Failed to load: " + path, ex);
    }
}
