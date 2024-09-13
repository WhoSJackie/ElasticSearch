<template>
  <div class="hello">
    <el-button type="primary" @click="openLoginBox">登陆</el-button>
    <LoginBox v-if="showLogin" @closeLoginBox="closeLoginBox"></LoginBox>
  </div>
</template>

<script>
import LoginBox from "../components/LoginBox";
export default {
  components:{
    LoginBox
  },
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      showLogin:false,
    }
  },
  methods:{
    closeLoginBox: function () {
      this.showLogin = false;
    },
    openLoginBox(){
      this.showLogin = true;
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
