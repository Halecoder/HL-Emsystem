package com.hl.emsystem.utils.excel.convert;

import cn.hutool.core.convert.Convert;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * Excel转换器
 */
@Slf4j
public class ExcelLongConvert implements Converter<Long[]> {

    //在java中数据类型
    @Override
    public Class<Long[]> supportJavaTypeKey() {
        return Long[].class;
    }

    // 在excel中的数据类型
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    // 将excel的数据类型转为java数据类型
    @Override
    public Long[] convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if(cellData.getType().equals(CellDataTypeEnum.STRING)){
//            将[1,3]这种字符串转为数组
            String str  = cellData.getStringValue().replace("[", "").replace("]","");
            return Convert.toLongArray(str);
        }
        return Convert.toLongArray(cellData.getNumberValue());
    }

    // 将java的数据类型转为excel数据类型
    @Override
    public WriteCellData<Object> convertToExcelData(Long[] object, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        WriteCellData<Object> cellData = new WriteCellData<>();
        if (object.length > 1) {
            cellData.setType(CellDataTypeEnum.STRING);
            return new WriteCellData<>(Arrays.toString(Convert.toStrArray(object)));
        }
        return new WriteCellData<>(Arrays.toString(Convert.toNumberArray(object)));
    }

}
