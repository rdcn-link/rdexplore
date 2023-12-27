<template>
    <div class="code-editing">
        <div class="header">
            <h2>代码分析</h2>
            <el-select v-model="modeValue">
                <el-option
                        v-for="item in modeOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                </el-option>
            </el-select>

        </div>
        <codemirror  id="editor" v-model="code" :options="options"></codemirror>
    </div>

</template>

<script>
    import { codemirror } from 'vue-codemirror'
    // 核心样式
    import 'codemirror/lib/codemirror.css'
    // 引入主题后还需要在 options 中指定主题才会生效
    import 'codemirror/mode/javascript/javascript.js'
    import 'codemirror/mode/css/css.js'
    import 'codemirror/mode/python/python.js'
    import 'codemirror/mode/sql/sql.js'
    import 'codemirror/mode/vue/vue.js'
    import 'codemirror/lib/codemirror.css'
    // require active-line.js
    import 'codemirror/addon/selection/active-line.js'
    // styleSelectedText
    import 'codemirror/addon/selection/mark-selection.js'
    // hint
    import 'codemirror/addon/hint/show-hint.js'
    import 'codemirror/addon/hint/sql-hint.js'
    import 'codemirror/addon/hint/show-hint.css'
    import 'codemirror/addon/hint/javascript-hint.js'
    // highlightSelectionMatches
    import 'codemirror/addon/scroll/annotatescrollbar.js'
    import 'codemirror/addon/search/matchesonscrollbar.js'
    import 'codemirror/addon/search/match-highlighter.js'
    // keyMap
    import 'codemirror/mode/clike/clike.js'
    import 'codemirror/mode/sql/sql.js'
    import 'codemirror/addon/edit/matchbrackets.js'
    import 'codemirror/addon/comment/comment.js'
    import 'codemirror/addon/dialog/dialog.js'
    import 'codemirror/addon/dialog/dialog.css'
    import 'codemirror/addon/search/searchcursor.js'
    import 'codemirror/addon/search/search.js'
    import 'codemirror/keymap/sublime.js'
    // foldGutter
    import 'codemirror/addon/fold/foldgutter.css'
    import 'codemirror/addon/fold/brace-fold.js'
    import 'codemirror/addon/fold/comment-fold.js'
    import 'codemirror/addon/fold/foldcode.js'
    import 'codemirror/addon/fold/foldgutter.js'
    import 'codemirror/addon/fold/indent-fold.js'
    import 'codemirror/addon/fold/markdown-fold.js'
    import 'codemirror/addon/fold/xml-fold.js'
    // 编辑的主题文件
    import 'codemirror/theme/monokai.css'
    import 'codemirror/theme/base16-light.css'

    export default {
        name: "codeEditor",
        components: {
            codemirror
        },
        data() {
            return {
                modeValue: 'python',
                modeOptions: [
                    {
                        value: 'sql',
                        label: 'sql'
                    }, {
                        value: 'python',
                        label: 'python'
                    },
                ],

                code: '', // 编辑器绑定的值
                codejava: "",
                // 默认配置
                options: {
                    tabSize: 2, // tab
                    styleActiveLine: true, // 高亮选中行
                    lineNumbers: true, // 显示行号
                    styleSelectedText: true,
                    line: true,
                    foldGutter: true, // 块槽
                    gutters: ['CodeMirror-linenumbers', 'CodeMirror-foldgutter'],
                    highlightSelectionMatches: {showToken: /\w/, annotateScrollbar: true}, // 可以启用该选项来突出显示当前选中的内容的所有实例
                    mode: 'text/x-python',
                    // hint.js options
                    hintOptions: {
                        // 当匹配只有一项的时候是否自动补全
                        completeSingle: false
                    },
                    // 快捷键 可提供三种模式 sublime、emacs、vim
                    keyMap: 'sublime',
                    matchBrackets: true,
                    showCursorWhenSelecting: true,
                    theme: 'darcula', // 主题
                    extraKeys: {'Ctrl': 'autocomplete'} // 可以用于为编辑器指定额外的键绑定，以及keyMap定义的键绑定


                }
            }
        },
        computed: {
            codemirror() {
                return this.$refs.myCm.codemirror
            }
        },
    }
</script>

<style scoped>

</style>
