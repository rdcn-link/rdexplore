<template>
    <div class="home-index">
        <h1>数据源详情</h1>
        <div class="search">
            <div class="inner">
                <el-input v-model="uriVal" placeholder="请输入内容"></el-input>
                <el-button type="primary" @click="handleOnSearch" :disabled="uriVal.length==0" :loading="searchLoading">
                    {{searchLoading?'Loading':'Go'}}
                </el-button>
            </div>
        </div>

        <div class="objName" v-if="dataObj.name"><h2>{{ dataObj.name }}</h2>
        </div>

        <section>
            <div class="rdf-description" v-if="Object.keys(resourceMetaData).length > 0">
                <div class="title">MetaData</div>
                <div class="content">
                    <ul>
                        <li v-for="(value, key) in resourceMetaData" :key="key">
                            <template v-if="typeof value === 'object' && value !== null &&!Array.isArray(value)">
                                <label>{{ key }}</label>
                                <div class="test">
                                    <div v-for="(subvalue, subkey) in value" :key="subkey" class="secondItem">
                                        <div class="detail">

                                            <div class="subkey">{{ subkey }}</div>
                                            <div class="subvalue">{{ subvalue }}</div>

                                        </div>
                                    </div>
                                </div>
                            </template>
                            <template v-else-if="!Array.isArray(value)">
                                <label>{{ key }}</label>
                                <div class="test">
                                    <div v-if="typeof value=='string'"
                                         v-html="value.replace(/\[/g,'').replace(/\]/g,'<br/>').replace(/\n/g,'<br/>')"></div>
                                    <div v-else>{{value}}</div>
                                </div>

                            </template>
                            <template v-else>
                                <label>{{ key }}</label>
                                <div class="test">
                                    <div v-for="(obj, index) in value" :key="index" class="secondItem">
                                        <template v-if="typeof obj=='string'">
                                            <div class="subvalue">{{ obj }}</div>
                                        </template>
                                        <template v-else>
                                            <div v-for="(subvalue, subkey) in obj" :key="subkey" class="detail">

                                                <div class="subkey">{{ subkey }}</div>
                                                <div class="subvalue">{{ subvalue }}</div>

                                            </div>
                                        </template>

                                    </div>
                                </div>
                            </template>

                        </li>
                    </ul>
                </div>
            </div>
            <div class="table-structure" v-if="dataFrameVos.length > 0" v-for="(dataframe,dindex) in dataFrameVos"
                 :key="dindex">
                <div class="titleDiv">
                    <div class="title">DataFrame({{dataframe.dataframeId}})</div>
                    <div class="ask">
                        <el-tooltip class="item" effect="light" content="代码分析" placement="bottom">
                            <i class="el-icon-caret-right"
                               @click="getCodeData(dataframe.refUri,dindex,dataframe.dataframeId)"
                               style="cursor:pointer;font-size: 22px"></i>
                        </el-tooltip>
                    </div>
                </div>


                <div class="content">
                    <el-table :data="dataframe.jsonData" border style="width: 100%">
                        <el-table-column v-for="(item,i) in dataframe.tableProps" :prop="item" :label="item" :key="i"
                                         show-overflow-tooltip>
                            <template slot-scope="scope" >
                                <template v-if="item=='parsers'">
                                    <div  class="parsersButton">
                                        <el-button
                                                v-for="(pitem,pindex) in scope.row[item]"
                                                :key="pindex"
                                                size="small" style="font-size: 12px;margin-bottom: 8px"
                                                    @click="openParsers(pitem,dataframe.refUri,dindex,scope.$index)"
                                        >{{pitem.parserName}}
                                        </el-button>
                                    </div>

                                </template>
                                <template v-else>
                                    {{ scope.row[item] }}
                                </template>
                            </template>


                        </el-table-column>
                        <el-table-column
                                prop="openable"
                                label="Options"
                                width="180">
                            <template slot-scope="scope">
                                <template v-if="scope.row.openable">
                                    <el-button v-if="!scope.row.isOpen" size="small" style="font-size: 12px"
                                               @click="openItem(dataframe.refUri,dindex,scope.$index)"
                                               :loading="scope.row.opening">{{scope.row.opening?'':'open'}}
                                    </el-button>
                                    <div v-else style="padding: 5px 0">
                                        <el-tooltip class="item" effect="light" content="样例数据" placement="bottom">
                                            <i class="el-icon-s-data"
                                               @click="getSampleData(dataframe.refUri,dindex,scope.$index,scope.row.dataframeId)"></i>
                                        </el-tooltip>
                                        <el-tooltip class="item" effect="light" content="代码分析" placement="bottom">
                                            <i class="el-icon-caret-right"
                                               @click="getCodeData(dataframe.refUri,dindex,scope.row.dataframeId,scope.$index)"></i>
                                        </el-tooltip>
                                    </div>
                                </template>
                            </template>

                        </el-table-column>


                    </el-table>
                </div>
            </div>
        </section>

        <el-dialog :visible.sync="parserVisible" width="40%" custom-class="dialogDiv">
            <el-descriptions class="margin-top" title="parser详情" :column="1" :size="size" border>
                <el-descriptions-item  v-for="(value, key) in parserData.parserDetail" :key="key" :label="key" class="description-item">{{value}}</el-descriptions-item>
            </el-descriptions>
            <div style="margin: 30px 0">  <el-button type="primary" @click="clickUse">使用</el-button></div>


        </el-dialog>


        <el-dialog :visible.sync="dialogTableVisible" width="60%" custom-class="dialogDiv">
            <div class="table-structure">
                <div class="title">表结构</div>
                <div class="content">
                    <el-table :data="schemaFields" border style="width: 100%" v-loading="tableLoading">
                        <el-table-column prop="name" label="name">
                        </el-table-column>
                        <el-table-column prop="type" label="type">
                        </el-table-column>
                        <el-table-column prop="comment" label="comment" show-overflow-tooltip>
                        </el-table-column>

                    </el-table>
                </div>
            </div>
            <div class="table-structure">
                <div class="title">样例数据</div>
                <div class="content">
                    <el-table :data="sampleData" border v-loading="tableLoading">
                        <el-table-column v-for="(item,i) in sampleTitle" :prop="item" :label="item"
                                         :key="i" show-overflow-tooltip></el-table-column>

                    </el-table>
                </div>
            </div>


        </el-dialog>

        <el-dialog :visible.sync="dialogCodeVisible" width="60%" custom-class="dialogDiv">
            <div class="code-editing">
                <div class="header">
                    <div style="display: flex;align-items: center">
                        <h2>代码分析</h2>
                        <!--                        <el-button type="primary" icon="el-icon-download" size="mini" @click="handleSQLDownLoad"-->
                        <!--                                   style="height: 28px;margin-left: 30px"-->
                        <!--                                   v-if="performTable.length>0">-->
                        <!--                            下载全量结果数据-->
                        <!--                        </el-button>-->
                    </div>
                </div>
            </div>
            <div id="app" :key="langValue" v-loading="loading" element-loading-text="分析中"
                 element-loading-spinner="el-icon-loading"
                 element-loading-background="rgba(255,255,255,0.4)" v-if="dialogCodeVisible">
                <terminal name="my-terminal" @exec-cmd="onExecCmd" @on-keydown="onKeydown"
                          :title="'代码分析'" :context="langValue" :auto-help="false"
                          :init-log="initLog">
                    <template #textEditor="{ data }">
      <textarea name="editor"
                class="t-text-editor"
                v-model="data.value"
                @focus="data.onFocus"
                @blur="data.onBlur"></textarea>

                        <div class="t-text-editor-floor" align="center">
                            <button class="t-text-editor-floor-btn" @click="_textEditorClose(false)">Cancel</button>
                            <button class="t-text-editor-floor-btn" @click="_textEditorClose(true)">Save & Close
                            </button>
                        </div>

                    </template>
                </terminal>
            </div>
        </el-dialog>
        <div v-show="analysisTotal" >
            <div class="code-editing">
                <div class="header">
                    <div style="display: flex;align-items: center">
                        <h2>代码分析</h2>
                    </div>
                </div>
            </div>
            <div id="app1"  v-loading="loadingTotal" element-loading-text="分析中"
                 element-loading-spinner="el-icon-loading"
                 element-loading-background="rgba(255,255,255,0.4)" >
                <terminal name="my-terminal1" @exec-cmd="onExecCmdTotal" @on-keydown="onKeydown"
                          :title="'代码分析'" :context="'JOIN'"
                          :init-log="initLog12" >
                </terminal>
            </div>
        </div>

    </div>
