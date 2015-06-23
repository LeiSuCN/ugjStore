package com.uguanjia.o2o.service.jiadian;

import java.util.List;

/*******************************************
 * @DESCRIPTION: 
 * @AUTHOR:sulei
 * @VERSION:v1.0
 * @DATE:2015年6月22日
 *******************************************/
public class ServiceItem
{
    private int id;
    
    private String name;

    private int category;
    
    private int price;
    
    private List<ServiceItem> sub;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getCategory()
    {
        return category;
    }

    public void setCategory(int category)
    {
        this.category = category;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public List<ServiceItem> getSub()
    {
        return sub;
    }

    public void setSub(List<ServiceItem> sub)
    {
        this.sub = sub;
    }
}

