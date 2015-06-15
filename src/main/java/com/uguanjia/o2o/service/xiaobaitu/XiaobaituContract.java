package com.uguanjia.o2o.service.xiaobaitu;

import com.uguanjia.o2o.Contract;
import com.uguanjia.o2o.service.Service;


/*******************************************
 * @CLASS:XbtContract
 * @DESCRIPTION:	
 * @VERSION:v1.0
 *******************************************/
public class XiaobaituContract extends Contract{
	
	private String yiFang;
	
	private String gongShangZhuCeHao;
	
	private String faDingDaiBiaoRen;
	
	private String diZhi;
	
	private String youBian;
	
	private String dianHua;
	
	public XiaobaituContract() {
		setType(Service.XIAOBAITU.ID);
	}

	public String getYiFang() {
		return yiFang;
	}

	public void setYiFang(String yiFang) {
		this.yiFang = yiFang;
	}

	public String getGongShangZhuCeHao() {
		return gongShangZhuCeHao;
	}

	public void setGongShangZhuCeHao(String gongShangZhuCeHao) {
		this.gongShangZhuCeHao = gongShangZhuCeHao;
	}

	public String getFaDingDaiBiaoRen() {
		return faDingDaiBiaoRen;
	}

	public void setFaDingDaiBiaoRen(String faDingDaiBiaoRen) {
		this.faDingDaiBiaoRen = faDingDaiBiaoRen;
	}

	public String getDiZhi() {
		return diZhi;
	}

	public void setDiZhi(String diZhi) {
		this.diZhi = diZhi;
	}

	public String getYouBian() {
		return youBian;
	}

	public void setYouBian(String youBian) {
		this.youBian = youBian;
	}

	public String getDianHua() {
		return dianHua;
	}

	public void setDianHua(String dianHua) {
		this.dianHua = dianHua;
	}
}

