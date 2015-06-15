package com.uguanjia.o2o.service;
/*******************************************
 * @CLASS:Services
 * @DESCRIPTION:	
 * @VERSION:v1.0
 *******************************************/
public enum Service {
	
	XIAOBAITU       (1  ,"xiaobaitu","小白兔专业干洗")
	,ZHAOPIANCHONGXI(2  ,"zhaopianchongxi","照片冲洗")
	,SHUNFENG       (3  ,"shunfeng","顺丰快递")
	,CAINIAO        (4  ,"cainiao","菜鸟网络")
	,YIWANGTONG     (5  ,"yiwangtong","壹网通")
	,MAOWU          (101,"maowu","猫屋");
	
	public final int ID;
	
	public final String CODE;
	
	public final String NAME;
	
	private Service(int id, String code, String name){
		this.ID = id;
		this.CODE = code;
		this.NAME = name;
	}
	
	public static Service getById(int id){
		Service service = null;
		switch (id) {
		case 1:
			service = Service.XIAOBAITU;
			break;
		case 2:
			service = Service.ZHAOPIANCHONGXI;
			break;
		case 3:
			service = Service.SHUNFENG;
			break;
		case 4:
			service = Service.CAINIAO;
			break;
		case 5:
			service = Service.YIWANGTONG;
			break;
		default:
			service = null;
			break;
		}
		
		return service;
	}
}

