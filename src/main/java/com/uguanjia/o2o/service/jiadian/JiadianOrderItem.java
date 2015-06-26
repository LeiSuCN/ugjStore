package com.uguanjia.o2o.service.jiadian;
/*******************************************
 * @DESCRIPTION: 
 * @AUTHOR:sulei
 * @VERSION:v1.0
 * @DATE:2015年6月25日
 *******************************************/
public class JiadianOrderItem
{
    /** 类型编码 */
    private int code;
    
    private String name;
    
    private int amount;
    
    private float price;

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }
}

