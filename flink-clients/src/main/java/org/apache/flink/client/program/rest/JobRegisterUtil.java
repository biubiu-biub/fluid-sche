package org.apache.flink.client.program.rest;

import org.apache.flink.client.task;
import java.util.*;


import org.apache.flink.runtime.jobgraph.*;
public class JobRegisterUtil {
	public static List<task> jobGraph2tasks(JobGraph jobGraph){
		List<task> tasks=new ArrayList<>();
		Map<String, String> idMap=new TreeMap<>();
		Iterable<JobVertex> jobVertices=jobGraph.getVertices();

		for(JobVertex jobVertex:jobVertices){
			String uuid=UUID.randomUUID().toString();
			String jobVertexID=jobVertex.getID().toString();
			idMap.put(jobVertexID,uuid);
		}

		for(JobVertex jobVertex:jobVertices){
			ArrayList<String> dep =new ArrayList<>();
			String jid=jobVertex.getID().toString();
			String uuid=idMap.get(jid);
			List<JobEdge> edges=jobVertex.getInputs();
			edges.forEach(edge->{
				String producerJobVertexID=edge.getSource().getProducer().getID().toString();
				String producerUUID=idMap.get(producerJobVertexID);
				dep.add(producerUUID);
				// thrift will panic if any field is null,add a false dep for source operator
				if (dep.isEmpty()){
					dep.add("-1");
				}
			});
			task t=new task(uuid,jid,dep);
			tasks.add(t);

		}
		// just in case there is no operator...
		if (tasks.isEmpty())
			return Arrays.asList(new task("-1","-1",Arrays.asList("-1")));

		return tasks;
	}
}
