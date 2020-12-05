package org.apache.flink.client.program.rest;

import org.apache.flink.client.task;
import java.util.*;

import org.apache.flink.runtime.jobgraph.*;
public class JobRegisterUtil {
	public static List<task> jobGraph2tasks(JobGraph jobGraph){
		List<task> tasks=new ArrayList<>();
		Map<String,String> idMap=new TreeMap<>();
		String uuid=new String();
		String jobVertexID=new String();
		Iterable<JobVertex> jobVertices=jobGraph.getVertices();

		for(JobVertex jobVertex:jobVertices){
			uuid=UUID.randomUUID().toString();
			idMap.put(jobVertexID,uuid);
		}

		for(JobVertex jobVertex:jobVertices){
			List<String>dep =new ArrayList<>();
			List<JobEdge> edges=jobVertex.getInputs();
			edges.forEach(edge->{
				String producerJobVertexID=edge.getSource().getProducer().getID().toString();
				String producerUUID=idMap.get(producerJobVertexID);
				dep.add(producerUUID);
				String job=jobVertex.getID().toString();
				String UUID=idMap.get(job);

				task t=new task(UUID,job,dep);
				tasks.add(t);
			});


		}
		return tasks;
	}
}
