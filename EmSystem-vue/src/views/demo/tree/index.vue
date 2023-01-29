<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
      <el-form-item label="树节点名" prop="treeName">
        <el-input
          v-model="queryParams.treeName"
          placeholder="请输入树节点名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="daterangeCreateTime"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
        >展开/折叠</el-button>
      </el-col>
      <right-toolbar :show-search.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="treeList"
      row-key="nodeId"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <!-- <el-table-column label="父id" prop="parentId" /> -->
      <!-- <el-table-column prop="status" label="状态" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column> -->
      <el-table-column label="菜单名" prop="nodeName" />
      <el-table-column label="菜单id" align="center" prop="nodeId" />
      <el-table-column label="排序权值" align="center" prop="nodeCode" />
      <el-table-column label="图标" align="center" prop="icon">
        <template slot-scope="scope">
          <span v-if="scope.row.icon != null">
            <svg-icon :icon-class="scope.row.icon" />
          </span>
        </template>
      </el-table-column>
      <el-table-column label="路由地址" align="center" prop="url" />
      <!-- <el-table-column label="创建时间" align="center" prop="createTime" width="180"> -->
      <!-- <template #default="scope">
        <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
      </template> -->
      <!-- </el-table-column> -->
      <!-- 暂时只能构建两级路由   v-if="scope.row.parentId == 0" -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            v-if="scope.row.parentId == 0"
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
          >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改菜单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="680px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="上级菜单" prop="parentId">
              <treeselect
                v-model="form.parentId"
                :disabled="form.parentId == '0'"
                :options="treeOptions"
                :normalizer="normalizer"
                :show-count="true"
                placeholder="选择上级菜单"
              />
            </el-form-item>
          </el-col>
          <el-row>
            <el-col :span="24">
              <el-form-item label="菜单类型" prop="nodeType">
                <el-radio-group v-model="form.nodeType">
                  <el-radio v-if="form.parentId == 0" :label="1">目录</el-radio>
                  <el-radio v-if="form.parentId !== 0" :label="2">菜单</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col v-if="form.parentId==0" :span="12">
              <el-form-item prop="isFrame">
                <span slot="label">
                  <el-tooltip content="选择是外链则路由地址需要以`http(s)://`开头" placement="top">
                    <i class="el-icon-question" />
                  </el-tooltip>
                  是否外链
                </span>
                <el-radio-group v-model="form.isFrame">
                  <el-radio label="0">是</el-radio>
                  <el-radio label="1">否</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          <el-col :span="24">
            <el-form-item label="菜单图标" prop="icon">
              <el-popover
                placement="bottom-start"
                width="460"
                trigger="click"
                @show="$refs['iconSelect'].reset()"
              >
                <IconSelect ref="iconSelect" @selected="selected" />
                <el-input slot="reference" v-model="form.icon" placeholder="点击选择图标" readonly>
                  <svg-icon
                    v-if="form.icon"
                    slot="prefix"
                    :icon-class="form.icon"
                    class="el-input__icon"
                    style="height: 32px;width: 16px;"
                  />
                  <i v-else slot="prefix" class="el-icon-search el-input__icon" />
                </el-input>
              </el-popover>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="菜单名称" prop="nodeName">
              <el-input v-model="form.nodeName" placeholder="请输入菜单名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示排序" prop="nodeCode">
              <el-input-number v-model="form.nodeCode" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
          <el-col v-if="form.nodeType != '1'|| form.isFrame==0" :span="12">
            <el-form-item prop="url">
              <span slot="label">
                <el-tooltip content="访问的路由地址，如：`user`，目录为空，菜单为内部链接或者外链" placement="top">
                  <i class="el-icon-question" />
                </el-tooltip>
                路由地址
              </span>
              <el-input v-model="form.url" placeholder="请输入路由地址" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

import { listTree, getTree, delTree, addTree, updateTree } from '@/api/demo/tree'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import IconSelect from '@/components/IconSelect'

export default {
  name: 'Tree',
  components: {
    Treeselect, IconSelect
  },
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 测试树表表格数据
      treeList: [],
      // 测试单个节点下是否还有子节点
      childrenList: [],
      // 测试树表树选项
      treeOptions: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 是否展开，默认全部展开
      isExpandAll: false,
      // 重新渲染表格状态
      refreshTable: true,
      // 创建时间时间范围
      daterangeCreateTime: [],
      // 查询参数
      queryParams: {
        treeName: null,
        createTime: null
      },
      // 表单参数
      form: { },
      // 表单校验
      rules: {
        nodeName: [
          { required: true, message: '菜单名称不能为空', trigger: 'blur' }
        ],
        nodeCode: [
          { required: true, message: '菜单顺序不能为空', trigger: 'blur' }
        ],
        url: [
          { required: true, message: '路由地址不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 选择图标
    selected(name) {
      this.form.icon = name
    },
    /** 查询测试树表列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      if (this.daterangeCreateTime != null && this.daterangeCreateTime !== '') {
        this.queryParams.params['beginCreateTime'] = this.daterangeCreateTime[0]
        this.queryParams.params['endCreateTime'] = this.daterangeCreateTime[1]
      }
      listTree(this.queryParams).then(response => {
        this.treeList = this.handleTree(response.data, 'nodeId')
        this.loading = false
      })
    },
    /** 转换测试树表数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children
      }
      return {
        id: node.nodeId,
        label: node.nodeName,
        children: node.children
      }
    },
    /** 查询测试树表下拉树结构 */
    getTreeselect() {
      listTree().then(response => {
        this.treeOptions = []
        const data = { nodeId: 0, nodeName: '顶级节点', children: [] }
        data.children = this.handleTree(response.data, 'nodeId', 'parentId')
        this.treeOptions.push(data)
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        nodeId: undefined,
        parentId: 0,
        icon: null,
        nodeType: null,
        nodeName: null,
        url: '',
        isFrame: '1'
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeCreateTime = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset()
      this.getTreeselect()
      if (row != null && row.nodeId) {
        this.form.parentId = row.nodeId
      } else {
        this.form.parentId = 0
      }
      this.open = true
      this.title = '添加菜单'
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false
      this.isExpandAll = !this.isExpandAll
      this.$nextTick(() => {
        this.refreshTable = true
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true
      this.reset()
      this.getTreeselect()
      if (row != null) {
        this.form.parentId = row.nodeId
      }
      getTree(row.nodeId).then(response => {
        this.loading = false
        this.form = response.data
        this.open = true
        this.title = '修改菜单'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.buttonLoading = true
          if (this.form.nodeId !== undefined) {
            updateTree(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            }).finally(() => {
              this.buttonLoading = false
            })
          } else {
            addTree(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
              this.getList()
            }).catch(
              () => {}
            ).finally(() => {
              this.buttonLoading = false
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$modal.confirm('是否确认删除测试树表编号为"' + row.nodeId + '"的数据项？').then(() => {
        this.loading = true
        return delTree(row.nodeId)
      }).then(() => {
        this.loading = false
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(
        () => {}
      ).finally(() => {
        this.loading = false
      })
    }
  }
}
</script>
