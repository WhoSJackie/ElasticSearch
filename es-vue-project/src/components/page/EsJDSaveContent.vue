<template>

    <div>
        <el-input v-model="keyWord" placeholder="请输入需要获取内容的关键词"></el-input>
      <span>
        <el-button trpe="primary" @click="saveContent">获取数据</el-button>
      </span>
    </div>

</template>

<script>
export default {
  data(){
    return {
      "keyWord":'',
    }
  },
  methods:{
      saveContent(){
        var keyWord = this.keyWord;
          this.$axios.get('/eshandler/saveContent/'+keyWord).then(res=>{
            console.log(res.data);
              if (res.data){
                  this.$message({
                      message:'成功存入es中',
                      type:'success'
                  })
              } else{
                this.$message({
                  message:'存入es失败',
                  type:'error'
                })
              }
          })
      }
  }
}
</script>

<style scoped>

</style>
