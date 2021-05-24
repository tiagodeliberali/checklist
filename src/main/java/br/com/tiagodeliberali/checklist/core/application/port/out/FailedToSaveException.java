package br.com.tiagodeliberali.checklist.core.application.port.out;

public class FailedToSaveException extends Exception {
    public FailedToSaveException(String path, Throwable ex) {
        super("Failed to save to path: " + path, ex);
    }
}
