package util;

import android.os.Environment;


import java.io.File;

/**
 * 常量
 */
public class Constant {
    /**
     * 版本更新
     */
    public static final String version_appkey = "ac6ee47dd8ea8efd539d3f5102d0dba4";

    public static final String PACKAGE_NAME = "com.bidostar.pinan";
    /***
     * 数据库版本号
     * v1.8.1.
     * v1.7.1.5     ->9
     * v1.7.0.1  -> 7
     * v1.6  -> 6
     * v2.2  ->14
     * v2.3.0 -> 15
     * 2.3.1 -> 17
     * 2.3.2 -> 18
     */
    public static final int DB_VERSION = 18;//
    public static final boolean DEBUG = true;
    public static final boolean LOG_DEBUG = true;
    public static final String UP_DATA_VERSION = "debug";

    public static final String BAIDU_URL = "http://api.map.baidu.com/staticimage/v2";
    public static final String BAIDU_MAP_AK = "zGl3Htct9xnON1qwKGz2lUW5";
    public static final int timeout = 1000 * 5;
    /**
     * SharedPreference的key
     */
    public static final String PREFERENCE_IS_STARTED = "isStarted";//引导页动画
    public static final String PREFERENCE_FILE = "obd_preference"; // sharedpreference文件名
    public static final String PREFERENCE_FILE_USER = "user_code";//用户账号信息
    public static final String PREFERENCE_KEY_TOKEN = "pref_token"; // 用户登录凭证
    public static final String PREFERENCE_KEY_CAR_ID = "pref_car_id"; // 当前选中的车辆id
    public static final String PREFERENCE_KEY_PHONE = "phone_mobile";//手机号
    public static final String PREFERENCE_KEY_SHAKELEVEL = "shake_level";//震动等级


    public static final String PREFERENCE_KEY_LOCATION_CATY = "location_city";//定位城市
    public static final String PREFERENCE_KEY_LOCATION_LATITUDE = "location_latitude";//定位纬度
    public static final String PREFERENCE_KEY_LOCATION_LONGITUDE = "location_longitude";//定位经度


    /**
     * 车辆检测
     */
    public static final String PREFERENCE_KEY_LAST_IS_DETECTION = "last_is_detection";//上次是否检测过
    public static final String PREFERENCE_KEY_DETECTION_RESULT = "last_detection_result";//上次检测结果
    public static final String PREFERENCE_KEY_DETECTION_SCORE = "score";//检测分数
    public static final String PREFERENCE_KEY_CID = "cid";//车辆ID
    public static final String PREFERENCE_KEY_LAST_DECTED_TIME = "last_dected_time";//上次检测时间
    public static final String PREFERENCE_KEY_LAST_DECTED_COUNT = "dected_error_count";//检测 车况 风险个数
    public static final String PREFERENCE_KEY_LICENSE_PLATE_IMG = "bundle_license_plate_img";//车辆驾驶证
    public static final String PREFERENCE_KEY_DRIVER_LICENSE_IMG = "bundle_driver_license_img";//车辆行驶证
    public static final String PREFERENCE_KEY_MSG_NOTIFY = "msg_notify";//消息通知开关
    public static final String PREFERENCE_KEY_DRIVER_RECORD = "driver_record";//行车记录开关
    public static final String PREFERENCE_KEY_SECURITY = "security_open";//安防设置开关  如果消息通知不打开  则安防不能设置
    public static final String PREFERENCE_KEY_MESSAGE_TROUBLE = "message_no_trouble_open";//消息免打扰开关  如果消息通知不打开  则安防不能设置
    public static final String PREFERENCE_KEY_MESSAGE_START_TIME = "message_no_start_time";//开始时间
    public static final String PREFERENCE_KEY_MESSAGE_END_TIME = "message_no_end_time";//结束时间
    public static final String PREFERENCE_KEY_CAMERA_DIRECTION = "camera_direction";//摄像头方向0：后，1：前
    public static final String PREFERENCE_KEY_CAMERA_FLASH = "camera_flash";//是否开启闪光灯0：开启，1：关闭
    public static final String PREFERENCE_KEY_CAMERA_TIP = "camera_tip";//事故提示：true：开启，false：关闭
    public static final String PREFERENCE_KEY_CURRENT_CITY_JNAME = "current_pinyin";//当前城市拼音
    public static final String PREFERENCE_KEY_CURRENT_CITY_NAME = "current_name";//当前城市名称
    public static final String PREFERENCE_KEY_DATE_LIST = "date_list";//有行程的日期列表
    public static final String PREFERENCE_KEY_DRIVER_INFO_TODAY = "driver_info_today";//当天驾驶分数信息
    public static final String PREFERENCE_KEY_HULUE = "hulue";//忽略本次更新
    public static final String PREFERENCE_KEY_VIOLATION_VIEW_TYPE_CHOOSE = "violation_view_type_choose";//违章类型选择(1:list;0:grid)
    public static final String PREFERENCE_KEY_IS_PROTOCOL = "login_is_protocol";//是否同意协议
    public static final String PREFERENCE_KEY_SUPPORT_BUNDLE_KEY_DEVICE_ID = "prefrence_device_id";
    public static final String PREFERENCE_KEY_CAR_STATUS_TAG = "car_status_tag";
    public static final String PREFERENCE_KEY_START_SPEED = "triggerStartSpeed";
    public static final String PREFERENCE_KEY_STOP_SPEED = "triggerStopSpeed";

