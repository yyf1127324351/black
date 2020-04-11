package com.back.model;

import java.util.Date;
import java.util.List;

public class BaseDto {
    /*规定必须要有的公共字段,所有表都必须有*/
    private String lastUpdateBy;/*最后修改人*/
    private Date lastUpdateTime;/*最后修改时间*/
    private Integer valid;/*是否有效0否1是*/

    /*分页字段*/
    private Integer page=1;//easyui
    private Integer rows=50;//easyui
    private Integer start = (getPage() - 1) * getRows();
    private Integer limit = getRows();

    /*排序*/
    private String sort;
    private String order;

    /*登陆人权限*/
    private List<String> deptAuthorityList;

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<String> getDeptAuthorityList() {
        return deptAuthorityList;
    }

    public void setDeptAuthorityList(List<String> deptAuthorityList) {
        this.deptAuthorityList = deptAuthorityList;
    }
}
