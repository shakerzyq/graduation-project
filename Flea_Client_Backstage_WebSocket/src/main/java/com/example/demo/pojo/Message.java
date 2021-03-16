package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 周杨清
 * @date 2021/2/21 18:02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private String userId;

    private String message;
}
