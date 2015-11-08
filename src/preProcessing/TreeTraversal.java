package preProcessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class TreeTraversal {
	
	static final String FILE_IN = "src/gc/tree/TreeNode.txt";
	static final String FILE_OUT = "src/gc/tree/TreeRule.txt";
	
	static final HashMap<String,String> mapping_s3 = new HashMap<String,String>();
	static final HashMap<String,String> map_GC_CLASS_CD = new HashMap<String,String>();
	static final HashMap<String,String> map_SOURCE_TRADE_STATUS_CD = new HashMap<String,String>();
	static final HashMap<String,String> map_SOURCE_TRANS_CD = new HashMap<String,String>();
	static final HashMap<String,String> map_GTM_INSTRUCT_TYPE_CD = new HashMap<String,String>();
	static final HashMap<String,String> map_GTM_MESSAGE_TYPE_CD = new HashMap<String,String>();
	static final HashMap<String,String> map_TRAN_SUB_TYPE_CD = new HashMap<String,String>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		TreeTraversal t = new TreeTraversal();
		t.makeMappings();
		TreeNode root = t.makeTree();
		t.treeTraversal(root);
	}
	
	class TreeNode{
		String val;
		TreeNode left;
		TreeNode right;
		String split;
		String sLeft;
		String sRight;
		int iLeft;
		int iRight;
		TreeNode(String val, int iLeft, int iRight, String split, String sLeft, String sRight){
			this.val = val;
			this.iLeft = iLeft;
			this.iRight = iRight;
			this.split = split;
			this.sLeft = sLeft;
			this.sRight = sRight;
		}
	}
	
	public void treeTraversal(TreeNode root){
		Stack<TreeNode> s = new Stack<TreeNode>();
		try{
			BufferedWriter write = new BufferedWriter(new FileWriter(new File(FILE_OUT)));
			int i = 1;
			while(root != null){
				if(root.iLeft == 0 && root.iRight == 0){
					System.out.println(i + ": " + root.sLeft + root.val);
					write.write(i + ": " + root.sLeft + root.val);
					write.newLine();
					i++;
				}
				
				if(root.iRight != 0){
					root.right.sLeft = root.sRight + " -> " + root.right.sLeft;
					root.right.sRight = root.sRight + " -> " + root.right.sRight;
					s.push(root.right);
				}
				
				if(root.iLeft != 0){
					root.left.sLeft = root.sLeft + " -> " + root.left.sLeft;
					root.left.sRight = root.sLeft + " -> " + root.left.sRight;
					root = root.left;
				}else if(!s.isEmpty()){
					root = s.pop();
				}else{
					root = null;
				}
			}
			write.flush();
			write.close();
			
		}catch(IOException e){
			e.getStackTrace();
		}
		
	}
	
    private TreeNode makeTree(){
    	ArrayList<TreeNode> node = new ArrayList<TreeNode>();
    	node.add(new TreeNode("0",0,0,"0","0","0"));
    	try {
			BufferedReader read = new BufferedReader(new FileReader(new File(FILE_IN)));
			String temp = null;			
			while((temp = read.readLine()) != null){
				String[] s = temp.split(",");
				s[3] = mapping_s3.get(s[3]);
				s[4] = convert(s[3],s[4]);
				s[5] = convert(s[3],s[5]);
				TreeNode t = new TreeNode(s[0],Integer.parseInt(s[1]),Integer.parseInt(s[2]),s[3],s[4],s[5]);
				node.add(t);
			}
			read.close();		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	for(int i = 1; i < node.size(); i++){
    		TreeNode temp = node.get(i);
    		if(temp.split != null && temp.sLeft.split(",").length == 1){
    			temp.sLeft = temp.split + " = " + temp.sLeft.trim();
    		}else if(temp.split != null){
    			temp.sLeft = temp.split + " in (" + temp.sLeft.trim() + ")";
    		}
    		
    		if(temp.split != null && temp.sRight.split(",").length == 1){
    			temp.sRight = temp.split + " = " + temp.sRight.trim();
    		}else if(temp.split != null){
    			temp.sRight = temp.split + " in (" + temp.sRight.trim() + ")";
    		}
    		
    		temp.left = node.get(temp.iLeft);
    		temp.right = node.get(temp.iRight);
    		node.set(i, temp);
    	}
    	return node.get(1);
    }
    
    public String convert(String column, String str){
    	if(column == null) return str;
    	String[] s = str.split(" ");
    	String[] res = new String[s.length];
    	HashMap<String,String> map = new HashMap<String,String>();
    	if(column.equals("GC_CLASS_CD") || column.equals("GC_CLASS_CD_A")){
    		map = map_GC_CLASS_CD;
    	}else if(column.equals("SOURCE_TRADE_STATUS_CD") || column.equals("SOURCE_TRADE_STATUS_CD_A")){
    		map = map_SOURCE_TRADE_STATUS_CD;
    	}else if(column.equals("SOURCE_TRANS_CD") || column.equals("SOURCE_TRANS_CD_A")){
    		map = map_SOURCE_TRANS_CD;
    	}else if(column.equals("GTM_INSTRUCT_TYPE_CD") || column.equals("GTM_INSTRUCT_TYPE_CD_A")){
    		map = map_GTM_INSTRUCT_TYPE_CD;
    	}else if(column.equals("GTM_MESSAGE_TYPE_CD") || column.equals("GTM_MESSAGE_TYPE_CD_A")){
    		map = map_GTM_MESSAGE_TYPE_CD;
    	}else if(column.equals("TRAN_SUB_TYPE_CD") || column.equals("TRAN_SUB_TYPE_CD_A")){
    		map = map_TRAN_SUB_TYPE_CD;
    	}else if(column.equals("LEG_QTY") || column.equals("LEG_SEQ")){
    		map.put("27","null");
    	}else if(column.equals("TRAN_REF_NUM_1")){
    		
    	}else{
    		map.put("1", "Y");
    		map.put("2", "N");
    	}
    	
    	for(int i = 0; i < s.length; i++){
    		if(s[0].equals("0")){
    			res[i] = "null";
    		}else if(map.containsKey(s[0])){
    			res[i] = map.get(s[i]);
    		}else{
    			res[i] = s[i];
    		}
    	}
    	return String.join(",", res);
    }
    
    public void makeMappings(){
    	BufferedReader read = null;
		String tempString = null;
		
		try{
			read = new BufferedReader(new FileReader(new File("src/gc/tree/Mapping_features.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 mapping_s3.put(s[0], s[1]);
			 }
			 read.close();
			 read = new BufferedReader(new FileReader(new File("src/preProcessing/mapping/GC_CLASS_CD.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 map_GC_CLASS_CD.put(s[1], s[0]);
			 }
			 read.close();
			 
			 read = new BufferedReader(new FileReader(new File("src/preProcessing/mapping/SOURCE_TRADE_STATUS_CD.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 map_SOURCE_TRADE_STATUS_CD.put(s[1], s[0]);
			 }
			 read.close();
			 
			 read = new BufferedReader(new FileReader(new File("src/preProcessing/mapping/SOURCE_TRANS_CD.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 map_SOURCE_TRANS_CD.put(s[1], s[0]);
			 }
			 read.close();
			 
			 read = new BufferedReader(new FileReader(new File("src/preProcessing/mapping/GTM_INSTRUCT_TYPE_CD.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 map_GTM_INSTRUCT_TYPE_CD.put(s[1], s[0]);
			 }
			 read.close();
			 
			 read = new BufferedReader(new FileReader(new File("src/preProcessing/mapping/GTM_MESSAGE_TYPE_CD.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 map_GTM_MESSAGE_TYPE_CD.put(s[1], s[0]);
			 }
			 read.close();
			 
			 read = new BufferedReader(new FileReader(new File("src/preProcessing/mapping/TRAN_SUB_TYPE_CD.txt")));
			 while((tempString = read.readLine()) != null){
				 String[] s = tempString.split(",");
				 map_TRAN_SUB_TYPE_CD.put(s[1], s[0]);
			 }
			 read.close();
		}catch(IOException e){
			e.getStackTrace();
		}
		
		
    }

}
