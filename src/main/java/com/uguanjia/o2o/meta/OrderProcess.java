package com.uguanjia.o2o.meta;
/*******************************************
 * @CLASS:OrderProcess
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年3月24日 上午12:19:55
 *******************************************/
public class OrderProcess {
	
	public static enum Status{
		
		 NEW     ( 1 ,"new"    ,"新建")
		,ACCEPT  ( 2 ,"accept" ,"接受")
		,REJECT  ( 3 ,"reject"   ,"拒绝")
		,CANCEL  (98 ,"cancel" ,"取消")
		,COMPLETE(99 ,"comple" ,"完成");
		
		public final int id;
		public final String code;
		public final String name;
		
		private Status(int id, String  code, String name)
		{
			this.id = id;
			this.code = code;
			this.name = name;
		}
		
		public static Status get(int id)
		{
			Status ret = null;
			
			Status[] status = Status.values();
			
			for(Status s :  status)
			{
				if( s.id == id )
				{
					ret = s;
					break;
				}
			}
			
			return ret;
		}
	}

}