    public static final String PREFERENCE_KEY_SERVICE_CITY_NAME = "service_city_name";//服务当前城市
    //活动
    public static final String PREFERENCE_KEY_ACTIVE_ID = "active_id";//已经查看过的活动ID  v2.3.1
    public static final String PREFERENCE_KEY_HISTORY_TOPIC = "history_topic_publish";
    public static final String PREFERENCE_KEY_HISTORY_TOPIC_ID = "history_topic_publish_id";

    /**
     * Bundle的key
     */
    public static final String BUNDLE_KEY_WXFLAG = "bundle_wxflag"; // 微信登录凭证
    public static final String BUNDLE_KEY_VEHICLE_BRAND = "bundle_vehicle_brand"; // 汽车品牌
    public static final String BUNDLE_KEY_VEHICLE_BRAND_ID = "bundle_vehicle_brand_id"; // 汽车品牌id
    public static final String BUNDLE_KEY_VEHICLE_SERIES_ID = "bundle_vehicle_series_id"; // 车系id
    public static final String BUNDLE_KEY_VEHICLE_YEAR = "bundle_vehicle_year"; // 年款
    public static final String BUNDLE_KEY_PROVINCE = "bundle_province"; // 省份
    public static final String BUNDLE_KEY_CITY = "bundle_city"; // 城市
    public static final String BUNDLE_KEY_CID = "bundle_cid"; // cid
    public static final String BUNDLE_KEY_DEVICE_CODE = "bundle_device_code"; // device_code
    public static final String BUNDLE_KEY_YMD = "ymd"; // 查询的日期
    public static final String BUNDLE_KEY_MILEAGE = "mileage"; // 里程
    public static final String BUNDLE_KEY_TIMECOST = "timeCost"; // 耗时
    public static final String BUNDLE_KEY_FUELCOST = "fuelCost"; // 耗油
    public static final String BUNDLE_KEY_ROUTEID = "routeId"; // 行程id
    public static final String BUNDLE_KEY_ROUTE_MAX_SPEED = "maxspeed";//最大速度
    public static final String BUNDLE_KEY_GOTO_OBD_BIND = "bundle_goto_obd_bind"; // 是否要绑定obd
    public static final String BUNDLE_KEY_ROUTE_TIME = "route_time";//路线行驶开始结束时间
    public static final String BUNDLE_ACCIDENT_TYPE = "accident_type";//事故类型，单车；多车
    public static final String BUNDLE_KEY_ROUTE_START_TIME = "startTime";//行程开始时间
    public static final String BUNDLE_KEY_ROUTE_END_TIME = "endTime";//行程结束时间
    public static final String BUNDLE_KEY_DETAIL_ILLEGAL_ITEM = "detail_illegal";//违章查询单个Item
    public static final String BUNDLE_KEY_RECEIVE_ADDRESS_ITEM = "receive_address";//收货地址单个Item
    //商场
    public static final String BUNDLE_KEY_URL = "url";//商场URL
    public static final String BUNDLE_KEY_TITLE = "title";//推广标题
    public static final String BUNDLE_KEY_GOOD_ID = "goodsId";//商品ID

    /**
     * 缓存文件夹
     */
    public static final String CACHE_DIR = "obd/";
    public static final String IMAGE_DIR = "obd/images/";

    /**
     * 接口
     */
    //    public static final String URL_BASE = "http://211.154.152.14:8088/obd_new/";
    //    public static final String URL_BASE = "http://www.bidostar.cn:8088/obd_new/";
    //          public static final String URL_BASE = "http://api.centos.bidostar.com/";
    //          public static final String URL_RESOURCE_BASE = "http://res.centos.bidostar.com/";
    //线上测试

    //    public static final String URL_BASE = "http://dev.api.bidostar.com/";
    //    public static final String URL_RESOURCE_BASE = "http://dev.res.bidostar.com/";
    //    线上
    //        public static final String URL_BASE = "http://api.bidostar.com/";
    //        public static final String URL_RESOURCE_BASE = "http://res.bidostar.com/";
    //        本地
    public static final String URL_BASE = "http://api.ttt.com/";
    public static final String URL_RESOURCE_BASE = "http://res.ttt.com/";

    //    public static final String URL_BASE = "http://192.168.1.30:8080/api/";
    //    public static final String URL_RESOURCE_BASE = "http://192.168.1.30/res/";
    public static final String URL_API_BASE = URL_BASE + "v1/";

