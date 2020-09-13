# TimeTableBot

适用于大学的课程表qq机器人

注意，本项目带有本校的一些特定特性，可能无法在其他学校的教务系统工作

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

# 学期信息
year: 2020
term: 0

termBegin: "2020-08-31"

classTime:
  - "08:00"
  - "08:55"
  - "10:10"
  - "11:05"
  - "14:30"
  - "15:25"
  - "16:40"
  - "17:35"
  - "19:30"
  - "20:25"
  - "21:20"
```
