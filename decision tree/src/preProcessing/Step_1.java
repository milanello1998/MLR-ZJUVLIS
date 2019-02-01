package preProcessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class Step_1 {

	static final int SOURCE_ID = 0;
	static final int GC_CLASS_CD = 1;
	static final int SOURCE_TRADE_STATUS_CD = 2;
	static final int SOURCE_TRANS_CD = 3;
	static final int SSB_ASSET_ID = 4;
	static final int FUND_ID = 5;
	static final int CTPY_ID = 6;
	static final int CONTRACTUAL_SETTLE_DATE = 7;
	static final int TRADE_DATE = 8;
	static final int TRAN_REF_NUM = 9;
	static final int SSB_TRADE_ID = 10;
	static final int CONTRACTUAL_SHARE_QTY = 11;
	static final int UNIT_PRICE_AMT = 12;
	static final int GTM_TRADE_RECEIPT_DATE = 13;
	static final int GTM_INSTRUCT_TYPE_CD = 14;
	static final int GTM_MESSAGE_TYPE_CD = 15;
	static final int LOCAL_CCY_CD = 16;
	static final int BUY_TRANS_AMT = 17;
	static final int BUY_CCY_CD = 18;
	static final int SELL_TRANS_AMT = 19;
	static final int SELL_CCY_CD = 20;
	static final int ORIG_FACE_POS_QTY = 21;
	static final int TAXES_LOCAL_AMT = 22;
	static final int INTEREST_LOCAL_AMT = 23;
	static final int COMMISSION_LOCAL_AMT = 24;
	static final int FEES_LOCAL_AMT = 25;
	static final int OTHER_FEES_LOCAL_AMT = 26;
	static final int CONTRACTUAL_NET_LOCAL_AMT = 27;
	static final int LEG_QTY = 28;
	static final int LEG_SEQ = 29;
	static final int TOMS_LINK_UNIQUE_ID = 30;
	static final int TRAN_SUB_TYPE_CD = 31;
	static final int MATCH_CD = 32;
	static final int CATEGORY = 32;
	
	static final int LENGTH = 33;
	
	static final String FILE_IN = "src/preProcessing/trade_on_06_15.csv";
	static final String FILE_OUT = "src/preProcessing/trade_on_06_15_output_1.csv";
	

	static final HashMap<String,Integer> map_SSB = new HashMap<String,Integer>();
	static final HashMap<String,Integer> map_TRAN = new HashMap<String,Integer>();
	
	static final HashMap<String,String> instruction_GC_CLASS_CD = new HashMap<String,String>();
	static final HashMap<String,String> instruction_SOURCE_TRADE_STATUS_CD = new HashMap<String,String>();
	static final HashMap<String,String> accounting_SOURCE_TRADE_STATUS_CD = new HashMap<String,String>();
	static final HashMap<String,String> instruction_SOURCE_TRANS_CD = new HashMap<String,String>();
	static final HashMap<String,String> accounting_SOURCE_TRANS_CD = new HashMap<String,String>();
	static final HashMap<String,String> instruction_category_SOURCE_TRANS_CD = new HashMap<String,String>();
	static final HashMap<String,String> accounting_category_SOURCE_TRANS_CD = new HashMap<String,String>();
	
	static final String outputHead = "SOURCE_ID,GC_CLASS_CD,SOURCE_TRADE_STATUS_CD,SOURCE_TRANS_CD,SSB_ASSET_ID,FUND_ID,CTPY_ID,CONTRACTUAL_SETTLE_DATE,TRADE_DATE,TRAN_REF_NUM,SSB_TRADE_ID,CONTRACTUAL_SHARE_QTY,UNIT_PRICE_AMT,GTM_TRADE_RECEIPT_DATE,GTM_INSTRUCT_TYPE_CD,GTM_MESSAGE_TYPE_CD,LOCAL_CCY_CD,BUY_TRANS_AMT,BUY_CCY_CD,SELL_TRANS_AMT,SELL_CCY_CD,ORIG_FACE_POS_QTY,TAXES_LOCAL_AMT,INTEREST_LOCAL_AMT,COMMISSION_LOCAL_AMT,FEES_LOCAL_AMT,OTHER_FEES_LOCAL_AMT,CONTRACTUAL_NET_LOCAL_AMT,LEG_QTY,LEG_SEQ,TOMS_LINK_UNIQUE_ID,TRAN_SUB_TYPE_CD,MATCH_CD,SOURCE_ID_A,GC_CLASS_CD_A,SOURCE_TRADE_STATUS_CD_A,SOURCE_TRANS_CD_A,SSB_ASSET_ID_A,FUND_ID_A,CTPY_ID_A,CONTRACTUAL_SETTLE_DATE_A,TRADE_DATE_A,TRAN_REF_NUM_A,SSB_TRADE_ID_A,CONTRACTUAL_SHARE_QTY_A,UNIT_PRICE_AMT_A,GTM_TRADE_RECEIPT_DATE_A,GTM_INSTRUCT_TYPE_CD_A,GTM_MESSAGE_TYPE_CD_A,LOCAL_CCY_CD_A,BUY_TRANS_AMT_A,BUY_CCY_CD_A,SELL_TRANS_AMT_A,SELL_CCY_CD_A,ORIG_FACE_POS_QTY_A,TAXES_LOCAL_AMT_A,INTEREST_LOCAL_AMT_A,COMMISSION_LOCAL_AMT_A,FEES_LOCAL_AMT_A,OTHER_FEES_LOCAL_AMT_A,CONTRACTUAL_NET_LOCAL_AMT_A,LEG_QTY_A,LEG_SEQ_A,TOMS_LINK_UNIQUE_ID_A,TRAN_SUB_TYPE_CD_A,MATCH_CD_A,SOURCE_ID_1,GC_CLASS_CD_1,SOURCE_TRADE_STATUS_CD_1,SOURCE_TRANS_CD_1,SSB_ASSET_ID_1,FUND_ID_1,CTPY_ID_1,CONTRACTUAL_SETTLE_DATE_1,TRADE_DATE_1,TRAN_REF_NUM_1,SSB_TRADE_ID_1,CONTRACTUAL_SHARE_QTY_1,UNIT_PRICE_AMT_1,GTM_TRADE_RECEIPT_DATE_1,GTM_INSTRUCT_TYPE_CD_1,GTM_MESSAGE_TYPE_CD_1,LOCAL_CCY_CD_1,BUY_TRANS_AMT_1,BUY_CCY_CD_1,SELL_TRANS_AMT_1,SELL_CCY_CD_1,ORIG_FACE_POS_QTY_1,TAXES_LOCAL_AMT_1,INTEREST_LOCAL_AMT_1,COMMISSION_LOCAL_AMT_1,FEES_LOCAL_AMT_1,OTHER_FEES_LOCAL_AMT_1,CONTRACTUAL_NET_LOCAL_AMT_1,LEG_QTY_1,LEG_SEQ_1,TOMS_LINK_UNIQUE_ID_1,TRAN_SUB_TYPE_CD_1,CATEGORY_1";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new Step_1().step1();
	}
	
	private void step1(){
		File infile = new File(FILE_IN);
		File outfile = new File(FILE_OUT);
		BufferedReader read = null;
		BufferedWriter write = null;
		
		try{
			
			System.out.println("Started!");
			read = new BufferedReader(new FileReader(infile));
		    write = new BufferedWriter(new FileWriter(outfile));
		    write.write(outputHead);
		    write.newLine();
			
			ArrayList<String[]> instruction = new ArrayList<String[]>();
			ArrayList<String[]> accounting = new ArrayList<String[]>();
			
			//read input file into instruction and accounting list
			String tempString = null;
			read.readLine();    
			while((tempString = read.readLine()) != null){
				String[] s = tempString.split(",");
				if(isValid(s)){
					if(s[SOURCE_ID].equals("36")){
						instruction.add(nullHandling(s));
					}else if(s[SOURCE_ID].equals("16") || s[SOURCE_ID].equals("18")){
						accounting.add(nullHandling(s));
					}
				}
												
			}
			read.close();			
			System.out.println("Read File Successfully!");
			
			//make mapping for first 3 columns
			makeMappings();
			System.out.println("Make Mapping Successfully!");
			 			
			//make hash table for SSB_TRADE_ID and TRAN_REF_NUM
			//if there's only one item, then <item,index>
			//if there're duplicated items, then <item,-1>
			for(int i = 0; i < accounting.size(); i++){
				if(map_SSB.put(accounting.get(i)[SSB_TRADE_ID], i) != null){
				    map_SSB.put(accounting.get(i)[SSB_TRADE_ID], -1);
				}
				if(map_TRAN.put(accounting.get(i)[TRAN_REF_NUM], i) != null){
					map_TRAN.put(accounting.get(i)[TRAN_REF_NUM], -1);
				}
				
				
			}
			
			System.out.println("Make Hash Table Successfully!");
			
			//for each instruction record, find the associated accounting data
			//first look up by SSB_TRADE_ID, if not found, look up by TRAN_REF_NUM
			//if no associated accounting data is found, set -1 for all columns
			//convert data columns and compare columns
			 for(String[] s : instruction){
				 int index = 0;
				 String s_All = null;
				 String[] s_accounting = new String[LENGTH];  // accounting side
				 if(map_SSB.get(s[SSB_TRADE_ID]) != null && map_SSB.get(s[SSB_TRADE_ID]) != -1){
					 index = map_SSB.get(s[SSB_TRADE_ID]);
					 s_accounting = accounting.get(index);
				 }else if(map_TRAN.get(s[TRAN_REF_NUM]) != null && map_TRAN.get(s[TRAN_REF_NUM]) != -1){
					 index = map_TRAN.get(s[TRAN_REF_NUM]);
					 s_accounting = accounting.get(index);
				 }else{
					 Arrays.fill(s_accounting, "-1");
				 }
				 
				 //transform date
				 s[CONTRACTUAL_SETTLE_DATE] = transformDate(s[TRADE_DATE],s[CONTRACTUAL_SETTLE_DATE]);
				 s[GTM_TRADE_RECEIPT_DATE] = transformDate(s[TRADE_DATE],s[GTM_TRADE_RECEIPT_DATE]);
				 s_accounting[CONTRACTUAL_SETTLE_DATE] = transformDate(s_accounting[TRADE_DATE],s_accounting[CONTRACTUAL_SETTLE_DATE]);
				 s_accounting[GTM_TRADE_RECEIPT_DATE] = transformDate(s_accounting[TRADE_DATE],s_accounting[GTM_TRADE_RECEIPT_DATE]);
				 
				 //make compare columns(compare String[] s and String[] s_accounting)
				 String[] compare = makeCompare(s,s_accounting);    //compare columns
				 
				 s_All = String.join(",", String.join(",", s), String.join(",", s_accounting), String.join(",", compare));
				 write.write(s_All);
				 write.newLine();				 
			 }

//				System.out.println("Finished!");
			write.flush();
			write.close();
			System.out.println("Finished!");
			
		}catch(IOException e){
			e.getStackTrace();
		}
		
	}
	
	//change null to 0;
	public String[] nullHandling(String[] s){
		for(int i = 0; i < s.length; i++){
			if(s[i] == null || s[i].equals("null") || s[i].equals(""))
				s[i] = "0";
		}
		return s;
	}
	
	//transform date
	//return offset of trans_date and trade_date
	//if trade_date and trans_date equals to -1, indicates that no associated accounting date, return -1
	//if trade_date or trans_date is null, return -9999
	private String transformDate(String trade_date, String trans_date){
		if(trade_date == null || trans_date == null)
			return "-9999";
		
		if(trade_date.equals("0") || trans_date.equals("0"))
			return "-9999";
		
	    if(trade_date.equals("-1") || trans_date.equals("-1"))
			return "-1";
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    
	    try{
	    	Date d1 = sdf.parse(trade_date);
	    	Date d2 = sdf.parse(trans_date);
	    	int offset = (int)((d2.getTime() - d1.getTime())/(24*60*60*1000));
	    	return String.valueOf(offset);
	    }catch(ParseException e){
			e.getStackTrace();
		}
		
	    return null;
	}
	
	//check format
	private boolean isValid(String[] s){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try{
			if(!s[CONTRACTUAL_SETTLE_DATE].equals("null")) sdf.parse(s[CONTRACTUAL_SETTLE_DATE]);
			if(!s[TRADE_DATE].equals("null")) sdf.parse(s[TRADE_DATE]);
			if(!s[GTM_TRADE_RECEIPT_DATE].equals("null")) sdf.parse(s[GTM_TRADE_RECEIPT_DATE]);
			return true;
		}catch(ParseException e){
			return false;
		}
		
	}
	
	//make mapping for first 3 columns
	private void makeMappings(){
		BufferedReader read = null;
		String tempString = null;
		try{
			 read = new BufferedReader(new FileReader(new File("src/preProcessing/mapping/GC_CLASS_CD_instruction.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 instruction_GC_CLASS_CD.put(s[0], s[1]);
			 }
			 read.close();
			 
			 read = new BufferedReader(new FileReader(new File("src/preProcessing/mapping/SOURCE_TRADE_STATUS_CD_instruction.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 instruction_SOURCE_TRADE_STATUS_CD.put(s[0], s[1]);
			 }
			 read.close();
			 
			 read = new BufferedReader(new FileReader(new File("src/preProcessing/mapping/SOURCE_TRADE_STATUS_CD_accounting.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 accounting_SOURCE_TRADE_STATUS_CD.put(s[0], s[1]);
			 }
			 read.close();
			 
			 read = new BufferedReader(new FileReader(new File("src/preProcessing/mapping/SOURCE_TRANS_CD_instruction_map.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 instruction_SOURCE_TRANS_CD.put(s[0], s[1]);
			 }
			 read.close();
			 
			 read = new BufferedReader(new FileReader(new File("src/preProcessing/mapping/SOURCE_TRANS_CD_accounting_map.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 accounting_SOURCE_TRANS_CD.put(s[0], s[1]);
			 }
			 read.close();
			 
			 read = new BufferedReader(new FileReader(new File("src/preProcessing/mapping/SOURCE_TRANS_CD_instruction_category.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 instruction_category_SOURCE_TRANS_CD.put(s[0], s[1]);
			 }
			 read.close();
			 
			 read = new BufferedReader(new FileReader(new File("src/preProcessing/mapping/SOURCE_TRANS_CD_accounting_category.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 accounting_category_SOURCE_TRANS_CD.put(s[0], s[1]);
			 }
			 read.close();
			 
		}catch(IOException e){
			e.getStackTrace();
		}
	}
	
	//make compare columns(compare String[] s and String[] s_accounting)
	//all columns except MATCH_CD, add one column category_1
	//YES: 1; NO:2
	private String[] makeCompare(String[] ins, String[] acc){
		String[] compare = new String[LENGTH];
		if(acc[SOURCE_ID] == "-1"){
			Arrays.fill(compare, "-1");
			return compare;
		}
		String s1 = null;
		String s2 = null;
		compare[SOURCE_ID] = ins[SOURCE_ID].equals(acc[SOURCE_ID]) ? "1" : "2";
		s1 = instruction_GC_CLASS_CD.get(ins[GC_CLASS_CD]) == null ? ins[GC_CLASS_CD] : instruction_GC_CLASS_CD.get(ins[GC_CLASS_CD]);
		compare[GC_CLASS_CD] = s1.equals(acc[GC_CLASS_CD]) ? "1" : "2";
		s1 = instruction_SOURCE_TRADE_STATUS_CD.get(ins[SOURCE_TRADE_STATUS_CD]) == null ? ins[SOURCE_TRADE_STATUS_CD] : instruction_SOURCE_TRADE_STATUS_CD.get(ins[SOURCE_TRADE_STATUS_CD]);
		s2 = accounting_SOURCE_TRADE_STATUS_CD.get(acc[SOURCE_TRADE_STATUS_CD]) == null ? acc[SOURCE_TRADE_STATUS_CD] : accounting_SOURCE_TRADE_STATUS_CD.get(acc[SOURCE_TRADE_STATUS_CD]);
		compare[SOURCE_TRADE_STATUS_CD] = s1.equals(s2) ? "1" : "2";
		s1 = instruction_SOURCE_TRANS_CD.get(ins[SOURCE_TRANS_CD]) == null ? ins[SOURCE_TRANS_CD] : instruction_SOURCE_TRANS_CD.get(ins[SOURCE_TRANS_CD]);
		s2 = accounting_SOURCE_TRANS_CD.get(acc[SOURCE_TRANS_CD]) == null ? acc[SOURCE_TRANS_CD] : accounting_SOURCE_TRANS_CD.get(acc[SOURCE_TRANS_CD]);
		compare[SOURCE_TRANS_CD] = s1.equals(s2) ? "1" : "2";
		compare[SSB_ASSET_ID] = ins[SSB_ASSET_ID].equals(acc[SSB_ASSET_ID]) ? "1" : "2";
		compare[FUND_ID] = ins[FUND_ID].equals(acc[FUND_ID]) ? "1" : "2";
		compare[CTPY_ID] = ins[CTPY_ID].equals(acc[CTPY_ID]) ? "1" : "2";
		compare[CONTRACTUAL_SETTLE_DATE] = ins[CONTRACTUAL_SETTLE_DATE].equals(acc[CONTRACTUAL_SETTLE_DATE]) ? "1" : "2";
		compare[TRADE_DATE] = ins[TRADE_DATE].equals(acc[TRADE_DATE]) ? "1" : "2";
//		compare[TRAN_REF_NUM] = ins[TRAN_REF_NUM].equals(acc[TRAN_REF_NUM]) ? "1" : "2";
		compare[SSB_TRADE_ID] = ins[SSB_TRADE_ID].equals(acc[SSB_TRADE_ID]) ? "1" : "2";
		compare[CONTRACTUAL_SHARE_QTY] = Math.abs(Double.parseDouble(ins[CONTRACTUAL_SHARE_QTY]) - Double.parseDouble(acc[CONTRACTUAL_SHARE_QTY])) < 0.1 ? "1" : "2";
		compare[UNIT_PRICE_AMT] = Math.abs(Double.parseDouble(ins[UNIT_PRICE_AMT]) - Double.parseDouble(acc[UNIT_PRICE_AMT])) < 0.01 ? "1" : "2";
		compare[GTM_TRADE_RECEIPT_DATE] = ins[GTM_TRADE_RECEIPT_DATE].equals(acc[GTM_TRADE_RECEIPT_DATE]) ? "1" : "2";
		compare[GTM_INSTRUCT_TYPE_CD] = ins[GTM_INSTRUCT_TYPE_CD].equals(acc[GTM_INSTRUCT_TYPE_CD]) ? "1" : "2";
		compare[GTM_MESSAGE_TYPE_CD] = ins[GTM_MESSAGE_TYPE_CD].equals(acc[GTM_MESSAGE_TYPE_CD]) ? "1" : "2";
		compare[LOCAL_CCY_CD] = ins[LOCAL_CCY_CD].equals(acc[LOCAL_CCY_CD]) ? "1" : "2";
		compare[BUY_TRANS_AMT] = Math.abs(Double.parseDouble(ins[BUY_TRANS_AMT]) - Double.parseDouble(acc[BUY_TRANS_AMT])) < 1 ? "1" : "2";
		compare[BUY_CCY_CD] = ins[BUY_CCY_CD].equals(acc[BUY_CCY_CD]) ? "1" : "2";
		compare[SELL_TRANS_AMT] = Math.abs(Double.parseDouble(ins[SELL_TRANS_AMT]) - Double.parseDouble(acc[SELL_TRANS_AMT])) < 1 ? "1" : "2";
		compare[SELL_CCY_CD] = ins[SELL_CCY_CD].equals(acc[SELL_CCY_CD]) ? "1" : "2";
		compare[ORIG_FACE_POS_QTY] = Math.abs(Double.parseDouble(ins[ORIG_FACE_POS_QTY]) - Double.parseDouble(acc[ORIG_FACE_POS_QTY])) < 0.1 ? "1" : "2";
		compare[TAXES_LOCAL_AMT] = Math.abs(Double.parseDouble(ins[TAXES_LOCAL_AMT]) - Double.parseDouble(acc[TAXES_LOCAL_AMT])) < 1 ? "1" : "2";
		compare[INTEREST_LOCAL_AMT] = Math.abs(Double.parseDouble(ins[INTEREST_LOCAL_AMT]) - Double.parseDouble(acc[INTEREST_LOCAL_AMT])) < 1 ? "1" : "2";
		compare[COMMISSION_LOCAL_AMT] = Math.abs(Double.parseDouble(ins[COMMISSION_LOCAL_AMT]) - Double.parseDouble(acc[COMMISSION_LOCAL_AMT])) < 1 ? "1" : "2";
		compare[FEES_LOCAL_AMT] = Math.abs(Double.parseDouble(ins[FEES_LOCAL_AMT]) - Double.parseDouble(acc[FEES_LOCAL_AMT])) < 1 ? "1" : "2";
		compare[OTHER_FEES_LOCAL_AMT] = Math.abs(Double.parseDouble(ins[OTHER_FEES_LOCAL_AMT]) - Double.parseDouble(acc[OTHER_FEES_LOCAL_AMT])) < 1 ? "1" : "2";
		compare[CONTRACTUAL_NET_LOCAL_AMT] = Math.abs(Double.parseDouble(ins[CONTRACTUAL_NET_LOCAL_AMT]) - Double.parseDouble(acc[CONTRACTUAL_NET_LOCAL_AMT])) < 1 ? "1" : "2";
		compare[LEG_QTY] = ins[LEG_QTY].equals(acc[LEG_QTY]) ? "1" : "2";
		compare[LEG_SEQ] = ins[LEG_SEQ].equals(acc[LEG_SEQ]) ? "1" : "2";
		compare[TOMS_LINK_UNIQUE_ID] = ins[TOMS_LINK_UNIQUE_ID].equals(acc[TOMS_LINK_UNIQUE_ID]) ? "1" : "2";
		compare[TRAN_SUB_TYPE_CD] = ins[TRAN_SUB_TYPE_CD].equals(acc[TRAN_SUB_TYPE_CD]) ? "1" : "2";
		s1 = instruction_category_SOURCE_TRANS_CD.get(ins[SOURCE_TRANS_CD]) == null ? ins[SOURCE_TRANS_CD] : instruction_category_SOURCE_TRANS_CD.get(ins[SOURCE_TRANS_CD]);
		s2 = accounting_category_SOURCE_TRANS_CD.get(acc[SOURCE_TRANS_CD]) == null ? acc[SOURCE_TRANS_CD] : accounting_category_SOURCE_TRANS_CD.get(acc[SOURCE_TRANS_CD]);
		compare[CATEGORY] = s1.equals(s2) ? "1" : "2";	
		
		if(ins[TRAN_REF_NUM].equals(acc[TRAN_REF_NUM])){
			compare[TRAN_REF_NUM] = "1";
		}else if(ins[TRAN_REF_NUM].length() >= 15 && acc[TRAN_REF_NUM].length() >= 15 && String.valueOf(ins[TRAN_REF_NUM].toCharArray(), 0, 15).equals(String.valueOf(acc[TRAN_REF_NUM].toCharArray(), 0, 15))){
			compare[TRAN_REF_NUM] = "2";
		}else if(ins[TRAN_REF_NUM].length() >= 7 && acc[TRAN_REF_NUM].length() >= 7 && String.valueOf(ins[TRAN_REF_NUM].toCharArray(), 0, 7).equals(String.valueOf(acc[TRAN_REF_NUM].toCharArray(), 0, 7))){
			compare[TRAN_REF_NUM] = "3";
		}else{
			compare[TRAN_REF_NUM] = "4";
		}
		return compare;
	}

}
