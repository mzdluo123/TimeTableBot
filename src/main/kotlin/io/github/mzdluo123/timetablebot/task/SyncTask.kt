package io.github.mzdluo123.timetablebot.task

import io.github.mzdluo123.timetablebot.appJob
import io.github.mzdluo123.timetablebot.bots.BotsManager
import io.github.mzdluo123.timetablebot.config.AppConfig
import io.github.mzdluo123.timetablebot.gen.timetable.tables.daos.UserDao
import io.github.mzdluo123.timetablebot.schoolservice.*
import io.github.mzdluo123.timetablebot.utils.Dependencies
import io.github.mzdluo123.timetablebot.utils.createDao
import io.github.mzdluo123.timetablebot.utils.logger
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
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + SupervisorJob(appJob)

    init {
        startExecuteLoop()
    }

    private fun startExecuteLoop() {
        launch {
            for (task in taskQueue) {
                try {
                    logger.info("开始处理课程表同步任务 ${task.uid}")
                    Dependencies.resetCookie()
                    val studentId = userDao.fetchOneById(task.uid).studentId
                    loginToCAS(studentId.toString(),task.pwd)
                    val timetable = getTimeTable(AppConfig.getInstance().year,AppConfig.getInstance().term)
                    logger.info(timetable.toString())


                    BotsManager.sendMsg(task.uid,PlainText("课程表刷新完成，共记录了${timetable?.kbList?.size ?: 0}节课程"))
                }catch (e:AuthorizationException){
                    BotsManager.sendMsg(task.uid,PlainText("登录失败: ${e.message}"))
                }catch (e:EncryptionFailed){
                    BotsManager.sendMsg(task.uid,PlainText("密码加密失败，请联系机器人管理员"))
                }catch (e:UnableGetTimeTableException){
                    BotsManager.sendMsg(task.uid,PlainText("无法获取课程表，请联系管理员"))
                }catch (e:Exception){
                    BotsManager.sendMsg(task.uid,PlainText("出现未知错误，请联系管理员: $e"))
                }
            }
        }
    }

    public suspend fun requestSync(task: SyncRequest){
        taskQueue.send(task)
    }


}