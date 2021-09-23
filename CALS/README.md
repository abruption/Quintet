## CALS Lambda 소스 분석

**※ CalsConsoleWebAuthAuthorize Lambda는 실행하기 위해 development.json이 아닌 local.json으로 수정하여 실행해야 함.**

- CalsConsoleWebAuthAuthorize는 권한에서 Apply를 수행할 때 실행되는 Lambda
- CalsComWebCommonSelectData는 데이터를 조회하기 위한 Lambda

1. template.yaml 파일의 Handler 부분을 확인하면서 시작점 확인
2. Authorize와 SelectData 람다와 lambda.js 파일 비교 등

<hr>

## CalsConsoleWebAuthAuthorize 소스 분석
### controller.js & baseController.js
- @rdk/lambda의 `baseController.js`
    - [Object.assign](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Object/assign) (링크)
    - _objRequestParams, _eventSource 같이 변수명 앞에 UnderScore가 있는 경우는 Private한 지역변수나 Sub Function일 경우에 사용한다.  
<br />

### dao.js & baseDAO.js
- @rdk/orm의 `index.js`
    - mysql, msql, mysqlImport, DBConnectException, RdkOrmMysql 등의 필요한 Library등이 require 되어 선언되어 있다.  
<br />

- @rdk/api-cals의 `calsSdk.js`
    - rdk framework의 ORM MySQL 관련 서비스를 제공하는 모듈  
<br />  

- @rdk/api-aws의 `secretManager.js`
    ~~~js
    const AWS = require('aws-sdk')
    const secretsmanager = new AWS.SecretsManager({
    apiVersion: '2017-10-17'
    })
    ~~~
    - AWS-SDK의 SecretsManager 정보를 담고 있는 부분
<br/>

- @rdk/api-aws의 `arn.js`
    - rdk framework의 AWS API ARN 관련 서비스를 제공하는 모듈
    - @sandfox/arn을 require하여 사용하고 있음
        - arn 주소를 관리하기 위해 splitN을 통해 Scheme, partition, service, region 등을 join으로 합쳐서 Return함.   
<br/>

- Server Script Variables
    ~~~js
    this.bCallScript = clsConnectionGenerateModel.bCallScript
    this.arrContorls = clsConnectionGenerateModel.arrContorls
    this.arrFields = clsConnectionGenerateModel.arrFields
    this.objServerScriptInfo = clsConnectionGenerateModel.objServerScriptInfo
    this.objAccountInfo = clsConnectionGenerateModel.objAccountInfo
    this.objCalsSDK = null
    ~~~
<br/>

#### baseDAO의 getLoginSubDAO Function 실행 순서
1. Component DataSource 존재 여부 조회
2. Component DataSource Connecion 내용 조회
3. Application ID 기준 application 정보 조회 내용 조회  
    - Sub Connection 객체 생성 (NODE_ENV가 `local`인지, local이 아닌 `production` 환경인지 확인)  
    - Component DataSource 존재 여부 확인 (`production` 환경일 경우)  
        - Application이 Console이 아닐 경우 확인  
    - ServerScript 관련 로직 시작

<br />

## CalsComWebCommonSelectData 소스 분석
### controller.js & baseController.js
- `service.js` 모듈을 로드하여 clsService라는 새로운 객체를 생성한다.
- @rdk/lambda의 `baseController.js`
    - [Object.assign](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Object/assign) (링크)
    - _objRequestParams, _eventSource 같이 변수명 앞에 UnderScore가 있는 경우는 Private한 지역변수나 Sub Function일 경우에 사용한다.  
    - 요청 객체와 반환 객체를 생성하고 `Object.assign`의 전처리를 거쳐서 eventSource 별 Parsing 절차를 거친 후 반환하여 `global` 변수로 지정한다.
- execute()를 통해 필수 필드 중 입력되지 않은 정보가 있는지 확인한다. (존재하지 않으면 미리 정의된 Exception을 throw)
- 선택 필드 중 입력되지 않은 정보가 있다면 기본 값으로 세팅한다.

<br />

