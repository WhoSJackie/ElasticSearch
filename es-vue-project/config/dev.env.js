'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',

  //开发环境,目前沒有加网关
  PIC_URL_PRE: '"http://localhost:8089/"',
  ADMIN_API: '"http://localhost:8089/mogu-admin"',
  PICTURE_API: '"http://192.168.3.96:8001/picture"',
  WEB_API: '"http://192.168.3.96:8001/api"',
  Search_API: '"http://localhost:8089/mogu-search"',
  Spider_API: '"http://localhost:8089/mogu-spider"',
  FILE_API: '"http://192.168.3.96:8001/"',
  BLOG_WEB_URL: '"http://localhost:8089/"',
  SOLR_API: '"http://localhost:8080/solr"',
  ELASTIC_SEARCH: '"http://localhost:5601"',

})
