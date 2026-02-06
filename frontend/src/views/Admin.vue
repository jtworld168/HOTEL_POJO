<template>
  <div class="admin-page">
    <Header />
    
    <div class="container">
      <el-card>
        <template #header>
          <div class="card-header">
            <span>管理后台</span>
          </div>
        </template>
        
        <el-tabs v-model="activeTab">
          <el-tab-pane label="订单管理" name="orders">
            <div class="section">
              <h3>订单列表</h3>
              <el-table :data="orders" style="width: 100%">
                <el-table-column prop="orderNo" label="订单号" width="200" />
                <el-table-column prop="totalAmount" label="金额" width="120">
                  <template #default="{ row }">
                    ¥{{ row.totalAmount.toFixed(2) }}
                  </template>
                </el-table-column>
                <el-table-column prop="status" label="状态" width="120" />
                <el-table-column prop="createTime" label="创建时间" />
              </el-table>
            </div>
          </el-tab-pane>
          
          <el-tab-pane label="商品管理" name="products">
            <div class="section">
              <h3>商品管理</h3>
              <p>商品管理功能开发中...</p>
            </div>
          </el-tab-pane>
          
          <el-tab-pane label="用户管理" name="users">
            <div class="section">
              <h3>用户管理</h3>
              <p>用户管理功能开发中...</p>
            </div>
          </el-tab-pane>
          
          <el-tab-pane label="优惠券管理" name="coupons">
            <div class="section">
              <h3>优惠券管理</h3>
              <p>优惠券管理功能开发中...</p>
            </div>
          </el-tab-pane>
        </el-tabs>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import Header from '@/components/Header.vue'
import { getOrderList, type Order } from '@/api/order'

const activeTab = ref('orders')
const orders = ref<Order[]>([])

onMounted(async () => {
  await loadOrders()
})

const loadOrders = async () => {
  try {
    const { data } = await getOrderList()
    orders.value = data.data
  } catch (error) {
    console.error('加载订单失败', error)
  }
}
</script>

<style scoped>
.admin-page {
  min-height: 100vh;
  background: #f5f7fa;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.card-header {
  font-size: 20px;
  font-weight: bold;
}

.section {
  padding: 20px 0;
}

.section h3 {
  margin-bottom: 20px;
  font-size: 18px;
  color: #303133;
}
</style>
