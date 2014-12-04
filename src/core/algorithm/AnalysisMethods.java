package core.algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import metamodel.MethodElement;

public class AnalysisMethods {

	public List<String> calculateTotalTokens(List<MethodElement> melist){
        List<String> totalTokens=new LinkedList<String>();
        if(melist!=null){
        	for(MethodElement me:melist){
        		String mename=me.getM_method().getName();
        		String regstr="\\w[^\\p{Upper}]*";
        		Pattern ptUpper=Pattern.compile(regstr);
        		Matcher matcherUpper=ptUpper.matcher(mename);
        		List<String> upperStrList=new LinkedList<String>();
        		while(matcherUpper.find()){
        			upperStrList.add(matcherUpper.group());
        		}
        		String regstr2="[\\\\_|\\d]";
        		List<String> tokenList=new LinkedList<String>();
        		
        	    for(String upperStr: upperStrList){
        	    	if(upperStr!=null){
        	    		String[] tokens=upperStr.split(regstr2);
        	    		for(int i=0; i<tokens.length;i++){
        	    			tokenList.add(tokens[i]);
        	    		}
        	    	}
        	    	
        	    }
        	    List<String> sigTokenList=filterToken(tokenList);
        	    for(String sigToken:sigTokenList){
        	    	if(!totalTokens.contains(sigToken)){
        	    		totalTokens.add(sigToken);
        	    	}
        	    }
        	}
        }
        return totalTokens;
	}

	private List<String> filterToken(List<String> tokens) {
		// TODO Auto-generated method stub
		List<String> resTokens=new LinkedList<String>();
		if(tokens!=null){
			for(String token:tokens){
				if(token.equals("Of")|| token.equals("of")||token.equals("in")||token.equals("In")||token.equals("for")||token.equals("For")||token.equals("with")||token.equals("With")||token.equals("at")||token.equals("At")||token.equals("on")||token.equals("On")||token.equals("from")||token.equals("From")){
					//
				}
				else{
					resTokens.add(token);
				}
			}
		}
		return resTokens;
	}

	

}
