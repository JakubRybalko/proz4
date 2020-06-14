
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.json.JSONObject;

//import com.google.gson.Gson;

//import jdk.jshell.JShell;
//import jdk.jshell.Snippet;
//import jdk.jshell.SnippetEvent;

// klasa główna implementująca polecenia HTTP
// jest adnotowana @Path
// jest publiczna
// ma domyślny i publiczny konstruktor
@Path("hello")
public class ServerMain {
	
	Client client = ClientBuilder.newClient(); 
	String exp = "2*3";
	URI uri = URI.create("http://api.mathjs.org/v4/" + "?expr=" + exp); 	
	WebTarget webTarget = client.target(uri);
	String plainAnswer = webTarget.request().accept(MediaType.TEXT_PLAIN).get(String.class);
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String crunchifyREST(InputStream incomingData) {
		StringBuilder crunchifyBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				crunchifyBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		String dae = crunchifyBuilder.toString();
		System.out.println("Data1 Received: " + crunchifyBuilder.toString()+ "\n");
		//JSONObject obj2 = new JSONObject(dae);
		//System.out.println(obj2);
		dae = dae.substring(9, dae.length()-2);
		System.out.println(dae);
		// return HTTP response 200 in case of success
		dae = dae.replace("/", "%2F");
		dae = dae.replace("+", "%2B");
		uri = URI.create("http://api.mathjs.org/v4/"+"?expr="+dae);
		WebTarget webTarget = client.target(uri);
		String plainAnswerw = webTarget.request().accept(MediaType.TEXT_PLAIN).get(String.class);
		System.out.println(plainAnswerw);
		JSONObject obj2 = new JSONObject();
		obj2.put("result", plainAnswerw);
		System.out.println(obj2);
		return obj2.toString();
		
	}

}