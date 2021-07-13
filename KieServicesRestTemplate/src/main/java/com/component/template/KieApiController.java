package com.component.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.server.api.model.instance.TaskSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
@RestController
public class KieApiController {

	@Value("${serverURL}")
    private String serverURL;
	
	@Value("${kieUser}")
    private String kieUser;
	
	@Value("${password}")
    private String password;
	
	@Value("${containerId}")
    private String containerId;
	
	@Value("${processId}")
    private String processId;
	
	@Autowired
	KieProcessService kieProcessService;
	
	@Autowired
	KieTaskService kieTaskService;

	Map<String,Object> productRepo = new HashMap<String,Object>();
	   @RequestMapping(value = "/products", method = RequestMethod.POST)
	   public ResponseEntity<Object> createProduct(@RequestBody Product product) {
	      productRepo.put(product.getId(), product);
	      return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
	   }
	   
	   @RequestMapping(value = "/invokeFooProcess", method = RequestMethod.POST)
	   public ResponseEntity<Object> invokeFooProcess(@RequestBody Product product) {
		   
		   Map<String , Object> configMap = new HashMap<String , Object>();
	      productRepo.put("message", product.getName());
	      configMap.put("serverURL", serverURL);
	      configMap.put("kieUser", kieUser);
	      configMap.put("password", password);
	     Long pid =  kieProcessService.startProcess(containerId, processId, productRepo,configMap);
	     System.out.println("pid::::"+pid);
	      return new ResponseEntity<>("Process is created successfully", HttpStatus.CREATED);
	   }
	   
	   
	   @RequestMapping(value = "/group", method = RequestMethod.GET)
	   public ResponseEntity<Object> groupInbox() {
		   
		   Map<String , Object> configMap = new HashMap<String , Object>();
	      configMap.put("serverURL", serverURL);
	      configMap.put("kieUser", kieUser);
	      configMap.put("password", password);
	      
	      List<String> group = new ArrayList<>();
		  group.add("L1");
	      
	      List<TaskSummary> list = kieTaskService.groupInbox(group, 0, 10, configMap);
	      
	      System.out.println("List::"+list);
	      
	      return new ResponseEntity<>("fetched details", HttpStatus.OK);
	   }
	   
	   @RequestMapping(value = "/userInbox", method = RequestMethod.GET)
	   public ResponseEntity<Object> userInbox() {
		   
		   Map<String , Object> configMap = new HashMap<String , Object>();
	      configMap.put("serverURL", serverURL);
	      configMap.put("kieUser", kieUser);
	      configMap.put("password", password);
	      
	      String user="krisv";
	      List<TaskSummary> list = kieTaskService.userInbox(user, 0, 10, configMap);
	      
	      System.out.println("List::"+list);
	      
	      return new ResponseEntity<>("fetched details", HttpStatus.OK);
	   }
	   
	   @RequestMapping(value = "/claim", method = RequestMethod.POST)
	   public ResponseEntity<Object> claim(@RequestBody Task task) {
		   
		   Map<String , Object> configMap = new HashMap<String , Object>();
	      configMap.put("serverURL", serverURL);
	      configMap.put("kieUser", kieUser);
	      configMap.put("password", password);
	      
	      kieTaskService.claimTask(containerId, task.getTaskId(), task.getOwner(), configMap);
	      
	      return new ResponseEntity<>("Task is claimed successfully", HttpStatus.CREATED);
	   }
	   
	   @RequestMapping(value = "/startAndComplete", method = RequestMethod.POST)
	   public ResponseEntity<Object> startAndComplete(@RequestBody Task task) {
		   
		   Map<String , Object> configMap = new HashMap<String , Object>();
	      configMap.put("serverURL", serverURL);
	      configMap.put("kieUser", kieUser);
	      configMap.put("password", password);

	      kieTaskService.startAndCompleteTask(containerId, task.getTaskId(), task.getOwner(), configMap);
	      
	      return new ResponseEntity<>("Task is completed successfully", HttpStatus.CREATED);
	   }
	   
	   
	   @RequestMapping(value = "/groupbypid/{pid}", method = RequestMethod.GET)
	   public ResponseEntity<Object> groupInboxByPid(@PathVariable("pid") Long pid) {
		   
		   Map<String , Object> configMap = new HashMap<String , Object>();
	      configMap.put("serverURL", serverURL);
	      configMap.put("kieUser", kieUser);
	      configMap.put("password", password);
	      
	      List<TaskSummary> list = kieTaskService.groupInboxByProcessInstanceId(pid, 0, 10, configMap);
	      
	      System.out.println("List::"+list);
	      
	      return new ResponseEntity<>("fetched details", HttpStatus.OK);
	   }
	   
	   @RequestMapping(value = "/userInboxbypid/{pid}", method = RequestMethod.GET)
	   public ResponseEntity<Object> userInboxByPid(@PathVariable("pid") Long pid) {
		   
		   Map<String , Object> configMap = new HashMap<String , Object>();
	      configMap.put("serverURL", serverURL);
	      configMap.put("kieUser", kieUser);
	      configMap.put("password", password);
	      
	      List<TaskSummary> list = kieTaskService.userInboxByProcessInstanceId(pid, 0, 10, configMap);
	      
	      System.out.println("List::"+list);
	      
	      return new ResponseEntity<>("fetched details", HttpStatus.OK);
	   }
}
