package preProcessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Step_3 {
	static final int SSB_TRADE_ID = 10;
	static final int TRAN_REF_NUM = 9;
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
	static final int LOCAL_CCY_CD_A = 49;
	static final int BUY_CCY_CD_A = 51;
	static final int SELL_CCY_CD_A = 53;
	static final int GC_CLASS_CD_1 = 67;
	static final int SOURCE_TRADE_STATUS_CD_1 = 68;
	static final int SOURCE_TRANS_CD_1 = 69;
	static final int CATEGORY_1 = 98;
	static final int SSB_ASSET_ID_1 = 70;
	static final int FUND_ID_1 = 71;
	static final int CTPY_ID_1 = 72;
	static final int CONTRACTUAL_SETTLE_DATE_1 = 73;
	static final int TRADE_DATE_1 = 74;
	static final int TRAN_REF_NUM_1 = 75;
	static final int SSB_TRADE_ID_1= 76;
	static final int CONTRACTUAL_SHARE_QTY_1 = 77;
	static final int UNIT_PRICE_AMT_1 = 78;
	static final int LOCAL_CCY_CD_1= 82;
	static final int BUY_TRANS_AMT_1 = 83;
	static final int BUY_CCY_CD_1 = 84;
	static final int SELL_TRANS_AMT_1 = 85;
	static final int SELL_CCY_CD_1 = 86;
	static final int TAXES_LOCAL_AMT_1 = 88;
	static final int INTEREST_LOCAL_AMT_1 = 89;
	static final int COMMISSION_LOCAL_AMT_1 = 90;
	static final int FEES_LOCAL_AMT_1 = 91;
	static final int OTHER_FEES_LOCAL_AMT_1 = 92;
	static final int CONTRACTUAL_NET_LOCAL_AMT_1 = 93;
	static final int MATCH_CD = 32;
	
	static final String FILE_IN = "src/preProcessing/trade_on_06_15_output_2.csv";
	static final String FILE_OUT = "src/preProcessing/trade_on_06_15_output_3.csv";
				
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new Step_3().step3();
	}

	private void step3(){
		File infile = new File(FILE_IN);
		File outfile = new File(FILE_OUT);
		BufferedReader read = null;
		BufferedWriter write = null;
		
		try{
			System.out.println("Started!");
		    
			read = new BufferedReader(new FileReader(infile));
		    write = new BufferedWriter(new FileWriter(outfile));
		    
		    String tempString = null;
		    read.readLine();
	    		    		    
			String outputHead = "GC_CLASS_CD,SOURCE_TRADE_STATUS_CD,SOURCE_TRANS_CD,GTM_INSTRUCT_TYPE_CD,GTM_MESSAGE_TYPE_CD,LEG_QTY,LEG_SEQ,TRAN_SUB_TYPE_CD,GC_CLASS_CD_A,SOURCE_TRADE_STATUS_CD_A,SOURCE_TRANS_CD_A,GC_CLASS_CD_1,SOURCE_TRADE_STATUS_CD_1,SOURCE_TRANS_CD_1,CATEGORY_1,SSB_ASSET_ID_1,FUND_ID_1,CTPY_ID_1,CONTRACTUAL_SETTLE_DATE_1,TRADE_DATE_1,TRAN_REF_NUM_1,SSB_TRADE_ID_1,CONTRACTUAL_SHARE_QTY_1,UNIT_PRICE_AMT_1,LOCAL_CCY_CD_1,BUY_TRANS_AMT_1,BUY_CCY_CD_1,SELL_TRANS_AMT_1,SELL_CCY_CD_1,TAXES_LOCAL_AMT_1,INTEREST_LOCAL_AMT_1,COMMISSION_LOCAL_AMT_1,FEES_LOCAL_AMT_1,OTHER_FEES_LOCAL_AMT_1,CONTRACTUAL_NET_LOCAL_AMT_1,MATCH_CD";
			write.write(outputHead);
		    write.newLine();
		    
		    //read input file and convert MATCH_CD columns to number
		    while((tempString = read.readLine()) != null){
		    	String[] s = tempString.split(",");
		    	switch(s[MATCH_CD]){
		    		case "NC" : s[MATCH_CD] = "1"; break;
		    		case "RC" : s[MATCH_CD] = "2"; break;
		    		case "VC" : s[MATCH_CD] = "3"; break;
		    		case "B"  : s[MATCH_CD] = "4"; break;
		    		case "0" : s[MATCH_CD] = "5"; break;
		    	}	
		    	
		    	//choose the columns needed
		    	if((s[MATCH_CD].equals("1") || s[MATCH_CD].equals("2") || s[MATCH_CD].equals("3")) && !s[GC_CLASS_CD_A].equals("-1")){
		    		String res = String.join(",", s[GC_CLASS_CD],s[SOURCE_TRADE_STATUS_CD],s[SOURCE_TRANS_CD],s[GTM_INSTRUCT_TYPE_CD],s[GTM_MESSAGE_TYPE_CD],s[LEG_QTY],s[LEG_SEQ],s[TRAN_SUB_TYPE_CD],s[GC_CLASS_CD_A],s[SOURCE_TRADE_STATUS_CD_A],s[SOURCE_TRANS_CD_A],s[GC_CLASS_CD_1],s[SOURCE_TRADE_STATUS_CD_1],s[SOURCE_TRANS_CD_1],s[CATEGORY_1],s[SSB_ASSET_ID_1],s[FUND_ID_1],s[CTPY_ID_1],s[CONTRACTUAL_SETTLE_DATE_1],s[TRADE_DATE_1],s[TRAN_REF_NUM_1],s[SSB_TRADE_ID_1],s[CONTRACTUAL_SHARE_QTY_1],s[UNIT_PRICE_AMT_1],s[LOCAL_CCY_CD_1],s[BUY_TRANS_AMT_1],s[BUY_CCY_CD_1],s[SELL_TRANS_AMT_1],s[SELL_CCY_CD_1],s[TAXES_LOCAL_AMT_1],s[INTEREST_LOCAL_AMT_1],s[COMMISSION_LOCAL_AMT_1],s[FEES_LOCAL_AMT_1],s[OTHER_FEES_LOCAL_AMT_1],s[CONTRACTUAL_NET_LOCAL_AMT_1],s[MATCH_CD]);
				   	write.write(res);
			    	write.newLine();
		    	}
		    	
		    }
		    read.close();
		    write.flush();
		    write.close();
		    System.out.println("Finished!");
		}catch(IOException e){
			e.getStackTrace();
		    }
		
	}
}