### service.js & baseService.js
- `dao.js` 모듈을 로드하여 clsDAO라는 새로운 객체를 생성한다.
- @rdk/lambda의 `baseService.js`
    - @rdk/api-cals의 `CalsSdk.js`
    - @rdk/api-aws의 `arn.js`
    - exception 디렉토리의 `calssdkExecuteException` 모듈 require
    - `initCalsSDK(objParams)` : awsAccountId, sAccountId 등 정보를 wrap하고 Script Type별 default value를 셋팅한다. 이외 NODE_ENV 환경에 따른 setEmptyValue 설정 또는 Arn 설정 등이 이루어진다.
    - `executeBSScript(objParams, objReturn)` : initCalsSDK(objParams)의 작업이 완료되면 해당 정보를 CALS 상수 변수에 저장하고 메모리를 출력하는 기능을 수행한다.
    - `executePreScript(objParams)` : ServerScript를 수행하기 전 사전 작업을 수행하는 것으로 보임
    - `executePostScript(objParams, objReturn)` : ServerScript를 실제로 작동시키는 코드로 보임
- `getSymbolicData(objParams)` : Symbolic String 데이터 전체 조회
- `parseResult(objParams)` : Component 유형에 따른 결과값 파싱
- `getData()` : CalsComWebCommonSelectData Lambda의 메인 실행 함수  
    1. SubConnection 정보 조회 (objReturnSubDAO)
        ~~~js
        let clsSubDAO = null
        const objReturnSubDAO = await global.efsCommonModule.getDataSource({
            sDbSchema: global.clsBaseConstant.DB_CONFIG.DB_SCHEMA,
            sAccountId: objRequestParams.accountId,
            sApplicationId: objRequestParams.applicationId,
            sComponentId: objRequestParams.componentId,
            bConsoleFlag: objRequestParams.consoleFlag,
            sPlatformType: objRequestParams.platformType
            })
        if (global.clsBaseUtil.CompareUtility.isNotEmpty(objReturnSubDAO)) {
            global.rdkLogger.info('Using Sub DAO.')
            clsSubDAO = new DAO(objReturnSubDAO.clsSubConnectionGenerateModel)
            objRequestParams.sSubDataBase = objReturnSubDAO.subDataBase
        } else {
        this.setDAOParams({
            sUserId: objRequestParams.userId
        })
        objReturn = await clsDAO.selectUserAdminFlag({ objDAOParams: this.objDAOParams, objReturn })
        if (objReturn.data.length > 0) {
            objRequestParams.adminFlag = global.clsBaseUtil.ConvertUtility.convertCharToBool(objReturn.data[0].adminFlag)
            }
        }

        this.setDAOParams({
            sDbSchema: global.clsBaseConstant.DB_CONFIG.DB_SCHEMA,
            sComponentId: objRequestParams.componentId,
            sAccountId: objRequestParams.accountId,
            sApplicationId: objRequestParams.applicationId,
            sLinkId: objRequestParams.linkId,
            sPlatformType: objRequestParams.platformType
        })

        await global.efsCommonModule.getConfigData({ sType: 'select', objRequestParams })
        ~~~

    2. Data 조회
        ~~~js
        objRequestParams.arrDataRows = (await this.selectData({ objParams: objRequestParams, clsSubDAO, objReturn })).data
        ~~~

    3. Component 유형에 따른 결과값 parsing
        ~~~js
        objReturn.data = await this.parseResult(objRequestParams)

        global.rdkLogger.result(objReturn)
        return objReturn
        ~~~


- `selectData()` : 데이터 조회
    1. 검색 조건 설정
        ~~~js
        this.clearDAOParams()
        this.setDAOParams({
            sDbSchema: global.clsBaseConstant.DB_CONFIG.DB_SCHEMA,
            sComponentId: objParams.componentId,
            sUserId: objParams.userId,
            sAccountId: objParams.accountId,
            sApplicationId: objParams.applicationId,
            sSearchExpr: objParams.searchExpr,
            arrSearchSpecs: objParams.searchSepcs,
            sSortExpr: objParams.sortExpr,
            arrSortSpecs: objParams.sortSpecs,
            arrResultFields: objParams.resultFields,
            sLinkId: objParams.linkId,
            sLinkValue: objParams.linkValue,
            iPageIndex: objParams.pageIndex,
            iPageSize: objParams.pageSize,
            iTimezone: objParams.timezone,
            sDeploy: objParams.deploy,
            bExceptTl: objParams.exceptTl,
            bAdminFlag: objParams.adminFlag,
            objConfigData: objParams.objConfigData,
            sPlatformType: objParams.platformType,
            sSubDataBase: objRequestParams.sSubDataBase
        })
        ~~~

    2. ServerScript 정보 조회
        ~~~ js
        this.objDAOParams.objServerScriptInfo = await clsDAO.getServerScriptInfo(this.objDAOParams)
        this.objDAOParams = await this.executePreScript(this.objDAOParams)
        if (global.clsBaseUtil.CompareUtility.isNotEmpty(clsSubDAO)) {
        objReturn = await clsSubDAO.selectData({ objDAOParams: this.objDAOParams, objReturn })
        } else {
        objReturn = await clsDAO.selectData({ objDAOParams: this.objDAOParams, objReturn })
        }
        objReturn = await this.executePostScript(this.objDAOParams, objReturn)

        global.rdkLogger.result(objReturn)

        return global.lodash.cloneDeep(objReturn)
        ~~~

