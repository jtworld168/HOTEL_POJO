<template>
  <div class="coupon-page">
    <Header />
    
    <div class="container">
      <el-card>
        <template #header>
          <div class="card-header">
            <span>{{ $t('coupon.title') }}</span>
          </div>
        </template>
        
        <el-tabs v-model="activeTab">
          <el-tab-pane :label="$t('coupon.title')" name="all">
            <div class="coupons-grid" v-loading="loading">
              <div v-for="coupon in coupons" :key="coupon.id" class="coupon-card">
                <div class="coupon-left">
                  <div class="discount-value">
                    <span v-if="coupon.discountType === 'percentage'">
                      {{ coupon.discountValue }}折
                    </span>
                    <span v-else>
                      ¥{{ coupon.discountValue }}
                    </span>
                  </div>
                  <div class="min-amount">
                    满¥{{ coupon.minAmount }}可用
                  </div>
                </div>
                
                <div class="coupon-middle">
                  <div class="coupon-name">{{ coupon.name }}</div>
                  <div class="coupon-desc">{{ coupon.description }}</div>
                  <div class="coupon-time">
                    {{ formatDate(coupon.startTime) }} - {{ formatDate(coupon.endTime) }}
                  </div>
                </div>
                
                <div class="coupon-right">
                  <el-button 
                    v-if="!coupon.isClaimed" 
                    type="primary" 
                    @click="handleClaim(coupon.id)"
                  >
                    {{ $t('coupon.claim') }}
                  </el-button>
                  <el-tag v-else type="success">{{ $t('coupon.claimed') }}</el-tag>
                </div>
              </div>
            </div>
            
            <el-empty v-if="!loading && coupons.length === 0" :description="$t('coupon.empty')" />
          </el-tab-pane>
          
          <el-tab-pane :label="$t('coupon.myCoupons')" name="my">
            <div class="coupons-grid" v-loading="loadingMy">
              <div v-for="coupon in myCoupons" :key="coupon.id" class="coupon-card">
                <div class="coupon-left">
                  <div class="discount-value">
                    <span v-if="coupon.discountType === 'percentage'">
                      {{ coupon.discountValue }}折
                    </span>
                    <span v-else>
                      ¥{{ coupon.discountValue }}
                    </span>
                  </div>
                  <div class="min-amount">
                    满¥{{ coupon.minAmount }}可用
                  </div>
                </div>
                
                <div class="coupon-middle">
                  <div class="coupon-name">{{ coupon.name }}</div>
                  <div class="coupon-desc">{{ coupon.description }}</div>
                  <div class="coupon-time">
                    {{ formatDate(coupon.startTime) }} - {{ formatDate(coupon.endTime) }}
                  </div>
                </div>
                
                <div class="coupon-right">
                  <el-tag type="info">{{ $t('coupon.available') }}</el-tag>
                </div>
              </div>
            </div>
            
            <el-empty v-if="!loadingMy && myCoupons.length === 0" :description="$t('coupon.empty')" />
          </el-tab-pane>
        </el-tabs>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import Header from '@/components/Header.vue'
import { getCouponList, getUserCoupons, claimCoupon, type Coupon } from '@/api/coupon'

const activeTab = ref('all')
const coupons = ref<Coupon[]>([])
const myCoupons = ref<Coupon[]>([])
const loading = ref(false)
const loadingMy = ref(false)

onMounted(async () => {
  await loadCoupons()
  await loadMyCoupons()
})

const loadCoupons = async () => {
  loading.value = true
  try {
    const { data } = await getCouponList()
    coupons.value = data.data
  } catch (error) {
    console.error('加载优惠券失败', error)
  } finally {
    loading.value = false
  }
}

const loadMyCoupons = async () => {
  loadingMy.value = true
  try {
    const { data } = await getUserCoupons()
    myCoupons.value = data.data
  } catch (error) {
    console.error('加载我的优惠券失败', error)
  } finally {
    loadingMy.value = false
  }
}

const handleClaim = async (id: number) => {
  try {
    await claimCoupon(id)
    ElMessage.success('领取成功')
    await loadCoupons()
    await loadMyCoupons()
  } catch (error) {
    console.error('领取优惠券失败', error)
  }
}

const formatDate = (dateStr: string) => {
  const date = new Date(dateStr)
  return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`
}
</script>

<style scoped>
.coupon-page {
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

.coupons-grid {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.coupon-card {
  display: flex;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 8px;
  padding: 20px;
  color: #fff;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.coupon-left {
  flex-shrink: 0;
  width: 120px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border-right: 2px dashed rgba(255, 255, 255, 0.3);
  padding-right: 20px;
}

.discount-value {
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 10px;
}

.min-amount {
  font-size: 14px;
  opacity: 0.9;
}

.coupon-middle {
  flex: 1;
  padding: 0 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.coupon-name {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 5px;
}

.coupon-desc {
  font-size: 14px;
  opacity: 0.9;
  margin-bottom: 10px;
}

.coupon-time {
  font-size: 12px;
  opacity: 0.8;
}

.coupon-right {
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  padding-left: 20px;
}
</style>
