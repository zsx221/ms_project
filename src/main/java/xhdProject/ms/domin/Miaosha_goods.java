package xhdProject.ms.domin;

import java.util.Date;

public class Miaosha_goods {
    private Long id;
    private Long goodsId;
    private double miaosha_price;
    private Integer stock_count;
    private Date startDate;
    private Date endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public double getMiaosha_price() {
        return miaosha_price;
    }

    public void setMiaosha_price(double miaosha_price) {
        this.miaosha_price = miaosha_price;
    }

    public Integer getStock_count() {
        return stock_count;
    }

    public void setStock_count(Integer stock_count) {
        this.stock_count = stock_count;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
