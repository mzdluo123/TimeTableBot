# TimeTableBot

适用于大学的课程表qq机器人

* 上课提醒
* 正方教务系统自动导入数据


## 配置文件

文件名:config.yml

```yml
dbUrl: "jdbc:mysql://xxxxx"
dbUser: "xxx"
dbPwd: "xxx"
botAccounts:
  - id: xxx
    pwd: "xxxx"  # 账号可添加多个

baseUrl: "xxx" # api地址
authUrl: "xxx" # 统一认证地址

admin: # 管理员列表
  - xxxx

classTime:  # 每节课的上课时间
  - "9:00"
```