<br />

### dao.js & baseDAO.js
- `sqlstring` 모듈을 로드하여 sqlString이라는 상수 변수로 선언
- exception 디렉토리의 `wrongConfigException` 모듈을 WrongConfigException 상수 변수로 선언
- @rdk/lambda의 `baseDAO.js`
    - @rdk/orm 모듈 require
    - @rdk/orm에서 `DBConnectException` 모듈 require
    - @rdk/api-aws의 `secretManager.js`에서 mysql 관련 rdk 모듈 require
    - exception 폴더에서 `dataSQLException` 모듈 require
    - Connection 관련 객체 생성
        ~~~js
        global.ConnectionGenerateModel = rdkOrm.ConnectionGenerateModel
        const clsBaseConnectionGenerateModel = new global.ConnectionGenerateModel(
            {
                sHost: global.clsRdkConfig.DB_CONFIG.DB_HOST,
                sUser: global.clsRdkConfig.DB_CONFIG.DB_USER,
                sPassword: global.clsRdkConfig.DB_CONFIG.DB_PASSWORD,
                sDbSchema: global.clsRdkConfig.DB_CONFIG.DB_SCHEMA,
                sEngine: global.clsRdkConfig.DB_CONFIG.DB_ENGINE
            }
        )
        ~~~
    - `findConnection(clsConnectionGenerateModel)` : DB Host 검색 및 설정
        ~~~js
        findConnection (clsConnectionGenerateModel) {
        let objConnection = null
        const arrHosts = Object.keys(global.objDAOs)

        for (const sHost in arrHosts) {
            if (clsConnectionGenerateModel.getHost() === sHost) {
                objConnection = global.objDAOs[sHost]
                break
            }
        }
        return objConnection
        }
        ~~~
    - `netConnection(clsConnectionGenerateModel)` : DB 포트 및 Schema 설정
        ~~~js
        newConnection (clsConnectionGenerateModel) {
        if (global.tunnels) {
            const tunnel = global.tunnels.find(t => t.dstHost.indexOf(clsConnectionGenerateModel.sDbSchema) > -1)
            clsConnectionGenerateModel.sPort = tunnel ? tunnel.localPort : 3306
        }

        const objConnection = this.objDbEngine.getConnection(clsConnectionGenerateModel)
        global.objDAOs[clsConnectionGenerateModel.getDbSchema()] = this
        return objConnection
        }
        ~~~
    - `startTransaction()` : ORM을 통한 DB 실제 접속
        ~~~js
        async startTransaction () {
            try {
                await this.clsQueryExecuteModel.getConnection().connect()
                if (!this.bActiveTransaction) {
                    if (global.clsBaseConstant.DB_ENGINE.MSSQL.indexOf(this.sDbEngine) > -1) {
                        await this.clsQueryExecuteModel.getConnection().transaction()
                    } else {
                        await this.clsQueryExecuteModel.getConnection().query(global.clsBaseConstant.ORM_TRANSACTION_COMMAND.START)
                    }
                    this.changeTransactionStatus(true)
                }
            } catch (error) {
            global.rdkLogger.error(`baseDAO-startTransaction : ${error.message}`)
            throw new DBConnectException(error.message)
            }
        }
        ~~~
    - `commitTransaction()` : DB Engine 확인 후, Query Commit
        ~~~js
        async commitTransaction () {
            try {
                if (this.bActiveTransaction) {
                    if (global.clsBaseConstant.DB_ENGINE.MSSQL.indexOf(this.sDbEngine) > -1) {
                    await this.clsQueryExecuteModel.getConnection().commit()
                    } else {
                    await this.clsQueryExecuteModel.getConnection().query(global.clsBaseConstant.ORM_TRANSACTION_COMMAND.COMMIT)
                    }
                    this.changeTransactionStatus(false)
                    await this.endTransaction()
                }
            } catch (error) {
                if (global.clsBaseConstant.DB_ENGINE.MSSQL.indexOf(this.sDbEngine) > -1) {
                    global.rdkLogger.error(`baseDAO-executeQuery : ${error.message.name}`)
                    throw new DataSQLException(error.message.name)
                } else {
                    global.rdkLogger.error(`baseDAO-executeQuery : ${error.message}`)
                    throw new DataSQLException(error.message)
                }
            }
        }
        ~~~
    - `endTransaction()` : ROLLBACK 후 DB Connection 종료
        ~~~js
        async endTransaction () {
            try {
                if (this.bActiveTransaction) {
                    if (global.clsBaseConstant.DB_ENGINE.MSSQL.indexOf(this.sDbEngine) > -1) {
                    await this.clsQueryExecuteModel.getConnection().rollback()
                    } else {
                        await this.clsQueryExecuteModel.getConnection().query(global.clsBaseConstant.ORM_TRANSACTION_COMMAND.ROLLBACK)
                    }
                    this.changeTransactionStatus(false)
                }
                await this.clsQueryExecuteModel.getConnection().end()
            } catch (error) {
                global.rdkLogger.error(`baseDAO-endTransaction : ${error.message}`)
                throw new DBConnectException(error.message)
            }
        }
        ~~~
    - `changeTransactionStatus(bFlag)` : -
        ~~~js
        changeTransactionStatus (bFlag) {
            this.bActiveTransaction = bFlag
        }
        ~~~
    - `executeQuery(objReturn)` : BaseDAO 클래스 상단 부분에 `objConnection`과 `objMapper` 정보가 담겨있는 `this.clsQueryExecuteModel`를 쿼리에 담아 수행하고 Connection을 종료하여 결과 값을 return
        ~~~js
        async executeQuery (objReturn) {
            try {
            let objQueryResult = []
            if (this.bActiveTransaction) {
                objQueryResult = await this.objDbEngine.executeQuery(this.clsQueryExecuteModel)
            } else {
                await this.clsQueryExecuteModel.getConnection().connect()
                objQueryResult = await this.objDbEngine.executeQuery(this.clsQueryExecuteModel)
                await this.clsQueryExecuteModel.getConnection().end()
            }

            objReturn.success = true
            objReturn.data = objQueryResult
            } catch (error) {
            if (global.clsBaseConstant.DB_ENGINE.MSSQL.indexOf(this.sDbEngine) > -1) {
                global.rdkLogger.error(`baseDAO-executeQuery : ${error.message.name}`)
                throw new DataSQLException(error.message.name)
            } else {
                global.rdkLogger.error(`baseDAO-executeQuery : ${error.message}`)
                throw new DataSQLException(error.message)
            }
            }

            return objReturn
        }
        ~~~
    - `getServerScriptInfo({ sAccountId, sComponentId, sBizObjectId, bConsoleFlag, sPlatformType, sBizSvcName })` : ServerScript 관련 로직 수행
    - `getSubDAO(sDbSchema, sAccountId, sApplicationId, sComponentId, sOrigin, sPlatformType)`
        1. Component DataSource 존재 여부 조회
        2. Component DataSource Connection 내용 조회
        3. Application ID 기준 application 정보 내용 조회
            - NODE_ENV 환경에 따른 Sub Connection 객체 생성
            - 실제 배포 환경일 경우 (Component Datasource가 존재하는 경우 또는 Application이 Console이 아닌 경우)
            - ServerScript 관련 로직 시작
    - `getLoginSubDAO({ sDbSchema, sAccountId, sApplicationId, sComponentId, sBizObjectId, bConsoleFlag, sPlatformType })` : getSubDAO와 동일한 절차
