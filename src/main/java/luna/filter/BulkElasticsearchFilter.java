package luna.filter;

import java.util.Map;
import luna.output.BulkElasticsearchOutput;

/**
 * 
* Copyright: Copyright (c) 2017 XueErSi
* 
* @ClassName: BulkElasticsearchFilter.java
* @Description: Emit messages to Elasticsearch with bulk API.
*
* @version: v1.0.0
* @author: GaoXing Chen
* @date: 2017年8月21日 下午7:52:28 
*
* Modification History:
* Date         Author          Version			Description
*---------------------------------------------------------*
* 2017年8月21日     GaoXing Chen      v1.0.0				添加注释
 */
public class BulkElasticsearchFilter  extends BaseFilter{
	private final BulkElasticsearchOutput eshandler;

	public BulkElasticsearchFilter(Map config){
	    super();
		eshandler=new BulkElasticsearchOutput(config);
	}

	public void prepare(){
		eshandler.getBulkRequest();
	}

	public void emit(){
		eshandler.emitBulk();
		//response time
		logTime.info(System.currentTimeMillis());
	}

	public void filter(Map<String, Object>data){
	    //time maxwell get data
        super.filter(data);
		logTime.info(ts);
		if(type.contentEquals("insert")){
			eshandler.index(table, database,id,payload);
		}else if(type.contentEquals("delete")){
			eshandler.delete(table, database,id);
		}else if(type.contentEquals("update")){
			eshandler.update(table, database,id,payload);
		}
	}
}
