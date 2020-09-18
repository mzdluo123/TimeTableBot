package io.github.mzdluo123.timetablebot.task

import com.google.gson.JsonSyntaxException
import io.github.mzdluo123.timetablebot.appJob
import io.github.mzdluo123.timetablebot.bots.BotsManager
import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.controller.BotMsgListener
import io.github.mzdluo123.timetablebot.gen.timetable.tables.*
import io.github.mzdluo123.timetablebot.gen.timetable.tables.daos.CourseDao
import io.github.mzdluo123.timetablebot.gen.timetable.tables.daos.CourseTimeDao
import io.github.mzdluo123.timetablebot.gen.timetable.tables.daos.UserCourseDao
import io.github.mzdluo123.timetablebot.gen.timetable.tables.daos.UserDao
import io.github.mzdluo123.timetablebot.gen.timetable.tables.pojos.Course
import io.github.mzdluo123.timetablebot.schoolservice.*
import io.github.mzdluo123.timetablebot.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import net.mamoe.mirai.message.data.PlainText
import kotlin.coroutines.CoroutineContext

data class SyncRequest(val uid: Int, val pwd: String)

object SyncTask : CoroutineScope {
    private val taskQueue = Channel<SyncRequest>()
    private val logger = logger()
    private val userDao = createDao(UserDao::class)
    private val courseDao = createDao(CourseDao::class)
    private val userCourseDao = createDao(UserCourseDao::class)
    private val courseTimeDao = createDao(CourseTimeDao::class)
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + SupervisorJob(appJob)

    init {

        launch { executeLoop() }
    }

