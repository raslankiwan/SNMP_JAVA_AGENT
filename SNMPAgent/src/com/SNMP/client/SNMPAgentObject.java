package com.SNMP.client;

import java.io.File;
import java.io.IOException;
import org.snmp4j.agent.*;
import org.snmp4j.agent.mo.MOTableRow;
import org.snmp4j.agent.mo.snmp.RowStatus;
import org.snmp4j.agent.mo.snmp.SnmpCommunityMIB;
import org.snmp4j.agent.mo.snmp.SnmpCommunityMIB.SnmpCommunityEntryRow;
import org.snmp4j.agent.mo.snmp.SnmpNotificationMIB;
import org.snmp4j.agent.mo.snmp.SnmpTargetMIB;
import org.snmp4j.agent.mo.snmp.StorageType;
import org.snmp4j.agent.mo.snmp.VacmMIB;
import org.snmp4j.agent.security.MutableVACM;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.*;
import org.snmp4j.smi.*;
import org.snmp4j.transport.*;
import org.snmp4j.TransportMapping;

public class SNMPAgentObject extends BaseAgent {
	private String address;
	public SNMPAgentObject(String address) throws IOException {
		super(new File("conf.agent"), new File("bootCounter.agent"),
				  new CommandProcessor(
						  new OctetString(MPv3.createLocalEngineID())));
		this.address = address;

	}

	protected void addCommunities(SnmpCommunityMIB scmib) {
		
		Variable[] com2sec = new Variable[] {
				new OctetString("public"),
				new OctetString("cpublic"),
				getAgent().getContextEngineID(),
				new OctetString("public"),
				new OctetString(),
				new Integer32(StorageType.nonVolatile),
				new Integer32(RowStatus.active)
				};
        
		MOTableRow row = scmib.getSnmpCommunityEntry()
				.createRow(new OctetString("public2public").toSubIndex(true), com2sec);
        scmib.getSnmpCommunityEntry().addRow((SnmpCommunityEntryRow) row);
	}
	
	protected void addNotificationTargets(SnmpTargetMIB arg0, SnmpNotificationMIB arg1) {
		// TODO Auto-generated method stub
	}

	protected void addUsmUser(USM arg0) {
		// TODO Auto-generated method stub
	}

	protected void addViews(VacmMIB vacm) {
		// TODO Auto-generated method stub
		vacm.addGroup(SecurityModel.SECURITY_MODEL_SNMPv2c, new OctetString(
				 "cpublic"), new OctetString("v1v2group"),
				 StorageType.nonVolatile);
		 vacm.addAccess(new OctetString("v1v2group"), new OctetString("public"),
				 SecurityModel.SECURITY_MODEL_ANY, SecurityLevel.NOAUTH_NOPRIV,
				 MutableVACM.VACM_MATCH_EXACT, new OctetString("fullReadView"),
				 new OctetString("fullWriteView"), new OctetString(
						 "fullNotifyView"), StorageType.nonVolatile);
		 vacm.addViewTreeFamily(new OctetString("fullReadView"), new OID("1.3"),
				 new OctetString(), VacmMIB.vacmViewIncluded,
				 StorageType.nonVolatile);
	}

	protected void registerManagedObjects() {
		// TODO Auto-generated method stub
	}

	protected void unregisterManagedObjects() {
		// TODO Auto-generated method stub
	}

	protected void initTransportMappings() throws IOException {
		 transportMappings = new TransportMapping[1];
		 Address addr = GenericAddress.parse(address);
		 TransportMapping tm = TransportMappings.getInstance()
				 .createTransportMapping(addr);
		 transportMappings[0] = tm;
	}
	
	public void start() throws IOException {
		init();
		addShutdownHook();
        unregisterManagedObject(this.getSnmpv2MIB());
        getServer().addContext(new OctetString("public"));
        finishInit();
        run();
        sendColdStartNotification();
        //System.out.println("Snmp Get Response = " ); 
	}
	
	public void registerManagedObject(ManagedObject mo) {
		try {
			server.register(mo, null);
		} catch (DuplicateRegistrationException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void unregisterManagedObject(MOGroup moGroup) {
		// TODO Auto-generated method stub
		moGroup.unregisterMOs(server, getContext(moGroup));
	}

}
