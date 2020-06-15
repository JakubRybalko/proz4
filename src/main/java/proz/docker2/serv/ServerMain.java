package proz.docker2.serv;
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

import org.json.JSONException;
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
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String actionServer(InputStream incomingData) throws JSONException {
		
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		
		
		String string = sb.toString();
		JSONObject obj3 = new JSONObject(string);
		string = obj3.getString("result");
		string = string.replace("/", "%2F");
		string = string.replace("+", "%2B");
		URI uri = URI.create("http://api.mathjs.org/v4/"+"?expr="+string);
		
		WebTarget webTarget = client.target(uri);
		String plainAnswerw = webTarget.request().accept(MediaType.TEXT_PLAIN).get(String.class);

		JSONObject obj2 = new JSONObject();
		obj2.put("result", plainAnswerw);
		System.out.println(obj2);
		return obj2.toString();
		
	}

}