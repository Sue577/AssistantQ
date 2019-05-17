<template>
  <div>

    <Card>
      <Button style="margin: 10px;" type="primary" @click="exportExcel">新增</Button>
      <Button style="margin: 10px;" icon="ios-cloud-upload-outline" :loading="uploadLoading" @click="handleUploadFile">导入</Button>
      <Button style="margin: 10px;" icon="md-download" :loading="exportLoading" @click="exportExcel">导出</Button>
      <tables ref="tables" editable searchable search-place="top" v-model="tableData" :columns="columns" @on-delete="handleDelete"/>
    </Card>
  </div>
</template>

<script>
import excel from '@/libs/excel'
import Tables from '_c/tables'
import { getTableData } from '@/api/data'
export default {
  name: 'tables_page',
  components: {
    Tables
  },
  data () {
    return {
      columns: [
        { title: '用户ID', key: 'name', sortable: true },
        { title: '用户姓名', key: 'name', editable: true,sortable: true},
        { title: '用户邮箱', key: 'email', editable: true,sortable: true },
        { title: '手机号码', key: 'email', editable: true,sortable: true },
        { title: '用户类型', key: 'email', editable: true,sortable: true },
        { title: '创建时间', key: 'createTime',sortable: true },
        {
          title: 'Handle',
          key: 'handle',
          options: ['delete'],
          // button: [
          //   (h, params, vm) => {
          //     return h('Poptip', {
          //       props: {
          //         confirm: true,
          //         title: '你确定要删除吗?'
          //       },
          //       on: {
          //         'on-ok': () => {
          //           vm.$emit('on-delete', params)
          //           vm.$emit('input', params.tableData.filter((item, index) => index !== params.row.initRowIndex))
          //         }
          //       }
          //     }, [
          //       h('Button', '自定义按钮')
          //     ])
          //   }
          // ]
        }
      ],
      tableData: []
    }
  },
  methods: {
    initUpload () {
      this.file = null
      this.showProgress = false
      this.loadingProgress = 0
      this.tableData = []
      this.tableTitle = []
    },
    handleUploadFile () {
      this.initUpload()
    },
    handleDelete (params) {
      console.log(params)
    },
    exportExcel () {
      if (this.tableData.length) {
        this.exportLoading = true
        const params = {
          title: ['一级分类', '二级分类', '三级分类'],
          key: ['category1', 'category2', 'category3'],
          data: this.tableData,
          autoWidth: true,
          filename: '分类列表'
        }
        excel.export_array_to_excel(params)
        this.exportLoading = false
      } else {
        this.$Message.info('表格数据不能为空！')
      }
    }
  },
  mounted () {
    getTableData().then(res => {
      this.tableData = res.data
    })
  }
}
</script>

<style>

</style>
