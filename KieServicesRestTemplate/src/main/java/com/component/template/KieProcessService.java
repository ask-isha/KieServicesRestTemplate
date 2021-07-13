package com.component.template;

import java.util.Map;

public interface KieProcessService {

	Long startProcess(String containerId,String processId, Map<String,Object> variables , Map<String,Object> config);
}
