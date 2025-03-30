<!--<template>-->
<!--  <div class="page" id="app">-->
<!--    <div id="mallPage" class=" mallist tmall- page-not-market ">-->

<!--      &lt;!&ndash; 头部搜索 &ndash;&gt;-->
<!--      <div id="header" class=" header-list-app">-->
<!--        <div class="headerLayout">-->
<!--          <div class="headerCon ">-->
<!--            &lt;!&ndash; Logo&ndash;&gt;-->
<!--            <h1 id="mallLogo">-->
<!--              <img :src="'../../../static/images/jdlogo.png'" alt="">-->
<!--            </h1>-->

<!--            <div class="header-extra">-->

<!--              &lt;!&ndash;搜索&ndash;&gt;-->
<!--              <div id="mallSearch" class="mall-search">-->
<!--                <form name="searchTop" class="mallSearch-form clearfix">-->
<!--                  <fieldset>-->
<!--                    <legend>天猫搜索</legend>-->
<!--                    <div class="mallSearch-input clearfix">-->
<!--                      <div class="s-combobox" id="s-combobox-685">-->
<!--                        <div class="s-combobox-input-wrap">-->
<!--                          <input v-model="keyword" type="text" autocomplete="off" value="dd" id="mq"-->
<!--                                 class="s-combobox-input" aria-haspopup="true">-->
<!--                        </div>-->
<!--                      </div>-->
<!--                      <button type="submit" id="searchbtn" @click.prevent="searchKeyWord">搜索</button>-->
<!--                    </div>-->
<!--                  </fieldset>-->
<!--                </form>-->
<!--                <ul class="relKeyTop">-->
<!--                  <li><a>浩哥说前端</a></li>-->
<!--                  <li><a>中南大学</a></li>-->
<!--                  <li><a>浩哥讲Linux</a></li>-->
<!--                  <li><a>浩哥谈金融</a></li>-->
<!--                  <li><a>浩哥说Docker</a></li>-->
<!--                </ul>-->
<!--              </div>-->
<!--            </div>-->
<!--          </div>-->
<!--        </div>-->
<!--      </div>-->

<!--      &lt;!&ndash; 商品详情页面 &ndash;&gt;-->
<!--      <div id="content">-->
<!--        <div class="main">-->
<!--          &lt;!&ndash; 品牌分类 &ndash;&gt;-->
<!--          <form class="navAttrsForm">-->
<!--            <div class="attrs j_NavAttrs" style="display:block">-->
<!--              <div class="brandAttr j_nav_brand">-->
<!--                <div class="j_Brand attr">-->
<!--                  <div class="attrKey">-->
<!--                    品牌-->
<!--                  </div>-->
<!--                  <div class="attrValues">-->
<!--                    <ul class="av-collapse row-2">-->
<!--                      <li><a href="#"> PlusHuang </a></li>-->
<!--                      <li><a href="#"> Java </a></li>-->
<!--                    </ul>-->
<!--                  </div>-->
<!--                </div>-->
<!--              </div>-->
<!--            </div>-->
<!--          </form>-->

<!--          &lt;!&ndash; 排序规则 &ndash;&gt;-->
<!--          <div class="filter clearfix">-->
<!--            <a class="fSort fSort-cur">综合<i class="f-ico-arrow-d"></i></a>-->
<!--            <a class="fSort">人气<i class="f-ico-arrow-d"></i></a>-->
<!--            <a class="fSort">新品<i class="f-ico-arrow-d"></i></a>-->
<!--            <a class="fSort">销量<i class="f-ico-arrow-d"></i></a>-->
<!--            <a class="fSort">价格<i class="f-ico-triangle-mt"></i><i class="f-ico-triangle-mb"></i></a>-->
<!--          </div>-->

<!--          &lt;!&ndash; 商品详情 &ndash;&gt;-->
<!--          <div class="view grid-nosku">-->

