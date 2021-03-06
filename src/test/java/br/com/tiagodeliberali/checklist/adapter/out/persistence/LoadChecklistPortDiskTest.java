package br.com.tiagodeliberali.checklist.adapter.out.persistence;

import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoadChecklistPortDiskTest {
    @Test
    void load_json() throws FailedToLoadException {
        LoadChecklistPortDisk loadChecklist = new LoadChecklistPortDisk("src/test/resources/data/");

        Checklist checklist = loadChecklist.load("checklist");

        assertThat(checklist.getName()).isEqualTo("service checklist");
        assertThat(checklist.count()).isOne();
        assertThat(checklist.countTopics()).isOne();
    }
}
