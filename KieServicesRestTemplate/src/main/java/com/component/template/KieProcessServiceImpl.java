package com.component.template;

import java.util.Map;

import org.kie.server.client.KieServicesClient;
import org.kie.server.client.ProcessServicesClient;
import org.springframework.stereotype.Service;

@Service
public class KieProcessServiceImpl implements KieProcessService {

	@Override
	public Long startProcess(String containerId, String processId, Map<String, Object> variables, Map<String, Object> config) {

		KieConfiguration kieCfg = new KieConfiguration();
		
		KieServicesClient kieServicesClient = kieCfg.getKieClient(config);
		
		ProcessServicesClient processClient = kieServicesClient.getServicesClient(ProcessServicesClient.class);
		
		Long pid = processClient.startProcess(containerId, processId, variables);
		
		return pid;
	}

}
