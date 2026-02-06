<template>
  <div class="header">
    <div class="container">
      <div class="logo" @click="$router.push('/home')">
        <el-icon :size="28"><ShoppingBag /></el-icon>
        <span>{{ $t('header.title') }}</span>
      </div>

      <div class="nav">
        <el-button text @click="$router.push('/home')">{{ $t('header.home') }}</el-button>
        <el-button text @click="$router.push('/coupon')">{{ $t('header.coupons') }}</el-button>
        <el-button text @click="$router.push('/order')">{{ $t('header.orders') }}</el-button>
        <el-button v-if="userStore.userInfo?.role === 'admin'" text @click="$router.push('/admin')">
          {{ $t('header.admin') }}
        </el-button>
      </div>

      <div class="actions">
        <el-button @click="openScanner" circle>
          <el-icon><Camera /></el-icon>
        </el-button>

        <el-badge :value="cartStore.cartCount" :hidden="cartStore.cartCount === 0">
          <el-button @click="$router.push('/cart')" circle>
            <el-icon><ShoppingCart /></el-icon>
          </el-button>
        </el-badge>

        <el-dropdown trigger="click">
          <el-button circle>
            <el-icon><Switch /></el-icon>
          </el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="switchLanguage('zh')">中文</el-dropdown-item>
              <el-dropdown-item @click="switchLanguage('en')">English</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>

        <el-dropdown v-if="userStore.userInfo" trigger="click">
          <el-avatar :src="userStore.userInfo.avatar || defaultAvatar" />
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item disabled>{{ userStore.userInfo.username }}</el-dropdown-item>
              <el-dropdown-item divided @click="handleLogout">{{ $t('header.logout') }}</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>

    <BarcodeScanner v-model:visible="scannerVisible" @detected="handleBarcodeDetected" />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { useUserStore } from '@/store/user'
import { useCartStore } from '@/store/cart'
import { useAppStore } from '@/store/app'
import BarcodeScanner from './BarcodeScanner.vue'

const router = useRouter()
const { locale } = useI18n()
const userStore = useUserStore()
const cartStore = useCartStore()
const appStore = useAppStore()
const scannerVisible = ref(false)

const defaultAvatar = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'

const switchLanguage = (lang: string) => {
  locale.value = lang
  appStore.setLanguage(lang)
}

const handleLogout = () => {
  userStore.logout()
  router.push('/login')
}

const openScanner = () => {
  scannerVisible.value = true
}

const handleBarcodeDetected = (barcode: string) => {
  console.log('Barcode detected:', barcode)
  scannerVisible.value = false
}
</script>

<style scoped>
.header {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 18px;
  font-weight: bold;
  color: #409eff;
  cursor: pointer;
}

.nav {
  display: flex;
  gap: 10px;
}

.actions {
  display: flex;
  align-items: center;
  gap: 15px;
}

.el-avatar {
  cursor: pointer;
}
</style>
