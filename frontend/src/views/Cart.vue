<template>
  <div class="cart-page">
    <Header />
    
    <div class="container">
      <el-card>
        <template #header>
          <div class="card-header">
            <span>{{ $t('cart.title') }}</span>
            <el-button 
              v-if="cartStore.cartItems.length > 0" 
              type="danger" 
              text
              @click="handleClearCart"
            >
              {{ $t('cart.clear') }}
            </el-button>
          </div>
        </template>
        
        <div v-if="cartStore.cartItems.length === 0" class="empty-cart">
          <el-empty :description="$t('cart.empty')" />
        </div>
        
        <div v-else>
          <el-table :data="cartStore.cartItems" style="width: 100%">
            <el-table-column prop="productName" :label="$t('cart.product')" />
            
            <el-table-column :label="$t('cart.price')">
              <template #default="{ row }">
                <span v-if="showEmployeePrice(row)" class="employee-price">
                  ¥{{ row.employeePrice?.toFixed(2) }}
                </span>
                <span v-else class="price">
                  ¥{{ row.price.toFixed(2) }}
                </span>
              </template>
            </el-table-column>
            
            <el-table-column :label="$t('cart.quantity')">
              <template #default="{ row }">
                <el-input-number
                  :model-value="row.quantity"
                  :min="1"
                  size="small"
                  @change="(value: number) => handleUpdateQuantity(row.id, value)"
                />
              </template>
            </el-table-column>
            
            <el-table-column :label="$t('cart.total')">
              <template #default="{ row }">
                <span class="subtotal">
                  ¥{{ calculateSubtotal(row).toFixed(2) }}
                </span>
              </template>
            </el-table-column>
            
            <el-table-column :label="$t('common.delete')" width="100">
              <template #default="{ row }">
                <el-button type="danger" text @click="handleRemove(row.id)">
                  {{ $t('cart.remove') }}
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          
          <div class="cart-footer">
            <div class="total-section">
              <span class="total-label">{{ $t('cart.totalAmount') }}：</span>
              <span class="total-amount">¥{{ cartStore.totalAmount.toFixed(2) }}</span>
            </div>
            <el-button type="primary" size="large" @click="handleCheckout">
              {{ $t('cart.checkout') }}
            </el-button>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { ElMessageBox, ElMessage } from 'element-plus'
import Header from '@/components/Header.vue'
import { useCartStore } from '@/store/cart'
import { useUserStore } from '@/store/user'
import { createOrder } from '@/api/order'
import type { CartItem } from '@/api/cart'

const router = useRouter()
const { t } = useI18n()
const cartStore = useCartStore()
const userStore = useUserStore()

onMounted(() => {
  cartStore.getCartList()
})

const showEmployeePrice = (item: CartItem) => {
  return userStore.isEmployee() && item.employeePrice
}

const calculateSubtotal = (item: CartItem) => {
  const price = showEmployeePrice(item) ? item.employeePrice! : item.price
  return price * item.quantity
}

const handleUpdateQuantity = async (id: number, quantity: number) => {
  await cartStore.updateCart({ id, quantity })
}

const handleRemove = async (id: number) => {
  await cartStore.removeFromCart(id)
}

const handleClearCart = async () => {
  try {
    await ElMessageBox.confirm(
      t('cart.confirmClear'),
      t('common.confirm'),
      {
        confirmButtonText: t('common.confirm'),
        cancelButtonText: t('common.cancel'),
        type: 'warning',
      }
    )
    await cartStore.clearCart()
  } catch (error) {
    // 用户取消
  }
}

const handleCheckout = async () => {
  try {
    await createOrder({})
    ElMessage.success('订单创建成功')
    await cartStore.clearCart()
    router.push('/order')
  } catch (error) {
    console.error('创建订单失败', error)
  }
}
</script>

<style scoped>
.cart-page {
  min-height: 100vh;
  background: #f5f7fa;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 20px;
  font-weight: bold;
}

.empty-cart {
  padding: 60px 0;
}

.employee-price {
  color: #67c23a;
  font-weight: bold;
}

.price {
  color: #f56c6c;
  font-weight: bold;
}

.subtotal {
  font-weight: bold;
}

.cart-footer {
  margin-top: 30px;
  padding-top: 20px;
  border-top: 2px solid #ebeef5;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.total-section {
  display: flex;
  align-items: center;
}

.total-label {
  font-size: 18px;
  color: #606266;
  margin-right: 10px;
}

.total-amount {
  font-size: 28px;
  font-weight: bold;
  color: #f56c6c;
}
</style>
