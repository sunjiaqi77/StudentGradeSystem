<template>
  <div>
    <el-container>
      <el-main>
        <el-card>
          <el-form>
            <el-form-item label="班级名称" prop="classChoose">
              <el-select v-model="ruleForm.classIdChoose" placeholder="请选择">
                <el-option
                  v-for="classes in classList"
                  :key="classes.id"
                  :label="classes.name"
                  :value="classes.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-form>
        </el-card>
        <table class="styled-table" id="styled-table">
          <thead>
            <tr>
              <th>姓名</th>
              <template v-for="course in courseList">
                <th>{{ course.cname }}</th>
              </template>
              <th>总分</th>
              <th>平均分</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(student, index) in studentList" :key="index">
              <td>{{ student.sname }}</td>
              <template v-for="grade in gradeList[index]">
                <td v-if="grade != null">{{ grade.grade }}</td>
                <td v-else>无</td>
              </template>
              <td>{{ sumList[index] }}</td>
              <td>{{ avgList[index] }}</td>
            </tr>
          </tbody>
        </table>
      </el-main>
    </el-container>
    <button @click="exportGrades">导出成绩</button>
  </div>
</template>

<style>
.styled-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.styled-table th,
.styled-table td {
  padding: 10px;
  border: 1px solid #ccc;
  text-align: center;
}

.styled-table th {
  background-color: #f2f2f2;
  font-weight: bold;
}

.styled-table tr:nth-child(even) {
  background-color: #f8f8f8;
}

.styled-table tr:hover {
  background-color: #e2e2e2;
}
</style>


<script>
import * as XLSX from 'xlsx';
export default {
  data() {
    return {
      classList: [],
      studentList: [],
      courseList: [],
      gradeList: [],
      sumList: [],
      avgList: [],
      ruleForm: {
        classIdChoose: "",
      }
    };
  },
  methods: {
    exportGrades() {
      const tableElement = document.getElementById('styled-table'); // 使用相应的 HTML 表格元素的 ID 或其他选择器来获取表格元素
      const worksheet = XLSX.utils.table_to_sheet(tableElement);

      const workbook = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(workbook, worksheet, '成绩表');

      const today = new Date();
      const filename = `成绩表_${today.getFullYear()}${today.getMonth() + 1}${today.getDate()}.xlsx`;

      XLSX.writeFile(workbook, filename);
    }
  },
  created() {
    const that = this
    that.tid = sessionStorage.getItem('tid');

    axios.get("http://localhost:10086/course/findClassId?tid=" + this.tid).then(function (resp) {
      that.classList = resp.data;
    })
  },
  watch: {
    "ruleForm.classIdChoose": {
      handler(newChoose, old) {
        const that = this
        axios.get("http://localhost:10086/student/findByClassName?classId=" + newChoose).then(function (resp) {
          that.studentList = resp.data;
          console.log(that.studentList);
          axios.get("http://localhost:10086/course/findByClassId?classid=" + newChoose).then(function (resp) {
            that.courseList = resp.data;
            const requests1 = [];
            for (let i = 0; i < that.studentList.length; i++) {
              // 定义一个数组来保存所有的异步请求
              const requests = [];
              // 发起异步请求
              for (let j = 0; j < that.courseList.length; j++) {
                const request = axios.get("http://localhost:10086/SCT/find?sid=" + that.studentList[i].sid + "&cid=" + that.courseList[j].cid);
                requests.push(request);
                requests1.push(request);
              }
              // 使用 Promise.all 等待所有异步请求完成
              Promise.all(requests).then(function (responses) {
                let sum = 0.0;
                let avg = 0.0;
                let tempList = [];
                // 处理每个异步请求的响应
                for (let k = 0; k < requests.length; k ++) {
                  const resp = responses[k];
                  console.log(resp);

                  if (resp.data != null) {
                    sum = sum + resp.data.grade;
                  }
                  tempList.push(resp.data);
                }
                avg = sum / tempList.length;
                that.avgList.push(avg);
                that.sumList.push(sum);
                that.gradeList.push(tempList);
              });
            }
            Promise.all(requests1).then(function (responses) {
              console.log(that.studentList);
              for (let i = 0; i < that.studentList.length; i++) {
                for (let j = 0; j < that.studentList.length - i - 1; j++) {
                  if (that.avgList[j] < that.avgList[j + 1]) {
                    let t = that.avgList[j];
                    that.avgList[j] = that.avgList[j + 1];
                    that.avgList[j + 1] = t;
                    console.log(that.studentList[j + 1]);
                    t = that.studentList[j];
                    that.studentList[j] = that.studentList[j + 1];
                    that.studentList[j + 1] = t;
                    
                    t = that.gradeList[j];
                    that.gradeList[j] = that.gradeList[j + 1];
                    that.gradeList[j + 1] = t;
                    t = that.sumList[j];
                    that.sumList[j] = that.sumList[j + 1];
                    that.sumList[j + 1] = t;
                  }
                }
              }
            })
          })
        }
      )}
    }
  }
}
</script>