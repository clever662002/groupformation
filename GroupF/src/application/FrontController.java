package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dsrg.soenea.application.servlet.DispatcherServlet;
import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;
import org.dsrg.soenea.application.servlet.service.DispatcherFactory;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.domain.role.impl.GuestRole;
import org.dsrg.soenea.domain.role.mapper.RoleOutputMapper;
import org.dsrg.soenea.domain.user.GuestUser;
import org.dsrg.soenea.domain.user.IUser;
import org.dsrg.soenea.domain.user.User;
import org.dsrg.soenea.domain.user.mapper.UserInputMapper;
import org.dsrg.soenea.domain.user.mapper.UserOutputMapper;
import org.dsrg.soenea.service.MySQLConnectionFactory;
import org.dsrg.soenea.service.authorization.ApplicationAuthorizaton;
import org.dsrg.soenea.service.logging.Logging;
import org.dsrg.soenea.service.logging.SQLLogger;
import org.dsrg.soenea.service.registry.Registry;
import org.dsrg.soenea.service.threadLocal.DbRegistry;
import org.dsrg.soenea.service.threadLocal.ThreadLocalTracker;
import org.dsrg.soenea.uow.MapperFactory;
import org.dsrg.soenea.uow.UoW;

import domain.model.role.AdminRole;
import domain.model.role.RegisteredRole;

public class FrontController extends DispatcherServlet {

	private static final long serialVersionUID = 1L;
	public static final String LOG_STRING = "soenea.test";
	private static String defaultDispatcher = "";

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			defaultDispatcher = Registry.getProperty("defaultDispatcher");
		} catch (Exception e1) {

		}
		ApplicationAuthorizaton.setBasePath(getServletContext()
				.getRealPath("."));
		prepareDbRegistry();

		setupUoW();

		/**
		 * My attempts to use Logging commons utilities
		 */
		String loglevel = null;
		try {
			loglevel = Registry.getProperty("LogLevel");
			if (loglevel.trim().equals(""))
				throw new Exception("EmptyProperty");
		} catch (Exception e1) {
			loglevel = "INFO";
		}

		try {
			for (Handler h : Logger.getLogger(FrontController.LOG_STRING)
					.getHandlers()) {
				Logger.getLogger(FrontController.LOG_STRING).removeHandler(h);
			}

			ConsoleHandler ch = new ConsoleHandler();
			ch.setLevel(Level.ALL);
			Logger.getLogger(FrontController.LOG_STRING).addHandler(ch);
			Logger.getLogger(FrontController.LOG_STRING).setLevel(
					Level.parse(loglevel));
			Logging.setLoggerString(FrontController.LOG_STRING);
			SQLLogger.setLogThreshold(500);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void setupUoW() {
		MapperFactory myDomain2MapperMapper = new MapperFactory();

		myDomain2MapperMapper.addMapping(User.class, UserOutputMapper.class);
		myDomain2MapperMapper.addMapping(GuestUser.class,
				UserOutputMapper.class);
		myDomain2MapperMapper.addMapping(GuestRole.class,
				RoleOutputMapper.class);
		myDomain2MapperMapper.addMapping(AdminRole.class,
				RoleOutputMapper.class);
		myDomain2MapperMapper.addMapping(RegisteredRole.class,
				RoleOutputMapper.class);

		UoW.initMapperFactory(myDomain2MapperMapper);
	}

	public static void prepareDbRegistry() {
		prepareDbRegistry("");
	}

	public static void prepareDbRegistry(String key) {
		MySQLConnectionFactory f = new MySQLConnectionFactory(null, null, null,
				null);
		try {
			f.defaultInitialization();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		DbRegistry.setConFactory(key, f);
		String tablePrefix;
		try {
			tablePrefix = Registry.getProperty(key + "mySqlTablePrefix");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			tablePrefix = "";
		}
		if (tablePrefix == null) {
			tablePrefix = "";
		}
		DbRegistry.setTablePrefix(key, tablePrefix);
	}

	@Override
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			IOException {
		request.setCharacterEncoding("UTF-8");
		Helper myHelper = null;
		Dispatcher command = null;
		String commandName = null;
		IUser user = null;
		try {

			for (Object key : request.getParameterMap().keySet()) {
				Logger.getLogger(LOG_STRING).log(
						Level.FINEST,
						("Key: " + key + " Value: " + Arrays.toString(request
								.getParameterValues(key.toString()))));
			}
			commandName = getCommandName(request);

			if (commandName == null)
				commandName = "";

			user = (IUser) myHelper.getSessionAttribute("CurrentUser");

			if (user == null) {
				user = new GuestUser();
				request.getSession(true).setAttribute("CurrentUser", user);
			}

			if (!(user instanceof GuestUser)) {
				user = UserInputMapper.find(user.getId());
				request.getSession(true).setAttribute("CurrentUser", user);
			}

			if (!ApplicationAuthorizaton.hasAuthority(commandName, user.getRoles())) {
				throw new Exception("Access Denied to " + commandName
						+ " for user " + user.getUsername());
			}

			command = DispatcherFactory.getInstance(commandName);

			Logger.getLogger(LOG_STRING).log(Level.FINE,
					command.getClass().toString());
			command.init(request, response);
			long time = System.currentTimeMillis();
			command.execute();
			Logger.getLogger(LOG_STRING).log(
					Level.FINER,
					"Time to execute command: "
							+ (System.currentTimeMillis() - time) + "ms.");
		} catch (Exception exception) {
			Throwable e = exception;
			Logger.getLogger(LOG_STRING).throwing(getClass().getName(),
					"processRequest", e);
			request.setAttribute("errorMessage", e.getMessage());
			request.setAttribute("exception", e);
			request.getRequestDispatcher("/WEB-INF/JSP/html/Error.jsp")
					.forward(request, response);
		}
	}

	@Override
	protected void preProcessRequest(HttpServletRequest request,
			HttpServletResponse response) {
		super.preProcessRequest(request, response);
		UoW.newCurrent();
		try {
			DbRegistry.getDbConnection().setAutoCommit(false);
			DbRegistry.getDbConnection().createStatement()
					.execute("START TRANSACTION;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void postProcessRequest(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			DbRegistry.getDbConnection().createStatement().execute("ROLLBACK;");
			DbRegistry.getDbConnection().close();
			DbRegistry.closeDbConnectionIfNeeded();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ThreadLocalTracker.purgeThreadLocal();
	}

	protected String getCommandName(HttpServletRequest request)
			throws Exception {
		String commandName = request.getParameter("command");
		if (commandName == null || commandName.equals("")) {
			if (defaultDispatcher == null)
				throw new Exception("HTTP attribute 'command' is missing.");
			else
				commandName = defaultDispatcher;
		}
		return commandName;
	}

}
