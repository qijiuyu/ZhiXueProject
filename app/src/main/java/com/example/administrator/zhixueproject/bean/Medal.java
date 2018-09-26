package com.example.administrator.zhixueproject.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 勋章管理
 */
public class Medal extends BaseBean{

    private MedalBean data;

    public MedalBean getData() {
        return data;
    }

    public void setData(MedalBean data) {
        this.data = data;
    }

    public static class MedalBean implements Serializable{

        private List<MedalList> medalTypeList=new ArrayList<>();

        public List<MedalList> getMedalTypeList() {
            return medalTypeList;
        }

        public void setMedalTypeList(List<MedalList> medalTypeList) {
            this.medalTypeList = medalTypeList;
        }
    }

    public static class MedalList implements Serializable{
        private long medalTypeId;
        private String medalTypeMig;
        private String medalTypeInfo;
        private String medalTypeName;

        public long getMedalTypeId() {
            return medalTypeId;
        }

        public void setMedalTypeId(long medalTypeId) {
            this.medalTypeId = medalTypeId;
        }

        public String getMedalTypeMig() {
            return medalTypeMig;
        }

        public void setMedalTypeMig(String medalTypeMig) {
            this.medalTypeMig = medalTypeMig;
        }

        public String getMedalTypeInfo() {
            return medalTypeInfo;
        }

        public void setMedalTypeInfo(String medalTypeInfo) {
            this.medalTypeInfo = medalTypeInfo;
        }

        public String getMedalTypeName() {
            return medalTypeName;
        }

        public void setMedalTypeName(String medalTypeName) {
            this.medalTypeName = medalTypeName;
        }
    }
}
