package br.com.tiagodeliberali.checklist.core.application.port.out;

public class JsonFileNotParsed extends Exception {
    public JsonFileNotParsed(String path, Throwable ex) {
        super("Failed to parse json: " + path, ex);
    }
}
