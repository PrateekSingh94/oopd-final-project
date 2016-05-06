package abc;

import java.util.ArrayList;
import java.util.Collections;
/**
 * Acts as a backend for the entire class
 * Calculates the output for all input combinations
 * Activates the parser
 */
public class Backend {
	/**
	 * Calls the parser class
	 */
	ParserComplete pc = new ParserComplete();
	/**
	 * Initiates the Parsing process and the Output Calculation process
	 * @param context : the path of the working directory
	 * @return : The output for all the input possibilities in a list
	 */
	public ArrayList<Integer> Initiate(String context)
	{
		//Start parsing the XML file
		pc.parsing(context);
		System.out.println("Parsing done");
		//Get the number of outputs and number of inputs from the Storage Class
		int NumberOfInputs = pc.StorageObject.InputsCount;
		int NumberOfOutputs = pc.StorageObject.OutputsCount;
		//System.out.println(NumberOfInputs);
		ArrayList<Integer> variable = new ArrayList<Integer>();
		ArrayList<Integer> Outputs = new ArrayList<Integer>();
		//Just a measure to hopefully avoid a problem
		variable.clear();
		Outputs.clear();
		//Calculate the number of possibilities
		int NumberOfPossibilities = (int) Math.pow(2,NumberOfInputs);
		System.out.println("Number of possibilities = "+NumberOfPossibilities);
		//Starts output calculation
		for(int temp = 0; temp < NumberOfPossibilities; temp++)
		{
			System.out.println(NumberOfInputs + "\t" + NumberOfOutputs);
			ArrayList<Integer> inputs = new ArrayList<>();
			inputs = ConvertToBinary(temp,NumberOfInputs);
			for(int temp1 = 0; temp1 < NumberOfOutputs; temp1++)
			{
				String id = Integer.toString(temp1+1);
				System.out.println("Output id = "+(id));
				Outputs.add(Calculate("output", id, inputs));
			}
			inputs.clear();
		}
		System.out.println(Outputs);
		return Outputs;
	}
	/**
	 * Converts given decimal to binary with the required number of digits
	 * @param n : The decimal number
	 * @param NumberOfInputs : Number of digits in binary required(ex- 2 in 4 digits = 0010)
	 * @return Returns the binary as required.
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
	 * Calculates the output of the circuit for a given set of inputs
	 * @param type => object type :output, gate, input
	 * @param id : Object id
	 * @param inputs : List containing value for inputs
	 * @return Value at the output
	 */
	public int Calculate(String type, String id, ArrayList inputs)
	{
		if(type.matches("output"))
		{	
			System.out.println("Output "+id+" calculating");
			String OutputSourceType = (String) pc.StorageObject.OutputSourceTypeMap.get(id);
			System.out.println("Display 1 "+OutputSourceType);
			String OutputSourceId = (String) pc.StorageObject.OutputSourceIdMap.get(id);
			System.out.println("Display 2 "+OutputSourceId);
			int source1 = Calculate(OutputSourceType, OutputSourceId, inputs);
			System.out.println("Output "+id+" done");
			return source1;
		}
		if(type.matches("gate"))
		{
			System.out.println("Gate "+id+" calculating");
			String GateType = (String) pc.StorageObject.GateTypeMap.get(id);
			if(GateType.matches("NOT"))
			{
				String GateSourceType = (String) pc.StorageObject.GateSourceTypeMap.get(id);
				String GateSourceId = (String) pc.StorageObject.GateSourceIdMap.get(id);
				int source1 = Calculate(GateSourceType, GateSourceId, inputs);
				System.out.println("Gate "+id+" done");
				return TruthTable(source1);
			}
			else
			{
				String GateSourceType = (String) pc.StorageObject.GateSourceTypeMap.get(id);
				String GateSourceId = (String) pc.StorageObject.GateSourceIdMap.get(id);
				String OtherGateSourceType = (String) pc.StorageObject.GateOtherSourceTypeMap.get(id);
				String OtherGateIdType = (String) pc.StorageObject.GateOtherSourceIdMap.get(id);
				int source1 = Calculate(OtherGateSourceType, OtherGateIdType, inputs);
				int source2 = Calculate(GateSourceType, GateSourceId, inputs);
				System.out.println("Gate "+id+" done");
				return TruthTable(GateType, source1, source2);
			}
		}
		if(type.matches("input"))
		{
			System.out.println("Input "+id+" calculating");
			System.out.println("Input "+id+" done");
			return (int) inputs.get(Integer.parseInt(id)-1);
		}
		return -1;
	}
	/**
	 * Truth table function for AND, OR, NOR, NAND, XOR, XNOR gates
	 * @param type : Type of gate
	 * @param Input1 : Source 1 for the gate
	 * @param Input2 : Source 2 for the gate
	 * @return : if valid, result of gate operation(0/1), else -1
	 */
	public int TruthTable(String type, int Input1, int Input2)
	{
		if(type.matches("AND"))
		{
			if(Input1==0 && Input2==0) return 0;
			if(Input1==0 && Input2==1) return 0;
			if(Input1==1 && Input2==0) return 0;
			if(Input1==1 && Input2==1) return 1;
		}
		if(type.matches("OR"))
		{
			if(Input1==0 && Input2==0) return 0;
			if(Input1==0 && Input2==1) return 1;
			if(Input1==1 && Input2==0) return 1;
			if(Input1==1 && Input2==1) return 1;
		}
		if(type.matches("NAND"))
		{
			if(Input1==0 && Input2==0) return 1;
			if(Input1==0 && Input2==1) return 1;
			if(Input1==1 && Input2==0) return 1;
			if(Input1==1 && Input2==1) return 0;
		}
		if(type.matches("NOR"))
		{
			if(Input1==0 && Input2==0) return 1;
			if(Input1==0 && Input2==1) return 0;
			if(Input1==1 && Input2==0) return 0;
			if(Input1==1 && Input2==1) return 0;
		}
		if(type.matches("XOR"))
		{
			if(Input1==0 && Input2==0) return 0;
			if(Input1==0 && Input2==1) return 1;
			if(Input1==1 && Input2==0) return 1;
			if(Input1==1 && Input2==1) return 0;
		}
		if(type.matches("XNOR"))
		{
			if(Input1==0 && Input2==0) return 1;
			if(Input1==0 && Input2==1) return 0;
			if(Input1==1 && Input2==0) return 0;
			if(Input1==1 && Input2==1) return 1;
		}
		return -1;
	}
	/**
	 * Truth Table function for NOT gate
	 * @param Input1
	 * @return 0/1 if valid, else -1
	 */
	public int TruthTable(int Input1)
	{
		if(Input1==0) return 1;
		if(Input1==1) return 0;
		return -1;
	}
}