    /**
     * 帐号系统
     */
    public static final String URL_ACCOUNT_INFO = URL_API_BASE + "account/info.json"; // 获取微信用户信息
    public static final String URL_ACCOUNT_GET_VERIFY_CODE = URL_API_BASE + "account/get_verify_code.json"; // 获取短信验证码
    public static final String URL_ACCOUNT_BIND_PHONE = URL_API_BASE + "account/bind_mobile.json"; // 绑定手机
    public static final String URL_ACCOUNT_FORGET_PASSWORD = URL_API_BASE + "account/forget_password.json"; // 忘记密码，找回
    public static final String URL_ACCOUNT_BIND_WX = URL_API_BASE + "account/bind_wx.json"; // 绑定微信
    public static final String URL_LOGIN_PHONE = URL_API_BASE + "account/phone_login.json"; // 手机登陆
    public static final String URL_REGISTER_PHONE = URL_API_BASE + "account/register.json"; // 手机注册
    public static final String URL_GET_USERINFO_FLAG = URL_API_BASE + "account/get_user_by_flag.json"; // 通过Flag获取user
    public static final String URL_GET_WXUSERINFO_WXID = URL_API_BASE + "account/get_wxuser_by_wxuid.json"; //通过wxuid获取wxuser
    public static final String URL_SMS_LOGIN = URL_API_BASE + "account/sms_login.json";//短信验证码登录 v1.8.1
    public static final String URL_BIND_PHONE = URL_API_BASE + "account/wxbind_mobile.json";//微信绑定手机号  v2.0.1
    /**
     * 车辆定义
     */
    public static final String URL_VEHICLE_BRANDS = URL_API_BASE + "vehicle/brands.json"; // 获取品牌
    public static final String URL_VEHICLE_SERIES = URL_API_BASE + "vehicle/series.json"; // 获取车系
    public static final String URL_VEHICLE_YEARS = URL_API_BASE + "vehicle/years.json"; // 获取年款
    public static final String URL_VEHICLE_TYPES = URL_API_BASE + "vehicle/types.json"; // 获取车型

    /**
     * 车辆信息
     */
    public static final String URL_CAR_ADD = URL_API_BASE + "car/add.json"; // 添加车辆
    public static final String URL_CAR_UPDATE = URL_API_BASE + "car/update.json"; // 更新车辆信息
    public static final String URL_CAR_REMOVE = URL_API_BASE + "car/delete.json"; // 删除车辆
    public static final String URL_CAR_LIST = URL_API_BASE + "car/info.json"; // 车辆列表

    public static final String URL_CAR_DECTED = URL_API_BASE + "car/detection.json";//车辆检测  v1.3新增
    public static final String URL_FAULT_CODE_DETAIL = URL_BASE + "fault/info.json";//车辆检测  错误码详情  v1.3新增
    public static final String URL_CAR_CERITIFIED = URL_API_BASE + "car/submitCeritified.json";//车辆认证  v1.3新增

    public static final String URL_CAR_STATUS = URL_API_BASE + "car/vehicleCondition.json";//获取车况  v1.3新增
    /**
     * OBD设备管理
     */
    public static final String URL_DEVICE_UNBIND = URL_API_BASE + "device/unbind.json"; // 解绑OBD
    public static final String URL_DEVICE_ROUTES = URL_API_BASE + "device/routes.json"; // 行程信息
    public static final String URL_DEVICE_ROUTES_DELETE = URL_API_BASE + "device/delete_route.json";//删除行程  v1.8.1新增
    public static final String URL_DEVICE_ROUTES_DELETE_RECORD = URL_API_BASE + "device/delete_record.json";//行程删除记录  v1.8.1新增
    public static final String URL_DEVICE_ROUTE_TRACE_ALARM = URL_API_BASE + "device/trace_alarm.json";//一次行程轨迹信息中的警告信息  V1.7.1.1
    public static final String URL_DEVICE_ROUTE_TRACE_ALARM_2 = URL_API_BASE + "device/trace_alarm_v2.json";//一次行程轨迹信息中的警告信息  V2.2
    public static final String URL_DEVICE_LOCATION = URL_API_BASE + "device/status.json"; // 当前位置
    public static final String URL_DEVICE_TEST = URL_API_BASE + "device/test.json"; // 车辆检测
    public static final String URL_SERVICE_SECURITY_SETTING = URL_API_BASE + "car/resetSecuritySetting.json";//安防设置  v1.3新增
    public static final String URL_DEVICE_RESET_SHOCKLEVEL = URL_API_BASE + "car/resetShockLevel.json";//震动级别设置  v1.3新增
    public static final String URL_DEVICE_BOX_INFO = URL_API_BASE + "device/queryObdDeviceInfo.json";//获取即时判盒子信息  v13新增
    public static final String URL_DEVICE_BIND = URL_BASE + "v3/device/bind.json"; // 绑定OBD   v1.3  新增
    public static final String URL_DEVICE_BIND_BY_IMEI = URL_BASE + "v3/device/bindByIMEI.json";//绑定OBD通过IMEI  v1.6新增
    public static final String URL_DEVICE_TRACE_THUMB = URL_API_BASE + "device/traceThumb.json";//获取百度静态图轨迹坐标点  v1.4新增
    public static final String URL_DEVICE_ROUTE_DATE = URL_API_BASE + "car/vehicleRouteDate.json";//获取有轨迹的日期列表
    public static final String URL_DEVICE_SCORE_DATE = URL_API_BASE + "car/vehicleScoreDatas.action";//获取车辆近30天分数(v1.4)
    public static final String URL_DEVICE_SEND_LOCATION = URL_API_BASE + "device/send_location.json";//发送位置信息(v1.8.1)
    public static final String URL_DEVICE_SEND_TEXT_TO_DEVICE = URL_API_BASE + "device/send_text.json";//发送文本消息到设备 v1.8.1新增
    public static final String URL_DEVICE_CUR_TRACES = URL_API_BASE + "device/cur_trace.json";//获取当前未结束行程的轨迹(2.1.3)