<!--            <div class="product" v-for="res in result">-->
<!--              <div class="product-iWrap">-->
<!--                &lt;!&ndash;商品封面&ndash;&gt;-->
<!--                <div class="productImg-wrap">-->
<!--                  <a class="productImg">-->
<!--                    <img :src="res.img">-->
<!--                  </a>-->
<!--                </div>-->
<!--                &lt;!&ndash;价格&ndash;&gt;-->
<!--                <p class="productPrice">-->
<!--                  <em v-text="res.price"></em>-->
<!--                </p>-->
<!--                &lt;!&ndash;标题&ndash;&gt;-->
<!--                <p class="productTitle">-->
<!--                  <a v-html="res.title">  </a>-->
<!--                </p>-->
<!--                &lt;!&ndash; 店铺名 &ndash;&gt;-->
<!--                <div class="productShop">-->
<!--                  <span>店铺： Jackie的小商店 </span>-->
<!--                </div>-->
<!--                &lt;!&ndash; 成交信息 &ndash;&gt;-->
<!--                <p class="productStatus">-->
<!--                  <span>月成交<em>999笔</em></span>-->
<!--                  <span>评价 <a>3</a></span>-->
<!--                </p>-->
<!--              </div>-->
<!--            </div>-->
<!--&lt;!&ndash;            <div class="pagination">&ndash;&gt;-->
<!--&lt;!&ndash;              <el-pagination&ndash;&gt;-->
<!--&lt;!&ndash;                background&ndash;&gt;-->
<!--&lt;!&ndash;                layout="sizes,prev, pager, next"&ndash;&gt;-->
<!--&lt;!&ndash;                :total="total"&ndash;&gt;-->
<!--&lt;!&ndash;                :current-page="pageNo"&ndash;&gt;-->
<!--&lt;!&ndash;                :page-sizes="[5, 10, 20, 40]"&ndash;&gt;-->
<!--&lt;!&ndash;                :page-size="pageSize"&ndash;&gt;-->
<!--&lt;!&ndash;                @size-change="changePageSize"&ndash;&gt;-->
<!--&lt;!&ndash;                @current-change="changeCurPage"&ndash;&gt;-->
<!--&lt;!&ndash;              >&ndash;&gt;-->
<!--&lt;!&ndash;              </el-pagination>&ndash;&gt;-->
<!--&lt;!&ndash;            </div>&ndash;&gt;-->
<!--            <div id="J_bottomPage" class="p-wrap">-->
<!--              <span class="p-num">-->
<!--                <a class="pn-prev disabled"><i>&lt;</i><em>上一页</em></a>-->
<!--                <a @click="searchKeyWordByPage(0)" href="javascript:" class="curr">1</a>-->
<!--                <a @click="searchKeyWordByPage(1)" href="javascript:">2</a>-->
<!--                <a @click="searchKeyWordByPage(2)" href="javascript:">3</a>-->
<!--                <b class="pn-break">...</b>-->
<!--                &lt;!&ndash;还需要修改&ndash;&gt;-->
<!--                <a class="pn-next" @click="searchKeyWordByPage(2)" href="javascript:" title="使用方向键右键也可翻到下一页哦！"><em>下一页</em><i>&gt;</i></a>-->
<!--                </span>-->
<!--              <span class="p-skip">-->
<!--                <em>共<b>100</b>页&nbsp;&nbsp;到第</em>-->
<!--                <input class="input-txt" type="text" value="1" onkeydown="if(event.keyCode==13){SEARCH.page_jump(100,1);return false;}">-->
<!--                <em>页</em>-->
<!--                <a class="btn btn-default" onclick="SEARCH.page_jump(100,1)" href="javascript:">确定</a>-->
<!--              </span>-->
<!--            </div>-->
<!--          </div>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->

<!--export default {-->
<!--  data(){-->
<!--    return{-->
<!--      "keyword":'',//搜索关键字-->
<!--      "pageNo":0,-->
<!--      "pageSize":20,-->
<!--      "total":30,-->
<!--      "result":[]-->
<!--    }-->
<!--  },-->
<!--  methods:{-->
<!--    searchKeyWord(){-->
<!--      var keyword = this.keyword;-->
<!--      var pageNo = this.pageNo;-->
<!--      var pageSize = this.pageSize;-->
<!--      console.log(keyword);-->
<!--      this.$axios.get('/api/eshandler/queryContent/'+keyword+'/'+pageNo+'/'+pageSize).then(response=>{-->
<!--        console.log(response.data);-->
<!--        console.log(response.data.content);-->
<!--        this.result=response.data.content;-->
<!--        this.total = response.data.total;-->
<!--      })-->
<!--    },-->
<!--    // changePageSize(val){-->
<!--    //   this.pageSize = val;-->
<!--    // },-->
<!--    // changeCurPage(val){-->
<!--    //   this.pageNo = val;-->
<!--    // }-->
<!--    searchKeyWordByPage(val){-->
<!--      this.pageNo = val;-->
<!--      this.searchKeyWord();-->
<!--    },-->
<!--    searchKeyWordByNextPage(){-->
<!--      this.pageNo++;-->
<!--      this.searchKeyWord();-->
<!--    },-->

<!--  }-->
<!--}-->
<!--</script>-->

<!--<style scoped>-->
<!--@import "../../static/css/style.css";-->
<!--</style>-->
