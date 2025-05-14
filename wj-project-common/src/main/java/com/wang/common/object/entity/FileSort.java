package com.wang.common.object.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wang.common.object.vo.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_file_sort")
public class FileSort extends SuperEntity<FileSort> {

    private String projectName;

    private String sortName;

    private String url;

}
