package abc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
/**
 * Handles uploading of Image file and displaying of result
 * Servlet implementation class DisplayServlet
 */
@WebServlet("/ImageUpload")
public class ImageUpload extends HttpServlet{
	private static final long serialVersionUID = 1L;
	// private static final long serialVersionUID = 1L;
	// location to store file uploaded
	private static final String UPLOAD_DIRECTORY = "/"; 
	// upload settings
	Backend bk = new Backend();
	ArrayList<Integer> Output = new ArrayList<>(); //Stores outputs recieved from backend class
	int NumberOfInputs = bk.pc.StorageObject.InputsCount;
	int NumberOfOutputs = bk.pc.StorageObject.OutputsCount;
	int NumberOfPossibilities = (int) Math.pow(2,NumberOfInputs);
	/**
	 * Converts decimal to binary in the required number of bits
	 * @param n: The decimal number
	 * @param NumberOfInputs: The number of bits required
	 * @return: The binary in the required number of decimals
	 */
	public ArrayList<Integer> ConvertToBinary(int n, int NumberOfInputs)
	{
		System.out.println("Binary of "+n);
		ArrayList<Integer> binary = new ArrayList<>();
		if(n==0)
		{
			for(int temp = 0;temp<NumberOfInputs;temp++)
			{
				binary.add(0);
			}
		}
		if(n!=0)
		{
			int count = 0;
			while(n!=0)
			{
				int rem = n%2;
				n = n/2;
				binary.add(rem);
				count++;
			}
			if(count<NumberOfInputs)
			{
				for(int temp1=count+1;temp1<=NumberOfInputs;temp1++)
				{
					binary.add(0);
				}
			}
		}
		
		Collections.reverse(binary);
		System.out.print(" = "+binary+"\n");
		return binary;
	}
    /**
     * The constructor of the class
     */
	public ImageUpload()
    {
    	super();
    	Output.clear();
    	NumberOfInputs=0;
    	NumberOfOutputs=0;
    	NumberOfPossibilities=0;
    }
    /**
     * Performs the operations required after pressing Submit button
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	String context = request.getServletContext().getRealPath("/");
    	System.out.println(context);
    	//Call the initiate function in the Backend class to recieve output
		Output = bk.Initiate(context);
		//Create directory if there isn't one
		File file = new File(context+UPLOAD_DIRECTORY);
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
		//File upload start
		if(ServletFileUpload.isMultipartContent(request)){
            try {
            	List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(new ServletRequestContext(request));
                String type = null;
                for(FileItem item : multiparts){
                    if(!item.isFormField()){
                        String name = new File(item.getName()).getName();
                        type = item.getContentType();
                        System.out.println("Original File Name "+name+" of type "+type);
                        item.write( new File(context+UPLOAD_DIRECTORY + File.separator + "circuit.jpg"));
                    }
                }
                
                //File uploaded successfully
                //File Type Validation
                if(type.matches("image/png")||type.matches("image/gif")||type.matches("image/jpeg"))
                {
                	PrintWriter out = response.getWriter();
                	response.setContentType("text/html");
                	out.println("<html> <body>");
                	out.println("<h1 style=\"text-align:center;\">Image and XML uploaded successfully</h1>");
                	out.println("<br><br>");
                	//Insert image file into Response
                	out.println("<h1 style=\"text-align:center;\">Image file</h1>");
                	out.println("<center> <img src='/team50/circuit.jpg' alt='Circuit Image' ></img></center>");
                	//Insert truth table into Response
                	out.println("<h1 style=\"text-align:center;\">Output as Truth Table</h1>");
                	out.print("<center><table border=\"1\">");
                	int NumberOfInputs = bk.pc.StorageObject.InputsCount;
                	int NumberOfOutputs = bk.pc.StorageObject.OutputsCount;
                	int NumberOfPossibilities = (int) Math.pow(2,NumberOfInputs);
                	int OutputCounter = 0;
                	for(int temp = -1; temp < NumberOfPossibilities; temp++)
                	{
                		ArrayList<Integer> inputs = new ArrayList<>();
                		inputs = ConvertToBinary(temp,NumberOfInputs);
                		out.println("<tr>");
                		for(int temp2=0;temp2<NumberOfInputs;temp2++)
                		{
                			if(temp==-1)
                			{
                				out.print("<th>Input "+(temp2+1)+"</th>");
                			}
                			else
                			{
                				out.println("<td>"+inputs.get(temp2)+"</td>");
                			}
                		}
                		//output.concat("Input = "+inputs+" Output = ");
                		for(int temp1 = 0; temp1 < NumberOfOutputs; temp1++)
                		{
                			if(temp==-1)
                			{
                				out.println("<th>Output "+(temp1+1)+"</th>");
                			}
                			else
                			{
                				out.println("<td>"+Output.get(OutputCounter)+"</th>");
                				OutputCounter++;
                			}
                			//output.concat(""+bk.Calculate("output", id, inputs)+" ");
                		}
                		out.println("</tr>");
                	}
                	out.print("</table></center>");
                	out.println("<center><a href=\"index.jsp\"><h1>Redo Everything</h1></a></center>");
                	//Insert output using form into Response
                	out.println("<h1 style=\"text-align:center;\">Output using form</h1>");
                	out.println("<center><form action='DisplayServlet' method='post'>");
                	out.println("<table>");
                	out.println("<tr>");
                	for(int temp4=1; temp4<=NumberOfInputs;temp4++)
                	{
                		out.println("<td>");
                		out.println("Input "+temp4);
                		out.println("<input type='checkbox' name='"+temp4+"' value='1'></input>");
                		out.println("<input type='hidden' name='"+temp4+"' value='0'></input>");
                		out.println("</td>");
                	}
                	out.println("</tr></table>");
                	out.println("<input type='submit'>");
                	out.println("</form></center>");
                	out.println(" </body></html>");
                	//Form response ends
                }
                else
                {
                	//If Type Validation fails
                	PrintWriter out = response.getWriter();
                	response.setContentType("text/html");
                	out.println("<h3>The file selected is not supported.</h3>");
                	out.println("<h3> Choose An Image File to Upload</h3>");
                	out.println("<form action='ImageUpload' method='post' enctype='multipart/form-data'>");
                	out.println("<input type='file' name='file' />");    
                	out.println("<input type='submit' value='upload' />");    
                	out.println("</form>");   
                }
                //request.setAttribute("message", "Image Uploaded Successfully"+"\n"+"<img src=\"C:\\uploads\\circuit.jpg\" alt=\"Mountain View\" style=\"width:304px;height:228px;\"> \n Outputs are "+Output);
            } catch (Exception ex) {	
            	request.setAttribute("message", "File Upload Failed due to " + ex);
            }          
        }	
    }
}