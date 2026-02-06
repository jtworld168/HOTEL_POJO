<template>
  <div class="order-page">
    <Header />
    
    <div class="container">
      <el-card>
        <template #header>
          <div class="card-header">
            <span>{{ $t('order.title') }}</span>
          </div>
        </template>
        
        <div v-if="orders.length === 0" class="empty-orders">
          <el-empty :description="$t('order.empty')" />
        </div>
        
        <div v-else v-loading="loading">
          <el-table :data="orders" style="width: 100%">
            <el-table-column prop="orderNo" :label="$t('order.orderNo')" width="200" />
            
            <el-table-column :label="$t('order.totalAmount')" width="150">
              <template #default="{ row }">
                <span class="amount">¥{{ row.totalAmount.toFixed(2) }}</span>
              </template>
            </el-table-column>
            
            <el-table-column :label="$t('order.status')" width="120">
              <template #default="{ row }">
                <el-tag :type="getStatusType(row.status)">
                  {{ getStatusText(row.status) }}
                </el-tag>
              </template>
            </el-table-column>
            
            <el-table-column prop="createTime" :label="$t('order.createTime')" width="180" />
            
            <el-table-column :label="$t('order.detail')">
              <template #default="{ row }">
                <el-popover placement="left" :width="400" trigger="click">
                  <template #reference>
                    <el-button text type="primary">{{ $t('order.detail') }}</el-button>
                  </template>
                  <div class="order-items">
                    <div v-for="(item, index) in row.items" :key="index" class="order-item">
                      <span>{{ item.productName }}</span>
                      <span>x{{ item.quantity }}</span>
                      <span class="item-price">¥{{ item.price.toFixed(2) }}</span>
                    </div>
                  </div>
                </el-popover>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
import Header from '@/components/Header.vue'
import { getOrderList, type Order } from '@/api/order'

const { t } = useI18n()
const orders = ref<Order[]>([])
const loading = ref(false)

onMounted(async () => {
  await loadOrders()
})

const loadOrders = async () => {
  loading.value = true
  try {
    const { data } = await getOrderList()
    orders.value = data.data
  } catch (error) {
    console.error('加载订单失败', error)
  } finally {
    loading.value = false
  }
}

const getStatusType = (status: string) => {
  const typeMap: Record<string, any> = {
    pending: 'warning',
    paid: 'success',
    completed: 'info',
    cancelled: 'danger',
  }
  return typeMap[status] || 'info'
}

const getStatusText = (status: string) => {
  const textMap: Record<string, string> = {
    pending: t('order.statusPending'),
    paid: t('order.statusPaid'),
    completed: t('order.statusCompleted'),
    cancelled: t('order.statusCancelled'),
  }
  return textMap[status] || status
}
</script>

<style scoped>
.order-page {
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

.empty-orders {
  padding: 60px 0;
}

.amount {
  font-weight: bold;
  color: #f56c6c;
}

.order-items {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.order-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  background: #f5f7fa;
  border-radius: 4px;
}

.item-price {
  color: #f56c6c;
  font-weight: bold;
}
</style>
