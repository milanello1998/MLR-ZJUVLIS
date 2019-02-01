package preProcessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Find_K_Nearest {

	static final int MATCH_CD = 35;
	static final int NUM_FEATURES = 35;
	static final int K = 20;
	
	
	static final String FILE_IN = "src/preProcessing/trade_on_06_15_output_3.csv";
	static final String FILE_OUT = "src/preProcessing/Tree_in.csv";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new Find_K_Nearest().create_NC_File(K);
	}
	
	//read file and create NC list with RC's K nearest neighbors
	private void create_NC_File(int K){
		
		//read file and make normal list and abnormal list
		try {
			System.out.println("Started!");
			
			BufferedReader read = new BufferedReader(new FileReader(new File(FILE_IN)));
			String tempString = null;
			read.readLine();
			List<String[]> normalBig = new ArrayList<String[]>();
			List<String[]> abnormal = new ArrayList<String[]>();
			
			//Add original data to normal and abnormal list
			while((tempString = read.readLine()) != null){
				String[] s = tempString.split(",");
				if(s[MATCH_CD].equals("1")){
					normalBig.add(s);
				}else if(s[MATCH_CD].equals("2") ||s[MATCH_CD].equals("3")){
					abnormal.add(s);
				}			
			}
			read.close();
		    
			//for each RC record, find the K nearest neighbors and add to out list with RC record
			List<String[]> out = new ArrayList<String[]>();
			List<Double> dis = new ArrayList<Double>();
			for(String[] s : abnormal){
				out.add(s);
				List<String[]> temp = normalBig;
				List<Double> d = calDist(normalBig,s);
				for(int i = 1; i <= K; i++){
					double min = Double.MAX_VALUE;
					int index = 0;
					for(int j = 0; j < d.size(); j++){
						if(d.get(j) < min){
							index = j;
							min = d.get(j);
						}
					}
					out.add(temp.get(index));
					dis.add(min);
					temp.remove(index);
					d.remove(index);
				}
				
			}
			
			BufferedWriter write = new BufferedWriter(new FileWriter(new File(FILE_OUT)));
		    for(int i = 0; i < out.size(); i++){
		    	write.write(String.join(",", out.get(i)));
		    	write.newLine();
		    }
		    write.flush();
		    write.close();
			System.out.println("Finished!");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//for each record, return distance to each normal data
	private List<Double> calDist(List<String[]> normal, String[] abnormal){
		List<Double> dist = new ArrayList<Double>();
        
        for(int i = 0; i < normal.size(); i++){
			double d = dist(Arrays.copyOfRange(normal.get(i),0,NUM_FEATURES),Arrays.copyOfRange(abnormal,0,NUM_FEATURES));
			dist.add(d);
		}
       		
		return dist;
	}
	
	//distance function
    private double dist(String[] a,String[] b){
		
		double num = 0;
		int n = a.length;
		double[] weight = {0.6,0,0,0,0.7,0,0,0.6,0,0,0.7,0,0.9,0.7,1,0.9,1,0,0.8,0.8,0.9,1,0.7,0.7,0.9,0,0,0,0,0,0,0,0,0,0.7};
		
		for(int i = 0; i < n; i++){
			if(!a[i].equals(b[i])){
				num += weight[i];
			}
		}
		return num;
	}


}
