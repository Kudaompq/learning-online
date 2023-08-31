package top.kudaompq.base.model;

import lombok.Data;
import lombok.ToString;

/**
 * @description: 分页参数
 * @author: kudaompq
 **/

@Data
@ToString
public class PageParams {

    // 当前页数
    private Long pageNo = 1L;

    // 每页数据数量
    private Long pageSize = 10L;

    public PageParams() {
    }

    public PageParams(Long pageNo, Long pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}