</template>

<script>
    import {Terminal, api as TerminalApi} from "vue-web-terminal"


    export default {
        name: 'HomeView',
        components: {
            Terminal
        },
        computed: {
            initLog0(){
                return this.initLog1
            }
        },
        watch: {},
        data() {
            return {
                parserData:{},
                parserVisible:false,
                dataFrameVos: [],
                dialogTableVisible: false,
                enableTextEditor: false,
                analysisTotal: false,
                initLog: [],
                initLog1: [],
                initLog11: [{
                    content: '# 提示：请使用faird协议方法进行ASK操作。示例：<br/>1.获取schema：SCHEMA<br/>2.获取数据长度：LENGTH<br/>3.获取数据样例：SAMPLE<br/>4.获取前n数据：LIMIT {numRows}<br/>5.获取某几列数据：SELECT {col1, col2...}<br/>6.执行SQL语句：SQL "{sqlQuery}"<br/>7.联合查询：JOIN {dataframeId} <br/>8.打开某一行：OPEN {index}<br/>',
                }],
                initLog12: [{
                    content: '# 提示：请使用faird协议方法对已打开的DataFrames进行联合查询ASK操作。示例：<br/>1.INNER_JOIN {dataframe_id1} {dataframe_id2}<br/>2.LEFT_JOIN {dataframe_id1} {dataframe_id2}<br/>3.RIGHT_JOIN {dataframe_id1} {dataframe_id2}<br/>4.FULL_JOIN {dataframe_id1} {dataframe_id2}<br/>5.CROSS_JOIN {dataframe_id1} {dataframe_id2}<br/>6.UNION {dataframe_id1} {dataframe_id2}<br/>7.UNION_ALL {dataframe_id1} {dataframe_id2}<br/>8.INTERSECT {dataframe_id1} {dataframe_id2}<br/>9.INTERSECT_ALL {dataframe_id1} {dataframe_id2}',
                }],

                tipsText: '# 提示：请使用faird协议方法进行ASK操作。示例：<br/>1.获取schema：SCHEMA<br/>2.获取数据长度：LENGTH<br/>3.获取数据样例：SAMPLE<br/>4.获取前n数据：LIMIT {numRows}<br/>5.获取某几列数据：SELECT {col1, col2...}<br/>6.执行SQL语句：SQL "{sqlQuery}"<br/>7.联合查询：JOIN {dataframeId} <br/>8.打开某一行：OPEN {index}<br/>',
                pythonText: '',
                // pythonText: '# 数据资源作为二维表格结构数据，请使用dataframe变量对资源进行代码分析<br/># 有关Pandas DataFrame的各种操作和方法的详细说明请参考官方文档：<br/>https://pandas.pydata.org/pandas-docs/stable/reference/frame.html',
                loading: false,
                loadingTotal: false,
                langValue: 'ASK',
                langOptions: [],
                themesValue: 'xcode',
                themesOptions: [
                    {
                        value: 'xcode',
                        label: 'xcode'
                    }, {
                        value: 'eclipse',
                        label: 'eclipse'
                    }, {
                        value: 'monokai',
                        label: 'monokai'
                    }, {
                        value: 'cobalt',
                        label: 'cobalt'
                    },
                ],
                performResult: '',
                performTitle: [],
                performData: [],
                arr: [ //将brace/theme文件夹下的所有主题名字拷贝出来
                    'ambiance',
                    'chaos',
                    'chrome',
                    'clouds',
                    'clouds_midnight',
                    'cobalt',
                    'crimson_editor',
                    'dawn',
                    'dracula',
                    'dreamweaver',
                    'eclipse',
                    'github',
                    'gob',
                    'gruvbox',
                    'idle_fingers',
                    'iplastic',
                    'katzenmilch',
                    'kr_theme',
                    'kuroir',
                    'merbivore',
                    'merbivore_soft',
                    'monokai',
                    'mono_industrial',
                    'pastel_on_dark',
                    'solarized_dark',
                    'solarized_light',
                    'sqlserver',
                    'terminal',
                    'textmate',
                    'tomorrow',
                    'tomorrow_night',
                    'tomorrow_night_blue',
                    'tomorrow_night_bright',
                    'tomorrow_night_eighties',
                    'twilight',
                    'vibrant_ink',
                    'xcode',
                ],
                options: {
                    tabSize: 4, // tab默认大小
                    showPrintMargin: false, // 去除编辑器里的竖线
                    fontSize: 16, // 字体大小
                    highlightActiveLine: true, // 高亮配置
                    enableBasicAutocompletion: true, //启用基本自动完成
                    enableSnippets: true, // 启用代码段
                    enableLiveAutocompletion: true, // 启用实时自动完成
                },
                aceConfig: {
                    selectTheme: 'tomorrow_night_blue'
                },

                content: '',

                uriVal: "fair://127.0.0.1/eaa5d25a5b1549a08b49b45bb0ed33ff",
                // uriVal: "",
                dataset: [],
                operator: [],
                dataObj: {},
                rdfDescription: {},
                fields: [],
                schemaFields: [],

                sampleData: [],
                sampleTitle: [],
                tableLoading: true,
                scriptTemp: '',//临时的python语句

                resourceTitle: [],
                resourceData: [],

                dialogCodeVisible: false,
                resourceFirstIndex: 9999,
                resourceSecondIndex: 9999,
                resourceMetaData: {},
                searchLoading: false,

                performTable: [],
                dataframeId: '',

            }
        },
        mounted() {
            this.getDesc();
        },
        methods: {
            clickUse(){
                this.parserVisible=false;
                this.dataFrameVos[this.parserData.firstIndex].jsonData[this.parserData.secondIndex].opening = false;
                this.openItem(this.parserData.uri,this.parserData.firstIndex,this.parserData.secondIndex,this.parserData.parserDetail.ID)
            },
            openParsers(pitem,uri,firstIndex,secondIndex){
                this.parserVisible=true;
                this.parserData={parserDetail:pitem,uri,firstIndex,secondIndex};
            },
            getDesc() {
                let url1 = '/getDesc';
                let url2 = '/getDesc2';

                this.$api.setGetType(url1).then(res => {
                    this.initLog.push({content: res})
                });
                this.$api.setGetType(url2).then(res => {
                    this.initLog1.push({content: res})
                })
            },
            //点击open
            openItem(uri, firstIndex, secondIndex,parser='') {
                this.dataFrameVos[firstIndex].jsonData[secondIndex].opening = true;

                let url = '/open';


                let params = {
                    uri,
                    firstIndex,
                    secondIndex,
                    parser
                };
                this.$api.setGetType(url, params).then(res => {
                    if (res.code == 200) {
                        this.dataFrameVos[firstIndex].jsonData[secondIndex].opening = false;
                        this.dataFrameVos[firstIndex].jsonData[secondIndex].isOpen = true;
                        this.dataFrameVos[firstIndex].jsonData[secondIndex].dataframeId = res.data;

                    }
                })

            },

            onExecCmd(key, command, success, failed) {
                this.content = command;
                this.loading = true;
                let url = '/perform';

                if (command.includes('OPEN') && this.resourceSecondIndex == '9999') {
                     this.openItem(this.uriVal, this.resourceFirstIndex, secondIndex)
                }



                let params = {
                    uri: this.uriVal,
                    sql: command,
                    // firstIndex: this.resourceFirstIndex,
                    dataframeId: this.dataframeId,
                };
                // if(this.resourceSecondIndex!='9999')params.secondIndex=this.resourceSecondIndex

                this.$request.post(url, this.$qs.stringify(params)).then(res => {
                    if (res.data.code == 200) {
                        this.loading = false;
                        this.performResult = res.data.data.replace(/\n/g, "<br>");
                        success(this.performResult)

                    } else {
                        this.loading = false;
                        failed('Something wrong!!!')
                    }
                }).catch(err => {
                    this.loading = false;
                    failed('Something wrong!!!')
                });
                this.scriptTemp = ''
                // }


            },

            onExecCmdTotal(key, command, success, failed) {
                this.loadingTotal = true;
                let url = '/performJoin';

                let params = {
                    uri: this.uriVal,
                    sql: command
                };

                this.$request.post(url, this.$qs.stringify(params)).then(res => {
                    if (res.data.code == 200) {
                        this.loadingTotal = false;
                        success(res.data.data.replace(/\n/g, "<br>"))

                    } else {
                        this.loadingTotal = false;
                        failed('Something wrong!!!')
                    }
                }).catch(err => {
                    this.loadingTotal = false;
                    failed('Something wrong!!!')
                });
            },

            onKeydown(event) {
                if (this.enableTextEditor && event.key === 's' && event.ctrlKey) {
                    this._textEditorClose(true)
                    event.preventDefault()
                }
            },
            _textEditorClose(option) {
                TerminalApi.textEditorClose(this.name, option)
            },
            // 代码块初始化
            editorInit() {
                require('brace/ext/language_tools'); // language extension prerequsite...
                require('brace/mode/sql');
                require('brace/mode/python');   //language
                for (let i = 0; i < this.arr.length; i++) {//方便看哪个主题好看，循坏加载了所有主题，通过点击按钮切换
                    require("brace/theme/" + this.arr[i])
                }
            },
            //获取样例数据
            getSampleData(uri, firstIndex, secondIndex, dataframeId) {
                this.tableLoading = true;
                this.dialogTableVisible = true;
                let url = '/getSampleDataAndSchema';


                let params = {
                    uri,
                    dataframeId
                    // firstIndex,
                    // secondIndex
                };
                this.$api.setGetType(url, params).then(res => {
                        if (res.code == 200) {
                            let dataObj = res.data;
                            this.schemaFields = dataObj.schema;
                            this.sampleTitle = dataObj.schema.map(item => item.name);
                            this.sampleData = dataObj.data;
                            this.tableLoading = false;
                        }
                    }
                )

            },

            async getCodeData(uri, firstIndex, dataframeId, secondIndex = 9999) {
                this.performTable = [];
                this.dialogCodeVisible = true;
                this.resourceFirstIndex = firstIndex;
                this.dataframeId = dataframeId;
                this.resourceSecondIndex = secondIndex;
            },
            handleOnClick(item) {
                this.uriVal = item.uri;
                this.handleOnSearch()
            },
            xmlToJson(xmlStr) {
                let parser = new DOMParser();
                let xmlDoc = parser.parseFromString(xmlStr, "text/xml");// 将XML对象转换为JSON对象
                let bookObj = {};
                let book = xmlDoc.getElementsByTagName("rdf:Description")[0];
                 for (let i = 0; i < book.children.length; i++) {
                    let child = book.children[i];
                    const arrTemp = ['title', 'identifier', 'date', 'creator', 'size', 'publisher'];
                    const containsAny = arrTemp.some(item => child.nodeName.includes(item));
                    if (containsAny) {
                        bookObj[child.nodeName] = [];
                        for (let j = 0; j < child.children.length; j++) {
                            let child2 = child.children[j].children;
                            let bookChildObj = {};
                            for (let p = 0; p < child2.length; p++) {
                                let child3 = child2[p];
                                bookChildObj[child3.nodeName] = child3.textContent;
                            }
                            bookObj[child.nodeName].push(bookChildObj)
                        }

                    } else {
                        bookObj[child.nodeName] = child.textContent;
                    }

                }
                return bookObj;
            },
            handleOnSearch() {
                this.searchLoading = true;
                let url = '/getResourceByUri';

                this.$request.post(url, this.$qs.stringify({uri: this.uriVal})).then(res => {
                    if (res.data.code == 200) {
                        this.dataObj = res.data.data;
                        this.resourceMetaData = res.data.data.resourceMetaData;
                        this.dataFrameVos = this.dataObj.dataFrameVos;
                        if (this.dataFrameVos) {
                            setTimeout(()=>{this.analysisTotal = this.dataFrameVos.length > 0;},200)


                            this.dataFrameVos.forEach(item => {
                                const jsonData = item.jsonData;
                                const tableProps = [];

                                jsonData.forEach(data => {
                                    this.$set(data, 'isOpen', false); // 使用$set方法设置isOpen属性
                                    this.$set(data, 'opening', false); // 使用$set方法设置isOpen属性
                                    this.$set(data, 'dataframeId', ''); // 使用$set方法设置isOpen属性
                                    const props = Object.keys(data).filter(prop => prop !== "openable" && prop !== "_id" && prop !== "isOpen" && prop !== "content" && prop !== "parser" && prop !== "opening");
                                    tableProps.push(...props);
                                });


                                item.tableProps = Array.from(new Set(tableProps));
                            });

                        } else {
                            this.sampleData = [];
                            this.fields = []
                        }
                        this.searchLoading = false;

                    }
                })
            }
        }
    }
