package br.com.tiagodeliberali.checklist.core.domain;

public class TopicNotFoundException extends Exception {
    public TopicNotFoundException(TopicName name) {
        super("Topic not found" + name);
    }
}
