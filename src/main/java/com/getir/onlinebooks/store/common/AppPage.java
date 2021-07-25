package com.getir.onlinebooks.store.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@NoArgsConstructor
public class AppPage {
    private int pageNo = 0;
    private int pageSize = 10;
    private String sortBy;
    private String sortDirection = Sort.Direction.DESC.name();
}