    /**
     * 消息通知
     */
    public static final String URL_DEVICE_OPEN_PUSH_MSG = URL_BASE + "v3/push/registerPush.json";//启用消息推送  v1.3新增
    public static final String URL_DEVICE_CANCEL_PUSH_MSG = URL_BASE + "v3/push/cancelPush.json";//取消消息推送  v1.3新增
    public static final String URL_DEVICE_IS_AVAILABLE = URL_BASE + "v3/device/validate_obd";//验证设备是否可用  v1.3新增
    public static final String URL_DEVICE_MSG_RECORD = URL_BASE + "v1/alarm/getLatestRecord.json";//获取最新消息记录
    public static final String URL_DEVICE_MSG_MORE_THAN_RECORD = URL_BASE + "v1/alarm/getPreviousRecord.json";//获取更多消息记录
    public static final String URL_DEVICE_USER_SET_MESSAGE_INFORM = URL_BASE + "v1/settings/info.json";//获取用户设置的消息通知
    public static final String URL_DEVICE_USER_UPDATE_MESSAGE_INFORM = URL_BASE + "v1/settings/update.json";//更新用户设置的消息通知
    public static final String URL_MESSAGE_NOTIFICATION_NEW = URL_BASE + "v1/notification/getLatestRecord.json"; //获取最新通知 V1.7.1新增
    public static final String URL_MESSAGE_NOTIFICATION_MORE_THAN = URL_BASE + "v1/notification/getPreviousRecord.json";//获取更多通知 v1.7.1新增
    public static final String URL_MESSAGE_NOTIFICATION_DELETE = URL_API_BASE + "notification/delete.json";//删除通知 （v2.2）
    /**
     * 保险  事故
     */
    public static final String URL_SERVICE_INSURANCE_COMPANY = URL_API_BASE + "accident/insurance_company.json";//获取保险公司列表  v1.3新增
    public static final String URL_ACCIDENT_RECORD = URL_API_BASE + "/accident/records.json";//报案记录  事故记录v1.5 修改新增字段
    public static final String URL_UPLOAD_IMAGE = URL_BASE + "ImageUpLoad";//上传图片 v1.3新增(1.4改为resource)
    public static final String URL_UPLOAD_FILE = URL_BASE + "MediaUpLoad";//上传视频 v2.3.2新增
    public static final String URL_ACCIDENT_TYPE_LIST = URL_API_BASE + "accident/category.json";//获取事故类型列表(v1.4)
    public static final String URL_ACCIDENT_DUTY_LIST = URL_API_BASE + "accident/duty.json";//获取事故责任列表（v1.4）
    public static final String URL_ACCIDENT_DETAIL = URL_API_BASE + "accident/details.json";//获取事故详情（v1.4新增）
    public static final String URL_ACCIDENT_REPORT = URL_API_BASE + "accident/report_case.json";//事故上报  v1.3新增
    public static final String URL_ACCIDENT_CANCEL_CASE = URL_API_BASE + "accident/cancelCase.json";//取消报案 v1.5新增
    /**
     * v1.7.1修改
     */
    public static final String URL_ACCIDENT_TAKE_PICTURE = URL_API_BASE + "accident/takeEvidence.json"; //拍照取证v1.5新增
    public static final String URL_ACCIDENT_HAND_ORDER = URL_API_BASE + "accident/genLiabilityProtocols.json";//生成事故处理单 v1.5新增
    public static final String URL_ACCIDENT_SUBMIT_POLICE = URL_API_BASE + "accident/requireAssistance.json";//提交交警协助 v1.5新增
    public static final String URL_ACCIDENT_CANCEL_POLICE_DEAL = URL_API_BASE + "accident/cancelAssistance.json";//取消交警协助 v1.5新增
    public static final String URL_ACCIDENT_DEALING_ACCIDENT = URL_API_BASE + "accident/processingAccident.json";//正在处理中的事故信息 v1.5新增
    public static final String URL_ACCIDENT_ONECAR_REPORT = URL_API_BASE + "accident/reportSingleVehicleCase.json";//单车事故报案  v1.5新增
    public static final String URL_SMS_VERIF = URL_API_BASE + "account/code_verify.json";//短信验证 v1.5新增
    public static final String URL_REPORT_STATUS = URL_API_BASE + "accident/reportStatus.json";//获取事故状态 v1.5新增
    public static final String URL_GET_USERINFO = URL_API_BASE + "account/userInfo.json";//获取用户信息  v1.6新增
    /**
     * 我的
     */
    public static final String URL_MINE_VOCATION = URL_API_BASE + "account/industry.json";//行业  v1.6新增
    public static final String URL_MINE_SUBMIT_DRIVERLICENSE = URL_API_BASE + "driverLicense/submit.json";//提交驾驶证  v1.6新增
    public static final String URL_MINE_UPDATE_USER_INFO = URL_API_BASE + "account/updateUserInfo.json";//更新用户信息(头像，昵称，行业)  v1.6新增
    public static final String URL_MINE_GET_DRIVERLICENSE_INFO = URL_API_BASE + "driverLicense/info.json";//获取驾驶证信息 v1.6新增
    public static final String URL_MINE_GET_USER_POINT_RECORD = URL_API_BASE + "account/getBonusRecords.json";//获取用户积分记录  v1.6新增

