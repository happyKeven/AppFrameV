package com.appframe.data.responseEntity;

import com.keven.library.base.BaseResponse;

import java.util.List;

import lecho.lib.hellocharts.util.ChartUtils;

/**
 * <p>Title:ColumnChartResp</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright(c)2016</p>
 * User: 刘开峰
 * Date: 2016-12-20
 * Time: 18:20
 */
public class ColumnChartResp extends BaseResponse{
   private ColumnChartBean data;

    @Override
    public Object getData() {
        return data;
    }

    public class ColumnChartBean {

        private boolean hasAxes = true; // 是否有坐标轴
        private boolean hasAxesNames = true; // 是否有坐标轴的名称
        private boolean hasLabels = true; // 曲线的数据坐标是否加上备注
        private boolean hasLabelForSelected = false; // 点击数据坐标提示数据(设置了这个line.setHasLabels(true);就无效)

        private String[] xLabels = new String[]{}; // X轴的标注 {"10-22","11-22","12-22","1-22","6-22","5-23","5-22","6-22","5-23","5-22"};
        private List<List<SubValue>> subValues;
        private String         axisxName; // X轴的名字
        private String         axisyName; // Y轴的名字

        public boolean isHasAxes() {
            return hasAxes;
        }

        public void setHasAxes(boolean hasAxes) {
            this.hasAxes = hasAxes;
        }

        public boolean isHasAxesNames() {
            return hasAxesNames;
        }

        public void setHasAxesNames(boolean hasAxesNames) {
            this.hasAxesNames = hasAxesNames;
        }

        public boolean isHasLabels() {
            return hasLabels;
        }

        public void setHasLabels(boolean hasLabels) {
            this.hasLabels = hasLabels;
        }

        public boolean isHasLabelForSelected() {
            return hasLabelForSelected;
        }

        public void setHasLabelForSelected(boolean hasLabelForSelected) {
            this.hasLabelForSelected = hasLabelForSelected;
        }

        public String[] getxLabels() {
            return xLabels;
        }

        public void setxLabels(String[] xLabels) {
            this.xLabels = xLabels;
        }

        public List<List<SubValue>> getSubValues() {
            return subValues;
        }

        public void setSubValues(List<List<SubValue>> subValues) {
            this.subValues = subValues;
        }

        public String getAxisxName() {
            return axisxName;
        }

        public void setAxisxName(String axisxName) {
            this.axisxName = axisxName;
        }

        public String getAxisyName() {
            return axisyName;
        }

        public void setAxisyName(String axisyName) {
            this.axisyName = axisyName;
        }
    }

    public class SubValue {
        private String value;
        private int color = ChartUtils.pickColor();

        public SubValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }
}
