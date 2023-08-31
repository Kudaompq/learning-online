package top.kudaompq.base.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 分页结果
 * @author: kudaompq
 **/
@Data
@ToString
public class PageResult<T> implements Serializable {

    // 数据列表
    private List<T> items;
    // 总记录数
    private Long counts;
    // 当前页码
    private Long page;
    // 每页记录数
    private Long pageSize;

    public PageResult(List<T> items, Long counts, Long page, Long pageSize) {
        this.items = items;
        this.counts = counts;
        this.page = page;
        this.pageSize = pageSize;
    }

    public PageResult(Page<T> page){
        this.items = page.getRecords();
        this.counts = page.getTotal();
        this.page = page.getCurrent();
        this.pageSize = page.getSize();
    }

}
