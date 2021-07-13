package com.component.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.api.task.model.Status;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.UserTaskServicesClient;
import org.springframework.stereotype.Service;

@Service
public class KieTaskServiceImpl implements KieTaskService {

	// Service for Group Inbox
	@Override
	public List<TaskSummary> groupInbox( List<String> groups,
			Integer page, Integer pageSize, Map<String,Object> config) {

		KieConfiguration kieCfg = new KieConfiguration();
		KieServicesClient kieServicesClient = kieCfg.getKieClient(config);
		
		//Status.Ready
		List<String> selectedStatus = new ArrayList<>();
		selectedStatus.add(Status.Ready.name());
		
		UserTaskServicesClient taskClient = kieServicesClient.getServicesClient(UserTaskServicesClient.class);
    	List<TaskSummary> list = taskClient.findTasksAssignedAsPotentialOwner("",groups,selectedStatus, page, pageSize);

		return list;
	}

	// Service for User Inbox
	@Override
	public List<TaskSummary> userInbox(String userId, Integer page,
			Integer pageSize, Map<String,Object> config) {

		KieConfiguration kieCfg = new KieConfiguration();
		KieServicesClient kieServicesClient = kieCfg.getKieClient(config);

		List<String> selectedStatus = new ArrayList<>();
				selectedStatus.add(Status.Reserved.name());
		
		UserTaskServicesClient taskClient = kieServicesClient.getServicesClient(UserTaskServicesClient.class);
		List<TaskSummary> list = taskClient.findTasksAssignedAsPotentialOwner(userId, 0, 10);
		return list;
	}

	@Override
	public void startAndCompleteTask(String containerId, Long taskId, String owner,  Map<String,Object> config) {
		KieConfiguration kieCfg = new KieConfiguration();
		KieServicesClient kieServicesClient = kieCfg.getKieClient(config);
		UserTaskServicesClient taskClient = kieServicesClient.getServicesClient(UserTaskServicesClient.class);
		
		taskClient.startTask(containerId, taskId, owner);
		
		Map<String,Object> taskParams = new HashMap<String,Object>();
		taskClient.completeTask(containerId, taskId, owner,taskParams);		
		
		
	}

	@Override
	public void claimTask(String containerId, Long taskId, String owner,  Map<String,Object> config) {
		KieConfiguration kieCfg = new KieConfiguration();
		
		KieServicesClient kieServicesClient = kieCfg.getKieClient(config);
		UserTaskServicesClient taskClient = kieServicesClient.getServicesClient(UserTaskServicesClient.class);
		
		taskClient.claimTask(containerId, taskId, owner);
	}

	@Override
	public List<TaskSummary> groupInboxByProcessInstanceId(Long processInstanceId, Integer page, Integer pageSize,
			Map<String, Object> config) {
		KieConfiguration kieCfg = new KieConfiguration();
		KieServicesClient kieServicesClient = kieCfg.getKieClient(config);
		
		//Status.Ready
		List<String> selectedStatus = new ArrayList<>();
		selectedStatus.add(Status.Ready.name());//Status.Ready
		
		UserTaskServicesClient taskClient = kieServicesClient.getServicesClient(UserTaskServicesClient.class);
		List<TaskSummary> list = taskClient.findTasksByStatusByProcessInstanceId(processInstanceId, selectedStatus, page, pageSize);
		return list;
	}

	@Override
	public List<TaskSummary> userInboxByProcessInstanceId(Long processInstanceId, Integer page, Integer pageSize,
			Map<String, Object> config) {
        
		KieConfiguration kieCfg = new KieConfiguration();
		KieServicesClient kieServicesClient = kieCfg.getKieClient(config);
		
		//Status.Reserved
		List<String> selectedStatus = new ArrayList<>();
		selectedStatus.add(Status.Reserved.name());

		
		UserTaskServicesClient taskClient = kieServicesClient.getServicesClient(UserTaskServicesClient.class);
		List<TaskSummary> list = taskClient.findTasksByStatusByProcessInstanceId(processInstanceId, selectedStatus, page, pageSize);
		return list;
	}

}
