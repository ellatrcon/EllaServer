package com.ellactron.provissioning.controllers

import java.util.{Date, HashMap, Map}
import javax.validation.Valid

import com.ellactron.common.rest.{DeviceTGT, RegisterUserForm}
import com.ellactron.provissioning.services.AccountService
import net.tinybrick.security.authentication.filter.tools.IEncryptionManager
import net.tinybrick.utils.json.JsonMapper
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation._
import org.springframework.web.context.WebApplicationContext

/**
  * Created by ji.wang on 2017-05-15.
  */
@RestController
//@EnableAutoConfiguration
@RequestMapping(Array("/rest/v1"))
class UserRegister {
  val logger = Logger.getLogger(this.getClass())

  @Autowired private val appContext: WebApplicationContext = null
  //@Autowired private val validator: LocalValidatorFactoryBean = null
  @Autowired private val accountService: AccountService = null

  @RequestMapping(
    value = Array("/user/register"),
    method = Array(RequestMethod.POST),
    consumes = Array(MediaType.ALL_VALUE),
    produces = Array(MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE,
      MediaType.TEXT_HTML_VALUE))
  @ResponseBody
  def register(@Valid registerUserForm: RegisterUserForm): Map[String, Object] = {
    accountService.registerUser(registerUserForm)

    val responseBody: Map[String, Object] = new HashMap()
    responseBody.put("Greeting", "Hello!")
    responseBody
  }

  @RequestMapping(
    value = Array("/user/provisiondevice"),
    method = Array(RequestMethod.POST),
    consumes = Array(MediaType.ALL_VALUE),
    produces = Array(
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE,
      MediaType.TEXT_HTML_VALUE))
  @ResponseBody
  def provision(@RequestBody data: Map[String, Array[Byte]]): Map[String, Object] = {
    val deviceId = data.get("deviceId")

    val tgt = new DeviceTGT()
    tgt.setAccount("username")
    tgt.setDeviceId(deviceId)
    tgt.setTimeStamp(new Date())

    val tgtString = JsonMapper.buildNormalMapper().toJsonString(tgt)
    val encryptionManager = appContext.getBean(classOf[IEncryptionManager])

    val result: Map[String, Object] = new HashMap[String, Object]()
    result.put("tgt", encryptionManager.encrypt(tgtString))
    result
  }
}