    public static final String URL_MINE_TAKE_MONEY = URL_API_BASE + "account/requireWithdraw.json";//申请提现  v1.7.1新增
    public static final String URL_MINE_TAKE_MONEY_RECORD = URL_API_BASE + "account/withdrawRecord.json";//提现记录 v1.7.1新增
    public static final String URL_MINE_HOME_BANNER = URL_API_BASE + "service/banners.json";//获取首页Banner(v1.8.1)
    // v2.3.1 新增
    public static final String URL_MINE_FOLLOWS = URL_BASE + "v3/forum/follows.json";//关注列表(v2.3.1)
    public static final String URL_MINE_FANS = URL_BASE + "v3/forum/fans.json";//粉丝列表(v2.3.1)

    /**
     * 车辆驾驶得分  动态获取的
     */
    public static final String URL_CAR_DRIVER_SCORE = URL_API_BASE + "car/statistic.action";//动态获取车辆分数

    /**
     * 违章查询
     */
    public static final String URL_SERVICE_LICENSE_CATEGORIES = URL_API_BASE + "illegal/licenseCategories.json";//获取车辆车牌类型
    public static final String URL_SERVICE_TRAFFIC_MANAGEMENT_BUREAUS = URL_API_BASE + "illegal/trafficManagementBureaus.json";//获取交管所信息
    public static final String URL_SERVICE_VEHICLE_VIOLATION = URL_API_BASE + "illegal/vehicleViolation.json";//查询违章
    public static final String URL_SERVICE_ISREGIONARAILABLE = URL_API_BASE + "illegal/getBureau.json";//该区域是否支持违章查询
    public static final String URL_SERVICE_ILLEGAL_TYPES = URL_API_BASE + "illegal/types.json";//获取违章类型
    /**
     * 其他
     */
    public static final String URL_OTHER_VERSION_UPDATE = URL_API_BASE + "version/checkUpdate.json";//版本更新
    public static final String URL_FEED_BACK = URL_API_BASE + "feedback/feedback.json";//反馈  v1.3新增

    /**
     * 论坛  v.17新增
     */
    public static final String URL_FORUM_GET_DESIGN_TOPIC_BBS = URL_BASE + "v3/forum/posts.json";//获取指定话题帖子 v1.7新增
    public static final String URL_FORUM_CREATE_BBS = URL_BASE + "v3/forum/newPost.json";//发布帖子  v1.7新增
    public static final String URL_FORUM_DELETE_BBS = URL_BASE + "v3/forum/delPost.json";//删除帖子 v1.7新增
    public static final String URL_FORUM_BBS_DETAIL = URL_BASE + "v3/forum/postDetail.json";//帖子详情  v.1.7新增
    public static final String URL_FORUM_RELEASE_COMMENT = URL_BASE + "v3/forum/newReply.json";//发布评论 v1.7新增
    public static final String URL_FORUM_DELETE_COMMENT = URL_BASE + "v3/forum/delReply.json";//删除评论 v1.7新增
    public static final String URL_FORUM_GET_COMMENT = URL_BASE + "v3/forum/replys.json";//获取评论列表 v1.7新增
    public static final String URL_FORUM_PRAISE = URL_BASE + "v3/forum/praise.json";//点赞或取消 v1.7新增
    public static final String URL_FORUM_PRAISE_LIST = URL_BASE + "v3/forum/praises.json";//获取点赞用户列表  v1.7新增
    public static final String URL_FORUM_REPORT_BBS = URL_BASE + "v3/forum/tipOff.json";//举报帖子  v1.7新增
    public static final String URL_FORUM_RECEIVE_AWARD = URL_BASE + "v3/forum/getReward.json";//领取奖励  v1.7.1新增
    public static final String URL_FORUM_GET_MINE_TOPIC_BBS = URL_BASE + "v3/forum/mine_posts.json";//获取用户发表的帖子 (v1.8.1)
    public static final String URL_FORUM_CHANGE_FOLLOW_STATUS = URL_BASE + "v3/forum/follow.json"; //关注/取消关注 v2.3.1新增
    public static final String URL_FORUM_GET_BBS_USER_INFO = URL_BASE + "v3/forum/user.json"; //获取论坛用户信息

    /**
     * 分享 v1.7新增
     */
    public static final String URL_SHARED = URL_BASE + "v3/common/shortWXUrl.json";//分享  v1.7新增
    public static final String URL_SHARED_RECORED = URL_BASE + "v3/forum/saveShareLog.json";//分享日志  记录 分享次数  v1.7新增

