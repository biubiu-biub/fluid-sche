package org.apache.flink.client.program.rest;

import java.util.*;
import org.apache.flink.client.task;
import org.apache.flink.client.registerJob.Client;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.flink.runtime.jobgraph.*;

public class JobRegister {
	public static void doRegister(JobGraph jobGraph){
		String jobID=jobGraph.getJobID().toString();
		List<task> tasks =JobRegisterUtil.jobGraph2tasks(jobGraph);
		// TODO: 2020/12/3 :use config file instead of hard encoding

		try {
			TTransport tTransport= new TSocket("127.0.0.1", 4396);
			TProtocol protocol = new TBinaryProtocol(tTransport);
			Client client=new Client(protocol);
			tTransport.open();
			client.registerMyJob(jobID,tasks);
			tTransport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
