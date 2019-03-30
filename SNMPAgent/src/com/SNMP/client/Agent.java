package com.SNMP.client;

import java.awt.EventQueue;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.snmp4j.smi.OID;
import sun.net.util.IPAddressUtil;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import java.awt.Font;

public class Agent extends JFrame {

	private JPanel contentPane;
	private JTextField IP;
	private JTextField port;

	/**
	 * Launch the application.
	 */
	
	static final OID sysDescr = new OID(".1.3.6.1.2.1.1.1.0");
	static final OID sysObjectID = new OID(".1.3.6.1.2.1.1.2.0");
	static final OID sysUpTime = new OID(".1.3.6.1.2.1.1.3.0");
	static final OID sysContact = new OID(".1.3.6.1.2.1.1.4.0");
	static final OID sysName = new OID(".1.3.6.1.2.1.1.5.0");
	static final OID sysLocation = new OID(".1.3.6.1.2.1.1.6.0");
	static final OID sysServices = new OID(".1.3.6.1.2.1.1.7.0");
	private JSeparator separator;
	private JTextField desc;
	private JTextField name;
	private JTextField objID;
	private JTextField upTime;
	private JTextField contact;
	private JTextField location;
	private JTextField services;
	
	
	public static void main(String[] args) throws IOException {
		

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Agent frame = new Agent();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	
	void callAgent() 
	{	
		try
		{
			String prt= port.getText();
			String ip=IP.getText();
			boolean isIP = IPAddressUtil.isIPv4LiteralAddress(ip);
			if(isIP && prt.matches("\\d+"))
			{
				
			SNMPAgentObject   agent = new SNMPAgentObject(ip+"/"+prt);
		      agent.start();
		      agent.unregisterManagedObject(agent.getSnmpv2MIB());
		      agent.registerManagedObject(MOCreator.createReadOnly(sysDescr,desc.getText()));
		      agent.registerManagedObject(MOCreator.createReadOnly(sysObjectID,objID.getText()));
		      agent.registerManagedObject(MOCreator.createReadOnly(sysUpTime,upTime.getText()));
		      agent.registerManagedObject(MOCreator.createReadOnly(sysContact,contact.getText()));
		      agent.registerManagedObject(MOCreator.createReadOnly(sysName,name.getText()));
		      agent.registerManagedObject(MOCreator.createReadOnly(sysLocation,location.getText()));
		      agent.registerManagedObject(MOCreator.createReadOnly(sysServices,services.getText()));
	
		      JOptionPane.showMessageDialog(this.getComponent(0),"Request Performed","Success", JOptionPane.INFORMATION_MESSAGE);
	
			}	
			else 
			{
				JOptionPane.showMessageDialog(this.getComponent(0), "Please type correct IP address and port number", "Address Error",JOptionPane.ERROR_MESSAGE);

			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(this.getComponent(0), "Choose different port number","Port Number in use",JOptionPane.ERROR_MESSAGE);
		}



	    
	}
	public Agent() {
		setTitle("SNMP Agent Application");
		setFont(new Font("Bauhaus 93", Font.PLAIN, 12));
		setForeground(Color.BLACK);
		setBackground(new Color(255, 255, 255));
		setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\Projects\\Eclipse\\SNMPAgent\\trap-summary.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 665, 527);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(0, 0, 128));
		contentPane.setBackground(new Color(204, 255, 102));
		contentPane.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 100, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		IP = new JTextField();
		IP.setText("127.0.0.1");
		IP.setForeground(new Color(0, 0, 128));
		IP.setBounds(194, 61, 116, 22);
		contentPane.add(IP);
		IP.setColumns(10);
		
		JLabel lblIpAddress = new JLabel("IP Address");
		lblIpAddress.setForeground(Color.BLUE);
		lblIpAddress.setBounds(53, 61, 103, 22);
		contentPane.add(lblIpAddress);
		
		JLabel lblEnterPort = new JLabel("Port Number");
		lblEnterPort.setForeground(Color.BLUE);
		lblEnterPort.setBounds(333, 63, 83, 19);
		contentPane.add(lblEnterPort);
		
		port = new JTextField();
		port.setForeground(new Color(0, 0, 128));
		port.setColumns(10);
		port.setBounds(474, 61, 116, 22);
		contentPane.add(port);
		
		JButton btnSend = new JButton("Submitt");
		btnSend.setBackground(new Color(0, 102, 0));
		btnSend.setForeground(new Color(0, 0, 128));
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					callAgent();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSend.setBounds(204, 413, 258, 54);
		contentPane.add(btnSend);
		
		separator = new JSeparator();
		separator.setBounds(38, 253, 313, -53);
		contentPane.add(separator);
		
		desc = new JTextField();
		desc.setForeground(new Color(0, 0, 128));
		desc.setBounds(194, 206, 116, 22);
		contentPane.add(desc);
		desc.setColumns(10);
		
		JLabel lblSystemDescription = new JLabel("System Description");
		lblSystemDescription.setForeground(Color.BLUE);
		lblSystemDescription.setBounds(53, 206, 129, 22);
		contentPane.add(lblSystemDescription);
		
		JLabel lblSystemName = new JLabel("System Name");
		lblSystemName.setForeground(Color.BLUE);
		lblSystemName.setBounds(53, 253, 129, 22);
		contentPane.add(lblSystemName);
		
		name = new JTextField();
		name.setForeground(new Color(0, 0, 128));
		name.setColumns(10);
		name.setBounds(194, 253, 116, 22);
		contentPane.add(name);
		
		JLabel lblObjectId = new JLabel("ObjectID");
		lblObjectId.setForeground(Color.BLUE);
		lblObjectId.setBounds(53, 307, 129, 22);
		contentPane.add(lblObjectId);
		
		objID = new JTextField();
		objID.setForeground(new Color(0, 0, 128));
		objID.setColumns(10);
		objID.setBounds(194, 307, 116, 22);
		contentPane.add(objID);
		
		JLabel lblSystemUpTime = new JLabel("System Up Time");
		lblSystemUpTime.setForeground(Color.BLUE);
		lblSystemUpTime.setBounds(53, 355, 129, 22);
		contentPane.add(lblSystemUpTime);
		
		upTime = new JTextField();
		upTime.setForeground(new Color(0, 0, 128));
		upTime.setColumns(10);
		upTime.setBounds(194, 355, 116, 22);
		contentPane.add(upTime);
		
		JLabel lblSystemContact = new JLabel("System Contact");
		lblSystemContact.setForeground(Color.BLUE);
		lblSystemContact.setBounds(333, 206, 129, 22);
		contentPane.add(lblSystemContact);
		
		contact = new JTextField();
		contact.setForeground(new Color(0, 0, 128));
		contact.setColumns(10);
		contact.setBounds(474, 206, 116, 22);
		contentPane.add(contact);
		
		JLabel lblSystemLocation = new JLabel("System Location");
		lblSystemLocation.setForeground(Color.BLUE);
		lblSystemLocation.setBounds(333, 253, 129, 22);
		contentPane.add(lblSystemLocation);
		
		location = new JTextField();
		location.setForeground(new Color(0, 0, 128));
		location.setColumns(10);
		location.setBounds(474, 253, 116, 22);
		contentPane.add(location);
		
		JLabel lblSystem = new JLabel("System Services");
		lblSystem.setForeground(Color.BLUE);
		lblSystem.setBounds(333, 307, 129, 22);
		contentPane.add(lblSystem);
		
		services = new JTextField();
		services.setForeground(new Color(0, 0, 128));
		services.setColumns(10);
		services.setBounds(474, 307, 116, 22);
		contentPane.add(services);
	}
}
