## excel 导入数据

```
    @PostMapping(value = "/importOutreachStore")
    @ApiOperation(value = "导入外联分店", tags = ApiSwaggerTags.OUTREACH_STORE_TAG)
    @ApiResponses({
            @ApiResponse(message = "导入外联分店", code = 200, response = Object.class)
    })
    public String importOutreachStore(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        // 获取excel内容
        List<List<String>> dataList = ExcelParseUtil.getExcelContent(file.getInputStream());
        dataList.foreach(e->{
                     //分店id
                     String storeId = data.get(0);
                     //分店名称
                     String storeName = data.get(1);
                     ....
        })
```

## excel 导出数据

### 自动调用模式

参考在resource文件目录下建立excel/excel.xml的文件
```
<?xml version="1.0" encoding="UTF-8"?>
<excels>

    <!--导出文件的案例-->
    <!--自动会调用beanName等于dynamicManagerRemote的exportDynamic(DynamicQueryPageForm)的方法-->
    <excel id="exportDynamic" beanName="dynamicManagerRemote" methodName="exportDynamic"
           paramType="com.saas.newhouse.service.garden.apilist.form.dynamic.DynamicQueryPageForm"
           fileName="动态导出">
        <column key="gardenName" width="80">楼盘名称</column>
        <column key="gardenCityName" width="80">城市</column>
        <column key="cuName" width="80">城市公司</column>
        <column key="type" width="80"  enum="com.saas.newhouse.service.garden.apilist.enums.DynamicTypeEnum"
            enumDescription="desc">动态类型</column>
        <column key="title" width="80">标题</column>
        <column key="createTime" dataType="date" dateFormat="yyyy-MM-dd" width="80">发布时间</column>
        <column key="createName" width="80">发布人</column>
        <column key="shareCount" width="80">分享数</column>
    </excel>
        <!--此方法需要自己查询出对应d数据集合-->
    <excel id="exportIntentionalClue" fileName="客源分配数据导出">
        <column key="clueNumber" width="120">线索编号</column>
        <column key="customerServiceState" width="100" enumDescription="desc"
                enum="com.saas.newhouse.service.intention.apilist.enums.RelationStatus">联系状态
        </column>
        <column key="clueState" width="100" enumDescription="desc"
                enum="com.saas.newhouse.service.intention.apilist.enums.ClueStatus">分配状态
        </column>
        <column key="assignTime" dataType="date" dateFormat="yyyy-MM-dd HH:mm:ss" width="120">分配时间</column>
        <column key="reserveTime" dataType="date" dateFormat="yyyy-MM-dd HH:mm:ss" width="120">报备日期</column>
    </excel>

    <!--此方法需要自己查询出对应d数据集合-->
    <excel id="exportClue" fileName="客源导出">
        <column key="clueNumber" width="120">线索编号</column>
        <column key="customerServiceState" width="100" enumDescription="desc"
                enum="com.saas.newhouse.service.intention.apilist.enums.RelationStatus">联系状态
        </column>
        <column key="clueState" width="100" enumDescription="desc"
                enum="com.saas.newhouse.service.intention.apilist.enums.ClueStatus">分配状态
        </column>
        <column key="assignTime" dataType="date" dateFormat="yyyy-MM-dd HH:mm:ss" width="120">分配时间</column>
        <column key="reserveTime" dataType="date" dateFormat="yyyy-MM-dd HH:mm:ss" width="120">报备日期</column>
    </excel>
</excel>
```
#### 单个sheet的excel文件导出
###### 1.自动调用模式的使用例子
```        
ExcelRelationDto relationDto = new ExcelRelationDto()
                //在excel文件配置的key
                .setTagKeyId("exportDynamic")
                //请求参数对象，设置了dataList可以不提供参数，queryObject对应的是使用，自动调用的时候提供
                .setQueryObject(new Object())
                //是否文件名加后缀
                .setFileNameDateEnd(false);
        //excel.xml对应的自动调用bean方法查询数据时候使用
excelSubUnit.excelExportRelationModel(relationDto,httpServletResponse);
```
###### 2.自行查询数据集合进行导出
```
        ExcelDataDto excelDataDto = new ExcelDataDto()
                                        //在excel文件配置的key
                                        .setTagKeyId("excelKey")
                                        //数据集
                                        .setDataList(new ArrayList())
                                        //是否文件名加后缀
                                        .setFileNameDateEnd(false);
        //不使用自动调用bean的模式，直接传数据集合进行导出
        excelSubUnit.excelExportDataModel(excelDataDto,httpServletResponse);
```
###### 3.实体对象利用对应类的swagger参数作为导出模版
###### 实体结构
```
@ApiModel(description = "demo class")
public class DemoModel {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "状态")
    @ExcelField(enumDesc ="desc" )
    private StatusEnum statusEnum;


    /**
     * 该属性不导出，如果配置了excel ignore=true
     */
    @ApiModelProperty(value = "修改人")
    @ExcelField(ignore = true)
    private String updateId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createTime;
```
###### 导出的api调用
```
ExcelClazDto<DemoModel> excelClazDto = new ExcelClazDto<DemoModel>()
                                                            //claz为excel对应的转换体
                                                            .setClaz(DemoModel.class)
                                                            //数据集
                                                            .setDataList(new ArrayList<>());
        //根据class的swagger信息，作为模板进线导出
excelSubUnit.excelExportClassMode(excelClazDto,httpServletResponse);
```

### 多sheet的excel文件导出

##### 1.根据excel标签的key和数据集合进行导出多Sheet导出
```
 ExcelDataDto excelDataDto1 = new ExcelDataDto()
                //在excel文件配置的key
                .setTagKeyId("exportIntentionalClue")
                //数据集
                .setDataList(new ArrayList())
                //是否文件名加后缀
                .setFileNameDateEnd(false);

ExcelDataDto excelDataDto2 = new ExcelDataDto()
        //在excel文件配置的key
        .setTagKeyId("exportClue")
        //数据集
        .setDataList(new ArrayList())
        //是否文件名加后缀
        .setFileNameDateEnd(false);
List<ExcelDataDto> dataDtoList = Arrays.asList(excelDataDto1,excelDataDto2);
excelSubUnit.multiSheetsExportDataModel(fileName,true,dataDtoList,httpServletResponse);
```
##### 2.根据实体对象的swagger参数和数据集合进行导出多Sheet导出
```
DemoModel demoModel = new DemoModel();
SheetModel sheetModel=new SheetModel();
ExcelClazDto<DemoModel> excelClazDto1 = new ExcelClazDto<DemoModel>()
                                            .setClaz(DemoModel.class)
                                            .setDataList(Arrays.asList(demoModel));

ExcelClazDto<SheetModel> excelClazDto2 = new ExcelClazDto<SheetModel>()
                                            .setClaz(SheetModel.class)
                                            .setDataList(Arrays.asList(sheetModel));

List<ExcelClazDto> clazDtoList = Arrays.asList(excelClazDto1,excelClazDto2);
excelSubUnit.multiSheetsExportClazModel(fileName,true,clazDtoList,httpServletResponse);
```
