package abc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.coyote.Request;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

@WebServlet("/FileUploadServlet")
public class FileUploadHandler extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	//Set the directory to upload the files to 
	private final String UPLOAD_DIRECTORY = "/";
    public FileUploadHandler()
    {
    	super();
    }
    String type = null;  //Variable to store type of file 
    /**
     * Performs the operations required after pressing Submit button
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
    {
    	String context = request.getServletContext().getRealPath("/");      //Gets the address to the working folder
    	System.out.println(context);
    	File file = new File(context+UPLOAD_DIRECTORY);						
		//Create a folder if it does not exist
    	if (!file.exists()) {											
			if (file.mkdir()) {
				System.out.println("Directory is created!");
			} 
			else {
				System.out.println("Failed to create directory!");
			}
		}
    	//File Upload code starts
        if(ServletFileUpload.isMultipartContent(request))
       	{
        	try 
        	{
        		List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(new ServletRequestContext(request));
        		for(FileItem item : multiparts){
        			if(!item.isFormField()){
        				String name = new File(item.getName()).getName();
        				type = item.getContentType();
        				System.out.println("Original File Name "+name+" of type "+type);
       					item.write( new File(context+UPLOAD_DIRECTORY + File.separator + "circuit.xml"));
       				}
       			}
        		//File Type Validation
       			if(type.matches("text/xml"))
       			{
       				request.setAttribute("message", "XML Uploaded Successfully");
       			}
       			else{
       				PrintWriter out = response.getWriter();
        			response.setContentType("text/html");
        			out.println("<h3>The file selected is not supported.</h3>");
        			out.println("<h3> Choose an XML File to Upload</h3>");
       				out.println("<form action='FileUploadServlet' method='post' enctype='multipart/form-data'>");
       				out.println("<input type='file' name='file' />");    
       				out.println("<input type='submit' value='upload' />");    
       				out.println("</form>");
       			}
       			//File uploaded successfully             
        		} 
        	catch (Exception ex) {
        		request.setAttribute("message", "XML Upload Failed due to " + ex);
       		}            
        }
       	else{
       		request.setAttribute("message","Sorry this Servlet only handles file upload request");
       	}
        //Guarantees only forwards when the correct file type is uploaded
       	if(type.matches("text/xml"))
       	{
       		request.getRequestDispatcher("/imageimport.jsp").forward(request, response);  
       	}
   	}
}