<template>
    <div>
        <BannerDiv/>
        <div class="home">
            <div class="why">
                <div class="left">
                    <div class="left-inner">
                        <div class="text title">Who we are?</div>
                    </div>

                </div>
                <div class="right">
                    <div class="right-inner">
                        <div class="title1">Why we do it？</div>
                        <div class="content">RDCN is a system where multiple scientific data resources are
                            interconnected through communication paths, with the main purpose of sharing data resources
                            and services, making data more easily discovered, accessed, shared, circulated, and reused.
                        </div>
                        <div class="content">By providing high-speed data transmission, unified data standards and
                            formats, secure data storage and access mechanisms, an infrastructure is formed to connect
                            different scientific data centers and scientists for data sharing and exchange.
                        </div>
                        <el-button type="success" class="pbutton">About></el-button>

                    </div>
                </div>
            </div>
            <div class="parsers">
                <div class="w">
                    <div class="header">
                        <span class="fl">Parser List</span>
                        <router-link to="professors" class="fr">More ></router-link>
                    </div>
                </div>
                <div class="w">
                    <el-carousel indicator-position="outside" :interval="3000" trigger="click" arrow="never">
                        <el-carousel-item v-for="(item, index) in parsersList" :key="index">
                            <div class="parserList">
                                <div class="leftParser">
                                    <div class="parser" v-for="(subitem,subindex) in item.filter((_, i) => i % 2 === 0)"
                                         :key="subindex">
                                        <div class="pheader">
                                            <div class="lheder">created:{{subitem.dateCreated}}&nbsp;&nbsp;
                                                creator:{{subitem.creator}}
                                            </div>
                                            <div class="rheader">&nbsp;&nbsp;version:{{subitem.version}}&nbsp;&nbsp;size:{{subitem.size}}B
                                            </div>
                                        </div>
                                        <div class="ptitle">{{subitem.name}}</div>
                                        <div class="pcontent">{{subitem.description}}</div>
                                    </div>

                                </div>
                                <div class="leftParser">
                                    <div class="parser" v-for="(subitem,subindex) in item.filter((_, i) => i % 2 !== 0)"
                                         :key="subindex">
                                        <div class="pheader">
                                            <div class="lheder">created:{{subitem.dateCreated}}&nbsp;&nbsp;
                                                creator:{{subitem.creator}}
                                            </div>
                                            <div class="rheader">&nbsp;&nbsp;version:{{subitem.version}}&nbsp;&nbsp;size:{{subitem.size}}B
                                            </div>
                                        </div>
                                        <div class="ptitle">{{subitem.name}}</div>
                                        <div class="pcontent">{{subitem.description}}
                                        </div>
                                    </div>

                                </div>
                            </div>

                        </el-carousel-item>
                    </el-carousel>
                </div>
            </div>
            <div class="graph">
                <div class="w">
                    <div class="header">
                        <span class="fl">Intranet & Public Hub</span>
                        <router-link to="professors" class="fr">More ></router-link>
                    </div>
                    <div class="bg">
                        <div class="bg1"></div>
                        <div class="bg2"></div>
                    </div>
                </div>
            </div>
            <!--            <div class="sdkList">-->
            <!--                <div class="w">-->
            <!--                    <div class="header">-->
            <!--                        <span class="fl">SDK</span>-->
            <!--                        <router-link to="professors" class="fr">More ></router-link>-->
            <!--                    </div>-->
            <!--                </div>-->
            <!--                <div class="w container">-->
            <!--                    <div class="sidebar">-->
            <!--                        <ul>-->
            <!--                            <li v-for="(item, index) in items" :key="index" @click="selectItem(index)"-->
            <!--                                :class="{ 'active': selectedItem === index }">{{ item.title }}-->
            <!--                            </li>-->
            <!--                        </ul>-->
            <!--                    </div>-->
            <!--                    <div class="content">-->
            <!--                        <div v-for="(item, index) in items" :key="index" v-show="selectedItem === index">-->
            <!--                            <h2>{{ item.title }}</h2>-->
            <!--                            <p>{{ item.content }}</p>-->
            <!--                        </div>-->
            <!--                    </div>-->
            <!--                </div>-->
            <!--            </div>-->
            <div class="newsList">
                <div class="w">
                    <div class="header">
                        <span class="fl">News & Press</span>
                        <router-link to="professors" class="fr">More ></router-link>
                    </div>
                </div>
                <div class="w newsContainer">
                    <div class="newsDetail">
                        <el-carousel indicator-position="outside" :interval="5000" trigger="click" arrow="never">
                            <el-carousel-item v-for="(item, index) in listsArr1" :key="index">
                                <div class="lists">
                                    <div class="list-header">
                                        <div>News{{item.id}} ></div>
                                        <div>{{item.time}}</div>
                                    </div>
                                    <div class="list-poster">
                                        <a href="javascript:;" @click="toNewsDetail(item.id)">
                                            <img :src="item.poster" alt>
                                        </a>
                                    </div>
                                    <div class="list-msg">
                                        <div class="list-title ellipsis">{{ item.brief }}</div>
                                    </div>
                                </div>

                            </el-carousel-item>
                        </el-carousel>
                    </div>
                    <div class="newsDetail">
                        <el-carousel indicator-position="outside" :interval="5000" trigger="click" arrow="never">
                            <el-carousel-item v-for="(item, index) in listsArr1" :key="index">
                                <div class="lists">
                                    <div class="list-header">
                                        <div>News{{item.id}} ></div>
                                        <div>{{item.time}}</div>
                                    </div>
                                    <div class="list-poster">
                                        <a href="javascript:;" @click="toNewsDetail(item.id)">
                                            <img :src="item.poster" alt>
                                        </a>
                                    </div>
                                    <div class="list-msg">
                                        <div class="list-title ellipsis">{{ item.brief }}</div>
                                    </div>
                                </div>

                            </el-carousel-item>
                        </el-carousel>
                    </div>
                    <div class="newsDetail">
                        <el-carousel indicator-position="outside" :interval="5000" trigger="click" arrow="never">
                            <el-carousel-item v-for="(item, index) in listsArr1" :key="index">
                                <div class="lists">
                                    <div class="list-header">
                                        <div>News{{item.id}} ></div>
                                        <div>{{item.time}}</div>
                                    </div>
                                    <div class="list-poster">
                                        <a href="javascript:;" @click="toNewsDetail(item.id)">
                                            <img :src="item.poster" alt>
                                        </a>
                                    </div>
                                    <div class="list-msg">
                                        <div class="list-title ellipsis">{{ item.brief }}</div>
                                    </div>
                                </div>

                            </el-carousel-item>
                        </el-carousel>
                    </div>
                </div>
            </div>
            <div class="cases">
                <div class="w">
                    <div class="header">
                        <span class="fl">Use Cases</span>
                        <router-link to="professors" class="fr">More ></router-link>
                    </div>
                </div>
                <div class="w">
                    <el-carousel indicator-position="outside" :interval="5000" trigger="click" arrow="never">
                        <el-carousel-item v-for="(item, index) in casesList" :key="index">
                            <div class="caseDetail">
                                <div class="leftParser caseContent">
                                    <div class="title1">{{item.title}}</div>
                                    <div class="content">{{item.content}}
                                    </div>

                                    <el-button class="pbutton">Read></el-button>

                                </div>
                                <div class="leftParser">
                                    <img :src="item.img" alt="">
                                </div>
                            </div>

                        </el-carousel-item>
                    </el-carousel>
                </div>
            </div>
            <div class="join">
                <div class="w">
                    <div class="title">How to join rdcn?</div>
                    <div class="list">
                        <div class="listDetail">
                            <div class="index">1</div>
                            <div class="desc">Registration</div>
                        </div>
                        <div class="listDetail">
                            <div class="index">2</div>
                            <div class="desc">Fill out Documents</div>
                        </div>
                        <div class="listDetail">
                            <div class="index">3</div>
                            <div class="desc">Documents send</div>
                        </div>
                        <div class="listDetail">
                            <div class="index">4</div>
                            <div class="desc">Examine</div>
                        </div>
                    </div>
                    <el-button type="success" class="pbutton">Join Now></el-button>
                </div>

            </div>
        </div>
    </div>
