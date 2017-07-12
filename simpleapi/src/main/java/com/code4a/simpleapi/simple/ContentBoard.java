package com.code4a.simpleapi.simple;

import java.io.Serializable;
import java.util.List;

/**
 * Created by code4a on 2017/7/11.
 */

public class ContentBoard {


    /**
     * total : 3
     * data : [{"category":"常用","data":{"count":2,"list":[[{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/0.png","name":"emoji0"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/1.png","name":"emoji1"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/2.png","name":"emoji2"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/3.png","name":"emoji3"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/4.png","name":"emoji4"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/5.png","name":"emoji5"}],[{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/6.png","name":"emoji6"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/7.png","name":"emoji7"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/8.png","name":"emoji8"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/9.png","name":"emoji9"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/10.png","name":"emoji10"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/11.png","name":"emoji11"}]]}},{"category":"热门","data":{"count":2,"list":[[{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/0.png","name":"emoji0"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/1.png","name":"emoji1"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/2.png","name":"emoji2"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/3.png","name":"emoji3"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/4.png","name":"emoji4"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/5.png","name":"emoji5"}],[{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/6.png","name":"emoji6"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/7.png","name":"emoji7"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/8.png","name":"emoji8"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/9.png","name":"emoji9"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/10.png","name":"emoji10"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/11.png","name":"emoji11"}]]}},{"category":"收藏","data":{"count":2,"list":[[{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/0.png","name":"emoji0"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/1.png","name":"emoji1"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/2.png","name":"emoji2"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/3.png","name":"emoji3"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/4.png","name":"emoji4"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/5.png","name":"emoji5"}],[{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/6.png","name":"emoji6"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/7.png","name":"emoji7"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/8.png","name":"emoji8"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/9.png","name":"emoji9"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/10.png","name":"emoji10"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/11.png","name":"emoji11"}]]}}]
     */

    private int total;
    private List<DataBeanX> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBeanX> getData() {
        return data;
    }

    public void setData(List<DataBeanX> data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * category : 常用
         * data : {"count":2,"list":[[{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/0.png","name":"emoji0"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/1.png","name":"emoji1"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/2.png","name":"emoji2"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/3.png","name":"emoji3"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/4.png","name":"emoji4"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/5.png","name":"emoji5"}],[{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/6.png","name":"emoji6"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/7.png","name":"emoji7"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/8.png","name":"emoji8"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/9.png","name":"emoji9"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/10.png","name":"emoji10"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/11.png","name":"emoji11"}]]}
         */

        private String category;
        private DataBean data;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean implements Serializable{
            /**
             * count : 2
             * list : [[{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/0.png","name":"emoji0"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/1.png","name":"emoji1"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/2.png","name":"emoji2"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/3.png","name":"emoji3"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/4.png","name":"emoji4"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/5.png","name":"emoji5"}],[{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/6.png","name":"emoji6"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/7.png","name":"emoji7"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/8.png","name":"emoji8"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/9.png","name":"emoji9"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/10.png","name":"emoji10"},{"icon":"https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/11.png","name":"emoji11"}]]
             */

            private int count;
            private List<List<ListBean>> list;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public List<List<ListBean>> getList() {
                return list;
            }

            public void setList(List<List<ListBean>> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * icon : https://raw.githubusercontent.com/code4a/code4aApi/master/res/emoji/0.png
                 * name : emoji0
                 */

                private String icon;
                private String name;

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
