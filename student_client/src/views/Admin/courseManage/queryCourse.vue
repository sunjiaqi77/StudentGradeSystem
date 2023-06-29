<template>
  <div>
    <el-container>
      <el-main>
        <el-card>
          <el-form :inline="true" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px" class="demo-ruleForm">
            <el-form-item label="课程名" prop="cname">
              <el-input v-model="ruleForm.cname"></el-input>
            </el-form-item>
            <el-form-item label="课程学分" prop="ccredit">
              <el-input v-model="ruleForm.ccredit"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card>
          <course-list :ruleForm="ruleForm" :isActive="true"></course-list>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>
<script>
import CourseList from "@/views/Admin/courseManage/courseList";
export default {
  components: {CourseList},
  data() {
    return {
      ruleForm: {
        cid: null,
        cname: null,
      },
      rules: {
        cname: [
        ],
        ccredit: [
          { message: '请输入学分', trigger: 'change' },
          { type: 'number', message: '请输入数字', trigger: 'blur' },
        ]
      }
    };
  },
  create() {
    this.ccredit = null
    this.cname = null
  },
  methods: {
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>