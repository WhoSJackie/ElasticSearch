<template>
<div id="table" class="app-container calendar-list-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container" style="margin: 10px 0 10px 0;">
      <el-button class="filter-item" type="primary" @click="download" icon="el-icon-edit" >下载</el-button>
    </div>
</div>
</template>

<script>
import { getToken } from '@/utils/auth'
import Vue from "vue";

export default {
  data() {
    return {

    };
  },
  methods: {
      download(){
        window.location.href='/api/cat/download';
      }
  }
};
</script>

<style scoped>
  .media-title {
    color: #8492a6;
    font-size: 14px;
    padding: 3px;
    display: inline-block;
    white-space: nowrap;
    width: 60%;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .el-image {
    width: 100%;
    height: 160px;
  }
</style>
