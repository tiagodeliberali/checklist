package br.com.tiagodeliberali.checklist.adapter.out.persistence;

import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoadServiceInfoPortDiskTests {
    @Test
    void load_json() throws FailedToLoadException {
        LoadServiceInfoPortDisk loadService = new LoadServiceInfoPortDisk("src/test/resources/data/");

        ServiceInfo service = loadService.load("service");

        assertThat(service.getRepo()).isEqualTo("us-tasks");
        assertThat(service.count()).isOne();
    }
}

