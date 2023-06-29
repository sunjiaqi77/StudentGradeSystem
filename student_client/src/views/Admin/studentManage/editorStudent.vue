<template>
  <div>
    <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="学生ID" prop="sid">
        <el-input v-model="ruleForm.sid" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="学生姓名" prop="sname">
        <el-input v-model="ruleForm.sname" :value="ruleForm.sname"></el-input>
      </el-form-item>
      <el-form-item label="学生班级ID" prop="classId">
        <el-input v-model="ruleForm.classId" :value="ruleForm.classId"></el-input>
      </el-form-item>
      <el-form-item label="初始密码" prop="password">
        <el-input v-model="ruleForm.password" :value="ruleForm.password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      ruleForm: {
        sid: null,
        sname: null,
        password: null,
      },
      rules: {
        sid: [
          { required: true, message: '请输入id', trigger: 'blur' }
        ],
        sname: [
          { required: true, message: '请输入名称', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur' }
        ],
        classId: [
          {required: true}
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'change' }
        ],
      }
    };
  },
  created() {
    const that = this;
    if (this.$route.query.sid === undefined || this.$route.query.sid == null) {
      this.ruleForm.sid = 9;
    } else {
      this.ruleForm.sid = this.$route.query.sid;
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const that = this;
          console.log(this.ruleForm);
          axios.post("http://localhost:10086/student/updateStudent", this.ruleForm).then(function (resp) {
            if (resp.data === true) {
              that.$message({
                showClose: true,
                message: '编辑成功',
                type: 'success'
              });
            } else {
              that.$message.error('编辑失败，请检查数据库');
            }
            that.$router.push("/studentList");
          });
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>
