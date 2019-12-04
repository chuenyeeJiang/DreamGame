package com.chuenyee.service.api;

import java.util.List;

public interface PageInfo {
	
	<T> void startPage(int pn, int pageSize);
	
	int getPageNum();

	public void setPageNum(int pageNum);

	public int getPageSize();

	public void setPageSize(int pageSize);

	public int getPages();

	public void setPages(int pages);
	
	public int getPrePage();

	public void setPrePage(int prePage);

	public int getNextPage();

	public void setNextPage(int nextPage);

	public int getTotal();

	public void setTotal(int total);

	public boolean isHasPreviousPage();

	public void setHasPreviousPage(boolean hasPreviousPage);

	public boolean isHasNextPage();

	public void setHasNextPage(boolean hasNextPage);

	public int[] getNavigatepageNums();

	public void setNavigatepageNums(int[] navigatepageNums);

	public int getNavigatePages();

	public void setNavigatePages(int navigatePages);
	
	public List getList();
	
	public void setList(List list);
	
	public List getOrilist();

	public void setOrilist(List orilist);
}

