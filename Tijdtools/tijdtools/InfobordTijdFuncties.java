package tijdtools;

import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;

public class InfobordTijdFuncties {

	public static Tijd getCentralTime()
    {
    	try {
    		HTTPFuncties httpFuncties = new HTTPFuncties();
			String result = httpFuncties.executeGet("json");
			Tijd tijd = new ObjectMapper().readValue(result, Tijd.class);
	        return tijd;
    	} catch (IOException e) {
			e.printStackTrace();
			return new Tijd(0,0,0);
		}
    }
}
