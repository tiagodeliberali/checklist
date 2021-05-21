package br.com.tiagodeliberali.checklist.adapter.out.adapter;

import br.com.tiagodeliberali.checklist.adapter.out.persistence.LoadServiceInfoPortDisk;
import br.com.tiagodeliberali.checklist.core.application.port.out.JsonFileNotFound;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource("classpath:application.properties")
class LoadServiceInfoPortDiskTests {
    @Test
    void load_json() throws JsonFileNotFound {
        LoadServiceInfoPortDisk loadService = new LoadServiceInfoPortDisk("src/test/resources/data/");

        ServiceInfo service = loadService.load("service");

        assertThat(service.getRepo()).isEqualTo("us-tasks");
        assertThat(service.count()).isOne();
    }
}

