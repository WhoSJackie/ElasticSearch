<template>
  <div class="sidebar">
    <el-menu :default-active="onRoutes" class="el-menu-vertical-demo" unique-opened router>
      <template v-for="item in items">
        <template v-if="item.subs">
          <el-submenu
            :index="item.index"
            :key="item.index"
          >
            <template slot="title">
              <i :class="item.icon"></i>
              {{ item.title }}
            </template>
            <el-menu-item
              v-for="(subItem, i) in item.subs"
              :key="i"
              :index="subItem.index"
            ><i :class="item.icon"></i>
              {{ subItem.title }}
            </el-menu-item>
          </el-submenu>
        </template>

        <template v-else>
          <el-menu-item
            :index="item.index"
            :key="item.index"
          >
            <i :class="item.icon"></i>
            {{ item.title }}
          </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>

</template>

<script>
export default {
  data(){
    return {
      items:[
        {
          icon:"el-icon-setting",
          index:"/",
          title:"主页",
        },
        {
          icon:"el-icon-star-on",
          index:"1",
          title:"ES模块",
          subs:[
            {
              icon:"el-icon-star-on",
              index:"esIndex",
              title:"es查询",
            },
            {
              icon:"el-icon-star-on",
              index:"saveEsContent",
              title:"es获取数据",
            },
          ]
        },
        {
          icon:"el-icon-setting",
          index:"es",
          title:'helloworld'
        }
      ]
    }
  },
  computed: {
    onRoutes() {
      return this.$route.path.replace("/", "");
    },
  },
}
</script>

<style scoped>
.sidebar {
  display: block;
  position: absolute;
  width: 250px;
  left: 0;
  top: 70px;
  bottom: 0;
  background: #2e363f;
}
.sidebar > ul {
  height: 100%;
}
</style>
