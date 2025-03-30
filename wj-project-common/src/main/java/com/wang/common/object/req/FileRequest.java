package com.wang.common.object.req;

import com.wang.common.object.entity.File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileRequest {

    private List<String>  uidList;

}
