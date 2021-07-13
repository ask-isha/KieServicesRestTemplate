package com.component.template;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;

public class KieConfiguration {

	protected String serverURL = "http://localhost:8080/kie-server/services/rest/server";
	protected String kieUser = "krisv";
	protected String password = "krisv";

	private KieServicesConfiguration configureKieServices(Map<String,Object> config) {
		KieServicesConfiguration kieConfiguration = null;
		//System.out.println("config::"+config);
		kieConfiguration = KieServicesFactory.newRestConfiguration((String)config.get("serverURL"), (String)config.get("kieUser"),(String)config.get("password") );
		//kieConfiguration = KieServicesFactory.newRestConfiguration(serverURL, kieUser,password );
		//If you use custom classes, such as Obj.class, add them to the configuration.
        Set<Class<?>> extraClassList = new HashSet<Class<?>>();
        extraClassList.add(Product.class);
        kieConfiguration.addExtraClasses(extraClassList);
		
		
		kieConfiguration.setMarshallingFormat(MarshallingFormat.JAXB);
		
		return kieConfiguration;
	}

	public KieServicesClient getKieClient(Map<String,Object> config) {
		KieServicesClient kieServicesClient = null;
		KieServicesConfiguration configuration = configureKieServices(config);
		kieServicesClient = KieServicesFactory.newKieServicesClient(configuration);
		return kieServicesClient;

	}

}
