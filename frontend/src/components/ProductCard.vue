<template>
  <el-card class="product-card" :body-style="{ padding: '0px' }" shadow="hover" @click="goToDetail">
    <div class="image-wrapper">
      <img :src="product.imageUrl || defaultImage" class="image" />
      <div v-if="product.stock === 0" class="out-of-stock">{{ $t('product.outOfStock') }}</div>
    </div>
    <div class="content">
      <div class="name">{{ product.name }}</div>
      <div class="price-container">
        <span v-if="showEmployeePrice" class="employee-price">
          ¥{{ product.employeePrice?.toFixed(2) ?? '0.00' }}
        </span>
        <span :class="{ 'original-price': showEmployeePrice, 'price': !showEmployeePrice }">
          ¥{{ product.price?.toFixed(2) ?? '0.00' }}
        </span>
      </div>
      <div class="bottom">
        <span class="category">{{ product.category }}</span>
        <el-button 
          type="primary" 
          size="small" 
          :disabled="product.stock === 0"
          @click.stop="addToCart"
        >
          {{ $t('product.addToCart') }}
        </el-button>
      </div>
    </div>
  </el-card>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import type { Product } from '@/api/product'
import { useCartStore } from '@/store/cart'
import { useUserStore } from '@/store/user'

interface Props {
  product: Product
}

const props = defineProps<Props>()
const router = useRouter()
const cartStore = useCartStore()
const userStore = useUserStore()

const defaultImage = 'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg'

const showEmployeePrice = computed(() => {
  return userStore.isEmployee() && props.product.employeePrice
})

const goToDetail = () => {
  router.push(`/product/${props.product.id}`)
}

const addToCart = () => {
  cartStore.addToCart({
    productId: props.product.id,
    quantity: 1,
  })
}
</script>

<style scoped>
.product-card {
  cursor: pointer;
  transition: transform 0.2s;
}

.product-card:hover {
  transform: translateY(-5px);
}

.image-wrapper {
  position: relative;
  height: 200px;
  overflow: hidden;
}

.image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.out-of-stock {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: rgba(0, 0, 0, 0.7);
  color: #fff;
  padding: 10px 20px;
  border-radius: 4px;
  font-size: 16px;
}

.content {
  padding: 14px;
}

.name {
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 10px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.price-container {
  margin-bottom: 10px;
}

.price {
  font-size: 20px;
  font-weight: bold;
  color: #f56c6c;
}

.employee-price {
  font-size: 20px;
  font-weight: bold;
  color: #67c23a;
  margin-right: 10px;
}

.original-price {
  font-size: 14px;
  color: #909399;
  text-decoration: line-through;
}

.bottom {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.category {
  font-size: 12px;
  color: #909399;
}
</style>
