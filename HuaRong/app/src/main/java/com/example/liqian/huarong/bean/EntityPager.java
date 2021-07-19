package com.example.liqian.huarong.bean;

/**
 * Created by Administrator on 2019-08-15.
 */

public class EntityPager {

    public static EntityPager entityPager = null;

    private int TotalPages;
    private int PrePage;
    private int CurPage;
    private int NextPage;
    private int PageCapacity;
    private int TotalCapacity;
    private String PageResults;

    public int getTotalPages(){ return TotalPages; }
    public void setTotalPages(int _TotalPages){TotalPages = _TotalPages;}

    public int getPrePage(){ return PrePage; }
    public void setPrePage(int _PrePage){PrePage = _PrePage;}

    public int getCurPage(){ return CurPage; }
    public void setCurPage(int _CurPage){CurPage = _CurPage;}

    public int getNextPage(){ return NextPage; }
    public void setNextPage(int _NextPage){NextPage = _NextPage;}

    public int getPageCapacity(){ return PageCapacity; }
    public void setPageCapacity(int _PageCapacity){PageCapacity = _PageCapacity;}

    public int getTotalCapacity(){ return TotalCapacity; }
    public void setTotalCapacity(int _TotalCapacity){TotalCapacity = _TotalCapacity;}

    public String getPageResults(){ return PageResults; }
    public void setPageResults(String _PageResults){PageResults = _PageResults;}

}
