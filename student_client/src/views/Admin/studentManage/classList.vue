<template>
    <div>
      <el-table
          :data="tableData"
          border
          stripe
          style="width: 100%">
        <el-table-column
            prop="id"
            label="班级ID"
            width="150">
        </el-table-column>
        <el-table-column
            prop="name"
            label="班级名"
            width="150">
        </el-table-column>
      </el-table>
      <el-pagination
          background
          layout="prev, pager, next"
          :total="total"
          :page-size="pageSize"
          @current-change="changePage"
      > 
      </el-pagination>
    </div>
  </template>
  
  <script>
  export default {
    methods: {
      select(row) {
        console.log(row)
      },
      loadData() {
        const that = this;
        axios.post("http://localhost:10086/class/findAll", that.ruleForm)
          .then(function (resp) {
            that.tmpList = resp.data;
            console.log(that.tmpList.length);
            that.total = that.tmpList.length;
            that.changePage(1); // 初始化显示第一页数据
          })
          .catch(function (error) {
            console.error(error);
          });
      },
      changePage(page) {
        const that = this;
        page = page - 1;
        const start = page * that.pageSize;
        const end = start + that.pageSize;
        that.tableData = that.tmpList.slice(start, end);
      },
    },
    created() {
      this.loadData();
    },
    data() {
      return {
        tableData: null,
        pageSize: 7,
        total: null,
        tmpList: null,
      }
    },
    props: {
      ruleForm: Object,
      isActive: Boolean
    }
  }
  </script>