- `selectUserAdminFlag({objDAOParams, objReturn})` : User가 Admin인지 확인 (DB에서 UserAdminFlag 확인)
    ~~~js
    async selectUserAdminFlag ({ objDAOParams, objReturn }) {
        global.rdkLogger.param(objDAOParams)

        this.clsQueryExecuteModel.setQuery(
            global.clsBaseConstant.OBJ_MAPPER.namespace.selectData,
            global.clsBaseConstant.OBJ_QUERY_INFO.selectData.selectUserAdminFlag,
            objDAOParams
        )
        objReturn = await this.executeQuery(objReturn)

        global.rdkLogger.result(objReturn)
        return objReturn
  }
    ~~~
- `selectMakeQueryBaseData({objDAOParams, objReturn})` : Query Generation에 필요한 정보 전체 Select
    ~~~js
    async selectMakeQueryBaseData ({ objDAOParams, objReturn }) {
        global.rdkLogger.param(objDAOParams)

        this.clsQueryExecuteModel.setQuery(
            global.clsBaseConstant.OBJ_MAPPER.namespace.selectData,
            global.clsBaseConstant.OBJ_QUERY_INFO.selectData.selectMakeQueryBaseData,
            objDAOParams
        )
        objReturn = await this.executeQuery(objReturn)

        global.rdkLogger.result(objReturn)
        return objReturn
    }
    ~~~
