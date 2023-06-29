<template>
  <div>
    <el-card>
      <el-form style="width: 60%" :model="ruleForm" :rules="rules" :ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="课程名称" prop="studentChoose">
          <el-select v-model="ruleForm.courseIdChoose" placeholder="请选择">
            <el-option
              v-for="course in courseList"
              :key="course.cid"
              :label="course.cname"
              :value="course.cid">
            </el-option>
          </el-select>
          <!-- <el-input v-model="ruleForm.cname" :value="ruleForm.cname" :disabled="true"></el-input> -->
        </el-form-item>
        <el-form-item label="班级" prop="studentChoose">
          <el-select v-model="ruleForm.classChoose" placeholder="请选择">
            <el-option
              v-for="clazz in classList"
              :key="clazz.id"
              :label="clazz.name"
              :value="clazz.id">
            </el-option>
          </el-select>
          <!-- <el-input v-model="ruleForm.cname" :value="ruleForm.cname" :disabled="true"></el-input> -->
        </el-form-item>
        <el-form-item label="学生名" prop="studentChoose">
          <el-select v-model="ruleForm.studentChoose" placeholder="请选择">
            <el-option
              v-for="student in studentList"
              :key="student.sid"
              :label="student.sname"
              :value="student.sid">
            </el-option>
          </el-select>
          <!-- <el-input v-model="ruleForm.sname" :value="ruleForm.sname" :disabled="true"></el-input> -->
        </el-form-item>
        <el-form-item label="分数" prop="grade">
          <el-input v-model.number="ruleForm.grade" :value="ruleForm.grade"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm(ruleForm)">提交</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
<script>
export default {
  data() {
    var checkGrade = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('成绩不能为空'));
      }
      if (!Number.isInteger(value)) {
        callback(new Error('请输入数字值'));
      } else {
        if (value > 100 || value < 0) {
          callback(new Error('成绩范围 [0 - 100]'));
        } else {
          callback();
        }
      }
    };
    return {
      ruleForm: {
        courseIdChoose: "",
        classChoose: "",
        studentChoose: "",
        grade: 0,
      },
      courseList: [],
      classList: [],
      studentList: [],
      rules: {
        grade: [
          { required: true, message: '请输入学分', trigger: 'change'},
          { type: 'number', message: '请输入数字', trigger: 'change'},
          { validator: checkGrade, trigger: 'blur'}
        ],
      }
    };
  },
  created() {
    const that = this;
    that.tid = sessionStorage.getItem("tid");

    axios.get('http://localhost:10086/course/findMyCourse?tid=' + this.tid).then(function (resp) {
      that.courseList = resp.data;
      console.log(that.courseList);
    })
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const that = this
          const cid = that.ruleForm.courseIdChoose;
          const tid = sessionStorage.getItem("tid");
          const sid = that.ruleForm.studentChoose
          const grade = that.ruleForm.grade
          axios.get("http://localhost:10086/SCT/updateById?sid=" + sid + "&cid=" + cid + "&tid=" + tid + "&grade=" + grade).then(function (resp) {
            if (resp.data === true) {
              that.$message({
                showClose: true,
                message: '编辑成功',
                type: 'success'
              });
            }
            else {
              that.$message.error('编辑失败，请检查数据库');
            }
            that.$router.push("/queryGradeCourse")
          })
        } else {
          return false;
        }
      }
    )},
    reset(formName) {
      this.$refs[formName].resetFields();
    }
  },
  watch: {
    "ruleForm.courseIdChoose": {
      handler(courseIdChoose, newCourse) {
        const that = this
        axios.get("http://localhost:10086/student/findByCourseName?cid=" + courseIdChoose).then(function (resp) {
          that.classList = resp.data;
          console.log(that.classList);
        })
      }
    },
    "ruleForm.classChoose": {
      handler(classChoose, newClass) {
        const that = this
        axios.get("http://localhost:10086/student/findByClassName?classId=" + classChoose).then(function (resp) {
          that.studentList = resp.data;
          console.log(that.studentList);
        })
      }
    }
  }
}
</script>