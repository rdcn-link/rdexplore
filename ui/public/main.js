const { app, BrowserWindow, Menu } = require("electron"); //引入electron
const path = require("path");

// 禁用菜单，一般情况下，不需要禁用
Menu.setApplicationMenu(null);

function createWindow() {
  let icon = path.join(__dirname, "./favicon.ico");
  //窗口配置程序运行窗口的大小
  let win = new BrowserWindow({
    icon,
    width: 1200,
    height: 800,
    // frame: false,

    // minWidth: 1200,
    // minHeight: 800,
    // center: true,
    // skipTaskbar: false,
    // transparent: false,
    webPreferences: {
      nodeIntegration: true,
      contextIsolation: false,
      // webSecurity: false,
    },

    // titleBarStyle: 'hidden',
  }); //创建一个窗口

  if (process.platform === "darwin") {
    app.dock.setIcon(icon);
  }

  win.loadURL(`file://${__dirname}/index.html`); //在窗口内要展示的内容index.html 就是打包生成的index.html

  //   win.webContents.openDevTools(); //开启调试工具

  win.on("close", () => {
    //回收BrowserWindow对象
    win = null;
  });
  win.on("resize", () => {
    win.reload();
  });
}

// 当 Electron 初始化完成时调用该方法
app.on("ready", createWindow);
// app.whenReady().then(() => {});

app.on("window-all-closed", () => {
  //   app.quit();

  // 在 macOS 上，除非用户使用 Cmd + Q 确定地退出，
  // 否则绝大部分应用及其菜单栏会保持激活。
  if (process.platform !== "darwin") {
    app.quit();
  }
});
app.on("activate", () => {
  //   if (win == null) {
  //     createWindow();
  //   }

  // 在 macOS 上，当单击 dock 图标并且没有其他窗口打开时，
  // 重新创建一个窗口。
  if (BrowserWindow.getAllWindows().length === 0) {
    createWindow();
  }
});
