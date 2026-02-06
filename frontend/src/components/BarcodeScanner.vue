<template>
  <el-dialog v-model="visible" :title="$t('scanner.title')" width="600px" @close="stopScanning">
    <div class="scanner-container">
      <video ref="videoRef" autoplay></video>
      <div v-if="isScanning" class="scanning-indicator">
        {{ $t('scanner.scanning') }}
      </div>
    </div>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, watch, onUnmounted } from 'vue'
import { BrowserMultiFormatReader } from '@zxing/browser'
import { ElMessage } from 'element-plus'
import { getProductByBarcode } from '@/api/product'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'

interface Props {
  visible: boolean
}

interface Emits {
  (e: 'update:visible', value: boolean): void
  (e: 'detected', barcode: string): void
}

const props = defineProps<Props>()
const emit = defineEmits<Emits>()
const router = useRouter()
const { t } = useI18n()

const videoRef = ref<HTMLVideoElement>()
const isScanning = ref(false)
const codeReader = new BrowserMultiFormatReader()

const visible = ref(props.visible)

watch(() => props.visible, (newVal) => {
  visible.value = newVal
  if (newVal) {
    startScanning()
  } else {
    stopScanning()
  }
})

watch(visible, (newVal) => {
  emit('update:visible', newVal)
})

const startScanning = async () => {
  try {
    isScanning.value = true
    const videoInputDevices = await BrowserMultiFormatReader.listVideoInputDevices()
    
    if (videoInputDevices.length === 0) {
      ElMessage.error('未检测到摄像头')
      return
    }

    const selectedDeviceId = videoInputDevices[0].deviceId

    await codeReader.decodeFromVideoDevice(
      selectedDeviceId,
      videoRef.value,
      async (result, error) => {
        if (result) {
          const barcode = result.getText()
          emit('detected', barcode)
          
          try {
            const { data } = await getProductByBarcode(barcode)
            if (data.data) {
              router.push(`/product/${data.data.id}`)
              visible.value = false
            }
          } catch (err) {
            ElMessage.error(t('scanner.notFound'))
          }
        }
        if (error && error.name !== 'NotFoundException') {
          console.error(error)
        }
      }
    )
  } catch (error) {
    console.error('启动扫描失败', error)
    ElMessage.error('启动摄像头失败')
  }
}

const stopScanning = () => {
  isScanning.value = false
  try {
    if (videoRef.value && videoRef.value.srcObject) {
      const stream = videoRef.value.srcObject as MediaStream
      stream.getTracks().forEach(track => track.stop())
    }
  } catch (error) {
    console.error('停止扫描失败', error)
  }
}

onUnmounted(() => {
  stopScanning()
})
</script>

<style scoped>
.scanner-container {
  position: relative;
  width: 100%;
  height: 400px;
  background: #000;
  display: flex;
  align-items: center;
  justify-content: center;
}

video {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.scanning-indicator {
  position: absolute;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  background: rgba(0, 0, 0, 0.7);
  color: #fff;
  padding: 10px 20px;
  border-radius: 4px;
}
</style>
