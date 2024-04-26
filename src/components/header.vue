<template>
    <div class="header">
        <div class="top">
            <div class="logo fl">
                <router-link to="/">
                    <img src="@/assets/logo1.png" alt="RDCN">
                </router-link>

                <el-input
                        placeholder=""
                        v-model="inputValue">
                    <i slot="prefix" class="el-input__icon el-icon-search"></i>
                </el-input>
            </div>
            <div class="tel fr">Join <i class="el-icon-plus"></i></div>
        </div>
        <div class="menu">
            <div class="center w">
                <div v-for="(item, index) in menuLists"
                     :key="index"
                     :class="{'active': activeIndex === index}"
                     class="link"
                     @mouseenter="showSubMenu=true"
                     @mouseleave="showSubMenu=false"
                >{{ item.title }}
                    <i class="el-icon-arrow-down" style="margin-left: 5px;color: #fff"></i>
                </div>
            </div>
            <div class="sub-menu"
                 v-if="showSubMenu"
                 @mouseenter="showSubMenu=true"
                 @mouseleave="showSubMenu = false">
                <div class="menu2 w">
                    <div v-for="(item, index) in menuLists"
                         :key="index"
                         class="link1"
                    >
                        <div class="nav" v-for="(citem,cindex) in item.children" :key="cindex" @click="openPage(citem)">
                            {{citem.name}}
                        </div>
                    </div>
                </div>

            </div>
        </div>

    </div>
</template>
<script>
    export default {
        data() {
            return {
                menuLists: [
                    {
                        title: 'What Is RDCN',
                        children: [
                            {name: 'About RDCN', path: ''},
                            {name: 'Vision & Mission', path: ''},
                        ]
                    },
                    {
                        title: 'Who We Are',
                        children: [
                            {name: 'Association', path: ''},
                            {name: 'Ecosystems', path: ''},
                            {name: 'Contacts', path: ''},
                        ]
                    },
                    {
                        title: 'Updates',
                        children: [
                            {name: 'Latest News', path: ''},
                            {name: 'Press Releases', path: ''},
                            {name: 'Evnets', path: ''},
                        ]
                    },
                    {
                        title: 'Documents',
                        children: [
                            {name: 'Vocabulary Specification', path: '',page:''},
                            {name: 'Document Repository', path: ''},
                            {name: 'Standards', path: ''},
                        ]
                    },
                    {
                        title: 'Using RDCN',
                        children: [
                            {name: 'Who can use', path: ''},
                            {name: 'Software & Tools', path: ''},
                            {name: 'Use cases', path: ''},
                            {name: 'Monitoring & Visualization', path: ''},
                        ]
                    },
                    {
                        title: 'Membership',
                        children: [
                            {name: 'Members Directory', path: ''},
                            {name: 'Why join?', path: ''},
                            {name: 'How to join?', path: ''},
                            {name: 'Members Platform', path: ''},
                        ]
                    },
                    {
                        title: 'Dev Specifications',
                        children: [
                            {
                                name: 'Operator Specification',
                                path: '',
                                page: 'https://github.com/rdcn-link/faird/wiki/%E7%AE%97%E5%AD%90%E5%BC%80%E5%8F%91%E8%A7%84%E8%8C%83'
                            },
                            {
                                name: 'Parser Specification',
                                path: '',
                                page: 'https://github.com/rdcn-link/faird/wiki/parser%E5%BC%80%E5%8F%91%E8%A7%84%E8%8C%83'
                            },
                        ]
                    }
                ],
                activeIndex: 0,
                inputValue: '',
                showSubMenu: false,
            }
        },
        methods: {
            openPage(citem) {
                if (citem.page) {
                    window.open(citem.page, '_blank')
                }
            },
        },
    }
</script>
<style lang="scss" scoped>
    .header {
        width: 100%;
        position: relative;
        background-color: #000000;

        .top {
            width: 1780px;
            margin: 0 auto;
            height: 100px;
            color: #fff;
            display: flex;
            justify-content: space-between;
            align-items: center;

            .logo {
                display: flex;

                a {
                    display: inline-block;
                    height: 50px;
                }

                img {
                    width: 57px;
                    height: 54px;
                }
            }

            .tel {
                font-size: 24px;
            }

            ::v-deep {
                .el-icon-search {
                    color: #fff;
                    font-size: 18px;
                    margin-top: 2px;
                }

                .el-input {
                    margin-left: 30px;
                    height: 50px;
                }

                .el-input__inner {
                    height: 100%;
                    width: 500px;
                    background-color: rgba(118, 119, 120, 0.3);

                    border-radius: 25px;
                    border: transparent !important;
                    color: #fff;
                }

                .el-input__inner:focus {

                    border-color: #fff;

                }
            }

        }

        .menu {
            width: 100%;
            position: relative;
            height: 60px;
            background-color: #000;
            box-shadow: 0 4px 4px 0 rgba(19, 59, 40, 0.25);
            text-align: center;
            border-bottom: 1px solid #fff;

            .center {
                height: 60px;
                display: flex;
                justify-content: space-between;
                align-items: center;

                .link {
                    flex: 1;
                    line-height: 60px;
                    text-align: left;
                    height: 100%;
                    color: #fff;
                    font-size: 18px;


                    &:hover {
                        cursor: pointer;
                        color: #c6ff00;
                    }
                }
            }


            .sub-menu {
                position: relative;
                background: rgba(0, 0, 0, 0.7);
                /*background-color: rgba(207, 207, 207, 0.4);*/
                padding: 20px;
                z-index: 999;
                transition: height 1s ease; /* 添加transition属性，设置过渡时间和缓动函数 */
                .menu2 {
                    display: flex;
                    justify-content: space-between;

                    .link1 {
                        flex: 1;
                        text-align: left;

                        .nav {
                            font-size: 16px;
                            line-height: 50px;
                            color: #fff;

                            &:hover {
                                cursor: pointer;
                                color: #c6ff00;
                                text-decoration: underline;

                            }
                        }

                    }

                }
            }


        }

    }
</style>
