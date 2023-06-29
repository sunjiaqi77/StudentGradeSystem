<template>
  <div>
    <el-container>
      <el-main>
        <el-card>
          <el-form :inline="true" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="工号" prop="tid">
              <el-input v-model.number="ruleForm.tid"></el-input>
            </el-form-item>
            <el-form-item label="教师姓名" prop="tname">
              <el-input v-model="ruleForm.tname"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>
          <div>
            <input type="file" @change="handleFileUpload" />
            <button @click="uploadFile">Upload</button>
        </div>
        </el-card>
        <el-card>
          <teacher-list :ruleForm="this.ruleForm"></teacher-list>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>
<script>
import TeacherList from "@/views/Admin/teacherManage/teacherList";
import XLSX from 'xlsx';
export default {
  components: { TeacherList },
  data() {
    return {
      ruleForm: {
        tid: null,
        tname: null,
      },
      selectedFile: null,
      rules: {
        tid: [
          { type: 'number', message: '必须是数字类型' }
        ],
        tname: [

        ],
      }
    };
  },
  create() {
    this.tid = null
  },
  methods: {
    handleFileUpload(event) {
      this.selectedFile = event.target.files[0];
    },
    uploadFile() {
      const formData = new FormData();
      formData.append('file', this.selectedFile);

      axios.post('http://localhost:10086/api/upload', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
      .then(response => {
        console.log('File uploaded successfully');
      })
      .catch(error => {
        console.error('Error uploading file:', error);
      });
    },
    resetForm(formName) {
        this.$refs[formName].resetFields();
      }
  }
}
</script> 
