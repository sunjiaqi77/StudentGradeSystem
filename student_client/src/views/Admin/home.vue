<template>
  <div>
    <el-container>
      <el-main>
        <h1>admin home</h1>
        <el-card style="width: 40%">
          <p><i class="el-icon-s-home" style="margin-right: 18px"></i>学生管理系统</p>
          <p><i class="el-icon-s-custom" style="margin-right: 18px"></i>开发：孙嘉琦</p>
          <p><i class="el-icon-location-outline" style="margin-right: 18px"></i>湖南工程学院</p>
        </el-card>
        <logout></logout>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import Logout from "@/components/logout";
export default {
  name: "home",
  components: {Logout},
}
</script>

<style scoped>

</style>