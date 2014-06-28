package com.xyz.ReduceSort;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapreduce.Mapper;

public class RSJoinMapper extends
		Mapper<LongWritable, Text, RSJoinCompositeKey, Text> {

	RSJoinCompositeKey ckwKey = new RSJoinCompositeKey();
	Text txtValue = new Text("");
	int intSrcIndex = 0;
	StringBuilder strMapValueBuilder = new StringBuilder("");
	List<Integer> lstRequiredAttribList = new ArrayList<Integer>();

	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		// {{
		// Get the source index; (employee = 1, salary = 2)
		// Added as configuration in driver
		FileSplit fsFileSplit = (FileSplit) context.getInputSplit();
		intSrcIndex = Integer.parseInt(context.getConfiguration().get(
				fsFileSplit.getPath().getName()));
		// }}

		// {{
		// Initialize the list of fields to emit as output based on
		// intSrcIndex (1=employee, 2=current salary, 3=historical salary)
		if (intSrcIndex == 1) // photo Id
		{

			lstRequiredAttribList.add(0); // PhotoId
		} else // placeId
		{
			lstRequiredAttribList.add(6); // Place Details

		}
		// }}

	}

	private String buildMapValue(String arrEntityAttributesList[]) {
		// This method returns csv list of values to emit based on data entity

		strMapValueBuilder.setLength(0);// Initialize

		// Build list of attributes to output based on source - employee/salary
		for (int i = 1; i < arrEntityAttributesList.length; i++) {
			// If the field is in the list of required output
			// append to stringbuilder
			if (lstRequiredAttribList.contains(i)) {
				strMapValueBuilder.append(arrEntityAttributesList[i]).append(
						",");
			}
		}
		if (strMapValueBuilder.length() > 0) {
			// Drop last comma
			strMapValueBuilder.setLength(strMapValueBuilder.length() - 1);
		}

		return strMapValueBuilder.toString();
	}

	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		if (value.toString().length() > 0) {
			String arrEntityAttributes[] = value.toString().split(" ");

			if (intSrcIndex == 1) // photo Id
			{
				ckwKey.setPlaceId(arrEntityAttributes[4].toString());
			} else if (intSrcIndex == 2) {
				ckwKey.setPlaceId(arrEntityAttributes[0].toString());
			}
			ckwKey.setNumsrc(intSrcIndex);
			txtValue.set(buildMapValue(arrEntityAttributes));

			context.write(ckwKey, txtValue);
		}

	}

}