</template>
<script>
    import BannerDiv from "../../components/banner";

    export default {
        components: {BannerDiv},
        data() {
            return {
                selectedItem: 0,

                items: [
                    {title: 'Casdc SDK 000', content: '内容1'},
                    {title: 'Casdc SDK 000', content: '内容2'},
                    {title: 'Casdc SDK 000', content: '内容3'},
                    {title: 'Casdc SDK 000', content: '内容4'},
                    {title: 'Casdc SDK 000', content: '内容5'},
                    {title: 'Casdc SDK 000', content: '内容6'}
                ],
                listsArr1: [{
                    'id': 0,
                    'time': '1987-01-03',
                    'title': '整至命却',
                    'poster': require('../../assets/news1.png'),
                    'brief': 'Trusted Federated InfrastructureData, infrastructure, and govern...'
                }, {
                    'id': 1,
                    'time': '1987-01-03',
                    'title': '整至命却',
                    'poster': require('../../assets/news1.png'),
                    'brief': 'Trusted Federated InfrastructureData, infrastructure, and govern...'
                }, {
                    'id': 2,
                    'time': '1987-01-03',
                    'title': '整至命却',
                    'poster': require('../../assets/news1.png'),
                    'brief': 'Trusted Federated InfrastructureData, infrastructure, and govern...'
                },],
                casesList: [{
                    title: 'Scientific Data Cross-Center Collaborative Task.',
                    content: 'Identify the location of the silt dam in Jiuyuangou County, Yulin City, Shaanxi Province, and assess its ecological value.',
                    img: require('../../assets/case1.png')
                }, {
                    title: 'National Astronomical Science Data Center Collaborative Task.',
                    content: 'How to conduct cross-verification of star catalog data between the National Astronomical Observatory and the Yunnan Astronomical Observatory?',
                    img: require('../../assets/case2.png')
                }],
                parsersList:[]
                // parsersList: [
                //     [
                //         {
                //             "URI": "6597672c764094097ff6d0bf",
                //             "creator": "cnic",
                //             "dateCreated": "2024/01/05",
                //             "description": "Parse csv file",
                //             "email": "yxzhang@cnic.cn",
                //             "fileFormat": "text/csv",
                //             "group": "common",
                //             "name": "CsvParser",
                //             "origin_name": "csvParser.jar",
                //             "properties": [
                //                 {
                //                     "allowableValues": [
                //                         "true",
                //                         "false"
                //                     ],
                //                     "defaultValue": "false",
                //                     "description": "Whether the csv file has a header",
                //                     "example": "true",
                //                     "language": "TEXT",
                //                     "name": "header",
                //                     "required": true,
                //                     "sensitive": true
                //                 },
                //                 {
                //                     "defaultValue": ",",
                //                     "description": "The delimiter of csv file",
                //                     "example": ",",
                //                     "language": "TEXT",
                //                     "name": "delimiter",
                //                     "required": true,
                //                     "sensitive": true
                //                 },
                //                 {
                //                     "defaultValue": "",
                //                     "description": "The schema of csv file",
                //                     "example": "id,name,gender,age",
                //                     "language": "TEXT",
                //                     "name": "schema",
                //                     "required": false,
                //                     "sensitive": true
                //                 }
                //             ],
                //             "publisher": "中国科学院计算机网络信息中心",
                //             "size": 3423,
                //             "subject": {
                //                 "@id": "http://semweb.casdc.cn/resource/GB13745-2009#1101780",
                //                 "@value": "计算数论"
                //             },
                //             "version": "1.0"
                //         },
                //         {
                //             "URI": "65976dfe764094097ff6d0c0",
                //             "creator": "cnic",
                //             "dateCreated": "2024/01/05",
                //             "description": "XlsxParser is used for extracting and interpreting the data stored in the Microsoft Excel spreadsheet format. ",
                //             "email": "faird@cnic.cn",
                //             "fileFormat": "application/vnd.ms-excel",
                //             "group": "common",
                //             "name": "XlsxParser",
                //             "origin_name": "XlsxParser.jar",
                //             "properties": [
                //                 {
                //                     "allowableValues": [
                //                         "true",
                //                         "false"
                //                     ],
                //                     "defaultValue": "true",
                //                     "description": "Whether the excel file has a header",
                //                     "example": "true",
                //                     "language": "TEXT",
                //                     "name": "header",
                //                     "required": true,
                //                     "sensitive": true
                //                 },
                //                 {
                //                     "defaultValue": "'Sheet1'!",
                //                     "description": "'Sheet1'!",
                //                     "example": "'Sheet1'!",
                //                     "language": "TEXT",
                //                     "name": "dataAddress",
                //                     "required": true,
                //                     "sensitive": true
                //                 },
                //                 {
                //                     "defaultValue": "",
                //                     "description": "The num of show. Empty represents retrieving all the rows.",
                //                     "example": "10",
                //                     "language": "TEXT",
                //                     "name": "showNum",
                //                     "required": false,
                //                     "sensitive": true
                //                 },
                //                 {
                //                     "allowableValues": [
                //                         "true",
                //                         "false"
                //                     ],
                //                     "defaultValue": "false",
                //                     "example": "false",
                //                     "language": "TEXT",
                //                     "name": "truncate",
                //                     "required": false,
                //                     "sensitive": true
                //                 }
                //             ],
                //             "publisher": "中国科学院计算机网络信息中心",
                //             "size": 3352,
                //             "subject": {
                //                 "@id": "http://semweb.casdc.cn/resource/GB13745-2009#5201050",
                //                 "@value": "数据结构"
                //             },
                //             "version": "1.0"
                //         },
                //         {
                //             "URI": "65977015764094097ff6d0c1",
                //             "creator": "cnic",
                //             "dateCreated": "2024/01/05",
                //             "description": "A parser for parsing JSON files is responsible for extracting and interpreting data stored in the JSON (JavaScript Object Notation) format.",
                //             "email": "faird@cnic.cn",
                //             "fileFormat": "application/json",
                //             "group": "common",
                //             "name": "JsonParser",
                //             "origin_name": "JsonParser.jar",
                //             "publisher": "中国科学院计算机网络信息中心",
                //             "size": 2228,
                //             "subject": {
                //                 "@id": "http://semweb.casdc.cn/resource/GB13745-2009#5201050",
                //                 "@value": "数据结构"
                //             },
                //             "version": "1.0"
                //         },
                //         {
                //             "URI": "65977216764094097ff6d0c2",
                //             "creator": "cnic",
                //             "dateCreated": "2024/01/05",
                //             "description": "PngParser is used for extracting and interpreting the data stored in the image/png format.",
                //             "email": "faird@cnic.cn",
                //             "fileFormat": "image/png",
                //             "group": "common",
                //             "name": "PngParser",
                //             "origin_name": "PngParser.jar",
                //             "publisher": "中国科学院计算机网络信息中心",
                //             "size": 2391,
                //             "subject": {
                //                 "@id": "http://semweb.casdc.cn/resource/GB13745-2009#5201050",
                //                 "@value": "数据结构"
                //             },
                //             "version": "1.0"
                //         },
                //         {
                //             "URI": "65977486764094097ff6d0c3",
                //             "creator": "cnic",
                //             "dateCreated": "2024/01/05",
                //             "description": "XmlParser is used for extracting and interpreting the data stored in the xml format. ",
                //             "email": "faird@cnic.cn",
                //             "fileFormat": "text/xml",
                //             "group": "common",
                //             "name": "XmlParser",
                //             "origin_name": "XmlParser.jar",
                //             "properties": [
                //                 {
                //                     "defaultValue": "",
                //                     "description": "the path of xml file",
                //                     "example": "hdfs://192.168.3.138:8020/work/test/testxml.xml",
                //                     "language": "TEXT",
                //                     "name": "xmlPath",
                //                     "required": true,
                //                     "sensitive": true
                //                 },
                //                 {
                //                     "defaultValue": "",
                //                     "description": "the tag you want to parse in xml file",
                //                     "example": "name",
                //                     "language": "TEXT",
                //                     "name": "rowTag",
                //                     "required": true,
                //                     "sensitive": true
                //                 }
                //             ],
                //             "publisher": "中国科学院计算机网络信息中心",
                //             "size": 2855,
                //             "subject": {
                //                 "@id": "http://semweb.casdc.cn/resource/GB13745-2009#5201050",
                //                 "@value": "数据结构"
                //             },
                //             "version": "1.0"
                //         }
                //     ]
                // ],
            }
        },
        created() {
        },
        mounted() {
            this.getParsersList()

        },
        methods: {
            getParsersList() {
                this.$api.setGetType('/list_parser').then((res) => {
                    if (res.code === 200) {
                        const resData = res.data;
                        const groupSize = 8;
                        this.parsersList = [];
                        for (let i = 0, len = resData.length; i < len; i += groupSize) {
                            this.parsersList.push(resData.slice(i, i + groupSize));
                        }
                    }
                })
            },
            selectItem(index) {
                this.selectedItem = index
            },
            loadmore() {
                console.log('加载更多')
            },
            toNewsDetail(index) {
                console.log(index)
            },
        }
    };
