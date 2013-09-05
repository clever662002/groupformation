package domain.commands;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.DomainCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.uow.UoW;

import application.dispatchers.MultipartHttpServletHelper;
import domain.model.user.NamedUser;

public class UploadCSVCommand extends DomainCommand {
	
	private final Map<String, FileItem> fileItems = new Hashtable<String, FileItem>();
	private final Map<String, Object> formItems = new Hashtable<String, Object>();
	
	private final int MAX_FILE_MEMORY_SIZE = 10000;
	private final int MAX_REQUEST_SIZE = 10000;
	private final int MAX_FILE_SIZE = 10000;

	

	public UploadCSVCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void setUp() throws CommandException {
	}

	@Override
	public void process() throws CommandException {
		try {
			final List<FileItem> items = getMultipartFileItems(((MultipartHttpServletHelper)helper).getRequest());
			getMultipartFileItemsAndParameters(items, fileItems, formItems);
			int totalAdded = 0;
			int totalFailed = 0;
			helper.setRequestAttribute(MultipartHttpServletHelper.FORM_ITEM_STRING, formItems);
			helper.setRequestAttribute(MultipartHttpServletHelper.FILE_ITEM_STRING, fileItems);
			boolean found=false;
			
			for(int i=0;i<items.size();i++) {
				FileItem item = items.get(i);
				if(item.getFieldName().equals("file")) {
					found = true;
					String test = item.getString();

					String[] allRows = test.split("\r\n");
					
					for(int j=0;j<allRows.length;j++) {
						try {
							UoW.newCurrent();
							System.out.println("Processing row: "+allRows[j]);
							String[] values = allRows[j].split(",");
							NamedUser user = new NamedUser(0,values[0], values[1],values[2]);
							user.setPassword(values[3]);
							UoW.getCurrent().registerNew(user);
							UoW.getCurrent().commit(); //Don't need to wait for all users to be added, commit now
							totalAdded++;
						}
						catch(Exception e) {
							//failed to add someone
							totalFailed++;
						}
					}
					System.out.println("Added " + totalAdded + " user(s) to the database. ("+totalFailed+") user(s) failed to be added.");
					
					
				}
				
			}
			if(found) {
				this.helper.setRequestAttribute("message", "CSV Uploaded successfully. ("+totalAdded+") User(s) have been added to the database. ("+totalFailed+") user(s) failed to be added.");
			}
			
		}
		catch (Exception e) {
			this.helper.setRequestAttribute("message", "Failed to add user.\n" + e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void getMultipartFileItemsAndParameters(final List<FileItem> items,
			final Map<String, FileItem> fileItems, final Map<String, Object> formItems) {
		for (FileItem item : items) {
			if (item.isFormField()) {
				// a bit of ugly if for example a <select multiple='multiple'>
				if (formItems.containsKey(item.getFieldName())) {
					Object values = formItems.get(item.getFieldName());
					if (values instanceof Collection == false) {
						String tmp = (String)values;
						values = new ArrayList<String>();
						formItems.put(item.getFieldName(), values);
						((Collection)values).add(tmp);
					}
					((Collection)values).add(item.getString());
				} else
					formItems.put(item.getFieldName(),	item.getString());
			} else {
				fileItems.put(item.getFieldName(),	item);
			}
		}
	}
	
	
	protected List<FileItem> getMultipartFileItems(HttpServletRequest request) throws FileUploadException
	{
		final DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(MAX_FILE_MEMORY_SIZE);
		final ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(MAX_REQUEST_SIZE);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		final List<FileItem> items;
		items = upload.parseRequest(request);
		return items;
	}


	@Override
	public void tearDown() throws CommandError {
	}

}
