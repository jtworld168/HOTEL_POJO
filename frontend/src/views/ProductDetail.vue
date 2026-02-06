<template>
  <div class="product-detail-page">
    <Header />
    
    <div class="container" v-loading="loading">
      <el-card v-if="product">
        <div class="product-detail">
          <div class="product-image">
            <img :src="product.imageUrl || defaultImage" :alt="product.name" />
          </div>
          
          <div class="product-info">
            <h1 class="product-name">{{ product.name }}</h1>
            
            <div class="price-section">
              <div v-if="showEmployeePrice" class="employee-price-container">
                <span class="label">{{ $t('product.employeePrice') }}：</span>
                <span class="employee-price">¥{{ product.employeePrice?.toFixed(2) ?? '0.00' }}</span>
              </div>
              <div class="price-container">
                <span class="label">{{ $t('product.price') }}：</span>
                <span :class="{ 'original-price': showEmployeePrice, 'price': !showEmployeePrice }">
                  ¥{{ product.price?.toFixed(2) ?? '0.00' }}
                </span>
              </div>
            </div>
            
            <div class="info-item">
              <span class="label">{{ $t('product.stock') }}：</span>
              <span>{{ product.stock }}</span>
            </div>
            
            <div class="info-item">
              <span class="label">{{ $t('product.category') }}：</span>
              <span>{{ product.category }}</span>
            </div>
            
            <div v-if="product.barcode" class="info-item">
              <span class="label">{{ $t('product.barcode') }}：</span>
              <span>{{ product.barcode }}</span>
            </div>
            
            <div class="description">
              <div class="label">{{ $t('product.description') }}：</div>
              <p>{{ product.description }}</p>
            </div>
            
            <div class="quantity-selector">
              <span class="label">{{ $t('product.quantity') }}：</span>
              <el-input-number v-model="quantity" :min="1" :max="product.stock" />
            </div>
            
            <div class="action-buttons">
              <el-button 
                type="primary" 
                size="large" 
                :disabled="product.stock === 0"
                @click="addToCart"
              >
                {{ $t('product.addToCart') }}
              </el-button>
              <el-button 
                size="large" 
                :disabled="product.stock === 0"
                @click="buyNow"
              >
                {{ $t('product.buyNow') }}
              </el-button>
            </div>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import Header from '@/components/Header.vue'
import { getProduct, type Product } from '@/api/product'
import { useCartStore } from '@/store/cart'
import { useUserStore } from '@/store/user'

const route = useRoute()
const router = useRouter()
const cartStore = useCartStore()
const userStore = useUserStore()

const product = ref<Product>()
const quantity = ref(1)
const loading = ref(false)

const defaultImage = 'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg'

const showEmployeePrice = computed(() => {
  return userStore.isEmployee() && product.value?.employeePrice
})

onMounted(async () => {
  await loadProduct()
})

const loadProduct = async () => {
  loading.value = true
  try {
    const id = Number(route.params.id)
    const { data } = await getProduct(id)
    product.value = data.data
  } catch (error) {
    console.error('加载商品失败', error)
  } finally {
    loading.value = false
  }
}

const addToCart = async () => {
  if (!product.value) return
  await cartStore.addToCart({
    productId: product.value.id,
    quantity: quantity.value,
  })
}

const buyNow = async () => {
  await addToCart()
  router.push('/cart')
}
</script>

<style scoped>
.product-detail-page {
  min-height: 100vh;
  background: #f5f7fa;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.product-detail {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 40px;
}

.product-image {
  width: 100%;
}

.product-image img {
  width: 100%;
  border-radius: 8px;
}

.product-info {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.product-name {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
}

.price-section {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.employee-price-container,
.price-container {
  display: flex;
  align-items: center;
}

.employee-price {
  font-size: 32px;
  font-weight: bold;
  color: #67c23a;
}

.price {
  font-size: 32px;
  font-weight: bold;
  color: #f56c6c;
}

.original-price {
  font-size: 20px;
  color: #909399;
  text-decoration: line-through;
}

.info-item {
  display: flex;
  align-items: center;
  font-size: 16px;
}

.label {
  color: #606266;
  margin-right: 10px;
}

.description {
  padding-top: 20px;
  border-top: 1px solid #ebeef5;
}

.description p {
  color: #606266;
  line-height: 1.6;
  margin-top: 10px;
}

.quantity-selector {
  display: flex;
  align-items: center;
}

.action-buttons {
  display: flex;
  gap: 20px;
}

.action-buttons .el-button {
  flex: 1;
}
</style>
