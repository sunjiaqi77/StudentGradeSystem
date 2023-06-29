<template>
  <div>
    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%">
      <el-table-column
          fixed
          prop="cid"
          label="课程号"
          width="150">
      </el-table-column>
      <el-table-column
          prop="cname"
          label="课程名"
          width="150">
      </el-table-column>
      <el-table-column
          prop="ccredit"
          label="学分"
          width="150">
      </el-table-column>
      <el-table-column
          prop="tid"
          label="授课教师ID"
          width="150">
      </el-table-column>
      <el-table-column
          prop="tname"
          label="授课教师姓名"
          width="150">
      </el-table-column>
      <el-table-column
          label="操作"
          width="100">
        <template slot-scope="scope">
          <el-popconfirm
              confirm-button-text='删除'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="删除不可复原"
              @confirm="deleteTeacher(scope.row)"
          >
            <el-button slot="reference" type="text" size="small">删除</el-button>
          </el-popconfirm>
          <el-button @click="editor(scope.row)" type="text" size="small" style="padding-left: 13px;">编辑</el-button>
        </template>
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
    deleteTeacher(row) {
      const that = this
      axios.get('http://localhost:10086/course/deleteById?cid=' + row.cid).then(function (resp) {
        console.log(resp)
        if (resp.data === true) {
          that.$message({
            showClose: true,
            message: '删除成功',
            type: 'success'
          });
          window.location.reload()
        }
        else {
          that.$message({
            showClose: true,
            message: '删除出错，请查询数据库连接',
            type: 'error'
          });
        }
      }).catch(function (error) {
        that.$message({
          showClose: true,
          message: '删除出错，存在外键依赖',
          type: 'error'
        });
      })
    },
    loadData() {
      const that = this;
      axios.post("http://localhost:10086/course/findBySearch", that.ruleForm)
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
    editor(row) {
      this.$router.push({
        path: '/editorCourse',
        query: {
          cid: row.cid,
        }
      })
    }
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
  },
  watch: {
    ruleForm: {
      handler(newRuleForm, oldRuleForm) {
        console.log("组件监听 form")
        console.log(newRuleForm)
        const that = this
        that.tmpList = null
        that.total = null
        that.tableData = null
        axios.post("http://localhost:10086/course/findBySearch", newRuleForm).then(function (resp) {
          console.log("查询结果:");
          console.log(resp)
          that.tmpList = resp.data
          that.total = resp.data.length
          let start = 0, end = that.pageSize
          let length = that.tmpList.length
          let ans = (end < length) ? end : length
          that.tableData = that.tmpList.slice(start, ans)
        })
      },
      deep: true,
      immediate: true
    }
  },
}
</script>