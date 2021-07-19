package com.example.liqian.huarong.bean;

import java.util.List;

/**
 * Created by Administrator on 2019-07-12.
 */

public class EntPages<T> {
    public static EntPages entPages = null;

    private int TotalPages = 0;
    private int PrePage = 0;
    private int CurPage = 0;
    private int NextPage = 0;
    private int PageCapacity = 0;
    private int TotalCapacity = 0;
    private List<T> PageResults = null;

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

    public List<T> getPageResults(){ return PageResults; }
    public void setPageResults(List<T> _PageResults){PageResults = _PageResults;}

    @Override
    public String toString() {
        return "EntPages{" +
                "TotalPages=" + TotalPages +
                ", PrePage=" + PrePage +
                ", CurPage=" + CurPage +
                ", NextPage=" + NextPage +
                ", PageCapacity=" + PageCapacity +
                ", TotalCapacity=" + TotalCapacity +
                ", PageResults=" + PageResults +
                '}';
    }
}
