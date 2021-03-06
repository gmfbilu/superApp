package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stickSection;


import java.util.List;


public class StockEntity {

    // 振幅榜
    public List<StockInfo> amplitude_list;

    // 跌幅榜
    public List<StockInfo> down_list;

    // 换手率
    public List<StockInfo> change_list;

    // 涨幅榜
    public List<StockInfo> increase_list;

    public static class StockInfo {

        private int itemType;

        public String stickyHeadName;

        public double rate;
        public String current_price;
        public String stock_code;
        public String stock_name;

        public StockInfo(int itemType) {
            this.itemType = itemType;
        }

        public StockInfo(int itemType, String stickyHeadName) {
            this(itemType);
            this.stickyHeadName = stickyHeadName;
        }

        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        public boolean check;

        @Override
        public String toString() {
            return "StockInfo{" +
                    "itemType=" + itemType +
                    ", stickyHeadName='" + stickyHeadName + '\'' +
                    ", rate=" + rate +
                    ", current_price='" + current_price + '\'' +
                    ", stock_code='" + stock_code + '\'' +
                    ", stock_name='" + stock_name + '\'' +
                    ", check=" + check +
                    '}';
        }
    }

}
