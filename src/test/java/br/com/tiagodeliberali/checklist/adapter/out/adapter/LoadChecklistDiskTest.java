package br.com.tiagodeliberali.checklist.adapter.out.adapter;

import br.com.tiagodeliberali.checklist.adapter.out.persistence.LoadChecklistDisk;
import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoadChecklistDiskTest {
    @Test
    void load_json() throws FailedToLoadException {
        LoadChecklistDisk loadChecklist = new LoadChecklistDisk("src/test/resources/data/");

        Checklist checklist = loadChecklist.load("checklist");

        assertThat(checklist.getName()).isEqualTo("service checklist");
        assertThat(checklist.countThemes()).isOne();
        assertThat(checklist.countTopics()).isOne();
    }
}
