package ro.hpe;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import ro.hpe.objects.Params;


/**
 * @author popescra
 * Calss that contain all Rest Endpoints
 *
 */
@Path("/service")
public class MessageRestService {

	/**
	 * Process endpoint.  Receive an argument that contain the file date 
	 * 	File processed will be found at location pointed by URL_PATH/MCP_@param
	 * @param msg date used in selection of file to be processed
	 * @return Web response.  always 200OK, The content will contain message with status of processed file 
	 */
	@GET
	@Path("/process/{param}")
	public Response processMsg(@PathParam("param") String msg) {

		
		ProcessMessage processMsg = new ProcessMessage();
		String result = processMsg.processJson(msg);
		return Response.status(200).entity(result).build();
		
	}
	
	/**Kpis endpoint.
	 * @return JSON with KPI information
	 */
	@GET
	@Path("/kpis")
	public Response processKpi() {
	
		String result = Params.getKpiJson();
		return Response.status(200).entity(result).build();
		
	}
	
	/**Metrics endpoint
	 * @return JSON with metrics information
	 */
	@GET
	@Path("/metrics")
	public Response processMetrics() {
	
		String result = Params.getMetricsJson();
		return Response.status(200).entity(result).build();
		
	}


}