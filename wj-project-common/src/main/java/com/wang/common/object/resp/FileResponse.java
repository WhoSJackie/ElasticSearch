package com.wang.common.object.resp;

import com.wang.common.object.entity.File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {

    private List<String> errFile;

    private List<File> succFileList;

}