    /**
     * 商城
     */
    public static final String URL_MARKET_AD = URL_API_BASE + "shop/spread.json";//商城推广  v1.8新增
    public static final String URL_MARKET_GOODS = URL_API_BASE + "shop/goods.json";//首页商品列表  v1.8新增
    public static final String URL_MARKET_GOOD_DETAIL = URL_API_BASE + "shop/goods_detail.json";//商品详情  v1.8  新增
    public static final String URL_MARKET_ADD_RECEIVER = URL_API_BASE + "shop/save_consignee.json"; //新增收货人v1.8新增
    public static final String URL_MARKET_DELETE_RECEIVER = URL_API_BASE + "shop/delete_consignee.json";//删除收货人//v1.8新增
    public static final String URL_MARKET_UPDATE_RECEIVER = URL_API_BASE + "shop/update_consignee.json";//更新收货人信息   v1.8新增
    public static final String URL_MARKET_RECEIVERS = URL_API_BASE + "shop/consignees.json";//收货人列表  v1.8新增
    public static final String URL_MARKET_ORDER = URL_API_BASE + "shop/order.json";//下单  v1.8新增
    public static final String URL_MARKET_ORDER_LIST = URL_API_BASE + "shop/order_record.json";//订单列表  v1.8新增
    public static final String URL_MARKET_POINT_RULE = URL_API_BASE + "shop/points_usage_rule.json";//积分使用规则  v1.8新增
    public static final String URL_MARKET_DISTRICT = URL_API_BASE + "shop/district.json";//获取地域列表  v1.8新增
    public static final String URL_MARKET_WECHAT_PAY_ORDER = URL_API_BASE + "shop/wx_prepay_order.json";//微信下单支付   v1.8新增
    public static final String URL_MARKET_ORDER_DETAIL = URL_API_BASE + "shop/order_detail.json";//订单详情   v1.8新增
    public static final String URL_MARKET_CANCEL_ORDER = URL_API_BASE + "shop/cancel_order.json";//取消订单   v1.8新增
    public static final String URL_MARKET_CONFRIM_RECEIVE_GOOD = URL_API_BASE + "shop/confirm_receipt.json";//确认收货   v1.8新增

    /**
     * 2.2 流量管理
     */
    public static final String URL_FLOW_MANAGE_FLOWPKG_LIST = URL_BASE + "flow_packet/list.json";//获取流量包 2.2新增
    public static final String URL_FLOW_MANAGE_ORDER_LIST = URL_BASE + "flow_packet/order_record.json";//获取流量订单历史列表 2.2
    public static final String URL_FLOW_MANAGE_WX_PRE_PAY = URL_BASE + "flow_packet/wx_pay.json";//获取微信支付预付单  2.2
    public static final String URL_FLOW_MANAGE_FLOW_ORDER_DETAIL = URL_BASE + "flow_packet/order_detail.json";//获取支付订单详情
    public static final String URL_FLOW_MANAGE_FLOW_USAGE_INFO = URL_BASE + "flow_usage/info.json";//获取流量使用详情

    /**
     * 演示数据
     */
    public static final String URL_DEMO_ROUTE = URL_API_BASE + "demo/routes.json";//行程演示  v1.8.1
    public static final String URL_DEMO_ROUTE_TRACE_ALARM = URL_API_BASE + "demo/trace_alarm.json";//行程轨迹和报警演示  v1.8.1
    public static final String URL_DEMO_SECURITY_ALARM_RECORD = URL_API_BASE + "demo/alarm.json";//报警记录// v1.8.1
    public static final String URL_DEMO_ROUTE_SCORE = URL_API_BASE + "demo/score.json";//行程打分演示  v1.8.1
    public static final String URL_DEMO_DEVICE_STATUS = URL_API_BASE + "demo/status.json";//设备状态演示数据  v1.8.1

    /**
     * 后视镜
     */
    public static final String URL_MIRROR_GET_UPLOAD_PIC = URL_API_BASE + "device/alarm_file.json";//获取设备上传图片(v1.8.1)
    public static final String URL_MIRROR_DELETE_PIC = URL_API_BASE + "device/delete_alarm_file.json";//删除图片(v1.8.1)
    public static final String URL_MIRROR_CAPTURE_PIC = URL_API_BASE + "device/capture.json";//拍照(v1.8.1)
    /**
     * 首页面功能接口
     */
    public static final String URL_HOME_FUNCTION = URL_API_BASE + "service/category.json";//首页面功能接口  (V2.2)
    public static final String URL_HOME_GET_ACTIVE = URL_API_BASE + "service/get_event.json";//获取活动   (v2.2)
    public static final String URL_HOME_PAGE = URL_BASE + "v3/forum/home.json";//获取首页面信息  v2.1.5
    /**
     * 服务
     */
    public static final String URL_SERVICE_HOME = URL_API_BASE + "service/home.json";//服务信息v2.3.1
    public static final String URL_SERVICE_WEATHER_CITIES = URL_API_BASE + "service/cities.json";//天气城市查询列表 v2.3.1
    public static final String URL_SERVICE_CITIES = URL_API_BASE + "service/provinces.json";//获取所有城市v2.3.1
    /**
     * 商户服务
     */
    public static final int STORES_DEFAULT_PAGESIZE = 20;

