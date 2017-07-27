import java.io.*;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
public class grammarSampleGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebClient wc = new WebClient();
		HtmlPage p = null;
		try {
			p = wc.getPage("https://www.techhouse.org/cgi-bin/cchin/sentence.cgi");
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String txt = p.asXml();
		System.out.println(txt);
		wc.close();
	}

}
