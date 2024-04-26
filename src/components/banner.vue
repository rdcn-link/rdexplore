<template>
    <div class="banner">
        <div class="w">
            <el-carousel
                    trigger="click"
                    :indicator-position="indicatorStatus"
                    arrow="never"
                    :interval="3000"
            >
                <el-carousel-item v-for="(item, index) in carouselItems" :key="index">
                    <div class="carousel-item">
                        <div class="carousel-text">
                            <p>Research</p>
                            <p>Data</p>
                            <p style="color:#c6ff00 ">&</p>
                            <p>Collaboration</p>
                            <p>Network</p>
                        </div>
                        <div class="carousel-img" :style="{ 'background-image': 'url(' + item.imgSrc + ')' }"></div>
                    </div>
                </el-carousel-item>
            </el-carousel>
        </div>

    </div>
</template>
<script>
    export default {
        data() {
            return {
                carouselItems: [
                    {title: '标题1', description: '描述1', imgSrc: require('@/assets/banner.png')},
                    {title: '标题2', description: '描述2', imgSrc: require('@/assets/banner.png')},
                    {title: '标题3', description: '描述3', imgSrc: require('@/assets/banner.png')}
                ],
                bannerLists: ['./static/images/banner.png', './static/images/banner.png', './static/images/banner.png', './static/images/banner.png'],
                indicatorStatus: 'inside'
            }
        },
        created() {
            this.indicatorStatus = this.bannerLists.length > 1 ? 'inside' : 'none'
        }
    }
</script>
<style lang="scss" scoped>

    ::v-deep {
        .el-carousel__container {
            height: 840px;
        }

        .el-carousel__indicators { // 指示器，关键字 unset，表示重置
            left: unset;
            transform: unset;
            right: 8%;
            bottom: 30px;
        }

        .el-carousel__button { // 指示器按钮
            width: 14px;
            height: 14px;
            border-radius: 50%;
            background-color: transparent;
            border: 3px solid #fff;
        }

        .is-active .el-carousel__button { // 指示器激活按钮
            background: #fff;
        }
    }

    .banner .el-carousel__arrow {
        font-size: 20px;
        background-color: rgba(31, 45, 61, 0.3);
    }

    .banner {
        background-color: #000;
    }

    .carousel-item {
        display: flex;
        justify-content: space-between;
        align-items: center;
        width: 100%;
        height: 840px;

        .carousel-img {
            width: 928px;
            height: 482px;
            background-size: 100% 100%;
            background-repeat: no-repeat;
            background-position: center;
        }

        .carousel-text {
            width: 524px;
            font-family: PingFangSC-Medium;
            font-size: 100px;
            font-weight: normal;
            font-stretch: normal;
            line-height: 100px;
            color: #ffffff;
        }
    }
</style>
