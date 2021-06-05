package br.com.tiagodeliberali.checklist.core.application;

import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.application.port.out.LoadServiceInfoPort;
import br.com.tiagodeliberali.checklist.core.application.service.IndexService;
import br.com.tiagodeliberali.checklist.core.domain.checklist.EntityId;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IndexServiceTest {
    @Mock
    LoadServiceInfoPort loadServiceInfoPort;

    @InjectMocks
    IndexService indexService;

    @Test
    void build_index_from_multiple_services() throws FailedToLoadException {
        // arrange
        arrangeLoadService();

        // act
        Map<String, Set<String>> result = indexService.getServiceByRequirements();

        // assert
        assertThat(result.keySet().size()).isEqualTo(3);
        assertThat(result.keySet()).contains("req1");
        assertThat(result.keySet()).contains("req2");
        assertThat(result.keySet()).contains("req3");

        assertThat(result.get("req1").size()).isOne();
        assertThat(result.get("req1")).contains("service-a");

        assertThat(result.get("req2").size()).isEqualTo(2);
        assertThat(result.get("req2")).contains("service-a");
        assertThat(result.get("req2")).contains("service-b");

        assertThat(result.get("req3").size()).isOne();
        assertThat(result.get("req3")).contains("service-b");
    }

    @Test
    void build_based_on_and_with_single_requirements() throws FailedToLoadException {
        // arrange
        arrangeLoadService();

        Set<String> requirements = new HashSet<>();
        requirements.add("req2");

        // act
        Set<String> result = indexService.getServiceByRequirementsAnd(requirements);

        // assert
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains("service-a");
        assertThat(result).contains("service-b");
    }

    @Test
    void build_based_on_and_with_multiple_requirements() throws FailedToLoadException {
        // arrange
        arrangeLoadService();

        Set<String> requirements = new HashSet<>();
        requirements.add("req2");
        requirements.add("req3");

        // act
        Set<String> result = indexService.getServiceByRequirementsAnd(requirements);

        // assert
        assertThat(result.size()).isOne();
        assertThat(result).contains("service-b");
    }

    @Test
    void build_based_on_or_with_single_requirements() throws FailedToLoadException {
        // arrange
        arrangeLoadService();

        Set<String> requirements = new HashSet<>();
        requirements.add("req2");

        // act
        Set<String> result = indexService.getServiceByRequirementsOr(requirements);

        // assert
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains("service-a");
        assertThat(result).contains("service-b");
    }

    @Test
    void build_based_on_or_with_multiple_requirements() throws FailedToLoadException {
        // arrange
        arrangeLoadService();

        Set<String> requirements = new HashSet<>();
        requirements.add("req1");
        requirements.add("req3");

        // act
        Set<String> result = indexService.getServiceByRequirementsOr(requirements);

        // assert
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains("service-a");
        assertThat(result).contains("service-b");
    }

    private void arrangeLoadService() throws FailedToLoadException {
        List<String> serviceNames = Arrays.asList("service-a", "service-b");
        when(loadServiceInfoPort.loadAll()).thenReturn(serviceNames);

        when(loadServiceInfoPort.load("service-a"))
                .thenReturn(buildService("service-a", Arrays.asList("req1", "req2")));

        when(loadServiceInfoPort.load("service-b"))
                .thenReturn(buildService("service-b", Arrays.asList("req2", "req3")));
    }

    private ServiceInfo buildService(String serviceName, List<String> requirementes) {
        ServiceInfo serviceInfo =  ServiceInfo.create(serviceName);

        requirementes.forEach(requirement ->
                serviceInfo.addRequirement(new EntityId("topic"), new EntityId(requirement)));

        return serviceInfo;
    }
}