</script>
<style lang="less" scoped>
    .home-index {
        width: 60%;
        margin: auto;
        .description-item {
            width: 150px;
            text-align: right;
        }


        .search {
            .inner {
                display: flex;
                align-items: center;

                .el-input {
                    margin-right: 10px;
                }

            }
        }

        .code-editing {
            .header {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            ::v-deep {
                .el-select {
                    width: 100px;

                    .el-input__inner {
                        height: 34px;
                    }

                    .el-input__icon {
                        line-height: 34px;
                    }
                }
            }
        }

        .objName {
            display: flex;
            align-items: center;

            i {
                font-size: 20px;
                font-weight: bold;
                margin-left: 20px;

                &:hover {
                    cursor: pointer;
                    color: #409EFF;
                }
            }
        }

        .resources {
            display: flex;

            margin-top: 30px;
            justify-content: space-around;

            // align-items: center;
            .item {
                ul {
                    list-style: none;
                    padding: 0;

                    li {
                        color: #409EFF;
                        cursor: pointer;
                    }
                }
            }

        }

        .perform {
            display: flex;
            justify-content: flex-end;
            margin: 20px 0;
        }

        section {
            > div {
                margin-bottom: 45px;
                background-color: #fff;
                padding: 30px;
                border-radius: 12px;
            }

            .titleDiv {
                display: flex;
                justify-content: space-between;

                .title {
                    font-size: 18px;
                    font-weight: 800;
                    // margin-top: 20px;
                    margin-bottom: 10px;
                    // color: #409EFF;
                    border-bottom: 1px solid #eee;
                    padding-bottom: 10px;
                }
            }


            .rdf-description {


                .content {
                    ul {
                        list-style: none;
                        padding: 0;

                        li {
                            padding: 5px 0;
                            display: flex;

                            label {
                                display: inline-block;
                                width: 300px;
                                color: #40a0ff;
                            }

                            span {
                                flex: 1;
                                position: relative;

                                &::before {
                                    content: "";
                                    width: 6px;
                                    height: 6px;
                                    position: absolute;
                                    top: 8px;
                                    left: -16px;
                                    background-color: #000;
                                }
                            }

                            .test {
                                flex: 1;
                                padding: 30px 0 10px;
                                margin-left: -250px;

                                .secondItem {
                                    display: flex;

                                    .detail {
                                        display: flex;
                                        /*flex: 1;*/
                                        padding-top: 5px;


                                        .subkey {
                                            color: #8f87ff;

                                        }

                                        .subvalue {
                                            min-width: 100px;
                                            text-indent: 1rem;
                                            margin-right: 20px;

                                        }

                                    }

                                }
                            }


                        }
                    }

                }
            }

            .table-structure {
                ::v-deep {
                    .el-table td.el-table__cell div {
                        display: flex;

                        [class*=" el-icon-"], [class^=el-icon-] {
                            display: block;
                            width: 30px;
                            /*width: 3%;*/
                            /*text-align: center;*/
                            font-size: 22px;
                            cursor: pointer;

                            &:hover {
                                color: #409EFF;
                            }
                        }

                        .el-icon-loading {
                            font-size: 12px;
                            cursor: not-allowed;
                        }
                    }
                }
                .parsersButton{
                    display: flex;flex-wrap: wrap;
                    ::v-deep{
                        .el-button{
                            margin-left: 10px;
                        }

                    }
                }
            }
        }

        #app, #app1 {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 500px;
            margin-bottom: 100px;
        }

        .dialogDiv {
            ::v-deep {
                .el-dialog__body {
                    padding: 50px;
                }
            }

        }

        ::v-deep {
            .el-dialog {
                border-radius: 10px;
                padding: 0 20px;

                .table-structure {
                    margin-bottom: 50px;

                    .title {
                        font-size: 18px;
                        font-weight: 800;
                        margin-bottom: 10px;
                        border-bottom: 1px solid #eee;
                        padding-bottom: 10px;
                    }
                }
            }

        }
    }
</style>