</script>
<style lang="scss" scoped>
    .home {
        ::v-deep {
            .el-carousel__container {
                height: 560px;
            }
        }
    }

    .home .el-carousel__button {
        opacity: 1;
        width: 12px;
        height: 12px;
        border-radius: 100%;
        background: #afafaf;
    }

    .home .el-carousel__indicator.is-active button {
        background-image: linear-gradient(-180deg, #2b6d51 0%, #133b28 100%);
    }

    .home .el-carousel__arrow {
        font-size: 44px;
        width: 70px;
        height: 70px;
        color: #133b28;
        background: #ffffff;
        box-shadow: 0 0 8px 0 rgba(255, 127, 72, 0.25);
    }

    .home {
        overflow: hidden;

        .reason {
            padding-top: 70px;
            padding-bottom: 80px;
            text-align: center;

            .top {
                position: relative;
                display: inline-block;
                font-size: 36px;
                color: #133b28;
                font-weight: bold;

                .line {
                    position: absolute;
                    opacity: 0.2;
                    background: #1e4c35;
                    width: 582px;
                    height: 1px;
                    top: 26px;

                    &.line-l {
                        right: 460px;
                    }

                    &.line-r {
                        left: 460px;
                    }
                }
            }

            .intro {
                font-size: 24px;
                color: #252525;
                line-height: 38px;
                padding-top: 36px;
            }

            .icons {
                width: 1200px;
                margin: 0 auto;
                padding: 90px 60px 0;

                .part {
                    width: 160px;
                    margin: 0 100px;

                    .part-top {
                        width: 100%;
                        height: 160px;
                        line-height: 160px;
                        opacity: 0.3;
                        background: #133b28;
                        box-shadow: 0 0 8px 0 #133b28;
                        border-radius: 100%;
                        text-align: center;
                        margin-bottom: 16px;

                        &.second {
                            opacity: 1;
                        }

                        &.third {
                            opacity: 0.5;
                        }
                    }

                    .part-title {
                        font-size: 18px;
                        color: #133b28;
                    }
                }
            }
        }

        .header {
            height: 80px;
            line-height: 80px;
            font-weight: bold;
            margin-bottom: 40px;

            .fl {
            }

            .fr {
                font-size: 24px;
            }
        }

        .review,
        .news {
            background: #ffffff;
            box-shadow: 0 0 8px 0 rgba(19, 59, 40, 0.25);
            padding-top: 20px;

            .lists {
                padding: 0 15px;

                .list {
                    width: 370px;
                    margin: 0 10px 40px;

                    .list-poster {
                        width: 100%;
                        height: 200px;
                        border-radius: 10px;
                        overflow: hidden;

                        a {
                            display: block;
                            width: 100%;
                            height: 100%;
                            position: relative;

                            img {
                                width: 100%;
                                height: 100%;
                            }

                            .list-brief {
                                width: 100%;
                                position: absolute;
                                bottom: -100px;
                                left: 0;
                                padding: 10px;
                                background-image: linear-gradient(
                                                -180deg,
                                                rgba(19, 59, 40, 0.5) 0%,
                                                #133b28 100%
                                );
                                color: #ffffff;
                                line-height: 18px;
                                transition: all 0.3s;
                            }

                            &:hover {
                                .list-brief {
                                    bottom: 0;
                                }
                            }
                        }
                    }

                    .list-msg {
                        padding: 10px 10px 0;

                        .list-title {
                            font-size: 18px;
                            color: #133b28;
                            font-weight: bold;
                        }

                        .list-info {
                            margin-top: 6px;

                            .fl {
                                opacity: 0.3;
                            }

                            .fr {
                                width: 58px;
                                height: 18px;
                                line-height: 18px;
                                text-align: center;
                                border: 1px solid #133b28;
                                font-size: 12px;
                            }
                        }
                    }
                }
            }
        }

        .teachers {
            padding-top: 20px;
            padding-bottom: 40px;

            .teachers-lists {
                width: 1348px;
                margin: 0 auto;

                .teachers-list {
                    padding: 0 90px;

                    .item {
                        display: block;
                        width: 270px;
                        height: 493px;
                        margin: 0 11px;
                        background: #ffffff;
                        box-shadow: 0 0 8px 0 rgba(30, 76, 53, 0.25);
                        border-radius: 10px;
                        overflow: hidden;

                        .avator {
                            width: 100%;
                            height: 236px;

                            img {
                                width: 100%;
                                height: 100%;
                            }
                        }

                        .intro {
                            padding: 30px 20px 0;

                            .name {
                                color: #133b28;
                                font-size: 18px;
                                font-weight: bold;
                                margin-bottom: 20px;
                            }

                            .brief {
                                color: #666666;
                                line-height: 40px;
                            }
                        }
                    }
                }
            }
        }

        .news {
            padding-bottom: 30px;

            .loadmore {
                width: 370px;
                height: 48px;
                line-height: 48px;
                margin: 0 auto;
                border: 2px solid #133b28;
                border-radius: 10px;
                text-align: center;
                font-size: 18px;
                color: #133b28;
                cursor: pointer;
            }
        }
    }


    .why {
        width: 100%;
        height: 900px;
        background-image: url('../../assets/why.png');
        background-size: 100% 100%;
        background-repeat: no-repeat;
        background-position: center;
        margin-top: -10px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        /*padding-left: 160px;*/

        .left, .right {
            flex: 1;
            display: flex;
            height: 100%;
        }

        .left {
            justify-content: flex-end;
        }

        .right {
            justify-content: flex-start;
        }

        .left-inner {
            width: 800px;

            .text {
                font-family: PingFangSC-Regular;
                font-size: 100px;
                font-weight: normal;
                font-stretch: normal;
                line-height: 900px;
                text-align: center;
                color: #ffffff;
            }
        }

        .right {
            background-color: rgba(84, 71, 255, 0.8);
            background-blend-mode: normal;
            text-align: right;

            .right-inner {
                width: 800px;
                padding: 80px 0 0 120px;

                .title1 {
                    font-family: PingFangSC-Medium;
                    font-size: 90px;
                    color: #ffffff;
                }

                .content {
                    margin-top: 60px;
                    font-family: PingFangSC-Regular;
                    font-size: 24px;
                    line-height: 40px;
                    color: #ffffff;
                }

                .pbutton {
                    margin-top: 60px;
                    font-family: PingFangSC-Medium;
                    font-size: 24px;
                    padding: 10px 30px;
                    color: #000;

                }
            }


        }

        .right {
            /*background-color: #eaffea;*/
        }


    }

    .parsers {
        padding: 100px 0 50px;
        background-color: #fff;

        .header {
            font-family: PingFangSC-Medium;
            height: 80px;
            line-height: 80px;
            padding: 0 20px;
            font-size: 70px;
            font-weight: bold;
            margin-bottom: 40px;


            .fl {
                font-size: 36px;
                color: #555555;
            }

            .fr {
                font-size: 24px;
                color: #133b28;
            }
        }

        .parserList {
            display: flex;

            .leftParser {
                flex: 1;
                padding: 0 20px;

                &:nth-child(1) {
                    margin-right: 10px;
                }
            }

            .leftParser {
                .parser {
                    margin-bottom: 50px;

                    .pheader {
                        display: flex;
                        justify-content: space-between;
                        font-family: PingFangSC-Regular;
                        font-size: 20px;
                        color: #555;
                    }

                    .ptitle {
                        width: 100%;
                        font-family: PingFangSC-Regular;
                        font-size: 30px;
                        line-height: 1.5;
                        color: #000000;
                        display: -webkit-box; /* 使用 Webkit 内核浏览器的内联盒子布局 */
                        -webkit-line-clamp: 1; /* 最多显示两行 */
                        -webkit-box-orient: vertical; /* 垂直方向排列 */
                        overflow: hidden; /* 隐藏溢出部分 */
                        text-overflow: ellipsis; /* 使用省略号表示溢出部分 */

                    }

                    .pcontent {
                        width: 100%;
                        font-family: PingFangSC-Regular;
                        font-size: 16px;
                        color: #888;
                        display: -webkit-box; /* 使用 Webkit 内核浏览器的内联盒子布局 */
                        -webkit-line-clamp: 1; /* 最多显示两行 */
                        -webkit-box-orient: vertical; /* 垂直方向排列 */
                        overflow: hidden; /* 隐藏溢出部分 */
                        text-overflow: ellipsis; /* 使用省略号表示溢出部分 */
                    }
                }
            }

        }


    }

    .graph {
        padding-top: 100px;
        width: 100%;
        height: 980px;
        background-image: url('../../assets/graph.png');
        background-size: 100% 100%;
        background-repeat: no-repeat;
        background-position: center;

        .header {
            font-size: 60px;
            color: #fff;

            a {
                color: #fff;
            }
        }

        .bg {
            display: flex;

            .bg1 {
                width: 600px;
                height: 580px;
                background-image: url('../../assets/gbg1.png');
                background-size: 100% 100%;
                background-repeat: no-repeat;
                background-position: center;
                margin-left: 400px;
                margin-top: 80px;
            }

            .bg2 {
                width: 702px;
                height: 325px;
                background-image: url('../../assets/gbg2.gif');
                background-size: 100% 100%;
                background-repeat: no-repeat;
                background-position: center;
                margin-left: -140px;
                margin-top: 220px;
            }
        }


    }

    .sdkList {
        padding: 100px 0 50px;
        background-color: #fff;

        .header {
            font-family: PingFangSC-Medium;
            height: 80px;
            line-height: 80px;
            font-size: 70px;
            font-weight: bold;
            margin-bottom: 40px;

            .fl {
                font-size: 36px;
                color: #555555;
            }

            .fr {
                font-size: 24px;
                color: #133b28;
            }
        }

        .container {
            display: flex;
            height: 600px;
            border: solid 3px #000000;

            .sidebar {
                width: 400px;
                background-color: #c6ff00;

                ul {
                    list-style: none;
                    padding: 0;
                }

                li {
                    font-family: PingFangSC-Regular;
                    font-size: 30px;
                    cursor: pointer;
                    height: 100px;
                    line-height: 100px;
                    text-align: center;
                }

                .active {
                    background-color: #fff;
                }


            }

            .content {
                flex: 1;
                padding: 20px;
            }

        }
    }

    .newsList {
        padding: 100px 0 50px;
        background-color: #f6f6f6;

        .header {
            font-family: PingFangSC-Medium;
            height: 80px;
            line-height: 80px;
            font-size: 70px;
            font-weight: bold;
            margin-bottom: 40px;

            .fl {
                font-size: 36px;
                color: #555555;
            }

            .fr {
                font-size: 24px;
                color: #133b28;
            }
        }

        .newsContainer {
            display: flex;
            justify-content: space-between;

            .newsDetail {
                flex: 1;
                margin-right: 80px;

                &:last-child {
                    margin-right: 0;
                }

                .lists {
                    .list-header {
                        font-family: PingFangSC-Regular;
                        font-size: 20px;
                        display: flex;
                        justify-content: space-between;
                        color: #555;
                        line-height: 2;
                    }


                    .list-poster {
                        margin: 20px 0;
                        width: 100%;
                        height: 300px;

                        a {
                            display: block;
                            width: 100%;
                            height: 100%;

                            img {
                                width: 480px;
                                height: 300px;
                            }
                        }
                    }

                    .list-msg {
                        padding: 10px 10px 0;

                        .list-title {
                            font-family: PingFangSC-Regular;
                            font-size: 30px;
                        }

                        .list-info {
                            margin-top: 6px;

                            .fl {
                                opacity: 0.3;
                            }

                            .fr {
                                width: 58px;
                                height: 18px;
                                line-height: 18px;
                                text-align: center;
                                border: 1px solid #133b28;
                                font-size: 12px;
                            }
                        }
                    }
                }
            }

        }
    }

    .cases {
        padding: 100px 0 50px;
        background-color: #fff;

        .header {
            font-family: PingFangSC-Medium;
            height: 80px;
            line-height: 80px;
            padding: 0 20px;
            font-size: 70px;
            font-weight: bold;
            margin-bottom: 40px;


            .fl {
                font-size: 36px;
                color: #555555;
            }

            .fr {
                font-size: 24px;
                color: #133b28;
            }
        }

        .caseDetail {
            display: flex;

            .leftParser {
                flex: 1;
                padding: 0 20px;

                &:nth-child(1) {
                    margin-right: 100px;
                }
            }

            .caseContent {
                text-align: left;

                .title1 {
                    font-family: PingFangSC-Regular;
                    font-size: 30px;
                    color: #000;

                }

                .content {
                    font-family: PingFangSC-Regular;
                    font-size: 20px;
                    line-height: 1.5;
                    color: #555;
                    margin: 80px 0 120px;

                }

                .pbutton {
                    font-family: PingFangSC-Medium;
                    font-size: 24px;
                    padding: 10px 30px;
                    color: #000;

                }


            }

            .leftParser {
                .parser {
                    margin-bottom: 50px;

                    .pheader {
                        display: flex;
                        justify-content: space-between;
                        font-family: PingFangSC-Regular;
                        font-size: 20px;
                        color: #555;
                    }

                    .ptitle {
                        font-family: PingFangSC-Regular;
                        font-size: 30px;
                        line-height: 1.5;
                        color: #000000;
                        white-space: nowrap; /* 禁止换行 */
                        overflow: hidden; /* 隐藏溢出部分 */
                        text-overflow: ellipsis; /* 使用省略号表示省略部分 */

                    }

                    .pcontent {
                        font-family: PingFangSC-Regular;
                        font-size: 16px;
                        color: #888;
                        white-space: nowrap; /* 禁止换行 */
                        overflow: hidden; /* 隐藏溢出部分 */
                        text-overflow: ellipsis; /* 使用省略号表示省略部分 */
                    }
                }

                img {
                    width: 850px;
                    height: 500px;
                }
            }

        }


    }

    .join {
        width: 100%;
        height: 660px;
        background-image: url('../../assets/join.png');
        background-size: 100% 100%;
        background-repeat: no-repeat;
        background-position: center;

        .list {
            .listDetail {
                display: flex;
                align-items: center;
                margin-top: 30px;

                .index {
                    background-color: #fff;
                    width: 30px;
                    height: 30px;
                    border-radius: 15px;
                    color: #000;
                    line-height: 30px;
                    text-align: center;
                    margin-right: 20px;
                }

                .desc {
                    font-family: PingFangSC-Regular;
                    font-size: 30px;
                    color: #fff;
                }
            }
        }

        .title {
            padding-top: 100px;
            font-family: PingFangSC-Medium;
            font-size: 70px;
            color: #fff;
        }

        .pbutton {
            margin-top: 80px;
            font-family: PingFangSC-Medium;
            font-size: 24px;
            padding: 10px 30px;
            color: #000;
        }
    }
</style>