    public static final String URL_MERCHANT_DISTRICT_STORES = URL_BASE + "mch/district.json";
    public static final String URL_MERCHANT_SERVICE_NEARY = URL_BASE + "mch/nearby.json";//获取附近的商户信息 v2.3.1
    public static final String URL_MERCHANT_INFO_DETAIL = URL_BASE + "mch/detail.json";//获取商户详情 v2.3.1
    public static final String URL_MERCHANT_VIP_CARD_INFO = URL_BASE + "mch/vip_card.json";//服务会员卡信息 v2.3.1
    public static final String URL_MERCHANT_SERVICE_TYPE_DETAIL = URL_BASE + "mch/services.json"; //获取该区域提供的服务类型 v2.3.1
    public static final String URL_MERCHANT_VIP_CONSUME_RECORD = URL_BASE + "mch/consume_record.json"; //会员卡消费记录(2.3.1)
    public static final String URL_MERCHANT_VIP_CONSUME = URL_BASE + "mch/consume.json"; //会员卡消费(2.3.1)
    public static final String URL_MERCHANT_VIP_CONSUME_DETAIL = URL_BASE + "mch/consume_detail.json"; //会员卡消费(2.3.1)


    /***
     * 注册协议
     */
    public static final String URL_REGIST_PROTOCOL = URL_BASE + "html/agreement.html";//注册协议 v2.0
    /**
     * 常见问题
     */
    public static final String URL_COMMON_ERROR = "http://master.bidostar.com/faq/index.html";//常见问题  v2.0
    /**
     * 用户手册
     */
    public static final String URL_USER_MONUAL = "http://master.bidostar.com/guide/index.html";//用户手册  v2.0
    public static final String URL_TAISHAN_BAOXIAN = "http://api.bidostar.com/taishan/home.action";//泰山保险 v2.3
    /**
     * 驾驶证识别
     */
    public static final String URL_DRIVER_LISENCE_INFO = URL_BASE + "ocr/ocr_driver_img.json";
    /**
     * 行驶证识别
     */
    public static final String URL_DRIVERING_LISENCE_INFO = URL_BASE + "ocr/ocr_driving_img.json";

    /**
     * 短信功能相关
     */
    public static final int FUN_NORMAL = 0;// 普通
    public static final int FUN_BIND_PHONE = 1;// 绑定手机号
    public static final int FUN_REGISGER_PHONE = 2;// 注册手机号
    public static final int FUN_REGISGER_ACCIDENT = 3;// 生成事故处理单时获取对方验证码

    public static final int FUN_LOGIN = 5;
    public static final int FUN_WECHAT_BIND_PHONE = 6;

    /**
     * 手机账号约束
     */
    public static final String REGS = "[^[0-9]\\d{8}$\u4E00-\u9FA5]";

    public static int position = 0;

    /**
     * 车险报案
     */
   /* public static final int[] insurancePhotoEvidenceMoreItems = {R.drawable.ic_accident_more_before, R.drawable.ic_accident_more_after,
            R.drawable.ic_accident_more_on, R.drawable.ic_accident_more_owner, R.drawable.ic_accident_more_other, R.drawable.ic_accident_one_car_jia,
            R.drawable.ic_accident_more_car_jia,
            R.drawable.ic_accident_other};

    public static final int[] insurancePhotoEvidenceOneItems = {R.drawable.ic_accident_one_before, R.drawable.ic_accident_one_after,
            R.drawable.ic_accident_one_on, R.drawable.ic_accident_one_car_jia, R.drawable.ic_accident_other};

    public static final int[] insurancePhotoEvidenceMoreGrayItems = {R.drawable.ic_accident_more_before_1, R.drawable.ic_accident_more_after_1,
            R.drawable.ic_accident_more_on_1, R.drawable.ic_accident_more_owner_1, R.drawable.ic_accident_more_other_1, R.drawable.ic_accident_one_car_jia_1,
            R.drawable.ic_accident_more_car_jia_1,
            R.drawable.ic_accident_other_1};

    public static final int[] insurancePhotoEvidenceOneGrayItems = {R.drawable.ic_accident_one_before_1, R.drawable.ic_accident_one_after_1,
            R.drawable.ic_accident_one_on_1, R.drawable.ic_accident_one_car_jia_1, R.drawable.ic_accident_other_1};

    public static final int[] insurancePhotoEvidenceMoreTipItems = {R.drawable.ic_accident_more_before_tip, R.drawable.ic_accident_more_after_tip,
            R.drawable.ic_accident_more_on_tip, R.drawable.ic_accident_more_owner_tip, R.drawable.ic_accident_more_other_tip, R.drawable.ic_accident_owner_car_jia_tip,
            R.drawable.ic_accident_other_car_jar_tip};

    public static final int[] insurancePhotoEvidenceOneTipItems = {R.drawable.ic_accident_one_before_tip, R.drawable.ic_accident_one_after_tip,
            R.drawable.ic_accident_one_on_tip, R.drawable.ic_accident_owner_car_jia_tip, R.drawable.ic_accident_scan_tip, R.drawable.ic_accident_scan_tip};

*/
    public static final String[] PhotoDescribeMoreList = {"侧前方", "侧后方", "碰撞部位", "本方车辆全景", "对方车辆全景", "本方车架号", "对方车架号", "其它现场照片"};
    public static final String[] PhotoDescribeMoreTipList = {"侧前方照片必须上传", "侧后方照片必须上传", "碰撞部位照片必须上传", "本方车辆全景照片必须上传", "对方车辆全景照片必须上传", "本方车架号照片必须上传", "对方车架号照片必须上传"};
    public static final String[] PhotoDescribeOneList = {"侧前方", "侧后方", "碰撞部位", "车架号", "其它现场照片"};
    public static final String[] PhotoDescribeOneTipList = {"侧前方照片必须上传", "侧后方照片必须上传", "碰撞部位照片必须上传", "车架号照片必须上传", "驾驶证照片必须上传", "行驶证照片必须上传"};
    public static final String[] ACCIDENT_MORE_TYPE = {"追尾", "逆行", "倒车", "溜车", "开关车门", "违反交通信号", "未按规定让行", "并线", "全部责任的其他情形", "双方应付同等责任"};
    public static final String[] ACCIDENT_ONE_TYPE = {"车辆坠落", "侧翻", "自燃", "碰撞受损", "高空赘物受损", "水淹车"};

