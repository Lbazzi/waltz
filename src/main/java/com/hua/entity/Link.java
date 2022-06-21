package com.hua.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link {

    private Integer id;
    private String name;
    private String url;
    private boolean doShow;
}
