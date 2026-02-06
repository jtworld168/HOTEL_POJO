<template>
  <div class="home-page">
    <Header />
    
    <div class="container">
      <div class="banner-section">
        <el-carousel height="400px" indicator-position="outside">
          <el-carousel-item v-for="banner in banners" :key="banner.id">
            <img :src="banner.imageUrl" :alt="banner.title" class="banner-image" />
          </el-carousel-item>
        </el-carousel>
      </div>

      <div class="welcome-section">
        <h2>{{ $t('home.welcome') }}</h2>
        <p v-if="userStore.userInfo">{{ $t('home.welcomeBack') }}，{{ userStore.userInfo.username }}！</p>
      </div>

      <div class="products-section">
        <div class="section-header">
          <h3>{{ $t('home.allProducts') }}</h3>
        </div>
        
        <div class="products-grid" v-loading="loading">
          <ProductCard v-for="product in products" :key="product.id" :product="product" />
        </div>
        
        <el-empty v-if="!loading && products.length === 0" :description="$t('home.emptyProducts')" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import Header from '@/components/Header.vue'
import ProductCard from '@/components/ProductCard.vue'
import { getProductList, type Product } from '@/api/product'
import { getBannerList, type Banner } from '@/api/banner'
import { useUserStore } from '@/store/user'
import { useCartStore } from '@/store/cart'

const userStore = useUserStore()
const cartStore = useCartStore()
const products = ref<Product[]>([])
const banners = ref<Banner[]>([])
const loading = ref(false)

onMounted(async () => {
  await loadData()
  cartStore.getCartList()
})

const loadData = async () => {
  loading.value = true
  try {
    const [productsRes, bannersRes] = await Promise.all([
      getProductList(),
      getBannerList(),
    ])
    products.value = productsRes.data.data
    banners.value = bannersRes.data.data
  } catch (error) {
    console.error('加载数据失败', error)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.home-page {
  min-height: 100vh;
  background: #f5f7fa;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.banner-section {
  margin-bottom: 30px;
  border-radius: 8px;
  overflow: hidden;
}

.banner-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.welcome-section {
  text-align: center;
  margin-bottom: 30px;
  padding: 20px;
  background: #fff;
  border-radius: 8px;
}

.welcome-section h2 {
  font-size: 28px;
  margin-bottom: 10px;
  color: #303133;
}

.welcome-section p {
  font-size: 16px;
  color: #606266;
}

.products-section {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
}

.section-header {
  margin-bottom: 20px;
}

.section-header h3 {
  font-size: 24px;
  color: #303133;
}

.products-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 20px;
}
</style>
