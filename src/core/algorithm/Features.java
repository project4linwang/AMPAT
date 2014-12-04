package core.algorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import metamodel.ClassElement;
import metamodel.FeatureData;
import metamodel.FieldElement;
import metamodel.MetaMethod;
import metamodel.MethodElement;
import metamodel.TmpDatabase;

public class Features {
private TmpDatabase tdb=TmpDatabase.getdbInstance();


   
    public void filterByFIV(int minValue){
    	tdb.getExportFList().clear();
    	List<FeatureData> fdlist=tdb.getFeatureList();
    	if(fdlist!=null){
    		for(FeatureData fd:fdlist){
    			int fiv=fd.getFIV();
    			if(fiv>=minValue){
    				tdb.getExportFList().add(fd);
    			}
    		}
    	}
    }
    public void filterByAC(int minValue){
    	tdb.getExportFList().clear();
    	List<FeatureData> fdlist=tdb.getFeatureList();
    	if(fdlist!=null){
    		for(FeatureData fd:fdlist){
    			int ac=fd.getAC();
    			if(ac>=minValue){
    				tdb.getExportFList().add(fd);
    			}
    		}
    	}
    }
    public void filterConcerns(){
    	tdb.getExportFList().clear();
    	List<FeatureData> fdlist=tdb.getFeatureList();
    	if(fdlist!=null){
    		for(FeatureData fd:fdlist){
    			double msp=fd.getMSP();
    			double tf=fd.getTF();
    			if(msp!=0.0 && tf!=1.0){
    				tdb.getExportFList().add(fd);
    			}
    		}
    	}
    }
	/***
	 * Depth of Inheritance Tree
	 * @param cname is the name of class
	 * @return the value of CIT
	 */
	public int calculateDIT(String cname){
		Map<String,String> gm=tdb.getGeneralizationmps();
		int depth=0;
		if(gm!=null){
			if(gm.size()>0){
				String supername=gm.get(cname);
				if(supername!=null){
					depth++;
					while(!supername.equals("java/lang/Object")){
						supername=gm.get(supername);
						if(supername!=null){
						   depth++;
						}
						else{
							break;
						}
					}
				}
				
			}
		}
		return depth;
		
	}
	/***
	 * Number of Children of a Class
	 * @param cname is the name of class
	 * @return
	 */
	public int calculateNOC(String cname){
		int noc=0;
		Map<String,String> gm=tdb.getGeneralizationmps();
		Set<String> kset=gm.keySet();
		for(String ks:kset){
			if(cname.equals(gm.get(ks))){
				noc++;
			}
		}
		return noc;
		
	}
/***
 * Coupling Between Object Classes
 * @param cname is the name of class
 * @return
 */
	public int calculateCBO(String cname){
		Map<String,String> ivc=new HashMap<String,String>();
		for(MethodElement me: tdb.getMethodList()){
			String owner=me.getM_method().getOwner();
			if(owner.equals(cname)){
				if(me!=null){
					List<MetaMethod> invokesmethods= me.getInvokesMethods();
					if(invokesmethods!=null){
						for(MetaMethod invokesmethod:invokesmethods){	
							if(!invokesmethod.getOwner().equals(owner)){
								ivc.put(invokesmethod.getOwner(),owner);
							}															
						}
					}
				}
			}
			
		}	
		int cbo=0;
		Set<String> kset=ivc.keySet();
		for(String ks:kset){
			if(cname.equals(ivc.get(ks))){
				cbo++;
			}
		}
		return cbo;
	}
	/***
	 * Weighted Methods per Class
	 * @param cname
	 * @return
	 */
	public int calculateWMC(String cname){
		int wmc=0;
		List<ClassElement> celist= tdb.getClasslist();
		if(celist.size()>0){
			for(ClassElement ce: celist){
				String classname="";
				if(ce.getPackageName()!=null){
					 classname=ce.getPackageName()+"/"+ce.getName();					
				}
				else{
					 classname=ce.getName();
				}				
				if(classname.equals(cname)){
					
					List<MetaMethod> mmlist=ce.getMethodList();
					for(MetaMethod me:mmlist){
						if(!me.getName().equals("<init>")){
							wmc++;
						}
					}
					
				}
			}
		}
		return wmc;
	}
	/***
	 * Fan-in Value
	 * @param sm_name is the name of method
	 * @param owner is the name of class and its package
	 * @return
	 */
	public int calculateFIV(String sm_name,String owner){
		int fiv=0;
		for(MethodElement me: tdb.getMethodList()){
			if(me!=null){
				//String methodName=me.getM_method().getOwner()+"/"+me.getM_method().getName();
				if(me.getM_method().getOwner().equals(owner) && me.getM_method().getName().equals(sm_name)){
					List<MetaMethod> invokedmethods= me.getInvokedMethods();
					if(invokedmethods!=null){
						for(MetaMethod invokedmethod:invokedmethods){
							if(!invokedmethod.getName().equals("<init>")){
								fiv++;	
							}
						}
					}
				}
			}
			
		}
		return fiv;
	}
	/***
	 * External Fan-in value, the distinct method bodies should belong to the external classes.
	 * @param sm_name
	 * @param owner
	 * @return
	 */
	public int calculateExFIV(String sm_name,String owner){
		int exfiv=0;
		for(MethodElement me: tdb.getMethodList()){
			if(me!=null){
				//String methodName=me.getM_method().getOwner()+"/"+me.getM_method().getName();
				if(me.getM_method().getOwner().equals(owner) && me.getM_method().getName().equals(sm_name)){
					List<MetaMethod> invokedmethods= me.getInvokedMethods();
					if(invokedmethods!=null){
						for(MetaMethod invokedmethod:invokedmethods){
							if(!invokedmethod.getName().equals("<init>")){
								if(!invokedmethod.getOwner().equals(owner)){
									exfiv++;
								}
									
							}
						}
					}
				}
			}
			
		}
		return exfiv;
	}
	/***
	 * Fan-out Value
	 * @param sm_name is the name of method
	 * @param owner is the name of class and its package
	 * @return
	 */
	public int calculateFOV(String sm_name,String owner){
		int fov=0;
		for(MethodElement me:tdb.getMethodList()){
			if(me!=null){				
				if(me.getM_method().getOwner().equals(owner) && me.getM_method().getName().equals(sm_name)){
					List<MetaMethod> invokesmethods=me.getInvokesMethods();
					for(MetaMethod invokesmethod:invokesmethods){
						if(!invokesmethod.getName().equals("<init>")){
							fov++;
						}
					}
				}
			}
		}
		return fov;
		
	}
	/***
	 * Method Internal Coupling 
	 * @param sm_name is the name of method
	 * @param owner is the name of class and its package
	 * @return
	 */
	public double calculateMIC(String sm_name,String owner){
		double mic=0.00d;
		int min=0;
		int mex=0;
		for(MethodElement me: tdb.getMethodList()){
			if(me!=null){
				if(me.getM_method().getOwner().equals(owner) && me.getM_method().getName().equals(sm_name)){
					List<MetaMethod> invokedmethods=me.getInvokedMethods();
					for(MetaMethod invokedmethod:invokedmethods){
						if(!invokedmethod.getName().equals("<init>")){
							if(invokedmethod.getOwner().equals(owner)){
								min++;
							}
							else{
								mex++;
							}
						}
						
					}
					mic=(double)min/(min+mex);
				}
			}
		}
		return mic;
		
	}
	/***
	 * Method External Coupling
	 * @param sm_name is the name of method
	 * @param owner is the name of class and its package
	 * @return
	 */
	public double calculateMEC(String sm_name,String owner){
		double mec=0.00d;
		int min=0;
		int mex=0;
		for(MethodElement me: tdb.getMethodList()){
			if(me!=null){
				if(me.getM_method().getOwner().equals(owner) && me.getM_method().getName().equals(sm_name)){
					List<MetaMethod> invokedmethods=me.getInvokedMethods();
					for(MetaMethod invokedmethod:invokedmethods){
						if(!invokedmethod.getName().equals("<init>")){
							if(invokedmethod.getOwner().equals(owner)){
								min++;
							}
							else{
								mex++;
							}
						}
						
					}
					mec=(double)mex/(min+mex);
				}
			}
		}
		return mec;
	}
	/***
	 * Method Spread
	 * @param sm_name is the name of method
	 * @param owner is the name of class and its package
	 * @return
	 */
	public double calculateMSP(String sm_name,String owner){
		double msp=0.00d;
		for(MethodElement me:tdb.getMethodList()){
			if(me!=null){
				if(me.getM_method().getOwner().equals(owner) && me.getM_method().getName().equals(sm_name)){
					List<MetaMethod> invokedmethods=me.getInvokedMethods();
					List<String>  IClist=new LinkedList<String>();
					for(MetaMethod invokedmethod:invokedmethods){
						if(!invokedmethod.getName().equals("<init>")){
							
							if(!IClist.contains(invokedmethod.getOwner())){
								IClist.add(invokedmethod.getOwner());
							}
						}
					}
					int nc_om=IClist.size();
					int nc_all=tdb.getClasslist().size();
					msp=(double)nc_om/nc_all;
				}
			}
		}
		return msp;
	}
	/***
	 * Return Type
	 * @param sm_name is the name of method
	 * @param owner is the name of class and its package
	 * @return
	 */
	public int getRV(String sm_name,String owner ){
		int result=0;
		for(MethodElement me:tdb.getMethodList()){
			if(me!=null){
				if(me.getM_method().getOwner().equals(owner) && me.getM_method().getName().equals(sm_name)){
					String desc=me.getM_method().getDesc();
					String regstr="\\(([^)]*)\\)";
					String r=desc.replaceFirst(regstr, "");
					if(r.equals("V")){
						result=0;
				    }
					else{
						result=1;
					}
			     }
		    }
	    }
		return result;
	}
	/***
	 * Affected Class
	 * @param sm_name is the name of method
	 * @param owner is the name of class and its package
	 * @return
	 */
	public int calculateAC(String sm_name,String owner){
		int ac=0;
		for(MethodElement me:tdb.getMethodList()){
			if(me!=null){
				if(me.getM_method().getOwner().equals(owner) && me.getM_method().getName().equals(sm_name)){
					List<MetaMethod> invokedmethods= me.getInvokedMethods();
					List<String>  IClist=new LinkedList<String>();
					if(invokedmethods!=null){
						for(MetaMethod invokedmethod:invokedmethods){
							if(!invokedmethod.getName().equals("<init>")){
								if(!IClist.contains(invokedmethod.getOwner())){
									IClist.add(invokedmethod.getOwner());
								}
							}
							
						}
					}
					ac=IClist.size();
				}
			}
		}
		return ac;
				
	}
	/***
	 * Response for a Class
	 * @param cname
	 * @return
	 */
	public int calculateRFC(String cname){
		int rfc=0;
		List<ClassElement> celist= tdb.getClasslist();
		if(celist.size()>0){
			for(ClassElement ce: celist){
				String classname="";
				if(ce.getPackageName()!=null){
					 classname=ce.getPackageName()+"/"+ce.getName();					
				}
				else{
					 classname=ce.getName();
				}				
				if(classname.equals(cname)){
					
					List<MetaMethod> mmlist=ce.getMethodList();
					int lnum=mmlist.size();
					int rnum=0;
					List<MethodElement> melist= tdb.getMethodList(mmlist);
					for(MethodElement me:melist){
						rnum=me.getInvokesMethods().size()+rnum;
					}
					rfc=lnum+rnum;
				}
			}
		}
		return rfc;
	}
	/***
	 * Lack of Cohesion on Methods
	 * @param cname
	 * @return
	 */
	public double calculateLCOM(String cname){
		double lcom=1.0d;
		int MF=0;
		int M=0;
		int F=0;
		List<ClassElement> celist= tdb.getClasslist();
		if(celist.size()>0){
			for(ClassElement ce: celist){
				String className=ce.getPackageName()+"/"+ce.getName();
				if(className.equals(cname)){
					List<FieldElement> felist=ce.getFieldlist();
					F=felist.size();
					for(FieldElement fe:felist){
						List<MetaMethod> mmlist=ce.getMethodList();
						List<MethodElement> melist=tdb.getMethodList(mmlist);
						M=melist.size();
						for(MethodElement me:melist){
							List<FieldElement> m_felist=me.getAccessFields();
							for(FieldElement m_fe:m_felist){
								if(fe.getOwner().equals(m_fe.getOwner()) && fe.getName().equals(m_fe.getName())){
									MF++;
									System.out.println("LCOM Method is " +me.getM_method().getOwner()+"/"+me.getM_method().getName());
								}
							}
						}
					}
				}
			}
		}
		System.out.println("MF: "+MF+" M: "+M+" F: "+F);
		if(M!=0 && F!=0){
			lcom=1.0d-((double)MF/(M*F));
		}
		return lcom;
	}
	/***
	 * Number of parameter of a given method
	 * @param sm_name
	 * @param owner
	 * @return
	 */
	public int calculateNP(String sm_name,String owner){
		int np=0;
		for(MethodElement me:tdb.getMethodList()){
			if(me!=null){
				if(me.getM_method().getOwner().equals(owner) && me.getM_method().getName().equals(sm_name)){
					np=me.getM_method().getArgumentList().size();
				}
			}
		}
		return np;	
	}
	/***
	 * Tangling Factor : c is the classes which call the given method, v1= sum of the value of CBO of c, TF=1/avg(v1).
	 * @param sm_name
	 * @param owner
	 * @return
	 */
	public double calculateTF(String sm_name,String owner){
		double tf=0.00d;
		for(MethodElement me:tdb.getMethodList()){
			if(me!=null){
				if(me.getM_method().getOwner().equals(owner) && me.getM_method().getName().equals(sm_name)){
					List<MetaMethod> invokedmethods=me.getInvokedMethods();
					List<String>  IClist=new LinkedList<String>();
					for(MetaMethod invokedmethod:invokedmethods){
						if(!invokedmethod.getName().equals("<init>")){
							
							if(!IClist.contains(invokedmethod.getOwner())){
								IClist.add(invokedmethod.getOwner());
							}
						}
					}
					int nc_tm=IClist.size();
					int sum_cbo=0;
					for(String cname:IClist){
						int tmp_cbo=calculateCBO(cname);
						sum_cbo=sum_cbo+tmp_cbo;
					}
					double avg_cbo=(double)sum_cbo/nc_tm;
					tf=1/avg_cbo;
					if(Double.isNaN(tf)){
						tf=1.0;
					}
				}
			}
		}
		return tf;
	}
	public int calculateMSig(String sm_name,String owner){
		int Msig=0;
		for(MethodElement me:tdb.getMethodList()){
			if(me!=null){
				if(me.getM_method().getOwner().equals(owner) && me.getM_method().getName().equals(sm_name)){
					Msig=me.getM_method().getSignature();
				}
			}
		}
		return Msig;
	}
	public double calculateCOM(String sm_name,String owner){
		double Com=0.0d;
		int F=0;
		int MF=0;
		List<ClassElement> celist= tdb.getClasslist();
		if(celist.size()>0){
			for(ClassElement ce: celist){
				String className=ce.getPackageName()+"/"+ce.getName();
				if(className.equals(owner)){
					List<FieldElement> felist=ce.getFieldlist();
					F=felist.size();
					List<MetaMethod> mmlist=ce.getMethodList();
					List<MethodElement> melist=tdb.getMethodList(mmlist);
					for(MethodElement me:melist){
						if(me.getM_method().getOwner().equals(owner) && me.getM_method().getName().equals(sm_name)){
							List<FieldElement> m_felist=me.getAccessFields();				
							for(FieldElement fe:felist){
								for(FieldElement m_fe:m_felist){
									if(fe.getName().equals(m_fe.getName())){
										MF++;
									}
								}
							}
						}
					}
					
				}
			}
		}
		if(F!=0){
			Com=(double)MF/F;
		}
		System.out.println("Name: "+Com);
		return Com;
		
	}
	public List<Integer> calculateBVector(String sm_name,String owner){
		List<Integer> bvector=new LinkedList<Integer>();
		for(MethodElement me:tdb.getMethodList()){
			if(me!=null){
				
				if(me.getM_method().getOwner().equals(owner) && me.getM_method().getName().equals(sm_name)){
					List<ClassElement> celist= tdb.getClasslist();
					for(ClassElement ce:celist){
						String classname="";
						int value=0;
						if(ce.getPackageName()!=null){
							 classname=ce.getPackageName()+"/"+ce.getName();					
						}
						else{
							 classname=ce.getName();
						}
						boolean hasmethod=false;
						List<MetaMethod> invokedmethods= me.getInvokedMethods();					
						if(invokedmethods!=null){
							for(MetaMethod invokedmethod:invokedmethods){
								if(!invokedmethod.getName().equals("<init>")){
									if(invokedmethod.getOwner().equals(classname)){
										hasmethod=true;
										break;
									}
									
								}
								
							}
						}
						
						if(hasmethod){
							value=1;
						}
						bvector.add(value);
						
					}	
					
				}
			}
		}
		return bvector;
	}
	public List<Integer> calculateSigTokens(String sm_name,String owner){
		List<Integer> sigTokens=new LinkedList<Integer>();
		String regstr="\\w[^\\p{Upper}]*";
		Pattern ptUpper=Pattern.compile(regstr);
		Matcher matcherUpper=ptUpper.matcher(sm_name);
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
	    List<String> sigTokenList=filterSigToken(tokenList);
	    List<String> totalTokenList=tdb.getTotalTokenList();
	    if(totalTokenList!=null){
	    	for(String totalToken:totalTokenList){
	    		if(sigTokenList.contains(totalToken)){
	    			sigTokens.add(1);
	    		}
	    		else{
	    			sigTokens.add(0);
	    		}
	    	}
	    }
	   return sigTokens;   
	}
	private List<String> filterSigToken(List<String> tokens){
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
