import CryptoJS from 'crypto-js'

export const md5 = (message: string): string => {
  return CryptoJS.MD5(message).toString()
}
