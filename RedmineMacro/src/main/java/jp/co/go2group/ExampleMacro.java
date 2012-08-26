package jp.co.go2group;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.VelocityContext;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.renderer.RenderContext;

/**
 * This very simple macro shows you the very basic use-case of displaying
 * *something* on the Confluence page where it is used. Use this example macro
 * to toy around, and then quickly move on to the next example - this macro
 * doesn't really show you all the fun stuff you can do with Confluence.
 */
public class ExampleMacro implements Macro {

	// We just have to define the variables and the setters, then Spring injects
	// the correct objects for us to use. Simple and efficient.
	// You just need to know *what* you want to inject and use.

	private final PageManager pageManager;
	private final SpaceManager spaceManager;

	public ExampleMacro(PageManager pageManager, SpaceManager spaceManager) {
		this.pageManager = pageManager;
		this.spaceManager = spaceManager;
	}

	/**
	 * This method returns XHTML to be displayed on the page that uses this
	 * macro we just do random stuff here, trying to show how you can access the
	 * most basic managers and model objects. No emphasis is put on beauty of
	 * code nor on doing actually useful things :-)
	 */
	@Override
	public String execute(Map<String, String> parameters, String body,
			ConversionContext context) throws MacroExecutionException {
		String restapi = (String) parameters.get("hostname");
		if (restapi == null || "".equals(restapi)) {
			restapi = "http://my.redmine.jp";
		}

		String url = restapi + "/demo/projects/demo/issues.json";
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line = null;

		try {
			HttpURLConnection con = (HttpURLConnection) new URL(url)
					.openConnection();
			con.setRequestMethod("GET");

			br = new BufferedReader(new InputStreamReader(con.getInputStream(),
					"UTF-8"));

			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			System.out.println(sb.toString());

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(br);
		}

		VelocityContext contextMap = new VelocityContext(
				MacroUtils.defaultVelocityContext());
		if (RenderContext.DISPLAY.equals(context.getOutputType())) {
			contextMap.put("redmine", sb.toString());
			contextMap.put("restapi", restapi);
		}

		return VelocityUtils.getRenderedTemplate("examplemacro.vm", contextMap);
	}

	@Override
	public BodyType getBodyType() {
		return BodyType.NONE;
	}

	@Override
	public OutputType getOutputType() {
		return OutputType.BLOCK;
	}

}
