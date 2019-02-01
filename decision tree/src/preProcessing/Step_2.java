package preProcessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Step_2 {
	
	static final int GC_CLASS_CD = 1;
	static final int SOURCE_TRADE_STATUS_CD = 2;
	static final int SOURCE_TRANS_CD = 3;
	static final int GTM_INSTRUCT_TYPE_CD = 14;
	static final int GTM_MESSAGE_TYPE_CD = 15;
	static final int LOCAL_CCY_CD = 16;
	static final int BUY_CCY_CD = 18;
	static final int SELL_CCY_CD = 20;
	static final int LEG_QTY = 28;
	static final int LEG_SEQ = 29;
	static final int TRAN_SUB_TYPE_CD = 31;
	static final int GC_CLASS_CD_A = 34;
	static final int SOURCE_TRADE_STATUS_CD_A = 35;
	static final int SOURCE_TRANS_CD_A = 36;
	static final int GTM_INSTRUCT_TYPE_CD_A = 47;
	static final int GTM_MESSAGE_TYPE_CD_A = 48;
	static final int LOCAL_CCY_CD_A = 49;
	static final int BUY_CCY_CD_A = 51;
	static final int SELL_CCY_CD_A = 53;
	static final int LEG_QTY_A = 61;
	static final int LEG_SEQ_A = 62;
	static final int TRAN_SUB_TYPE_CD_A = 64;
	static final int LENGTH = 99;
	
	static final String FILE_IN = "src/preProcessing/trade_on_06_15_output_1.csv";
	static final String FILE_OUT = "src/preProcessing/trade_on_06_15_output_2.csv";
	
	static final HashMap<String,String> map_GC_CLASS_CD = new HashMap<String,String>();
	static final HashMap<String,String> map_SOURCE_TRADE_STATUS_CD = new HashMap<String,String>();
	static final HashMap<String,String> map_SOURCE_TRANS_CD = new HashMap<String,String>();
	static final HashMap<String,String> map_GTM_INSTRUCT_TYPE_CD = new HashMap<String,String>();
	static final HashMap<String,String> map_GTM_MESSAGE_TYPE_CD = new HashMap<String,String>();
	static final HashMap<String,String> map_LOCAL_CCY_CD = new HashMap<String,String>();
	static final HashMap<String,String> map_BUY_CCY_CD = new HashMap<String,String>();
	static final HashMap<String,String> map_SELL_CCY_CD = new HashMap<String,String>();
	static final HashMap<String,String> map_TRAN_SUB_TYPE_CD = new HashMap<String,String>();
	
	static final String outputHead = "SOURCE_ID,GC_CLASS_CD,SOURCE_TRADE_STATUS_CD,SOURCE_TRANS_CD,SSB_ASSET_ID,FUND_ID,CTPY_ID,CONTRACTUAL_SETTLE_DATE,TRADE_DATE,TRAN_REF_NUM,SSB_TRADE_ID,CONTRACTUAL_SHARE_QTY,UNIT_PRICE_AMT,GTM_TRADE_RECEIPT_DATE,GTM_INSTRUCT_TYPE_CD,GTM_MESSAGE_TYPE_CD,LOCAL_CCY_CD,BUY_TRANS_AMT,BUY_CCY_CD,SELL_TRANS_AMT,SELL_CCY_CD,ORIG_FACE_POS_QTY,TAXES_LOCAL_AMT,INTEREST_LOCAL_AMT,COMMISSION_LOCAL_AMT,FEES_LOCAL_AMT,OTHER_FEES_LOCAL_AMT,CONTRACTUAL_NET_LOCAL_AMT,LEG_QTY,LEG_SEQ,TOMS_LINK_UNIQUE_ID,TRAN_SUB_TYPE_CD,MATCH_CD,SOURCE_ID_A,GC_CLASS_CD_A,SOURCE_TRADE_STATUS_CD_A,SOURCE_TRANS_CD_A,SSB_ASSET_ID_A,FUND_ID_A,CTPY_ID_A,CONTRACTUAL_SETTLE_DATE_A,TRADE_DATE_A,TRAN_REF_NUM_A,SSB_TRADE_ID_A,CONTRACTUAL_SHARE_QTY_A,UNIT_PRICE_AMT_A,GTM_TRADE_RECEIPT_DATE_A,GTM_INSTRUCT_TYPE_CD_A,GTM_MESSAGE_TYPE_CD_A,LOCAL_CCY_CD_A,BUY_TRANS_AMT_A,BUY_CCY_CD_A,SELL_TRANS_AMT_A,SELL_CCY_CD_A,ORIG_FACE_POS_QTY_A,TAXES_LOCAL_AMT_A,INTEREST_LOCAL_AMT_A,COMMISSION_LOCAL_AMT_A,FEES_LOCAL_AMT_A,OTHER_FEES_LOCAL_AMT_A,CONTRACTUAL_NET_LOCAL_AMT_A,LEG_QTY_A,LEG_SEQ_A,TOMS_LINK_UNIQUE_ID_A,TRAN_SUB_TYPE_CD_A,MATCH_CD_A,SOURCE_ID_1,GC_CLASS_CD_1,SOURCE_TRADE_STATUS_CD_1,SOURCE_TRANS_CD_1,SSB_ASSET_ID_1,FUND_ID_1,CTPY_ID_1,CONTRACTUAL_SETTLE_DATE_1,TRADE_DATE_1,TRAN_REF_NUM_1,SSB_TRADE_ID_1,CONTRACTUAL_SHARE_QTY_1,UNIT_PRICE_AMT_1,GTM_TRADE_RECEIPT_DATE_1,GTM_INSTRUCT_TYPE_CD_1,GTM_MESSAGE_TYPE_CD_1,LOCAL_CCY_CD_1,BUY_TRANS_AMT_1,BUY_CCY_CD_1,SELL_TRANS_AMT_1,SELL_CCY_CD_1,ORIG_FACE_POS_QTY_1,TAXES_LOCAL_AMT_1,INTEREST_LOCAL_AMT_1,COMMISSION_LOCAL_AMT_1,FEES_LOCAL_AMT_1,OTHER_FEES_LOCAL_AMT_1,CONTRACTUAL_NET_LOCAL_AMT_1,LEG_QTY_1,LEG_SEQ_1,TOMS_LINK_UNIQUE_ID_1,TRAN_SUB_TYPE_CD_1,CATEGORY_1";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new Step_2().step2();
	}
	
	private void step2(){
		File infile = new File(FILE_IN);
		File outfile = new File(FILE_OUT);
		BufferedReader read = null;
		BufferedWriter write = null;
		
		try{
			System.out.println("Started!");
			
			//read mapping file and make mapping for categorical columns
		    makeMappings();
//		    System.out.println("Make Mapping Successfully!");  
		    
			read = new BufferedReader(new FileReader(infile));
		    write = new BufferedWriter(new FileWriter(outfile));
		    write.write(outputHead);
		    write.newLine();
		    
		    String tempString = null;
		    read.readLine();
	    
		    //read input file and convert categorical columns to number
		    while((tempString = read.readLine()) != null){
		    	String[] s = tempString.split(",");
		    	s[GC_CLASS_CD] = map_GC_CLASS_CD.get(s[GC_CLASS_CD]);
		    	s[SOURCE_TRADE_STATUS_CD] = map_SOURCE_TRADE_STATUS_CD.get(s[SOURCE_TRADE_STATUS_CD]);
		    	s[SOURCE_TRANS_CD] = map_SOURCE_TRANS_CD.get(s[SOURCE_TRANS_CD]);
		    	s[GTM_INSTRUCT_TYPE_CD] = map_GTM_INSTRUCT_TYPE_CD.get(s[GTM_INSTRUCT_TYPE_CD]);
		    	s[GTM_MESSAGE_TYPE_CD] = map_GTM_MESSAGE_TYPE_CD.get(s[GTM_MESSAGE_TYPE_CD]);
		    	s[LOCAL_CCY_CD] = map_LOCAL_CCY_CD.get(s[LOCAL_CCY_CD]);
		    	s[BUY_CCY_CD] = map_BUY_CCY_CD.get(s[BUY_CCY_CD]);
		    	s[SELL_CCY_CD] = map_SELL_CCY_CD.get(s[SELL_CCY_CD]);
		    	s[TRAN_SUB_TYPE_CD] = map_TRAN_SUB_TYPE_CD.get(s[TRAN_SUB_TYPE_CD]);
		    	
		    	s[GC_CLASS_CD_A] = map_GC_CLASS_CD.get(s[GC_CLASS_CD_A]);
		    	s[SOURCE_TRADE_STATUS_CD_A] = map_SOURCE_TRADE_STATUS_CD.get(s[SOURCE_TRADE_STATUS_CD_A]);
		    	s[SOURCE_TRANS_CD_A] = map_SOURCE_TRANS_CD.get(s[SOURCE_TRANS_CD_A]);
		    	s[GTM_INSTRUCT_TYPE_CD_A] = map_GTM_INSTRUCT_TYPE_CD.get(s[GTM_INSTRUCT_TYPE_CD_A]);
		    	s[GTM_MESSAGE_TYPE_CD_A] = map_GTM_MESSAGE_TYPE_CD.get(s[GTM_MESSAGE_TYPE_CD_A]);
		    	s[LOCAL_CCY_CD_A] = map_LOCAL_CCY_CD.get(s[LOCAL_CCY_CD_A]);
		    	s[BUY_CCY_CD_A] = map_BUY_CCY_CD.get(s[BUY_CCY_CD_A]);
		    	s[SELL_CCY_CD_A] = map_SELL_CCY_CD.get(s[SELL_CCY_CD_A]);
		    	s[TRAN_SUB_TYPE_CD_A] = map_TRAN_SUB_TYPE_CD.get(s[TRAN_SUB_TYPE_CD_A]);
		    	
		    	if(s[LEG_QTY].equals("0")) s[LEG_QTY] = "27";
		    	if(s[LEG_SEQ].equals("0")) s[LEG_SEQ] = "27";
		    	if(s[LEG_QTY_A].equals("0")) s[LEG_QTY_A] = "27";
		    	if(s[LEG_SEQ_A].equals("0")) s[LEG_SEQ_A] = "27";
		    	
		    	write.write(String.join(",", s));;
		    	write.newLine();		    		
		    }
		    read.close();
		    
		    write.flush();
		    write.close();
		    System.out.println("Finished!");
 		
		}catch(IOException e){
			e.getStackTrace();
		}
		
	}
	
	private void makeMappings(){
		BufferedReader read = null;
		String tempString = null;
		
		try{
			read = new BufferedReader(new FileReader(new File("src/preProcessing/mapping/GC_CLASS_CD.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 map_GC_CLASS_CD.put(s[0], s[1]);
			 }
			 read.close();
			 
			 read = new BufferedReader(new FileReader(new File("src/preProcessing/mapping/SOURCE_TRADE_STATUS_CD.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 map_SOURCE_TRADE_STATUS_CD.put(s[0], s[1]);
			 }
			 read.close();
			 
			 read = new BufferedReader(new FileReader(new File("src/preProcessing/mapping/SOURCE_TRANS_CD.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 map_SOURCE_TRANS_CD.put(s[0], s[1]);
			 }
			 read.close();
			 
			 read = new BufferedReader(new FileReader(new File("src/preProcessing/mapping/GTM_INSTRUCT_TYPE_CD.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 map_GTM_INSTRUCT_TYPE_CD.put(s[0], s[1]);
			 }
			 read.close();
			 
			 read = new BufferedReader(new FileReader(new File("src/preProcessing/mapping/GTM_MESSAGE_TYPE_CD.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 map_GTM_MESSAGE_TYPE_CD.put(s[0], s[1]);
			 }
			 read.close();
			 
			 read = new BufferedReader(new FileReader(new File("src/preProcessing/mapping/LOCAL_CCY_CD.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 map_LOCAL_CCY_CD.put(s[0], s[1]);
			 }
			 read.close();
			 
			 read = new BufferedReader(new FileReader(new File("src/preProcessing/mapping/BUY_CCY_CD.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 map_BUY_CCY_CD.put(s[0], s[1]);
			 }
			 read.close();
			 
			 read = new BufferedReader(new FileReader(new File("src/preProcessing/mapping/SELL_CCY_CD.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 map_SELL_CCY_CD.put(s[0], s[1]);
			 }
			 read.close();
			 
			 read = new BufferedReader(new FileReader(new File("src/preProcessing/mapping/TRAN_SUB_TYPE_CD.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 map_TRAN_SUB_TYPE_CD.put(s[0], s[1]);
			 }
			 read.close();
			 System.out.println("Make Mapping Successfully!");
		}catch(IOException e){
			e.getStackTrace();
		}
	}

}
