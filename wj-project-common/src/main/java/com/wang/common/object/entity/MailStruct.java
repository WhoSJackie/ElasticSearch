package com.wang.common.object.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MailStruct implements Serializable {

    private String msgId;

    private String fromAddr;

    private String toAddr;

    private String title;

    private String content;

}
