<template>
  <div class="header">
    <div class="logo">测试表头</div>
    <div class="user-info">
      <span>
          <el-button type="text" @click="getHelp"  round>
              <span><svg width="30px" height="31px" id="图层_1" x="0px" y="0px"  viewBox="0 -10 30 36"  xml:space="preserve" class="jss42"><g><path d="M0.5,20.825l29,3v-17L2.843,3.149C1.604,2.978,0.5,3.94,0.5,5.191V20.825z" style="fill: rgb(213, 224, 238);"></path><path d="M0.5,9.761v14.874c0,0.637,0.551,1.133,1.184,1.068l20.892-2.161 c0.225-0.023,0.451,0.025,0.647,0.138l5.389,3.111c0.395,0.228,0.888-0.057,0.888-0.513v-3.453v-6V6.057 c0-0.651-0.575-1.152-1.22-1.063L1.427,8.697C0.896,8.771,0.5,9.225,0.5,9.761z" style="fill: rgb(40, 123, 247);"></path><g><path d="M5.224,13.384v4.591H4.021v-4.591H2.649v-1.02h3.947v1.02H5.224z" style="fill: rgb(255, 255, 255);"></path><path d="M7.7,15.181c0.11-0.365,0.268-0.639,0.473-0.819c0.206-0.182,0.46-0.272,0.765-0.272 c0.069,0,0.183,0.013,0.341,0.038c0.031,0.006,0.054,0.01,0.069,0.012l-0.165,1.173c-0.082-0.033-0.164-0.059-0.247-0.075 s-0.17-0.024-0.263-0.024c-0.332,0-0.571,0.085-0.716,0.255c-0.146,0.17-0.218,0.454-0.218,0.853v1.655H6.646v-3.759h1.077 L7.7,15.181z" style="fill: rgb(255, 255, 255);"></path><path d="M9.788,12.671c0-0.174,0.062-0.323,0.188-0.448c0.125-0.125,0.274-0.188,0.448-0.188 c0.176,0,0.328,0.062,0.454,0.186s0.189,0.274,0.189,0.45c0,0.177-0.063,0.327-0.189,0.452s-0.278,0.188-0.454,0.188 c-0.174,0-0.323-0.064-0.448-0.192C9.851,12.992,9.788,12.843,9.788,12.671z M9.884,17.975v-3.759h1.088v3.759H9.884z" style="fill: rgb(255, 255, 255);"></path><path d="M12.08,19.684v-5.468h1.046v0.62c0.143-0.25,0.319-0.438,0.527-0.562 c0.208-0.123,0.451-0.186,0.727-0.186c0.534,0,0.952,0.177,1.253,0.531c0.302,0.354,0.452,0.847,0.452,1.48 c0,0.61-0.151,1.101-0.456,1.47c-0.304,0.369-0.704,0.554-1.199,0.554c-0.299,0-0.553-0.056-0.761-0.167 c-0.208-0.11-0.387-0.285-0.535-0.522c0.013,0.095,0.022,0.197,0.029,0.309c0.006,0.11,0.01,0.237,0.01,0.381v1.56H12.08z  M13.076,16.089c0,0.34,0.084,0.604,0.251,0.794c0.167,0.188,0.4,0.283,0.7,0.283c0.299,0,0.532-0.095,0.699-0.283 c0.167-0.189,0.251-0.454,0.251-0.794c0-0.339-0.085-0.607-0.255-0.803s-0.404-0.293-0.703-0.293 c-0.289,0-0.518,0.099-0.688,0.295C13.161,15.485,13.076,15.752,13.076,16.089z" style="fill: rgb(255, 255, 255);"></path><path d="M17.012,17.975v-5.61h1.379c0.736,0,1.279,0.141,1.631,0.422s0.527,0.715,0.527,1.303 c0,0.544-0.178,0.971-0.531,1.28c-0.354,0.309-0.844,0.463-1.47,0.463h-0.329v2.143H17.012z M18.219,14.898h0.172 c0.327,0,0.565-0.067,0.713-0.203c0.148-0.136,0.223-0.353,0.223-0.651c0-0.276-0.074-0.478-0.223-0.604 c-0.147-0.127-0.386-0.189-0.713-0.189h-0.172V14.898z" style="fill: rgb(255, 255, 255);"></path><path d="M24.768,17.975H23.71l0.016-0.54c-0.148,0.237-0.327,0.412-0.535,0.522 c-0.208,0.111-0.462,0.167-0.761,0.167c-0.498,0-0.899-0.186-1.205-0.556c-0.305-0.37-0.458-0.859-0.458-1.468 c0-0.634,0.15-1.127,0.45-1.48c0.301-0.354,0.718-0.531,1.252-0.531c0.278,0,0.522,0.062,0.733,0.188 c0.211,0.126,0.388,0.312,0.53,0.56v-0.62h1.035V17.975z M23.772,16.089c0-0.337-0.085-0.604-0.255-0.801 c-0.17-0.196-0.399-0.295-0.688-0.295c-0.296,0-0.529,0.098-0.699,0.293s-0.255,0.464-0.255,0.803c0,0.34,0.084,0.604,0.251,0.794 c0.168,0.188,0.399,0.283,0.696,0.283c0.301,0,0.535-0.095,0.701-0.283C23.689,16.693,23.772,16.429,23.772,16.089z" style="fill: rgb(255, 255, 255);"></path><path d="M25.837,17.975v-5.939h1.088v5.939H25.837z" style="fill: rgb(255, 255, 255);"></path></g></g></svg></span>
              <span style="color: white">帮助</span>
          </el-button>
      </span>
<!--      <span>&nbsp</span>-->
      <el-dropdown trigger="click" @command="handleCommand">
        <span class="el-dropdown-link">
          <img class="user-logo" src="/static/images/img.jpg"/>
          {{ username1 }}
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="loginout">退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>
<script>
export default {
    data() {
        return {
            name: 'wang',
            username1: "testAccount1"
        }
    },

    mounted() {

        // this.username1 = this.$store.getters.name
        // this.$soa(
        //         "currentUser",{},returnMsg =>{
        //             if(returnMsg.succ){
        //                 this.username1 = returnMsg.data.result
        //             }else{
        //                 window.location.href="/login"
        //             }
        //     }
        // )

    },
    methods: {
        handleCommand(command) {
            if (command == 'loginout') {
                localStorage.removeItem('ms_username')
                window.location.href = "/logout"
            }
        },
        getHelp(){
            window.open('https://www.bing.com','_blank');
        },
        getDocument(){
            let url = this.$router.resolve('/docs');
            window.open(url.href,'_blank');
        }

    }
}
</script>
<style scoped>
.header {
    position: relative;
    box-sizing: border-box;
    width: 100%;
    height: 70px;
    font-size: 22px;
    line-height: 70px;
    color: #ffffff;
}

.header .logo {
    float: left;
    width: 250px;
    text-align: center;
}

.user-info {
    float: right;
    padding-right: 50px;
    font-size: 16px;
    color: #fff;
}

.user-info .el-dropdown-link {
    position: relative;
    display: inline-block;
    padding-left: 50px;
    color: #fff;
    cursor: pointer;
    vertical-align: middle;
}

.user-info .user-logo {
    position: absolute;
    left: 0;
    top: 15px;
    width: 40px;
    height: 40px;
    border-radius: 50%;
}

.el-dropdown-menu__item {
    text-align: center;
}
</style>
