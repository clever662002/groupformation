package filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class PermalinkFilter
 */
@WebFilter("/PermalinkFilter")
public class PermalinkFilter implements Filter {

	/**
	 * Default constructor. 
	 */
	public PermalinkFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest)request;
		
		String UrlCommand = dosomething(req);
		request.setAttribute("command", UrlCommand);

		// pass the request along the filter chain
		chain.doFilter(request, response);

	}

	private String dosomething(HttpServletRequest request) {


		String path = request.getPathInfo();	

		if (path == null)
			return null;
			
		// default command
		//String command = null;
		String command = "LoginDispatcher";

		if (path.equals("/BrowseGroups")) {
			// rewrite to actual URL
			command = "BrowseGroupsDispatcher";
		} else if (path.equals("/MyGroup")) {
			// rewrite to actual URL
			command = "ViewCurrentGroupDispatcher";
		}else {

			
			
			//do something
			
			 
		}

		return command;

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
