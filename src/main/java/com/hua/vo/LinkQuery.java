package com.hua.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkQuery {

    private Integer id;
    private String name;
    private String url;
    private boolean doShow;
}
