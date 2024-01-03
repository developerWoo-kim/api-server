package gw.apiserver.common.utils.pagination;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Pagination<T> {
    int count;
    boolean hasNextData;
    List<T> data;

    private Pagination(int count, boolean hasNextData, List<T> data) {
        this.count = count;
        this.hasNextData = hasNextData;
        this.data = data;
    }

    public static <T> Pagination<T> createPagination(boolean hasNextData, List<T> data) {
        return new Pagination<>(data.size(), hasNextData, data);
    }
}
