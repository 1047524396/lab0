<template>
  <div>
    <!-- 组件列表页 -->
    <div>
      <h2>组件列表</h2>
      <input v-model="searchText" placeholder="搜索组件名称">
      <button @click="search">搜索</button>
      <table>
        <thead>
          <tr>
            <th @click="sort('id')">ID</th>
            <th @click="sort('name')">名称</th>
            <th @click="sort('version')">版本</th>
            <th @click="sort('supplier')">供应商</th>
            <th @click="sort('packageManager')">包管理器</th>
            <th @click="sort('createdAt')">创建时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="component in filteredComponents" :key="component.id">
            <td>{{ component.id }}</td>
            <td>{{ component.name }}</td>
            <td>{{ component.version }}</td>
            <td>{{ component.supplier }}</td>
            <td>{{ component.packageManager }}</td>
            <td>{{ component.createdAt }}</td>
            <td>
              <button @click="deleteComponent(component.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
      <button @click="openCreateDialog">新建组件</button>
    </div>

    <!-- 列表详情页 -->
    <div v-if="selectedComponent">
      <h2>组件详情</h2>
      <ul>
        <li>ID: {{ selectedComponent.id }}</li>
        <li>名称: {{ selectedComponent.name }}</li>
        <li>版本: {{ selectedComponent.version }}</li>
        <li>供应商: {{ selectedComponent.supplier }}</li>
        <li>包管理器: {{ selectedComponent.packageManager }}</li>
        <li>创建时间: {{ selectedComponent.createdAt }}</li>
      </ul>
    </div>

    <!-- 新建组件页面弹出对话框 -->
    <div v-if="showCreateDialog">
      <h2>新建组件</h2>
      <input v-model="newComponent.name" placeholder="名称">
      <input v-model="newComponent.version" placeholder="版本">
      <input v-model="newComponent.supplier" placeholder="供应商">
      <input v-model="newComponent.packageManager" placeholder="包管理器">
      <button @click="createComponent">确认</button>
      <button @click="cancelCreate">取消</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      components: [], // 组件列表数据
      searchText: '', // 搜索关键字
      selectedComponent: null, // 选中的组件
      showCreateDialog: false, // 是否显示新建组件对话框
      newComponent: { // 新建组件表单数据
        name: '',
        version: '',
        supplier: '',
        packageManager: '',
      },
    };
  },
  computed: {
    filteredComponents() {
      // 根据搜索关键字筛选组件列表
      if (this.searchText) {
        const keyword = this.searchText.toLowerCase();
        return this.components.filter(component =>
          component.name.toLowerCase().includes(keyword)
        );
      } else {
        return this.components;
      }
    },
  },
  methods: {
    // 获取组件列表数据（示例：从后端API获取数据）
    fetchComponents() {
      axios.get('/api/components')
        .then(response => {
          this.components = response.data;
        })
        .catch(error => {
          console.error(error);
        });
    },
    // 删除指定组件（示例：向后端API发送删除请求）
    deleteComponent(componentId) {
      axios.delete(`/api/components/${componentId}`)
        .then(() => {
          this.fetchComponents(); // 更新组件列表数据
        })
        .catch(error => {
          console.error(error);
        });
    },
    // 打开新建组件对话框
    openCreateDialog() {
      this.showCreateDialog = true;
    },
    // 创建组件（示例：向后端API发送创建请求）
    createComponent() {
      axios.post('/api/components', this.newComponent)
        .then(() => {
          this.fetchComponents(); // 更新组件列表数据
          this.showCreateDialog = false; // 关闭对话框
          this.newComponent = { // 清空表单数据
            name: '',
            version: '',
            supplier: '',
            packageManager: '',
          };
        })
        .catch(error => {
          console.error(error);
        });
    },
    // 取消创建组件
    cancelCreate() {
      this.showCreateDialog = false;
      this.newComponent = {
        name: '',
        version: '',
        supplier: '',
        packageManager: '',
      };
    },
    // 排序组件列表
    sort(field) {
      axios.get(`/api/components?sortBy=${field}`)
        .then(response => {
          this.components = response.data;
        })
        .catch(error => {
          console.error(error);
        });
    },
    // 搜索组件
    search() {
      this.fetchComponents();
    },
  },
  mounted() {
    this.fetchComponents(); // 在组件加载时获取组件列表数据
  },
};
</script>
