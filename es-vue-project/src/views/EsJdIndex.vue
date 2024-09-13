<template>
  <div class="page" id="app">
    <div id="mallPage" class=" mallist tmall- page-not-market ">

      <!-- 头部搜索 -->
      <div id="header" class=" header-list-app">
        <div class="headerLayout">
          <div class="headerCon ">
            <!-- Logo-->
            <h1 id="mallLogo">
              <img :src="'../../../static/images/jdlogo.png'" alt="">
            </h1>

            <div class="header-extra">

              <!--搜索-->
              <div id="mallSearch" class="mall-search">
                <form name="searchTop" class="mallSearch-form clearfix">
                  <fieldset>
                    <legend>天猫搜索</legend>
                    <div class="mallSearch-input clearfix">
                      <div class="s-combobox" id="s-combobox-685">
                        <div class="s-combobox-input-wrap">
                          <input v-model="keyword" type="text" autocomplete="off" value="dd" id="mq"
                                 class="s-combobox-input" aria-haspopup="true">
                        </div>
                      </div>
                      <button type="submit" id="searchbtn" @click.prevent="searchKeyWord">搜索</button>
                    </div>
                  </fieldset>
                </form>
                <ul class="relKeyTop">
                  <li><a>浩哥说前端</a></li>
                  <li><a>中南大学</a></li>
                  <li><a>浩哥讲Linux</a></li>
                  <li><a>浩哥谈金融</a></li>
                  <li><a>浩哥说Docker</a></li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 商品详情页面 -->
      <div id="content">
        <div class="main">
          <!-- 品牌分类 -->
          <form class="navAttrsForm">
            <div class="attrs j_NavAttrs" style="display:block">
              <div class="brandAttr j_nav_brand">
                <div class="j_Brand attr">
                  <div class="attrKey">
                    品牌
                  </div>
                  <div class="attrValues">
                    <ul class="av-collapse row-2">
                      <li><a href="#"> PlusHuang </a></li>
                      <li><a href="#"> Java </a></li>
                    </ul>
                  </div>
                </div>
              </div>
            </div>
          </form>

          <!-- 排序规则 -->
          <div class="filter clearfix">
            <a class="fSort fSort-cur">综合<i class="f-ico-arrow-d"></i></a>
            <a class="fSort">人气<i class="f-ico-arrow-d"></i></a>
            <a class="fSort">新品<i class="f-ico-arrow-d"></i></a>
            <a class="fSort">销量<i class="f-ico-arrow-d"></i></a>
            <a class="fSort">价格<i class="f-ico-triangle-mt"></i><i class="f-ico-triangle-mb"></i></a>
          </div>

          <!-- 商品详情 -->
          <div class="view grid-nosku">

            <div class="product" v-for="res in result">
              <div class="product-iWrap">
                <!--商品封面-->
                <div class="productImg-wrap">
                  <a class="productImg">
                    <img :src="res.img">
                  </a>
                </div>
                <!--价格-->
                <p class="productPrice">
                  <em v-text="res.price"></em>
                </p>
                <!--标题-->
                <p class="productTitle">
                  <a v-html="res.title">  </a>
                </p>
                <!-- 店铺名 -->
                <div class="productShop">
                  <span>店铺： Jackie的小商店 </span>
                </div>
                <!-- 成交信息 -->
                <p class="productStatus">
                  <span>月成交<em>999笔</em></span>
                  <span>评价 <a>3</a></span>
                </p>
              </div>
            </div>
<!--            <div class="pagination">-->
<!--              <el-pagination-->
<!--                background-->
<!--                layout="sizes,prev, pager, next"-->
<!--                :total="total"-->
<!--                :current-page="pageNo"-->
<!--                :page-sizes="[5, 10, 20, 40]"-->
<!--                :page-size="pageSize"-->
<!--                @size-change="changePageSize"-->
<!--                @current-change="changeCurPage"-->
<!--              >-->
<!--              </el-pagination>-->
<!--            </div>-->
            <div id="J_bottomPage" class="p-wrap">
              <span class="p-num">
                <a class="pn-prev disabled"><i>&lt;</i><em>上一页</em></a>
                <a @click="searchKeyWordByPage(0)" href="javascript:;" class="curr">1</a>
                <a @click="searchKeyWordByPage(1)" href="javascript:;">2</a>
                <a @click="searchKeyWordByPage(2)" href="javascript:;">3</a>
                <b class="pn-break">...</b>
                <!--还需要修改-->
                <a class="pn-next" @click="searchKeyWordByPage(2)" href="javascript:;" title="使用方向键右键也可翻到下一页哦！"><em>下一页</em><i>&gt;</i></a>
                </span>
              <span class="p-skip">
                <em>共<b>100</b>页&nbsp;&nbsp;到第</em>
                <input class="input-txt" type="text" value="1" onkeydown="javascript:if(event.keyCode==13){SEARCH.page_jump(100,1);return false;}">
                <em>页</em>
                <a class="btn btn-default" onclick="SEARCH.page_jump(100,1)" href="javascript:;">确定</a>
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  data(){
    return{
      "keyword":'',//搜索关键字
      "pageNo":0,
      "pageSize":20,
      "total":30,
      "result":[]
    }
  },
  methods:{
    searchKeyWord(){
      var keyword = this.keyword;
      var pageNo = this.pageNo;
      var pageSize = this.pageSize;
      console.log(keyword);
      this.$axios.get('/api/eshandler/queryContent/'+keyword+'/'+pageNo+'/'+pageSize).then(response=>{
        console.log(response.data);
        console.log(response.data.content);
        this.result=response.data.content;
        this.total = response.data.total;
      })
    },
    // changePageSize(val){
    //   this.pageSize = val;
    // },
    // changeCurPage(val){
    //   this.pageNo = val;
    // }
    searchKeyWordByPage(val){
      this.pageNo = val;
      this.searchKeyWord();
    },
    searchKeyWordByNextPage(){
      this.pageNo++;
      this.searchKeyWord();
    },

  }
}
</script>

<style scoped>
@import "../../static/css/style.css";
</style>
