package com.travel.aroundthekorea.batch.spot.api.dto.response;

import java.util.List;

public class PublicDataResponse {


    private Response response;

    protected PublicDataResponse() {
    }

    public PublicDataResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public static class Response {
        private Header header;
        private Body body;

        protected Response() {
        }

        public Response(Header header, Body body) {
            this.header = header;
            this.body = body;
        }

        public Header getHeader() {
            return header;
        }

        public Body getBody() {
            return body;
        }
    }

    public static class Header {
        private String resultCode;
        private String resultMsg;

        protected Header() {
        }

        public Header(String resultCode, String resultMsg) {
            this.resultCode = resultCode;
            this.resultMsg = resultMsg;
        }

        public String getResultCode() {
            return resultCode;
        }

        public String getResultMsg() {
            return resultMsg;
        }
    }

    public static class Body {
        private Items items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;

        protected Body() {
        }

        public Body(Items items, int numOfRows, int pageNo, int totalCount) {
            this.items = items;
            this.numOfRows = numOfRows;
            this.pageNo = pageNo;
            this.totalCount = totalCount;
        }

        public Items getItems() {
            return items;
        }

        public int getNumOfRows() {
            return numOfRows;
        }

        public int getPageNo() {
            return pageNo;
        }

        public int getTotalCount() {
            return totalCount;
        }
    }

    public static class Items {
        private List<Item> item;

        protected Items() {
        }

        public Items(List<Item> item) {
            this.item = item;
        }

        public List<Item> getItem() {
            return item;
        }
    }

    public static class Item {
        private String mapx;
        private String mapy;
        private String mlevel;
        private String modifiedtime;
        private String showflag;
        private String sigungucode;
        private String tel;
        private String title;
        private String addr1;
        private String addr2;
        private String areacode;
        private String booktour;
        private String cat1;
        private String cat2;
        private String cat3;
        private String contentid;
        private String contenttypeid;
        private String createdtime;
        private String cpyrhtDivCd;
        private String firstimage;
        private String firstimage2;
        private String zipcode;

        protected Item() {
        }

        public Item(String mapx, String mapy, String mlevel, String modifiedtime, String showflag, String sigungucode, String tel, String title, String addr1, String addr2, String areacode, String booktour, String cat1, String cat2, String cat3, String contentid, String contenttypeid, String createdtime, String cpyrhtDivCd, String firstimage, String firstimage2, String zipcode) {
            this.mapx = mapx;
            this.mapy = mapy;
            this.mlevel = mlevel;
            this.modifiedtime = modifiedtime;
            this.showflag = showflag;
            this.sigungucode = sigungucode;
            this.tel = tel;
            this.title = title;
            this.addr1 = addr1;
            this.addr2 = addr2;
            this.areacode = areacode;
            this.booktour = booktour;
            this.cat1 = cat1;
            this.cat2 = cat2;
            this.cat3 = cat3;
            this.contentid = contentid;
            this.contenttypeid = contenttypeid;
            this.createdtime = createdtime;
            this.cpyrhtDivCd = cpyrhtDivCd;
            this.firstimage = firstimage;
            this.firstimage2 = firstimage2;
            this.zipcode = zipcode;
        }

        public String getMapx() {
            return mapx;
        }

        public String getMapy() {
            return mapy;
        }

        public String getMlevel() {
            return mlevel;
        }

        public String getModifiedtime() {
            return modifiedtime;
        }

        public String getShowflag() {
            return showflag;
        }

        public String getSigungucode() {
            return sigungucode;
        }

        public String getTel() {
            return tel;
        }

        public String getTitle() {
            return title;
        }

        public String getAddr1() {
            return addr1;
        }

        public String getAddr2() {
            return addr2;
        }

        public String getAreacode() {
            return areacode;
        }

        public String getBooktour() {
            return booktour;
        }

        public String getCat1() {
            return cat1;
        }

        public String getCat2() {
            return cat2;
        }

        public String getCat3() {
            return cat3;
        }

        public String getContentid() {
            return contentid;
        }

        public String getContenttypeid() {
            return contenttypeid;
        }

        public String getCreatedtime() {
            return createdtime;
        }

        public String getCpyrhtDivCd() {
            return cpyrhtDivCd;
        }

        public String getFirstimage() {
            return firstimage;
        }

        public String getFirstimage2() {
            return firstimage2;
        }

        public String getZipcode() {
            return zipcode;
        }
    }
}