    public static final String ACCIDENT_PIC_FILE_URL = Environment.getExternalStorageDirectory().toString() + File.separator;  //单车事故图片目录
    public static final String VIOLATION_IMG_FILE = "obd/image/violation/";  //随手拍违章图片目录
    public static final String ONE_CAR_ACCIDENT_FILE = "obd/image/oneCar/";  //单车事故图片目录
    public static final String USER_HEADER_FILE = "obd/image/userHeader/";  //头像图片目录
    public static final String MORE_CAR_ACCIDENT_FILE = "obd/image/moreCar/"; //多车事故图片目录
    public static final String LICENSE_PLATE_PATH = "obd/image/licensePlate/"; //驾驶证照片
    public static final String DRIVER_CAR_LOG_PATH = "obd/log/driverCar/"; //行车日志文件

    public static final int IMG_SCALE = 2; //上传图片比例
    public static final int IMG_USER_HEADER_SCALE = 2; //上传头像拍照图片比例

    /**
     * 上传照片
     */
    public static final int UPLOAD_CAR_DRIVER_LICENSE_PIC = 1;//上传驾驶证照片

    /**
     * 车辆
     */
    public static final int VEHICLE_AUTH_NO = 0; //0:未认证;1:认证中;2:已认证;3:认证失败
    public static final int VEHICLE_AUTH_IN = 1;
    public static final int VEHICLE_AUTH_SUCCESS = 2;
    public static final int VEHICLE_AUTH_FAILURE = 3;

    public static final int FUEL_PRICE = 6;//油价
    public static final int VEHICLE_STATUS_GET_TIME = 3000;//车况页面更新数据的间隔时间(毫秒)

    /**
     * 随手拍违章
     */
    public static final int ONE_PAGE_COUNT = 3 * 7;//一次加载多少条
    public static final int TAKE_PHOTO_COUNT = 3;//拍几张照片

    /**
     * 后视镜
     */
    public static final int MIRROR_ONE_PAGE_COUNT = 3;//一次加载多少条
    public static final int MIRROR_PICTURE_TYPE = 207;//获取后视镜图片的类型：事件类型(拍照：207;碰撞:21)
    public static final int MIRROR_GET_PICTURE_FAILD_COUNT = 60;//后视镜获取失败的最大次数

    /**
     * 后视镜绑定流程演示 地址
     */
    public static final String MIRROR_VIEW = "http://mp.weixin.qq.com/s?__biz=MzIwODU1MTU5NA==&mid=2247483725&idx=1&sn=8105c65f84c183ce3aa38137c9cb05f1&scene=0#rd";
    public static final String COMPANY_TEL = "tel:027-87256318";//公司电话
    public static final String COMPANY_TEL_CONTENT = "02787256318";//公司电话

    /**
     * 客服电话
     * 027-87250935  027-87250945 这俩是客服部门的， 027-87250565
     * 是武汉技术部（杨涛）的
     */
    public static final String CUSTOMER_SERVICE_CONTENT_1 = "027-87250935 (客服1)";
    public static final String CUSTOMER_SERVICE_CONTENT_2 = "027-87250945 (客服2)";
    public static final String CUSTOMER_SERVICE_CONTENT_3 = "027-87250565 (技术客服)";

    public static final String CUSTOMER_SERVICE_1 = "tel:027-87250935";
    public static final String CUSTOMER_SERVICE_2 = "tel:027-87250945";
    public static final String CUSTOMER_SERVICE_3 = "tel:027-87250565";

    public static int MirrorIndexActivity_ISDESRTOY = 0;

    /**
     * 话题阅读
     */
    public static final int SHARED_PECCANCY_FLAG = 7; // 阅读标志
    public static final int SHARED_READER_FLAG = 6; // 阅读标志
    public static final int SHARED_TOPIC_FLAG = 5; // 话题标志
    public static final int TOPIC_PAGE_SIZE = 20; // 列表条数
    public static final int TOPIC_COMMENT_PAGE_SIZE = 10; // 评论条数
    public static final int TOPIC_TYPE = 1;
    public static final int READER_TYPE = 2;
    public static final String URL_TYPE_LIST = URL_BASE + "v3/forum/topics.json"; //获取阅读分类列表
    public static final String URL_DETAIL_LIST = URL_BASE + "v3/forum/posts.json"; // 获取阅读分类数据


}