- `selectSymbolicData({objDAOParams, objReturn})` : SymbolicData 전체 조회
    ~~~js
    async selectData ({ objDAOParams, objReturn }) {
        global.rdkLogger.param(objDAOParams)
        const objMakeQueryResult = await global.efsCommonModule.makeQuery({sType: 'select',objReqParam: objDAOParams})
        const arrFileControlIds = objMakeQueryResult.arrFileControlIds
        
        this.clsQueryExecuteModel.setDirectQuery(objMakeQueryResult.sQuery, global.clsBaseConstant.ORM_QUERY_TYPE.SELECT, true)
        objReturn = await this.executeQuery(objReturn)
        for (const iDataIndex in objReturn.data) {
            if (global.clsBaseUtil.CompareUtility.isNotEmpty(objReturn.data[iDataIndex].lockFlag)) {
                objReturn.data[iDataIndex].lockFlag = global.clsBaseUtil.ConvertUtility.convertCharToBool(objReturn.data[iDataIndex].lockFlag)
            }

        for (const iFileControlIdIndex in arrFileControlIds) {
            objReturn.data[iDataIndex][arrFileControlIds[iFileControlIdIndex]] = JSON.parse(objReturn.data[iDataIndex][arrFileControlIds[iFileControlIdIndex]])
            }
        }

        global.rdkLogger.result(objReturn)
        return objReturn
    }
    ~~~
- `getNotAcFlag`,  `getWhereTlField`, `getWhereXTableField`, `getXTableField`, `getCaseXTableField` 등 필요한 조건에 맞는 Mapper가 호출되도록 파라미터를 넘겨받아 미리 작성된 쿼리문에 파라미터를 담아 Return한다.

<br />

### applicationDAO.js
- model 폴더의 `applicationLambdaBaseModel` 모듈을 AppLambdaBaseModel 상수 변수로 선언
- exception 폴더의  `applicationLambdaException` 모듈을 AppLambdaException 상수 변수로 선언
- `applicationDAO` 클래스가 `baseDAO.js`를 extend 하여 사용 함
- `insert({ sQueryId, objParam, objReturn, bIgnoreFlag })` 
    1. clsQueryExecuteModel 객체 등록 (DB Engine 정보 및 sQueryId, ORM_QUERY_TYPE.INSERT 등)
    2. insert 수행 (baseDAO.js의 executeQuery() 메서드 실행)
- `update({ sQueryId, objParam, objReturn, bIgnoreFlag })`
    1. clsQueryExecuteModel 객체 등록 (DB Engine 정보 및 sQueryId, ORM_QUERY_TYPE.UPDATE 등)
    2. update 수행 (baseDAO.js의 executeQuery() 메서드 실행)
- `delete ({ sQueryId, objParam, objReturn, bIgnoreFlag })`
    1. clsQueryExecuteModel 객체 등록 (DB Engine 정보 및 sQueryId, ORM_QUERY_TYPE.DELETE 등)
    2. delete 수행 (baseDAO.js의 executeQuery() 메서드 실행)
- `select ({ sQueryId, objParam, objReturn })`
    1. clsQueryExecuteModel 객체 등록 (DB Engine 정보 및 sQueryId, ORM_QUERY_TYPE.SELECT 등)
    2. select 수행 (baseDAO.js의 executeQuery() 메서드 실행)
- `callProcedure({ sQueryId, objParam, objReturn })`
    1. clsQueryExecuteModel 객체 등록 (DB Engine 정보 및 sQueryId, ORM_QUERY_TYPE.PROCEDURE 등)
    2. Procedure 수행 (baseDAO.js의 executeQuery() 메서드 실행)
- `checkParams(objParam, bIsInsert)` : 파라미터가 비었을 경우 초기 값 지정

<br />

## Authorize와 SelectData 람다와 lambda.js 파일 비교
1. SelectData에서는 `rdkLambdaInit`이라는 모듈을 불러오고 해당 모듈을 통해 initFunction을 파라미터로 보내 실행한다.
2. SelectData에서는 Library, Exception 선언등이 Lambda 실행 시에 이루어졌다면, Authorize에서는 Lambda 실행 전에 이미 선언되어 있다.
3. Authorize Lambda에서는 `EFS Module`이 존재하지 않는다.
4. SelectData에서는 `require Cache`를 삭제하는 부분이 존재한다.