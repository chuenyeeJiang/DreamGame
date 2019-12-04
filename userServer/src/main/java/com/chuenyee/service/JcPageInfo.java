package com.chuenyee.service;

import java.util.ArrayList;
import java.util.List;

import com.chuenyee.service.api.PageInfo;


public class JcPageInfo implements PageInfo{
	private int pageNum;
	private int pageSize;
	private int pages;
	private int prePage;
	private int nextPage;
	private int total;
	private boolean hasPreviousPage = false;
	private boolean hasNextPage = false;
	private int[] navigatepageNums;
	private int navigatePages;
	private List list;
	private List Orilist;

	public <T> JcPageInfo(List<T> Orilist, int navigatePages) {
		this.Orilist = Orilist;
		this.navigatePages = navigatePages;
	}

	public <T> void startPage(int pn, int pageSize) {
		this.total = this.Orilist.size();
		this.pageSize = pageSize;
		this.pages = total / pageSize + 1;
		// 基本信息赋值
		if (pn < 1) {
			this.pageNum = 1;
			System.out.println("this.pageNum = 1;");
		} else if (pn > pages) {
			this.pageNum = pages;
			System.out.println("this.pageNum = pages;");
		} else {
			this.pageNum = pn;
			System.out.println("this.pageNum = pn;");
		}
		this.prePage = this.pageNum - 1;
		this.nextPage = this.pageNum + 1;
		this.hasPreviousPage = this.pageNum > 1;
		this.hasNextPage = this.pageNum < pages;

		System.out.println("this.pages:"+this.pages);
		System.out.println("this.total:"+this.total);
		System.out.println("this.pageSize:"+this.pageSize);
		System.out.println("this.prePage:"+	this.prePage);
		System.out.println("this.nextPage:"+this.nextPage);
		System.out.println("this.hasPreviousPage:"+this.hasPreviousPage);
		System.out.println("this.hasNextPage:"+this.hasNextPage);
		// 导航条处理
		if (pages <= navigatePages) {
			navigatepageNums = new int[pages];
			for (int i = 0; i < pages; i++) {
				navigatepageNums[i] = i + 1;
			}
		} else {
			navigatepageNums = new int[navigatePages];
			int startNum = this.pageNum - navigatePages / 2;
			int endNum = this.pageNum + navigatePages / 2;
			if (startNum < 1) {
				startNum = 1;
				for (int i = 0; i < navigatePages; i++) {
					navigatepageNums[i] = startNum++;
				}
			} else if (endNum > pages) {
				endNum = pages;
				for (int i = navigatePages - 1; i >= 0; i--) {
					navigatepageNums[i] = endNum--;
				}
			} else {
				for (int i = 0; i < navigatePages; i++) {
					navigatepageNums[i] = startNum++;
				}
			}
		}

		// 数据分页

		System.out.println("pageNum:" + this.pageNum);
		System.out.println("pageSize:" + pageSize);
		list = new ArrayList<T>();
		if ((this.pageNum) * pageSize > total) {
			list.addAll(Orilist.subList((this.pageNum - 1) * pageSize,
					(pageNum - 1) * pageSize + total % pageSize));
		} else {
			list.addAll(Orilist.subList((this.pageNum - 1) * pageSize,
					this.pageNum * pageSize));
		}
		System.out.println(list);

	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public int[] getNavigatepageNums() {
		return navigatepageNums;
	}

	public void setNavigatepageNums(int[] navigatepageNums) {
		this.navigatepageNums = navigatepageNums;
	}

	public int getNavigatePages() {
		return navigatePages;
	}

	public void setNavigatePages(int navigatePages) {
		this.navigatePages = navigatePages;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public List getOrilist() {
		return Orilist;
	}

	public void setOrilist(List orilist) {
		Orilist = orilist;
	}
}