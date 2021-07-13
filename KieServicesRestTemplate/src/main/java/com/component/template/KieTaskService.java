package com.component.template;

import java.util.List;
import java.util.Map;

import org.kie.server.api.model.instance.TaskSummary;

public interface KieTaskService {

	List<TaskSummary> groupInbox( List<String> groups, Integer page, Integer pageSize, Map<String,Object> config);
	List<TaskSummary> userInbox(String userId, Integer page, Integer pageSize, Map<String,Object> config);
	List<TaskSummary> groupInboxByProcessInstanceId(Long processInstanceId, Integer page, Integer pageSize, Map<String,Object> config);
	List<TaskSummary> userInboxByProcessInstanceId(Long processInstanceId, Integer page, Integer pageSize, Map<String,Object> config);
	
	
	void startAndCompleteTask(String containerId, Long TaskId, String owner,  Map<String,Object> config);
	void claimTask(String containerId, Long TaskId, String owner, Map<String,Object> config);
}
