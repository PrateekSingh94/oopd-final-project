package abc;
import java.util.HashMap;
/**
 * Stores data contained in XML file into Data Structures
 */
public class StorageClass {
	HashMap OutputSourceTypeMap = new HashMap<String,String>();
	HashMap OutputSourceIdMap = new HashMap<String,String>();
	HashMap GateSourceTypeMap = new HashMap<String,String>();
	HashMap GateSourceIdMap = new HashMap<String,String>();
	HashMap GateOtherSourceTypeMap = new HashMap<String,String>();
	HashMap GateOtherSourceIdMap = new HashMap<String,String>();
	HashMap GateTypeMap = new HashMap<String,String>();
	int InputsCount = 0;
	int GatesCount = 0;
	int OutputsCount = 0;
	public StorageClass()
	{
		OutputSourceIdMap.clear();
		OutputSourceTypeMap.clear();
		GateOtherSourceIdMap.clear();
		GateOtherSourceTypeMap.clear();
		GateSourceIdMap.clear();
		GateSourceTypeMap.clear();
		GateTypeMap.clear();
		InputsCount = 0;
		GatesCount = 0;
		OutputsCount = 0;
	}
	/**
	 * Clears the contents of the data structures
	 */
	public void flush()
	{
		OutputSourceIdMap.clear();
		OutputSourceTypeMap.clear();
		GateOtherSourceIdMap.clear();
		GateOtherSourceTypeMap.clear();
		GateSourceIdMap.clear();
		GateSourceTypeMap.clear();
		GateTypeMap.clear();
		InputsCount = 0;
		GatesCount = 0;
		OutputsCount = 0;
	}
	/**
	 * Counts the number of inputs
	 * @param id: ID of the input
	 */
	public void setInputs(String id)
	{
		InputsCount++;
		System.out.println(InputsCount);
	}
	/**
	 * Counts the number of gates 
	 * @param id : Id of the gate
	 */
	public void setGates(String id)
	{
		GatesCount++;
		System.out.println(GatesCount);
	}
	/**
	 * Sets the data structures for the Outputs
	 * @param id: Id of the output
	 * @param SourceType: Source Gate or Input for the Output
	 * @param SourceId: Id of the source
	 */
	public void setOutputs(String id, String SourceType, String SourceId)
	{
		OutputsCount++;
		OutputSourceTypeMap.put(id, SourceType);
		OutputSourceIdMap.put(id, SourceId);
		
		System.out.println(OutputSourceTypeMap);
		System.out.println(OutputSourceIdMap);
	}
	/**
	 * Sets data structures for connections of gates (AND, OR, NAND, NOR, XOR, XNOR)
	 * @param id: Id of the gate
	 * @param type: Type of the gate
	 * @param SourceType: Source 1 of the gate
	 * @param SourceId: Id of Source 1
	 * @param OtherSourceType: Source 2 of the gate
	 * @param OtherSourceId: Id of source 2
	 */
	public void setConnections(String id, String type, String SourceType, String SourceId, String OtherSourceType, String OtherSourceId)
	{
		GateSourceTypeMap.put(id, SourceType);
		GateSourceIdMap.put(id, SourceId);
		GateOtherSourceTypeMap.put(id, OtherSourceType);
		GateOtherSourceIdMap.put(id, OtherSourceId);
		GateTypeMap.put(id, type);
		System.out.println(GateSourceTypeMap);
		System.out.println(GateSourceIdMap);
		System.out.println(GateOtherSourceTypeMap);
		System.out.println(GateOtherSourceIdMap);
		System.out.println(GateTypeMap);
	}
	/**
	 * Sets data structures for connections of NOT gate only
	 * @param id: Id of the gate
	 * @param type: Type of the gate
	 * @param SourceType: Source of the gate
	 * @param SourceId: Id of Source
	 */
	public void setConnections(String id, String type, String SourceType, String SourceId)
	{
		GateSourceTypeMap.put(id, SourceType);
		GateSourceIdMap.put(id, SourceId);
		GateTypeMap.put(id, type);
		System.out.println(GateSourceTypeMap);
		System.out.println(GateSourceIdMap);
		System.out.println(GateTypeMap);
	}
	/**
	 * @return the inputsCount
	 */
	public int getInputsCount() {
		return InputsCount;
	}
	/**
	 * @param inputsCount the inputsCount to set
	 */
	public void setInputsCount(int inputsCount) {
		InputsCount = inputsCount;
	}
	/**
	 * @return the gatesCount
	 */
	public int getGatesCount() {
		return GatesCount;
	}
	/**
	 * @param gatesCount the gatesCount to set
	 */
	public void setGatesCount(int gatesCount) {
		GatesCount = gatesCount;
	}
	/**
	 * @return the outputSourceTypeMap
	 */
	public HashMap getOutputSourceTypeMap() {
		return OutputSourceTypeMap;
	}
	/**
	 * @param outputSourceTypeMap the outputSourceTypeMap to set
	 */
	public void setOutputSourceTypeMap(HashMap outputSourceTypeMap) {
		OutputSourceTypeMap = outputSourceTypeMap;
	}
	/**
	 * @return the outputSourceIdMap
	 */
	public HashMap getOutputSourceIdMap() {
		return OutputSourceIdMap;
	}
	/**
	 * @param outputSourceIdMap the outputSourceIdMap to set
	 */
	public void setOutputSourceIdMap(HashMap outputSourceIdMap) {
		OutputSourceIdMap = outputSourceIdMap;
	}
	/**
	 * @return the gateSourceTypeMap
	 */
	public HashMap getGateSourceTypeMap() {
		return GateSourceTypeMap;
	}
	/**
	 * @param gateSourceTypeMap the gateSourceTypeMap to set
	 */
	public void setGateSourceTypeMap(HashMap gateSourceTypeMap) {
		GateSourceTypeMap = gateSourceTypeMap;
	}
	/**
	 * @return the gateSourceIdMap
	 */
	public HashMap getGateSourceIdMap() {
		return GateSourceIdMap;
	}
	/**
	 * @param gateSourceIdMap the gateSourceIdMap to set
	 */
	public void setGateSourceIdMap(HashMap gateSourceIdMap) {
		GateSourceIdMap = gateSourceIdMap;
	}
	/**
	 * @return the gateOtherSourceTypeMap
	 */
	public HashMap getGateOtherSourceTypeMap() {
		return GateOtherSourceTypeMap;
	}
	/**
	 * @param gateOtherSourceTypeMap the gateOtherSourceTypeMap to set
	 */
	public void setGateOtherSourceTypeMap(HashMap gateOtherSourceTypeMap) {
		GateOtherSourceTypeMap = gateOtherSourceTypeMap;
	}
	/**
	 * @return the gateOtherSourceIdMap
	 */
	public HashMap getGateOtherSourceIdMap() {
		return GateOtherSourceIdMap;
	}
	/**
	 * @param gateOtherSourceIdMap the gateOtherSourceIdMap to set
	 */
	public void setGateOtherSourceIdMap(HashMap gateOtherSourceIdMap) {
		GateOtherSourceIdMap = gateOtherSourceIdMap;
	}
	/**
	 * @return the gateTypeMap
	 */
	public HashMap getGateTypeMap() {
		return GateTypeMap;
	}
	/**
	 * @param gateTypeMap the gateTypeMap to set
	 */
	public void setGateTypeMap(HashMap gateTypeMap) {
		GateTypeMap = gateTypeMap;
	}
}