    private suspend fun executeLoop() {
        for (task in taskQueue) {
            try {
                logger.info("开始处理课程表同步任务 ${task.uid}")
                Dependencies.resetCookie()
                val user = userDao.fetchOneById(task.uid)
                val studentId = user.studentId
                try {
                    loginV1(studentId.toString(), task.pwd)
                }catch (e:Exception){
                    BotsManager.sendMsg(user.id,PlainText("登录失败，我们将再次使用统一认证系统登录，请耐心等待:${e.message}"))
                    Dependencies.resetCookie()
                    loginToCAS(studentId.toString(), task.pwd)
                }

                val timetable = getTimeTable(AppConfig.getInstance().year, AppConfig.getInstance().term)

                // 更新名字

                if (timetable != null) {
                    user.name = timetable.xsxx.xM
                    userDao.update(user)
                }


                //必修课
                timetable?.kbList?.forEach { c ->
                    var course = courseDao.fetchOneByCourseId(c.jxbId)
                    // 插入课程信息
                    if (course == null) {
                        course = Course().apply {
                            courseId = c.jxbId
                            name = c.kcmc
                            teacher = c.xm
                            weekPeriod = c.zhxs.toByte()
                            period = c.zxs.toByte()
                            score = c.xf.toDouble()
                        }
                        courseDao.insert(course)

                    }
                    // 插入课程和用户绑定
                    dbCtx {
                        val userCourse = it.fetchOne(
                            UserCourse.USER_COURSE,
                            UserCourse.USER_COURSE.USER.eq(user.id)
                                .and(UserCourse.USER_COURSE.COURSE.eq(course.id))
                        )
                        if (userCourse == null) {
                            userCourseDao.insert(
                                io.github.mzdluo123.timetablebot.gen.timetable.tables.pojos.UserCourse().apply {
                                    this.user = user.id
                                    this.course = course.id
                                })
                        }


                        var classRoom = it.fetchOne(Classroom.CLASSROOM, Classroom.CLASSROOM.LOCATION.eq(c.cdmc))
                        if (classRoom == null) {
                            classRoom = it.insertInto(Classroom.CLASSROOM).columns(Classroom.CLASSROOM.LOCATION)
                                .values(c.cdmc).returning(Classroom.CLASSROOM.ID).fetchOne()
                        }

                        val courseStartStop = "[0-9]+".toRegex().findAll(c.jcor).toList()
                        for (t in parseWeek(c.zcd)) {
                            val courseTime =
                                io.github.mzdluo123.timetablebot.gen.timetable.tables.pojos.CourseTime().apply {
                                    this.course = course.id
                                    this.classRoom = classRoom.id
                                    this.dayOfWeek = c.xqj.toByte()
                                    this.week = t.toByte()
                                    this.startTime = courseStartStop[0].value.toByte()
                                    this.length =
                                        (courseStartStop[1].value.toInt() + 1 - courseStartStop[0].value.toInt()).toByte()
                                }
                            if (!it.fetchExists(
                                    CourseTime.COURSE_TIME, CourseTime.COURSE_TIME.COURSE.eq(course.id).and(
                                        CourseTime.COURSE_TIME.CLASS_ROOM.eq(classRoom.id).and(
                                            CourseTime.COURSE_TIME.DAY_OF_WEEK.eq(courseTime.dayOfWeek).and(
                                                CourseTime.COURSE_TIME.WEEK.eq(courseTime.week).and(
                                                    CourseTime.COURSE_TIME.START_TIME.eq(courseTime.startTime).and(
                                                        CourseTime.COURSE_TIME.LENGTH.eq(courseTime.length)
                                                    )
                                                )
                                            )
                                        )
                                    )
                                )
                            ) {
                                courseTimeDao.insert(courseTime)
                            }
                        }

                    }

                }

                // 实践课

                timetable?.sjkList?.forEach { c ->
                    dbCtx {
                        val name = c.qtkcgs
                        val score = c.xf.toDouble()
                        val week = parseWeek(c.qsjsz)
                        val teacher = c.jsxm
                        for (w in week) {
                            var otherCourse = it.fetchOne(
                                OtherCourse.OTHER_COURSE, OtherCourse.OTHER_COURSE.NAME.eq(name)
                            )
                            if (otherCourse == null) {
                                otherCourse = it.insertInto(OtherCourse.OTHER_COURSE).columns(
                                    OtherCourse.OTHER_COURSE.NAME,
                                    OtherCourse.OTHER_COURSE.SCORE,
                                    OtherCourse.OTHER_COURSE.WEEK,
                                    OtherCourse.OTHER_COURSE.TEACHER
                                ).values(name, score, w.toByte(), teacher).returning(OtherCourse.OTHER_COURSE.ID)
                                    .fetchOne()
                            }

                            val userOtherCourse = it.fetchOne(
                                UserOtherCourse.USER_OTHER_COURSE,
                                UserOtherCourse.USER_OTHER_COURSE.USER.eq(user.id)
                                    .and(UserOtherCourse.USER_OTHER_COURSE.OTHER_COURSE.eq(otherCourse.id))
                            )
                            if (userOtherCourse == null) {
                                it.insertInto(UserOtherCourse.USER_OTHER_COURSE).columns(
                                    UserOtherCourse.USER_OTHER_COURSE.USER,
                                    UserOtherCourse.USER_OTHER_COURSE.OTHER_COURSE
                                ).values(user.id, otherCourse.id).execute()

                            }

                        }
                    }

                }
                user.enable = 1
                userDao.update(user)
                BotsManager.sendMsg(task.uid, PlainText("课程表刷新完成，共记录了${timetable?.kbList?.size ?: 0}节课程"))
            } catch (e: AuthorizationException) {
                BotsManager.sendMsg(task.uid, PlainText("登录失败: ${e.message}"))
            } catch (e: EncryptionFailed) {
                BotsManager.sendMsg(task.uid, PlainText("密码加密失败，请联系机器人管理员"))
            } catch (e: UnableGetTimeTableException) {
                BotsManager.sendMsg(task.uid, PlainText("无法获取课程表，请联系管理员"))
            } catch (e: JsonSyntaxException) {
                BotsManager.sendMsg(
                    task.uid,
                    PlainText("您未绑定手机，无法正常获取课程表，请到${AppConfig.getInstance().authUrl}/cas/login登录并绑定手机号；如已绑定仍出现这个问题请联系管理员")
                )
            } catch (e: Exception) {
                BotsManager.sendMsg(task.uid, PlainText("出现未知错误，请联系管理员: $e"))
                e.printStackTrace()
            }
        }
    }

    public suspend fun requestSync(task: SyncRequest) {
        taskQueue.send(task)
    }


}