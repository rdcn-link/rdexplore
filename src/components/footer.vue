<template>
  <div class="footer">
    <div class="w">
      <div class="logo">
        <router-link to="/">
          <img src="@/assets/logo1.png" alt="RDCN">
        </router-link>
      </div>
      <div class="helpList">
        <div class="helpDetail">
          <div class="helpTitle">What is rdcn</div>
          <div class="helpBorder"></div>
          <div class="helpList">About rdcn</div>
          <div class="helpList">Vision & Mission</div>
        </div>
        <div class="helpDetail">
          <div class="helpTitle">Who we are</div>
          <div class="helpBorder"></div>
          <div class="helpList">At a glance</div>
          <div class="helpList">Association</div>
          <div class="helpList">Ecosystems</div>
        </div>
        <div class="helpDetail">
          <div class="helpTitle">Updates</div>
          <div class="helpBorder"></div>
          <div class="helpList">Latest News</div>
        </div>
        <div class="helpDetail">
          <div class="helpTitle">Information</div>
          <div class="helpBorder"></div>
          <div class="helpList">FAQ
          </div>
          <div class="helpList">Documents Repository</div>
        </div>
        <div class="helpDetail">
          <div class="helpTitle">Contact Us</div>
          <div class="helpBorder"></div>
          <div class="helpList">rdcn@cnic.cn</div>
        </div>
        <div class="helpDetail">
          <el-button type="success" class="pbutton">Join</el-button>
          <el-button type="success" class="pbutton">Members Platform</el-button>
        </div>
      </div>
      <div class="copyright">Copyright ©Computer Network Information Center,CAS   |   2 Dongsheng South Road, Haidian District, Beijing   |   Privacy Policy</div>

    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
    }
  }
}
</script>
<style lang="scss" scoped>
.footer {
  background-color: #000;
  height: 530px;
  color: #fff;
  .w{
    padding-top: 80px;
    .logo{
      width: 57px;
      height: 54px;
      img{
        width: 100%;
        height: 100%;
      }
    }
    .helpList{
      margin-top: 20px;
      display: flex;
      justify-content: space-between;
      .helpDetail{
        .helpTitle{
          font-family: PingFangSC-Medium;
          font-size: 30px;
          line-height: 2;
        }
        .helpBorder{
          margin: 20px 0;
          width: 50px;
          height: 6px;
          border-radius: 3px;
          background-image: linear-gradient(
              #c6ff00,
              #c6ff00),
          linear-gradient(
              #e7e7e7,
              #e7e7e7);
          background-blend-mode: normal,
          normal;
        }
        .helpList{
          font-family: PingFangSC-Regular;
          font-size: 20px;
          line-height: 2;
          &:hover{
            cursor: pointer;
            color: #c6ff00;
            text-decoration: underline;
          }
        }
        .pbutton{
          width: 300px;
          height: 60px;
          background-color: #c6ff00;
          font-family: PingFangSC-Medium;
          font-size: 30px;
          color: #000;
          text-align: center;
          display: block;
          margin: 30px 0;

        }
      }
    }
    .copyright{
      margin-top: 50px;
      font-family: PingFangSC-Regular;
      font-size: 20px;
      text-align: center;
    }

  }
}
</style>
