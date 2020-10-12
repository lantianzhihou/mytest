package com.wangbo.test.util;

import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

public class PageUtil {
	/**
     * 
     * <手动分页并模拟数据跟pagehelper的分页一致，以保证前台分页的统一性>
     * <功能详细描述>
     * @param list 要分页的list
     * @param pageNum 当前页码
     * @param pageSize 每页数量
     * @return
     * @see [类、类#方法、类#成员]
     */
	public static <T> PageInfo<T> pageByManual(List<T> list,int pageNum,int pageSize){
        PageInfo<T> pageInfo = new PageInfo<T>();
        int total = 0; //总记录数
        int pages = 0; //总页数
        int startRow = 0; //当前页面第一个元素在数据库中的行号
        int endRow = 0; //当前页面最后一个元素在数据库中的行号
        int size = 0; //当前页的数量
        int prePage = 0; //前一页
        int nextPage = 0; //下一页      
        
        if (list != null && list.size() > 0) {
            total = list.size();
            pages = (int) (total / pageSize + ((total % pageSize == 0) ? 0 : 1));
            startRow = (pageNum-1)*pageSize+1;
            endRow = pageNum*pageSize;
            if (startRow > total) {
                startRow = 0;
                endRow = 0;
                size = 0;
            }else if (endRow > total) {
                endRow = total;
            }
            size=endRow-startRow+1;
            if (startRow > 0 && endRow > 0) {
                pageInfo.setList(list.subList(startRow -1 , endRow));//当前页的分页数据
            }
            if (pageNum > 1) {
                prePage = pageNum - 1;
            }
            if (pageNum < pages) {
                nextPage = pageNum + 1;
            }
        }
        pageInfo.setPageNum(pageNum);//当前页数
        pageInfo.setPrePage(prePage);
        pageInfo.setNextPage(nextPage);
        pageInfo.setPageSize(pageSize);//每页数量
        pageInfo.setTotal(total);//总记录数
        pageInfo.setPages(pages);//总页数
        pageInfo.setStartRow(startRow);//当前页开始行在总记录数中的位置，位置从1开始算
        pageInfo.setEndRow(endRow);//当前页结束行在总记录数中的位置，位置从1开始算
        pageInfo.setSize(size);//当前页的记录数，不一定等于pageSize
        return pageInfo;
    }
	
	/**
	 * A的分页参数传给B
	 */

	public static List<?> setPageParam(Page<?> A,Page<?> B){
		    B.setPageNum(A.getPageNum());
		    B.setTotal(A.getTotal());
		    B.setPages(A.getPages());
		    B.setPageSize(A.getPageSize());
		    return B;
	}
}
