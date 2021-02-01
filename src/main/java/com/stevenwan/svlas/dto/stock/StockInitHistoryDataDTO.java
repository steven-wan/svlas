package com.stevenwan.svlas.dto.stock;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author steven-wan
 * @since 2021-01-28
 */
@Data
public class StockInitHistoryDataDTO implements Serializable {
    /**
     * 开始时间
     */
    private String startDate;

    /**
     * 截至时间
     */
    private String endDate;

    /**
     * 股票代码
     */
    private String stockCode;


}
