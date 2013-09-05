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
		//String path = request.getRequestURI();
		System.out.println("path = " + path);
		if (path == null)
			return null;
			
		// default command
		//String command = null;
		String command = "LoginDispatcher";

		// don't know a better way to do this, but we could regular expression
		String basePath = "/SOEN393c";
		System.out.println("commandPath = " + path);

		if (/*commandPath.equals(path)*/path.equals("/BrowseInvites")) {
			// rewrite to actual URL
			command = "BrowseInvitesDispatcher";
		} else if (path.equals("/InviteUser")) {
			command = "InviteUserDispatcher";
		} else if (path.equals("/AcceptInvite")) {
			command = "AcceptInviteDispatcher";
		} else if (path.equals("/DeclineInvite")) {
			command = "DeclineInviteDispatcher";
		} else if (path.equals("/Login")) {
			// rewrite to actual URL
			command = "LoginDispatcher";
		} else if (path.equals("/BrowseGroups")) {
			// rewrite to actual URL
			command = "BrowseGroupsDispatcher";
		} else if (path.equals("/BrowseMyGroup")) {
			// rewrite to actual URL
			command = "BrowseMyGroupDispatcher";
		} else if (path.equals("/CreateGroupForm")) {
			// rewrite to actual URL
			command = "CreateGroupFormDispatcher";
		} else if (path.equals("/CreateGroup")) {
			// rewrite to actual URL
			command = "CreateGroupDispatcher";
		} else if (path.equals("/Profile")) {
			// rewrite to actual URL
			command = "ProfileDispatcher";
		} else if (path.equals("/Admin")) {
			// rewrite to actual URL
			command = "AddUserDispatcher";
		} else if (path.equals("/Logout")) {
			// rewrite to actual URL
			command = "LogoutDispatcher";
		}  else if (path.equals("/AddUser")) {
			// rewrite to actual URL
			command = "AddUserDispatcher";
		}  else if (path.equals("/CSVUpload")) {
			// rewrite to actual URL
			command = "CSVDispatcher";

		}  else if (path.contains("/RemoveGroup")){
			Pattern pattern = Pattern.compile("^/RemoveGroup/(\\d+)$");
			Matcher matcher = pattern.matcher(path);
			if (matcher.find()) {
				String idAsString = null;
				command = "RemoveGroupDispatcher";
				idAsString = matcher.group(1);
				try {
					//long id = Long.parseLong(idAsString);
					request.setAttribute("id", idAsString);
				} catch (NumberFormatException e) {
					return null;
				}
			}
		} else if (path.contains("/RemoveMember")){
			Pattern pattern = Pattern.compile("^/RemoveMember/(\\d+)$");
			Matcher matcher = pattern.matcher(path);
			if (matcher.find()) {
				String idAsString = null;
				command = "RemoveMemberDispatcher";
				idAsString = matcher.group(1);
				try {
					//long id = Long.parseLong(idAsString);
					request.setAttribute("id", idAsString);
				} catch (NumberFormatException e) {
					return null;
				}
			}
		} else if (path.equals("/EditMyGroup")){
			
			command = "EditGroupDispatcher";
			
		}else {

			
			
			//do something
			
			 
		}

		System.out.println("command = " + command);
		return command;

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
