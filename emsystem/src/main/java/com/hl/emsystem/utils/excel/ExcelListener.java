package com.hl.emsystem.utils.excel;

import com.alibaba.excel.read.listener.ReadListener;

public interface ExcelListener<T> extends ReadListener<T> {
    ExcelResult<T> getExcelResult();
}
