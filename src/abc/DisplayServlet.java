package abc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DisplayServlet
 * Implements the alternate version of the Results page on the WebApp
 */
@WebServlet("/DisplayServlet")
public class DisplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * Gets the decimal value from given binary value
     * @param Input : A list containing digits of binary number
     * @return The value in decimal
     */
    public Integer getPossibilityNumber(ArrayList Input)
    {
    	Collections.reverse(Input);
    	int value = 0;
    	for(int temp=0;temp<Input.size();temp++)
    	{
    		int intermediate = Integer.parseInt((String) Input.get(temp));
    		System.out.println(intermediate);
    		value +=  intermediate * (int)Math.pow(2, temp);
    	}
		return value;
    	
    }
	/**
	 * Performs the operations required after pressing Submit button
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Backend bk = new Backend();
		String context = request.getServletContext().getRealPath("/");
		ArrayList<Integer> Output = new ArrayList<>(); 
		Output.clear();
		Output = bk.Initiate(context);
		System.out.println("Output recieved by DisplayServlet "+Output);
		ArrayList<String> Input = new ArrayList<>();
		Input.clear();
		int NumberOfInputs = bk.pc.StorageObject.InputsCount;
		int NumberOfOutputs = bk.pc.StorageObject.OutputsCount;
		int NumberOfPossibilities = (int) Math.pow(2,NumberOfInputs);
		System.out.println("Reached marker");
		for(int temp=1;temp<=NumberOfInputs;temp++)
		{
			
			String par = Integer.toString(temp);
			System.out.println("Parameter "+par+"Value"+request.getParameter(par));
			Input.add(request.getParameter(par));
		}
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.println("<h2 style=\"text-align:center;\">Image file</h2>");
        out.println("<center> <img src='/team50/circuit.jpg' alt='Circuit Image' ></img></center>");
		out.println("<center><h2>Inputs recieved are </h2>");
		int PossibilityNumber = getPossibilityNumber(Input);
		out.println("<h4>");
		Collections.reverse(Input);
		for(int temp2 = 0;temp2<Input.size();temp2++)
		{
			out.print("Input "+(temp2+1)+" = " +Input.get(temp2)+"\t");
		}
		out.println("(Decimal "+PossibilityNumber+")</h4>");
		int index = (NumberOfOutputs*(PossibilityNumber));
		out.println("<h2>The outputs are as below:</h2>");
		
		int OutputCount = 1;
		out.println("<h4>");
		for(int temp2 = index;temp2<index+NumberOfOutputs;temp2++)
		{
			out.println("Output "+OutputCount+" = "+Output.get(temp2)+"\t");
			OutputCount++;
		}
		out.println("</h4>");
		
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
		
   		out.println("<h1 style=\"text-align:center;\">Output as Truth Table</h1>");
		   out.print("<center><table border=\"1\">");
		   NumberOfInputs = bk.pc.StorageObject.InputsCount;
		   NumberOfOutputs = bk.pc.StorageObject.OutputsCount;
		   NumberOfPossibilities = (int) Math.pow(2,NumberOfInputs);
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
				
	}
	/**
	 * Converts decimal to binary according to number of digits
	 * @param n: the decimal number
	 * @param NumberOfInputs: Number of bits required
	 * @return: Binary value in the given number of bits
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

}
