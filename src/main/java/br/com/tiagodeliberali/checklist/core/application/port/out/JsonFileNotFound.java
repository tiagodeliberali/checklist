package br.com.tiagodeliberali.checklist.core.application.port.out;

public class JsonFileNotFound extends Exception {
    public JsonFileNotFound(String path, Throwable ex) {
        super("Json file not found at: " + path, ex);
    }
}
