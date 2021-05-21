package br.com.tiagodeliberali.checklist.adapter.out.adapter;

import br.com.tiagodeliberali.checklist.adapter.out.persistence.LoadServiceInfoPortDisk;
import br.com.tiagodeliberali.checklist.core.application.port.out.JsonFileNotFound;
import br.com.tiagodeliberali.checklist.core.application.port.out.JsonFileNotParsed;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoadServiceInfoPortDiskTests {
    @Test
    void load_json() throws JsonFileNotFound, JsonFileNotParsed {
        LoadServiceInfoPortDisk loadService = new LoadServiceInfoPortDisk();

        ServiceInfo service = loadService.load("src/test/resources/data/service.json");

        assertThat(service.getRepo()).isEqualTo("us-tasks");
        assertThat(service.count()).isOne();
    }
}
